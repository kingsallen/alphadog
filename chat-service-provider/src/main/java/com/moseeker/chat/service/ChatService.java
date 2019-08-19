package com.moseeker.chat.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.dao.hrdb.HrChatVoiceDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChat;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatVoice;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrChatUnreadCountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatListRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.chat.constant.ChatOrigin;
import com.moseeker.chat.constant.ChatSpeakerType;
import com.moseeker.chat.constant.ChatVoiceConstant;
import com.moseeker.chat.exception.VoiceErrorEnum;
import com.moseeker.chat.service.entity.ChatDao;
import com.moseeker.chat.service.entity.ChatFactory;
import com.moseeker.chat.service.entity.UserInfoVO;
import com.moseeker.chat.utils.*;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.*;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.chat.struct.*;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import org.joda.time.DateTime;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.moseeker.chat.constant.ChatVoiceConstant.*;

/**
 * Created by jack on 08/03/2017.
 */
@Service
@CounterIface
@Transactional
public class ChatService {

    Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatDao chatDao;

    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;

    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;

    @Autowired
    private UserHrAccountDao hrAccountDao;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Autowired
    private HrWxWechatDao wechatDao;

    @Autowired
    private HrChatVoiceDao hrChatVoiceDao;

    @Autowired
    private ChatFactory chatFactory;

    @Autowired
    private UserWxUserDao wxUserDao;

    @Autowired
    private UserUserDao userUserDao;

    private ThreadPool pool = ThreadPool.Instance;

    private static final String MOBOT_MULTI_WELCOME_SEPARATOR_TEXT = "#多条对话分隔符#";

    /**
     * HR查找聊天室列表
     * //todo hr_chat_unread_count的初始化脚本和定时更新脚本
     *
     * @param hrId     HR编号 user_hr_account.id
     * @param pageNo   页码
     * @param pageSize 每页信息数量
     * @return 聊天室分页信息
     */
    public HRChatRoomsVO listHRChatRoom(int hrId, int pageNo, int pageSize) {
        logger.info("listHRChatRoom hrId:{}, pageNo:{} pageSize:{}", hrId, pageNo, pageSize);
        HRChatRoomsVO rooms = new HRChatRoomsVO();
        int count = chatDao.countHRChatRoom(hrId);
        Page page = new Page(pageNo, pageSize, count);
        rooms.setPageNo(page.getPageNo());
        rooms.setPageSize(page.getPageSize());
        rooms.setTotalPage(page.getTotalPage());
        rooms.setTotalRow(page.getTotalRow());
        if (count > 0) {
            List<HrChatUnreadCountDO> chatUnreadCountDOlist = chatDao.listChatRoomUnreadCount(ChatSpeakerType.HR, hrId,
                    page.getPageNo(), page.getPageSize());
            if (chatUnreadCountDOlist != null && chatUnreadCountDOlist.size() > 0) {
                int[] roomIdArray = chatUnreadCountDOlist.stream().mapToInt(chatUnreadCountDO -> chatUnreadCountDO.getRoomId()).toArray();
                int[] userIdArray = chatUnreadCountDOlist.stream().mapToInt(chatUnreadCountDO -> chatUnreadCountDO.getUserId()).toArray();
                Future chatRoomsFuture = pool.startTast(() -> chatDao.listChatRoom(roomIdArray));
                Future usersFuture = pool.startTast(() -> chatDao.listUsers(userIdArray));

                /** 封装聊天室返回值 */
                List<HRChatRoomVO> roomVOList = new ArrayList<>();
                chatUnreadCountDOlist.forEach(chatUnreadCountDO -> {
                    HRChatRoomVO hrChatRoomVO = new HRChatRoomVO();
                    hrChatRoomVO.setId(chatUnreadCountDO.getRoomId());
                    hrChatRoomVO.setUserId(chatUnreadCountDO.getUserId());

                    List<HrWxHrChatListDO> chatRoomList = null;
                    List<UserUserDO> userList = null;
                    try {
                        chatRoomList = (List<HrWxHrChatListDO>) chatRoomsFuture.get();

                    } catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage(), e);
                    }
                    /** 匹配聊天室的创建时间和状态 */
                    if (chatRoomList != null && chatRoomList.size() > 0) {
                        Optional<HrWxHrChatListDO> chatRoomDOOptional = chatRoomList.stream()
                                .filter(chatRoom -> chatRoom.getId() == chatUnreadCountDO.getRoomId()).findFirst();
                        if (chatRoomDOOptional.isPresent()) {
                            hrChatRoomVO.setCreateTime(chatRoomDOOptional.get().getUpdateTime());
                            hrChatRoomVO.setUnReadNum(chatRoomDOOptional.get().getHrUnreadCount());
                        }
                    }
                    /** 匹配用户名称和头像 */
                    try {
                        userList = (List<UserUserDO>) usersFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage(), e);
                    }
                    /** 匹配C端用户的名称和头像 */
                    if (userList != null && userList.size() > 0) {
                        Optional<UserUserDO> userUserDOOptional = userList.stream()
                                .filter(userUserDO -> userUserDO.getId() == chatUnreadCountDO.getUserId()).findAny();
                        if (userUserDOOptional.isPresent()) {
                            hrChatRoomVO.setHeadImgUrl(userUserDOOptional.get().getHeadimg());
                            String name = StringUtils.isNotNullOrEmpty(userUserDOOptional.get().getName())
                                    ? userUserDOOptional.get().getName() : userUserDOOptional.get().getNickname();
                            hrChatRoomVO.setName(name);
                        }
                    }
                    roomVOList.add(hrChatRoomVO);
                });
                rooms.setRooms(roomVOList);
            }
        }

        logger.info("listHRChatRoom result : {}", rooms);
        return rooms;
    }

    public HRChatRoomsIndexVO listHRChatRoomByIndex(int hrId, String keyword, int roomId, boolean apply, int pageSize) {

        HRChatRoomsIndexVO hrChatRoomsIndexVO = new HRChatRoomsIndexVO();
        List<Integer> chatUserIdList = chatDao.fetchUserIdByHrId(hrId, apply);

        if (chatUserIdList.size() > 0) {

            List<Integer> userIdList;
            if (org.apache.commons.lang.StringUtils.isNotBlank(keyword)) {
                userIdList = chatDao.findUserIdByName(keyword, chatUserIdList);
            } else {
                userIdList = chatUserIdList;
            }

            if (userIdList.size() > 0) {
                Timestamp update = null;
                if (roomId > 0) {
                    HrChatUnreadCountRecord room = chatDao.fetchRoomById(roomId);
                    if (room != null) {
                        update = room.getUpdateTime();
                    }
                }

                int total = chatDao.countRoom(hrId, userIdList, apply);
                hrChatRoomsIndexVO.setTotalRow(total);
                List<HrChatUnreadCountRecord> roomList = chatDao.fetchRooms(hrId, userIdList, apply, update, pageSize);

                if (roomList.size() > 0) {

                    List<HRChatRoomVO> rooms = new ArrayList<>();

                    int[] roomIdArray = roomList.stream().mapToInt(chatUnreadCountDO -> chatUnreadCountDO.getRoomId()).toArray();
                    int[] userIdArray = roomList.stream().mapToInt(chatUnreadCountDO -> chatUnreadCountDO.getUserId()).toArray();

                    Future chatRoomsFuture = pool.startTast(() -> chatDao.listChatRoom(roomIdArray));
                    Future usersFuture = pool.startTast(() -> chatDao.listUsers(userIdArray));

                    List<HrWxHrChatListDO> chatRoomList = null;
                    List<UserUserDO> userList = null;
                    /** 匹配聊天室的创建时间和状态 */
                    try {
                        chatRoomList = (List<HrWxHrChatListDO>) chatRoomsFuture.get();

                    } catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage(), e);
                    }
                    /** 匹配用户名称和头像 */
                    try {
                        userList = (List<UserUserDO>) usersFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage(), e);
                    }

                    for (HrChatUnreadCountRecord hrChatUnreadCountRecord : roomList) {

                        HRChatRoomVO hrChatRoomVO = new HRChatRoomVO();
                        hrChatRoomVO.setId(hrChatUnreadCountRecord.getRoomId());
                        hrChatRoomVO.setUserId(hrChatUnreadCountRecord.getUserId());
                        hrChatRoomVO.setApply(hrChatUnreadCountRecord.getApply() == 1 ? true : false);

                        /** 匹配聊天室的创建时间和状态 */
                        Optional<HrWxHrChatListDO> chatRoomDOOptional = chatRoomList.stream()
                                .filter(chatRoom -> chatRoom.getId() == hrChatUnreadCountRecord.getRoomId()).findFirst();
                        if (chatRoomDOOptional.isPresent()) {
                            hrChatRoomVO.setCreateTime(chatRoomDOOptional.get().getUpdateTime());
                            hrChatRoomVO.setUnReadNum(chatRoomDOOptional.get().getHrUnreadCount());
                        }

                        /** 匹配C端用户的名称和头像 */
                        if (userList != null && userList.size() > 0) {
                            Optional<UserUserDO> userUserDOOptional = userList.stream()
                                    .filter(userUserDO -> userUserDO.getId() == hrChatUnreadCountRecord.getUserId()).findAny();
                            if (userUserDOOptional.isPresent()) {
                                hrChatRoomVO.setHeadImgUrl(userUserDOOptional.get().getHeadimg());
                                String name = StringUtils.isNotNullOrEmpty(userUserDOOptional.get().getName())
                                        ? userUserDOOptional.get().getName() : userUserDOOptional.get().getNickname();
                                hrChatRoomVO.setName(name);
                            }
                        }

                        rooms.add(hrChatRoomVO);

                    }
                    hrChatRoomsIndexVO.setRooms(rooms);
                }
            }
        }
        return hrChatRoomsIndexVO;
    }

    public HRChatRoomVO getChatRoom(int roomId, int hrId) {
        HRChatRoomVO roomVO = new HRChatRoomVO();
        roomVO.setId(0);
        roomVO.setApply(false);
        roomVO.setUnReadNum(0);
        roomVO.setUserId(0);
        roomVO.setCreateTime("");
        HrChatUnreadCountRecord record = chatDao.fetchRoomById(roomId);
        if (record != null && record.getHrId() == hrId) {
            roomVO = new HRChatRoomVO();
            roomVO.setId(roomId);
            roomVO.setApply(record.getApply() == 1 ? true : false);
            roomVO.setUserId(record.getUserId());
            HrWxHrChatListDO chatListDO = chatDao.getChatRoom(roomId, 0, 0);
            if (chatListDO != null) {
                roomVO.setCreateTime(chatListDO.getUpdateTime());
                roomVO.setUnReadNum(chatListDO.getHrUnreadCount());
            }
            UserUserDO userUserDO = chatDao.getUser(record.getUserId());
            if (userUserDO != null) {
                String name = StringUtils.isNotNullOrEmpty(userUserDO.getName())
                        ? userUserDO.getName() : userUserDO.getNickname();
                if (org.apache.commons.lang.StringUtils.isBlank(name)) {
                    roomVO.setName("");
                } else {
                    roomVO.setName(name);
                }
                if (org.apache.commons.lang.StringUtils.isBlank(userUserDO.getHeadimg())) {
                    roomVO.setHeadImgUrl("");
                } else {
                    roomVO.setHeadImgUrl(userUserDO.getHeadimg());
                }
            }
        }
        return roomVO;
    }

    /**
     * 用户查找聊天室列表
     *
     * @param userId   用户编号
     * @param pageNo   页码
     * @param pageSize 分页信息
     * @return 聊天室分页信息
     */
    public UserChatRoomsVO listUserChatRoom(int userId, int pageNo, int pageSize) {

        logger.info("userChatRoomsVO userId:{}, pageNo:{} pageSize:{}", userId, pageNo, pageSize);
        UserChatRoomsVO userChatRoomsVO = new UserChatRoomsVO();

        //计算数量的操作理论上是最快的，所以用它去判断是否有聊天室
        int count = chatDao.countUserChatRoom(userId);
        Page page = new Page(pageNo, pageSize, count);
        userChatRoomsVO.setPageNo(page.getPageNo());
        userChatRoomsVO.setPageSize(page.getPageSize());
        userChatRoomsVO.setTotalPage(page.getTotalPage());
        userChatRoomsVO.setTotalRow(page.getTotalRow());

        if (count > 0) {
            //按照聊天室未读消息倒序查询聊天室信息
            List<HrChatUnreadCountDO> chatUnreadCountDOlist = chatDao.listChatRoomUnreadCount(ChatSpeakerType.USER,
                    userId, page.getPageNo(), page.getPageSize());
            if (chatUnreadCountDOlist != null && chatUnreadCountDOlist.size() > 0) {
                int[] roomIdArray = chatUnreadCountDOlist.stream().mapToInt(chatUnreadCountDO -> chatUnreadCountDO.getRoomId()).toArray();
                int[] hrIdArray = chatUnreadCountDOlist.stream().mapToInt(chatUnreadCountDO -> chatUnreadCountDO.getHrId()).toArray();

                logger.info("roomIdArray:{}", roomIdArray);
                logger.info("hrIdArray:{}", hrIdArray);

                //** 异步查找聊天室内容，HR信息，HR所属的公司信息 *//*
                Future chatRoomsFuture = pool.startTast(() -> chatDao.listChatRoom(roomIdArray));
                Future hrsFuture = pool.startTast(() -> chatDao.listHr(hrIdArray));
                Future companyFuture = pool.startTast(() -> chatDao.listCompany(hrIdArray));

                List<UserChatRoomVO> userChatRoomVOList = new ArrayList<>();
                chatUnreadCountDOlist.forEach(hrChatUnreadCountDO -> {
                    UserChatRoomVO userChatRoomVO = new UserChatRoomVO();
                    userChatRoomVO.setId(hrChatUnreadCountDO.getRoomId());

                    //** 匹配聊天室的状态的和最后发送消息的时间 *//*
                    try {
                        List<HrWxHrChatListDO> chatRooms = (List<HrWxHrChatListDO>) chatRoomsFuture.get();
                        if (chatRooms != null && chatRooms.size() > 0) {
                            Optional<HrWxHrChatListDO> chatRoomOptional = chatRooms.stream()
                                    .filter(chatRoom -> chatRoom.getId() == hrChatUnreadCountDO.getRoomId()).findFirst();
                            if (chatRoomOptional.isPresent()) {
                                userChatRoomVO.setCreateTime(chatRoomOptional.get().getUpdateTime());
                                userChatRoomVO.setUnReadNum(chatRoomOptional.get().getUserUnreadCount());
                            }
                        }

                    } catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage(), e);
                    }

                    //** 匹配HR的编号、头像和名称 *//*
                    try {
                        List<UserHrAccountDO> hrAccountDOList = (List<UserHrAccountDO>) hrsFuture.get();
                        if (hrAccountDOList != null && hrAccountDOList.size() > 0) {
                            List<Integer> wxUserIds = hrAccountDOList.stream()
                                    .filter(h -> h.getWxuserId() == 0)
                                    .map(h -> h.getWxuserId())
                                    .collect(Collectors.toList());

                            Map<Integer, UserWxUserDO> hrWxUserMap = wxUserDao.getWXUserMapByIds(wxUserIds);

                            Optional<UserHrAccountDO> hrAccountDOOptional = hrAccountDOList.stream()
                                    .filter(hrAccountDO -> hrAccountDO.getId() == hrChatUnreadCountDO.getHrId()).findAny();
                            if (hrAccountDOOptional.isPresent()) {
                                UserHrAccountDO hrAccount = hrAccountDOOptional.get();

                                String name = ChatHelper.hrChatName(hrAccount, hrWxUserMap.get(hrAccount.getWxuserId()));
                                userChatRoomVO.setName(name);
                                userChatRoomVO.setHrId(hrAccount.getId());
                                userChatRoomVO.setHeadImgUrl(hrAccount.getHeadimgurl());

                                //** 根据HR所属公司，匹配公司的名称和logo *//*
                                Map<Integer, HrCompanyDO> companyDOMap = (Map<Integer, HrCompanyDO>) companyFuture.get();
                                if (companyDOMap != null && companyDOMap.size() > 0) {
                                    if (companyDOMap.containsKey(hrAccount.getId()) && companyDOMap.get(hrAccount.getId()).getId() > 0) {
                                        userChatRoomVO.setCompanyLogo(companyDOMap.get(hrAccount.getId()).getLogo());
                                        String companyName;
                                        if (StringUtils.isNotNullOrEmpty(companyDOMap.get(hrAccount.getId()).getAbbreviation())) {
                                            companyName = companyDOMap.get(hrAccount.getId()).getAbbreviation();
                                        } else {
                                            companyName = companyDOMap.get(hrAccount.getId()).getName();
                                        }
                                        userChatRoomVO.setCompanyName(companyName);
                                    }
                                }
                            }
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage(), e);
                    }
                    logger.info("userChatRoomVO:{}", userChatRoomVO);
                    userChatRoomVOList.add(userChatRoomVO);
                });
                userChatRoomsVO.setRooms(userChatRoomVOList);
            }
        }
        logger.info("userChatRoomsVO result:{}", userChatRoomsVO);
        return userChatRoomsVO;
    }

    /**
     * 查找聊天记录
     *
     * @param roomId   聊天室编号
     * @param pageNo   页码
     * @param pageSize 每页显示的数量
     * @return
     */
    public ChatsVO listChatLogs(int roomId, int pageNo, int pageSize) {
        logger.info("listChatLogs roomId:{} pageNo:{}, pageSize:{}", roomId, pageNo, pageSize);
        ChatsVO chatsVO = new ChatsVO();

        int count = 0;
        Future<Integer> countFuture = pool.startTast(() -> chatDao.countChatLog(roomId));
        Future chatFuture = pool.startTast(() -> chatDao.listChatMsg(roomId, pageNo, pageSize));

        try {
            count = countFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }
        Page page = new Page(pageNo, pageSize, count);
        chatsVO.setPageNo(page.getPageNo());
        chatsVO.setPageSize(page.getPageSize());
        chatsVO.setTotalPage(page.getTotalPage());
        chatsVO.setTotalRow(page.getTotalRow());
        try {
            Result<Record> chatRecord = (Result<Record>) chatFuture.get();
            if (chatRecord != null && chatRecord.size() > 0) {
                List<ChatVO> chatVOList = new ArrayList<>();
                for (int i = 0; i < chatRecord.size(); i++) {
                    Record record = chatRecord.get(i);
                    // 组装聊天记录
                    ChatVO chatVO = new ChatVO();
                    chatVO.setId(record.getValue(HrWxHrChat.HR_WX_HR_CHAT.ID));
                    chatVO.setContent(record.getValue(HrWxHrChat.HR_WX_HR_CHAT.CONTENT));
                    chatVO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(record.getValue(HrWxHrChat.HR_WX_HR_CHAT.CREATE_TIME)));
                    chatVO.setRoomId(record.getValue(HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID));
                    String msgType = record.getValue(HrWxHrChat.HR_WX_HR_CHAT.MSG_TYPE);
                    if (StringUtils.isNullOrEmpty(msgType)) {
                        chatVO.setMsgType("html");
                    } else {
                        chatVO.setMsgType(msgType);
                    }
                    String picUrl = record.getValue(HrWxHrChat.HR_WX_HR_CHAT.PIC_URL);
                    String voiceUrl = record.getValue(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.LOCAL_URL);
                    if (ChatMsgType.VOICE.value().equals(msgType)) {
                        chatVO.setAssetUrl(voiceUrl);
                    } else {
                        chatVO.setAssetUrl(picUrl);
                    }
                    chatVO.setBtnContent(record.getValue(HrWxHrChat.HR_WX_HR_CHAT.BTN_CONTENT));
                    chatVO.setSpeaker(record.getValue(HrWxHrChat.HR_WX_HR_CHAT.SPEAKER));
                    chatVO.setServerId(record.getValue(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.SERVER_ID));
                    Byte a = record.getValue(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.DURATION);
                    if (null == a) {
                        chatVO.setDuration((byte) 0);
                    } else {
                        chatVO.setDuration(a);
                    }
                    Optional<ChatOrigin> chatOriginOptional = ChatOrigin.instanceFromValue(record.getValue(HrWxHrChat.HR_WX_HR_CHAT.ORIGIN));
                    if (chatOriginOptional.isPresent()) {
                        chatVO.setOrigin(chatOriginOptional.get().getValue());
                        chatVO.setOrigin_str(chatOriginOptional.get().getName());
                    } else {
                        chatVO.setOrigin(ChatOrigin.Human.getValue());
                        chatVO.setOrigin_str(ChatOrigin.Human.getName());
                    }
                    String compoundContent = (record.get(HrWxHrChat.HR_WX_HR_CHAT.COMPOUND_CONTENT));
                    if (org.apache.commons.lang.StringUtils.isNotBlank(compoundContent)) {
                        chatVO.setCompoundContent(compoundContent);
                    }
                    String stats = (record.get(HrWxHrChat.HR_WX_HR_CHAT.STATS));
                    if (org.apache.commons.lang.StringUtils.isNotBlank(stats)) {
                        chatVO.setStats(stats);
                    }
                    chatVOList.add(chatFactory.outputHandle(chatVO));
                }
                //Lists.reverse(chatDOList);
                Collections.reverse(chatVOList);
                chatsVO.setChatLogs(chatVOList);
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("listChatLogs result:{}", chatsVO);
        return chatsVO;
    }

    /**
     * 添加聊天内容，并修改未读消息数量
     * <p>
     * 应Mobot需求，去除聊天保存限制
     * <p>
     * 如以后有数据问题，可以先查询是否为Mobot入库数据
     *
     * @param chat 聊天信息
     */

    public int saveChat(ChatVO chat) throws BIZException {
        try {
            logger.info("saveChat chat:{}", JSON.toJSONString(chat));

            HrWxHrChatDO chatDO = new HrWxHrChatDO();
            String date = new DateTime().toString("yyyy-MM-dd HH:mm:ss");

            chatDO.setCreateTime(date);
            chatDO.setPid(chat.getPositionId());
            chatDO.setSpeaker(chat.getSpeaker());
            chatDO.setChatlistId(chat.getRoomId());
            chatDO.setOrigin(chat.getOrigin());
            chatDO.setMsgType(chat.getMsgType());
            chatDO.setCompoundContent(chat.getCompoundContent());
            chatDO.setStats(chat.getStats());
            chatDO.setStatus(ChatStatus.DEFAULT.value());
            if (StringUtils.isNotNullOrEmpty(chat.getContent())) {
                chatDO.setContent(chat.getContent());
            } else {
                chatDO.setContent("");
            }
            chatDO.setPicUrl(chat.getAssetUrl());
            chatDO.setBtnContent(JSON.toJSONString(chat.getBtnContent()));

            logger.info("saveChat before saveChat chatDO:{}", chatDO);

            chatDO = chatFactory.beforeSaveHandle(chatDO);
            chatDO = chatDao.saveChat(chatDO);

            // 新增聊天记录的id
            int chatId = chatDO.getId();
            if (ChatMsgType.VOICE.value().equals(chatDO.getMsgType())) {
                // 先插入语音记录表，在语音下载完成后更新将语音路径更新到数据库
                HrWxHrChatVoiceDO hrWxHrChatVoiceDO = new HrWxHrChatVoiceDO();
                hrWxHrChatVoiceDO.setChatId(chatId);
                hrWxHrChatVoiceDO.setDuration(chat.getDuration());
                hrWxHrChatVoiceDO.setServerId(chat.getServerId());
                hrWxHrChatVoiceDO.setCreateTime(date);
                hrWxHrChatVoiceDO.setUpdateTime(date);
                hrChatVoiceDao.addData(hrWxHrChatVoiceDO);
                //下载语音文件并更新数据库信息
                pool.startTast(() -> downloadVoiceFile(chat, chatId));
            }

            logger.info("saveChat after saveChat chatDO:{}", chatDO);
            chatDao.addChatTOChatRoom(chatDO);

            // 修改未读消息数量
            chatDao.addUnreadCount(chat.getRoomId(), chat.getSpeaker(), date);

            // TODO 暂时标记求职者转人工暗语对应的上一条聊天问题为需要HR回答问题（等待算法自动识别对话中的问题）
            byte chatStatus = getChatStatus(chat.getContent());
            if (chatStatus == ChatStatus.NEED_HR_ANSWER.value()){
                Integer lastQuestionChatId = chatDao.getUserLastQuestionChatRecordId(chat.getRoomId(), chatId);
                logger.info("getUserLastAnswerChatRecordId:{}", lastQuestionChatId);
                if(lastQuestionChatId > 0){
                    chatDao.updateChatStatus(lastQuestionChatId, ChatStatus.NEED_HR_ANSWER.value());
                }
            }

            return chatId;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BIZException(VoiceErrorEnum.CHAT_INFO_ADD_FAILED.getCode(), VoiceErrorEnum.CHAT_INFO_ADD_FAILED.getMsg());
        }
    }

    private byte getChatStatus(String content){
        // 回复暗语：请转HR，用于标记需要HR人工回复的消息记录
        content = content.trim().toUpperCase();

        if(StringUtils.isNotNullOrEmpty(content)
                && ("请转HR".equals(content) || content.startsWith("[请转HR]"))){
            return ChatStatus.NEED_HR_ANSWER.value();
        }
        return ChatStatus.DEFAULT.value();
    }

    /**
     * 下载文件，存储文件路径
     *
     * @param chat   chatvo
     * @param chatId 聊天记录id
     * @return
     * @author cjm
     * @date 2018/5/15
     */
    public String downloadVoiceFile(ChatVO chat, int chatId) throws Exception {
        try {
            //获取请求下载语音文件的参数 serverId accessToken
            String serverId = chat.getServerId();
            Result result = chatDao.getCompanyIdAndTokenByRoomId(chat.getRoomId());
            int companyId = (int) result.getValue(0, HrWxWechat.HR_WX_WECHAT.COMPANY_ID);
            String accessToken = String.valueOf(result.getValue(0, HrWxWechat.HR_WX_WECHAT.ACCESS_TOKEN));
            logger.info("==================serverId:{}, token:{}=======================", serverId, accessToken);
            //请求微信服务器下载
            Response response = UpDownLoadUtil.downloadMediaFileFromWechat(accessToken, serverId);
            //根据reponse响应码处理业务
            int status = response.getStatus();
            if (status == RespnoseUtil.PROGRAM_EXCEPTION.getStatus()) {
                logger.info("================下载语音时后台异常===============");
                return "failed";
            } else if (status == VoiceErrorEnum.VOICE_SEND_WARN_EMAIL.getCode()) {
                // 如果是此返回码，代表微信语音下载失败，解析微信返回错误码，发送报警邮件
                JSONObject jsonObject = JSONObject.parseObject(response.getData());
                logger.info("================下载语音时微信返回异常, errmsg:{}===============", jsonObject.get("errmsg"));
                String emailSubject = "公司id" + companyId + "微信语音下载失败";
                String emailContent = "errcode:" + jsonObject.get("errcode") + "</br>"
                        + "errmsg:" + jsonObject.get("errmsg") + "</br>"
                        + "serverId:" + serverId;
                EmailSendUtil.sendWarnEmail(ChatVoiceConstant.WARN_EMAIL_ADDRESS_DEV, emailContent, emailSubject);
                return "failed";
            }
            JSONObject downloadMediaFile = JSONObject.parseObject(response.getData());
            String voiceLocalUrl = downloadMediaFile.getString("fileLocalUrl");
            String fileAddress = downloadMediaFile.getString("file_address");
            String fileName = downloadMediaFile.getString("file_name");
            // 执行ffmpeg转换语音格式
            voiceLocalUrl = VoiceFormConvertUtil.amrToMp3(voiceLocalUrl, fileAddress, fileName);

            if (StringUtils.isNullOrEmpty(voiceLocalUrl)) {
                return "failed";
            }
            int successRow = hrChatVoiceDao.updateVoiceFileInfo(chatId, voiceLocalUrl);
            if (successRow < 1) {
                logger.error("================更新语音本地路径信息失败，chatId:{}=================", chatId);
                return "failed";
            }
            redisClient.set(Constant.APPID_ALPHADOG, VOICE_URL_IN_REDIS, serverId, voiceLocalUrl);
            // 持久化成功后，记录下载次数+1
            increasePullVoiceFrequency(companyId);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failed";
    }

    /**
     * 进入聊天室
     * 如果不存在聊天室，则创建聊天室。
     * 创建聊天室时，需要默认添加一条聊天内容，规则如下：
     * 1.如果HR的名称不存在，则存储 "我是{companyName}HR，我可以推荐您或者您的朋友加入我们！"
     * 2.如果HR的名称存在，则存储 "我是{hrName}，{companyName}HR，我可以推荐您或者您的朋友加入我们！"
     * 默认是c端账号进入聊天室，需要清空聊天该聊天室的未读消息
     *
     * @param userId     用户编号
     * @param hrId       HR编号
     * @param positionId 职位编号
     * @param roomId     聊天室编号
     * @param is_gamma   是否从聚合号进入
     * @return ResultOfSaveRoomVO
     */
    public ResultOfSaveRoomVO enterChatRoom(int userId, int hrId, int positionId, int roomId, final boolean is_gamma) throws BIZException {
        logger.info("enterChatRoom userId:{} hrId:{}, positionId:{} roomId:{}, is_gamma:{}", userId, hrId, positionId, roomId, is_gamma);
        final ResultOfSaveRoomVO resultOfSaveRoomVO;

        //检测HR是否存在
        boolean isHrDelete = false;
        UserHrAccountDO hrAccountDO = hrAccountDao.getValidAccount(hrId);
        if (hrAccountDO == null) {
            isHrDelete = true;
        }

        HrWxHrChatListDO chatRoom = chatDao.getChatRoom(roomId, userId, hrId);
        boolean chatDebut = false;
        if (chatRoom == null && !isHrDelete) {
            chatRoom = new HrWxHrChatListDO();
            String createTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
            chatRoom.setCreateTime(createTime);
            chatRoom.setHraccountId(hrId);
            chatRoom.setSysuserId(userId);
            chatRoom.setWelcomeStatus((byte) 1);
            chatRoom = chatDao.saveChatRoom(chatRoom);
            chatDebut = true;
        }

        if (chatRoom != null) {
            resultOfSaveRoomVO = searchResult(chatRoom, positionId);
            if (chatRoom.getWelcomeStatus() == 1) {
                // 支持多条Opening的欢迎语
                List<String> contents = getChatRoomInitCreateContentList(resultOfSaveRoomVO, is_gamma);
                // TODO MoBot配置欢迎语产品上限制最多5条，可循环插入，太多需要变为批处理
                if(contents != null && contents.size() > 0){
                    for (String content:contents) {
                        createChat(resultOfSaveRoomVO, content);
                    }
                }
                chatRoom.setWelcomeStatus((byte) 0);
                chatDao.updateChatRoom(chatRoom);
            }
            if (chatDebut) {
                HrChatUnreadCountDO unreadCountDO = new HrChatUnreadCountDO();
                unreadCountDO.setHrId(hrId);
                unreadCountDO.setUserId(userId);
                unreadCountDO.setHrHaveUnreadMsg((byte) 0);
                unreadCountDO.setUserHaveUnreadMsg((byte) 1);
                unreadCountDO.setRoomId(chatRoom.getId());
                chatDao.saveUnreadCount(unreadCountDO);
                resultOfSaveRoomVO.setChatDebut(chatDebut);
            } else {
                //默认清空C端账号的未读消息
                int chatRoomId = chatRoom.getId();
                pool.startTast(() -> chatDao.clearUserUnreadCount(chatRoomId, hrId, userId));
            }
        } else {
            resultOfSaveRoomVO = new ResultOfSaveRoomVO();
            resultOfSaveRoomVO.setHr(new HrVO());
        }


        resultOfSaveRoomVO.setChatDebut(chatDebut);
        if (resultOfSaveRoomVO.getHr() == null) {
            resultOfSaveRoomVO.setHr(new HrVO());
        }
        resultOfSaveRoomVO.getHr().setIsDelete(isHrDelete);
        logger.info("enterChatRoom result:{}", resultOfSaveRoomVO);
        return resultOfSaveRoomVO;
    }



    /**
     * 查找返回值
     *
     * @param chatRoom   聊天室
     * @param positionId 职位编号
     * @return
     */
    private ResultOfSaveRoomVO searchResult(HrWxHrChatListDO chatRoom, int positionId) {
        logger.info("searchResult HrWxHrChatListDO:{} positionId:{}", chatRoom, positionId);
        ResultOfSaveRoomVO resultOfSaveRoomVO = new ResultOfSaveRoomVO();
        resultOfSaveRoomVO.setRoomId(chatRoom.getId());

        /** 并行查询职位信息、hr信息、公司信息以及用户信息 */

        Future positionFuture = null;
        if (positionId > 0) {
            positionFuture = pool.startTast(() -> chatDao.getPositionById(positionId));
        }
        Future hrFuture = pool.startTast(() -> chatDao.getHr(chatRoom.getHraccountId()));
        Future hrCompanyFuture = pool.startTast(() -> chatDao.getCompany(chatRoom.getHraccountId()));
        Future userFuture = pool.startTast(() -> chatDao.getUser(chatRoom.getSysuserId()));

        /** 设置职位信息 */
        try {
            if (positionFuture != null) {
                JobPositionDO positionDO = (JobPositionDO) positionFuture.get();

                if (positionDO != null) {

                    PositionVO positionVO = new PositionVO();
                    positionVO.setPositionId(positionDO.getId());
                    positionVO.setPositionTitle(positionDO.getTitle());
                    positionVO.setSalaryBottom((int) positionDO.getSalaryBottom());
                    positionVO.setSalaryTop((int) positionDO.getSalaryTop());
                    positionVO.setUpdateTime(positionDO.getUpdateTime());
                    positionVO.setCity(positionDO.getCity());
                    positionVO.setStatus(Double.valueOf(positionDO.getStatus()).intValue());
                    positionVO.setTeam(positionDO.getDepartment());

                    if (positionDO.getCompanyId() > 0) {
                        HrCompanyDO companyDO = chatDao.getCompany(positionDO.getPublisher());
                        String companyName;
                        if (StringUtils.isNotNullOrEmpty(companyDO.getAbbreviation())) {
                            companyName = companyDO.getAbbreviation();
                        } else {
                            companyName = companyDO.getName();
                        }
                        positionVO.setCompanyName(companyName);
                    }
                    resultOfSaveRoomVO.setPosition(positionVO);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }

        /** 设置用户信息 */
        try {
            UserUserDO userUserDO = (UserUserDO) userFuture.get();
            if (userUserDO != null) {
                UserVO userVO = new UserVO();
                userVO.setUserId(userUserDO.getId());
                userVO.setUserHeadImg(userUserDO.getHeadimg());
                userVO.setUserId(chatRoom.getSysuserId());
                String name;
                if (StringUtils.isNotNullOrEmpty(userUserDO.getName())) {
                    name = userUserDO.getName();
                } else {
                    name = userUserDO.getNickname();
                }
                userVO.setUserName(name);
                resultOfSaveRoomVO.setUser(userVO);
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }

        /** 设置HR信息 */
        try {
            UserHrAccountDO hrAccountDO = (UserHrAccountDO) hrFuture.get();
            HrCompanyDO hrCompanyDO = (HrCompanyDO) hrCompanyFuture.get();
            if (hrAccountDO != null) {
                HrVO hrVO = new HrVO();

                UserWxUserDO wxUser = wxUserDao.getWXUserById(hrAccountDO.getWxuserId());
                String hrName = ChatHelper.hrChatName(hrAccountDO, wxUser);
                hrVO.setHrName(hrName);

                hrVO.setHrId(hrAccountDO.getId());
                hrVO.setHrHeadImg(hrAccountDO.getHeadimgurl());
                if (StringUtils.isNotNullOrEmpty(hrCompanyDO.getAbbreviation())) {
                    hrVO.setCompanyName(hrCompanyDO.getAbbreviation());
                } else {
                    hrVO.setCompanyName(hrCompanyDO.getName());
                }

                resultOfSaveRoomVO.setHr(hrVO);
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }

        logger.info("searchResult result:{}", resultOfSaveRoomVO);
        return resultOfSaveRoomVO;
    }

    /**
     * 创建聊天室时，初始化一条聊天记录
     *
     * @param resultOfSaveRoomVO 进入聊天室返回的结果
     * @return 聊天记录
     */
    private void createChat(ResultOfSaveRoomVO resultOfSaveRoomVO, String content) throws BIZException {
        String createTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");

        ChatVO chatDO = new ChatVO();
        chatDO.setRoomId(resultOfSaveRoomVO.getRoomId());
        chatDO.setSpeaker((byte) 1);
        chatDO.setOrigin(ChatOrigin.System.getValue());
        chatDO.setCreateTime(createTime);
        chatDO.setContent(content);
        if (resultOfSaveRoomVO.getPosition() != null) {
            chatDO.setPositionId(resultOfSaveRoomVO.getPosition().getPositionId());
        }
        chatDO.setMsgType(ChatMsgType.HTML.value());

        saveChat(chatDO);
        logger.info("createChat roomId:{}, result:{}", resultOfSaveRoomVO.getRoomId(), chatDO);
    }

    /**
     * 获取聊天室初始回复内容数据
     *
     * @param resultOfSaveRoomVO
     * @param is_gamma 是否聚合号
     * @return
     */
    private List<String> getChatRoomInitCreateContentList(ResultOfSaveRoomVO resultOfSaveRoomVO, boolean is_gamma) {
        List<String> contentList = new ArrayList<>();
        HrCompanyConf companyConf = null;
        String companyName = null;

        String hrName = resultOfSaveRoomVO.getHr().getHrName();

        // 聚合号直接返回默认的欢迎语
        if (is_gamma) {
            String GOMMA_WELCOME_CONTENT = "亲爱的%s：\n" +
                    "\t仟寻终于等到你啦。你可以在这里直接联系企业的HR，也可以直接和仟寻互动哦。\n" +
                    "\t祝你早日找到心仪的公司，加入令人振奋的团队，再次在你的职业之旅上迈步前行！\n";

            contentList.add(String.format(GOMMA_WELCOME_CONTENT, resultOfSaveRoomVO.getUser().getUserName()));
            return contentList;
        }

        HrCompanyDO companyDO = chatDao.getCompany(resultOfSaveRoomVO.getHr().getHrId());
        if (companyDO == null) {
            return null;
        }

        // 获取职位所属公司名称
        if (resultOfSaveRoomVO.getPosition() != null) {
            companyName = resultOfSaveRoomVO.getPosition().getCompanyName();
        } else {
            companyName = resultOfSaveRoomVO.getHr().getCompanyName();
        }

        // 获取主公司配置信息
        if (companyDO.getParentId() != 0) {
            companyConf = hrCompanyConfDao.getConfbyCompanyId(companyDO.getParentId());
        } else {
            companyConf = hrCompanyConfDao.getConfbyCompanyId(companyDO.getId());
        }

        // 公司是否开启了MoBot
        if (companyConf != null
                && companyConf.getHrChat() != null
                && companyConf.getHrChat().equals(CompanyConf.HRCHAT.ON_AND_MOBOT)){

            // 公司配置MoBot欢迎语，优先显示配置
            if (org.apache.commons.lang.StringUtils.isNotEmpty(companyConf.getMobotWelcome())) {
                String[] welcomeContents = companyConf.getMobotWelcome().split(MOBOT_MULTI_WELCOME_SEPARATOR_TEXT);
                return Arrays.asList(welcomeContents);
            }

            // 获取默认的MoBot欢迎语
            String mobotName = StringUtils.isNotNullOrEmpty(companyConf.getMobotName()) ? companyConf.getMobotName() : hrName;
            contentList.add(getDefaultChatWelcomeContent(mobotName, companyName));
            return contentList;
        }

        contentList.add(getDefaultChatWelcomeContent(hrName, companyName));
        return contentList;
    }

    private String getDefaultChatWelcomeContent(String hrName, String companyName){

        String AUTO_CONTENT_WITH_HR_NOTEXIST = "您好，我是{companyName}HR，关于职位和公司信息有任何问题请随时和我沟通。";
        String AUTO_CONTENT_WITH_HR_EXIST = "您好，我是{companyName}的{hrName}，关于职位和公司信息有任何问题请随时和我沟通。";

        if (StringUtils.isNotNullOrEmpty(hrName)) {
            return AUTO_CONTENT_WITH_HR_EXIST.replace("{hrName}", hrName).replace("{companyName}", companyName);
        }
        return AUTO_CONTENT_WITH_HR_NOTEXIST.replace("{companyName}", companyName);
    }

    /**
     * 获取聊天内容
     *
     * @param roomId  聊天室编号
     * @param speaker 聊天者
     * @return
     */
    public ChatVO getChat(int roomId, byte speaker) {
        ChatVO chatVO = new ChatVO();

        return chatVO;
    }

    /**
     * 退出聊天室
     *
     * @param roomId  聊天室编号
     * @param speaker 退出聊天室用户的类型 0表示用户，1表示HR
     */
    public void leaveChatRoom(int roomId, byte speaker) {
        logger.debug("leaveChatRoom roomId:{} speaker:{}", roomId, speaker);
        HrWxHrChatListDO chatRoom = new HrWxHrChatListDO();
        chatRoom.setId(roomId);

        HrChatUnreadCountDO hrChatUnreadCountDO = new HrChatUnreadCountDO();
        hrChatUnreadCountDO.setRoomId(roomId);
        String leaveTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        if (speaker == 0) {
            chatRoom.setUserUnreadCount(0);
            hrChatUnreadCountDO.setUserHaveUnreadMsg((byte) 0);
            hrChatUnreadCountDO.setWxChatTime(leaveTime);
            chatRoom.setWxChatTime(leaveTime);
        } else {
            chatRoom.setHrUnreadCount(0);
            hrChatUnreadCountDO.setHrHaveUnreadMsg((byte) 0);
            hrChatUnreadCountDO.setHrChatTime(leaveTime);
            chatRoom.setHrChatTime(leaveTime);
        }
        chatDao.updateChatRoom(chatRoom);
        chatDao.updateChatUnreadCount(hrChatUnreadCountDO);
    }

    /**
     * 查询聊天室最后聊天记录
     *
     * @param roomIdList 聊天室列表
     * @return 聊天记录列表
     * @throws CommonException
     */
    public List<ChatVO> listLastMessage(List<Integer> roomIdList) throws CommonException {

        return chatDao.listLastMessage(roomIdList);
    }

    /**
     * 查询聊天历史记录
     *
     * @param roomId   聊天室
     * @param chatId   聊天记录
     * @param pageSize
     * @return
     * @throws CommonException
     */
    public ChatHistory listMessage(int roomId, int chatId, int pageSize) throws CommonException {
        logger.info("listMessage roomId:{} speaker:{}, pageSize:{}", roomId, chatId, pageSize);
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setConversationId(roomId);
        if (pageSize <= 0 || pageSize > Constant.PAGE_SIZE) {
            pageSize = Constant.PAGE_SIZE;
        }
        int count = chatDao.countMessage(roomId, chatId);
        DateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        if (count >= 0) {
            List<ChatVO> chatVOList = new ArrayList<>();
            Result records = chatDao.listMessage(roomId, chatId, pageSize);
            if (records != null && records.size() > 0) {
                for (int i = 0; i < records.size(); i++) {
                    ChatVO chatVO = new ChatVO();
                    int id = (int) records.getValue(i, HrWxHrChat.HR_WX_HR_CHAT.ID);
                    byte origin = (byte) records.getValue(i, HrWxHrChat.HR_WX_HR_CHAT.ORIGIN);
                    byte speaker = (byte) records.getValue(i, HrWxHrChat.HR_WX_HR_CHAT.SPEAKER);
                    String assetUrl = (String) records.getValue(i, HrWxHrChat.HR_WX_HR_CHAT.PIC_URL);
                    String msgType = (String) records.getValue(i, HrWxHrChat.HR_WX_HR_CHAT.MSG_TYPE);
                    String btnContent = (String) records.getValue(i, HrWxHrChat.HR_WX_HR_CHAT.BTN_CONTENT);
                    String serverId = (String) records.getValue(i, HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.SERVER_ID);
                    String content = (String) records.getValue(i, HrWxHrChat.HR_WX_HR_CHAT.CONTENT);
                    int positionId = (int) records.getValue(i, HrWxHrChat.HR_WX_HR_CHAT.PID);
                    String createTime = sdf.format((Timestamp) records.getValue(i, HrWxHrChat.HR_WX_HR_CHAT.CREATE_TIME));
                    if (null != records.getValue(i, HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.DURATION)) {
                        chatVO.setDuration((byte) records.getValue(i, HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.DURATION));
                    }
                    if (speaker == 2) {
                        chatVO.setSpeaker((byte) 1);
                    } else {
                        chatVO.setSpeaker(speaker);
                    }
                    String compoundContent = (String) records.getValue(i, HrWxHrChat.HR_WX_HR_CHAT.COMPOUND_CONTENT);
                    chatVO.setAssetUrl(assetUrl);
                    chatVO.setCreateTime(createTime);
                    chatVO.setMsgType(msgType);

                    chatVO.setServerId(serverId);
                    chatVO.setBtnContent(btnContent);
                    chatVO.setContent(content);
                    chatVO.setOrigin(origin);
                    chatVO.setId(id);
                    chatVO.setPositionId(positionId);
                    chatVOList.add(chatFactory.outputHandle(chatVO));
                }
            }
            chatHistory.setChatList(chatVOList);
            if (records != null && count > records.size()) {
                chatHistory.setHasMore(true);
            } else {
                chatHistory.setHasMore(false);
            }
        }
        HrChatUnreadCountRecord hrChatUnreadCountRecord = chatDao.fetchRoomById(roomId);
        logger.info("listMessage hrChatUnreadCountRecord:{}", hrChatUnreadCountRecord);
        if (hrChatUnreadCountRecord != null) {
            if (hrChatUnreadCountRecord.getUpdateTime() != null) {
                chatHistory.setHrLeaveTime(
                        new DateTime(hrChatUnreadCountRecord.getHrChatTime()).toString("YYYY-MM-dd HH:mm:sss"));
            }
            if (hrChatUnreadCountRecord.getUserId() != null) {
                UserUserDO userUserDO = chatDao.getUser(hrChatUnreadCountRecord.getUserId());
                chatHistory.setUserId(userUserDO.getId());
                chatHistory.setName(userUserDO.getName());
            }
            logger.info("listMessage hrChatUnreadCountRecord:{}, chatId:{}", hrChatUnreadCountRecord, chatId);
            updateLeaveTime(hrChatUnreadCountRecord, chatId);
        }
        return chatHistory;
    }

    private void updateLeaveTime(HrChatUnreadCountRecord hrChatUnreadCountRecord, int chatId) {
        logger.info("ChatService updateLeaveTime hrChatUnreadCountRecord:{}, chatId:{}", hrChatUnreadCountRecord, chatId);
        if (chatId == 0) {
            hrChatUnreadCountRecord.setHrChatTime(new Timestamp(System.currentTimeMillis()));
            hrChatUnreadCountRecord.setHrHaveUnreadMsg((byte) 0);
            chatDao.updateChatRoom(hrChatUnreadCountRecord);

            HrWxHrChatListRecord hrWxHrChatListRecord = new HrWxHrChatListRecord();
            hrWxHrChatListRecord.setId(hrChatUnreadCountRecord.getRoomId());
            hrWxHrChatListRecord.setHrUnreadCount(0);
            chatDao.updateChatRoom(hrWxHrChatListRecord);
        } else {
            HrWxHrChatRecord chatRecord = chatDao.getChat(chatId);
            if (chatRecord != null) {
                logger.info("ChatService updateLeaveTime chatRecord:{}, chatId:{}", chatRecord, chatId);
                if (hrChatUnreadCountRecord.getHrChatTime() == null || hrChatUnreadCountRecord.getHrChatTime().getTime() < chatRecord.getCreateTime().getTime()) {
                    hrChatUnreadCountRecord.setHrChatTime(chatRecord.getCreateTime());
                    hrChatUnreadCountRecord.setHrHaveUnreadMsg((byte) 0);
                    chatDao.updateChatRoom(hrChatUnreadCountRecord);

                    HrWxHrChatListRecord hrWxHrChatListRecord = new HrWxHrChatListRecord();
                    hrWxHrChatListRecord.setId(hrChatUnreadCountRecord.getRoomId());
                    hrWxHrChatListRecord.setHrUnreadCount(0);
                    chatDao.updateChatRoom(hrWxHrChatListRecord);
                }
            }
        }
    }

    public List<String> getChatSug(int hrId, boolean applied, String keyword) {
        List<String> userNames = new ArrayList<>();

        List<Integer> chatUserIdList = chatDao.fetchUserIdByHrId(hrId, applied);
        if (chatUserIdList != null && chatUserIdList.size() > 0) {
            userNames = chatDao.findUserNameByKeyword(keyword, chatUserIdList);
        }
        return userNames;
    }

    public int getHRUnreadCount(int hrId) {
        return chatDao.countUnreadMessage(hrId);
    }

    public HrVO getHrInfo(int roomId) {
        HrVO hrVO = new HrVO();
        HrWxHrChatListDO chatRoom = chatDao.getChatRoom(roomId, 0, 0);
        if (chatRoom != null && chatRoom.getHraccountId() > 0) {
            UserHrAccountDO hr = chatDao.getHr(chatRoom.getHraccountId());
            if (hr != null) {
                hrVO.setHrId(hr.getId());
                hrVO.setHrHeadImg(hr.getHeadimgurl());

                UserWxUserDO wxUserDO = wxUserDao.getWXUserById(hr.getWxuserId());
                hrVO.setHrName(ChatHelper.hrChatName(hr, wxUserDO));
            }
        }
        return hrVO;
    }

    public void updateApplyStatus(int userId, int positionId) {
        logger.info("ChatService updateApplyStatus userId:{}, positionId:{}", userId, positionId);
        int publisher = chatDao.fetchPublisher(positionId);
        logger.info("ChatService updateApplyStatus publisher: {}", publisher);
        if (publisher > 0) {
            chatDao.updateApplyStatus(publisher, userId);
        }
        UserHrAccountDO userHrAccountDO = chatDao.getHr(publisher);
        if (userHrAccountDO != null && userHrAccountDO.getAccountType() == HRAccountType.SubAccount.getType()) {
            int accountId = chatDao.fetchSuperAccount(userHrAccountDO.getId());
            if (accountId > 0 && accountId != publisher) {
                chatDao.updateApplyStatus(accountId, userId);
            }
        }
    }

    public void roleLeaveChatRoom(int roleId, byte speaker) {
        logger.info("ChatService roleLeaveChatRoom roleId:{}, speaker:{}", roleId, speaker);
        if (chatDao.roleExist(roleId, speaker)) {
            List<Integer> roomIdList = chatDao.fetchRoomIdByRole(roleId, speaker);
            logger.info("ChatService roleLeaveChatRoom roomIdList::{}", roomIdList);
            if (roomIdList != null && roomIdList.size() > 0) {
                pool.startTast(() -> {
                    logger.info("ChatService roleLeaveChatRoom roomIdList::{}", roomIdList);
                    roomIdList.forEach(roomId -> leaveChatRoom(roomId, speaker));
                    return true;
                });
            }
        }
    }

    private long increasePullVoiceFrequency(int companyId) {
        String frequency = redisClient.get(Constant.APPID_ALPHADOG, VOICE_DOWNLOAD_FREQUENCY, String.valueOf(companyId));
        // 当天第一次下载语音
        if (StringUtils.isNullOrEmpty(frequency)) {
            DateTime now = DateTime.now();
            redisClient.set(Constant.APPID_ALPHADOG, VOICE_DOWNLOAD_FREQUENCY, String.valueOf(companyId), 1 + "");
            // 当天剩余时间作为过期时间
            int frequencyExpireTime = (24 * 60 * 60 * 1000 - now.millisOfDay().get()) / 1000;
            redisClient.expire(Constant.APPID_ALPHADOG, VOICE_DOWNLOAD_FREQUENCY, String.valueOf(companyId), frequencyExpireTime);
            return 1;
        } else {
            // 语音下载次数加一
            return redisClient.incr(Constant.APPID_ALPHADOG, VOICE_DOWNLOAD_FREQUENCY, String.valueOf(companyId));
        }
    }

    /**
     * 获取语音
     *
     * @param serverId 微信服务器生成的多媒体文件
     * @return Response
     * @author cjm
     * @date 2018/5/16
     */
    public Response pullVoiceFile(String serverId, int roomId, int userId, int hrId) {
        try {
//             检测拉取语音用户是否可访问
            boolean access = checkAccessLimit(roomId, userId, hrId);
            if (!access) {
                return ResponseUtils.fail(VoiceErrorEnum.VOICE_PULL_PARAM_ERROR.getCode(), VoiceErrorEnum.VOICE_PULL_PARAM_ERROR.getMsg());
            }
            String voiceLocalUrl = null;
            int i = 0;
            while (i < GET_REDIS_VOICE_RETRY_TIMES) {
                voiceLocalUrl = redisClient.get(Constant.APPID_ALPHADOG, VOICE_URL_IN_REDIS, serverId);
                if (StringUtils.isNullOrEmpty(voiceLocalUrl)) {
                    i++;
                    Thread.sleep(1000);
                } else {
                    break;
                }
            }
            if (StringUtils.isNullOrEmpty(voiceLocalUrl)) {
                return ResponseUtils.fail(VoiceErrorEnum.VOICE_PULL_BUSY.getCode(), VoiceErrorEnum.VOICE_PULL_BUSY.getMsg());
            }
            logger.info("==============voiceLocalUrl:{}================", voiceLocalUrl);
            Map<String, String> returnMap = new HashMap<>(1 >> 4);
            returnMap.put("voiceLocalUrl", voiceLocalUrl);
            return ResponseUtils.success(returnMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespnoseUtil.PROGRAM_EXCEPTION.toResponse();
    }

    /**
     * 拉取语音时通过查询是否为该聊天室用户拉取来验证访问限制
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/5/21
     */
    private boolean checkAccessLimit(int roomId, int userId, int hrId) {
        if (roomId == 0) {
            return false;
        }
        if (userId == 0 && hrId == 0) {
            return false;
        }
        HrWxHrChatListDO hrWxHrChatListDO = chatDao.getChatRoomByRoomId(roomId);
        if (null != hrWxHrChatListDO) {
            int dbHrId = hrWxHrChatListDO.getHraccountId();
            int dbUserId = hrWxHrChatListDO.getSysuserId();
            if (hrId != 0 && hrId != dbHrId) {
                return false;
            }
            if (userId != 0 && dbUserId != userId) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 语音下载次数清零
     *
     * @param companyId 公司id
     * @return
     * @author cjm
     * @date 2018/5/9
     */
    public Response clearVoiceLimitFrequency(int companyId) {
        try {

            // 获取accessToken
            Result record = wechatDao.getAccessTokenAndAppId(companyId);
            if (record == null || record.isEmpty()) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
            }
            String accessToken = String.valueOf(record.getValue(0, HrWxWechat.HR_WX_WECHAT.ACCESS_TOKEN));
            String appId = String.valueOf(record.getValue(0, HrWxWechat.HR_WX_WECHAT.APPID));

            // 向微信服务器请求下载次数清零
            JSONObject response = clearVoiceLimitFromWechat(accessToken, appId);

            if (null == response) {
                logger.info("===============当前公众号语音下载次数清零失败,companyId:{}, respose为null=================", companyId);
                return ResponseUtils.fail(VoiceErrorEnum.VOICE_DOWNLOAD_LIMIT_CLEAR_FAILED.getCode(), VoiceErrorEnum.VOICE_DOWNLOAD_LIMIT_CLEAR_FAILED.getMsg());
            } else if (!"0".equals(response.get("errcode").toString())) {
                logger.info("===============当前公众号语音下载次数清零失败,companyId:{}=================", companyId);
                logger.info("=====================errmsg:{}=======================", response.get("errmsg"));
                return ResponseUtils.fail(VoiceErrorEnum.VOICE_DOWNLOAD_LIMIT_CLEAR_FAILED.getCode(), String.valueOf(response.get("errmsg")));
            }
            logger.info("==========================errcode:{}=================================", response.get("errcode"));
            // 微信清零完成后修改redis中数据状态
            String clearObjectJson = redisClient.get(Constant.APPID_ALPHADOG, VOICE_CLEAR_TIMES, String.valueOf(companyId));
            JSONObject clearObject = null;
            if (StringUtils.isNotNullOrEmpty(clearObjectJson)) {
                clearObject = JSONObject.parseObject(clearObjectJson);
                int clearTimes = (int) clearObject.get("clear_times");
                clearObject.put("clear_times", clearTimes + 1);
                clearObject.put("warn_email_send_state", 0);
            } else {
                clearObject = new JSONObject();
                clearObject.put("clear_times", 1);
                clearObject.put("warn_email_send_state", 0);
            }
            String frequency = redisClient.get(Constant.APPID_ALPHADOG, VOICE_DOWNLOAD_FREQUENCY, String.valueOf(companyId));
            if (StringUtils.isNotNullOrEmpty(frequency)) {
                redisClient.set(Constant.APPID_ALPHADOG, VOICE_DOWNLOAD_FREQUENCY, String.valueOf(companyId), "0");
            }
            redisClient.set(Constant.APPID_ALPHADOG, VOICE_CLEAR_TIMES, String.valueOf(companyId), JSONObject.toJSONString(clearObject));
            clearObject.remove("warn_email_send_state");
            return ResponseUtils.success(clearObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespnoseUtil.PROGRAM_EXCEPTION.toResponse();
    }

    /**
     * 向微信服务器请求下载次数清零
     *
     * @param appId appid
     * @return
     * @author cjm
     * @date 2018/5/9
     */
    private JSONObject clearVoiceLimitFromWechat(String accessToken, String appId) {
        try {
            JSONObject request = new JSONObject();
            request.put("appid", appId);
            String param = request.toJSONString();
            String requestUrl = VOICE_CLEAR_URL.replace("ACCESS_TOKEN", accessToken);
            String responsJson = HttpClient.sendPost(requestUrl, param);
            return JSONObject.parseObject(responsJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询语音下载次数
     *
     * @param companyId 公司id
     * @return Response
     * @author cjm
     * @date 2018/5/9
     */
    public Response queryVoiceLimitFrequency(int companyId) {
        try {
            // 查询该公司是否存在
            Query query = new Query.QueryBuilder().where("company_id", companyId).buildQuery();
            HrWxWechatDO hrWxWechatDO = wechatDao.getData(query);
            if (hrWxWechatDO == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
            }
            // 查询语音下载次数
            String frequency = redisClient.get(Constant.APPID_ALPHADOG, VOICE_DOWNLOAD_FREQUENCY, String.valueOf(companyId));
            if (StringUtils.isNullOrEmpty(frequency)) {
                frequency = "0";
            }
            // 查询语音清零使用状态
            String clearObjectJson = redisClient.get(Constant.APPID_ALPHADOG, VOICE_CLEAR_TIMES, String.valueOf(companyId));
            JSONObject jsonObject;
            if (StringUtils.isNotNullOrEmpty(clearObjectJson)) {
                jsonObject = JSONObject.parseObject(clearObjectJson);
            } else {
                // 如果redis中没有，代表本月第一次使用，需初始化并设置过期时间
                jsonObject = initRedisClearTimesKeyExpire(companyId);
            }
            int clearTimes = (int) jsonObject.get("clear_times");
            int warnEmailSendState = (int) jsonObject.get("warn_email_send_state");
            JSONObject returnObject = new JSONObject();
            returnObject.put("clear_times", clearTimes);
            returnObject.put("warn_email_send_state", warnEmailSendState);
            returnObject.put("frequency", Integer.parseInt(frequency));
            return ResponseUtils.success(returnObject);
        } catch (Exception e) {
            logger.error("==============查询语音下载次数失败,companyId:{}================", companyId);
            e.printStackTrace();
        }
        return RespnoseUtil.PROGRAM_EXCEPTION.toResponse();
    }

    /**
     * 语音上传次数已达上限，发送报警邮件
     *
     * @param hrId hrid
     * @return response
     * @author cjm
     * @date 2018/5/16
     */
    public Response sendWarnEmail(int hrId) {
        try {
            HrCompanyDO hrCompanyDO = hrCompanyAccountDao.getHrCompany(hrId);
            if (null == hrCompanyDO) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_NOTEXIST);
            }
            int companyId = hrCompanyDO.getId();
            String companyName = hrCompanyDO.getName();
            String clearObjectJson = redisClient.get(Constant.APPID_ALPHADOG, VOICE_CLEAR_TIMES, String.valueOf(companyId));
            int clearTimes = 0;
            // 0 未发送 1 已发送
            int warnEmailSendState = 1;
            JSONObject jsonObject;
            // 当月第一次发送报警邮件时redis中还没有清零相关数据
            if (StringUtils.isNullOrEmpty(clearObjectJson)) {
                jsonObject = initRedisClearTimesKeyExpire(companyId);
            } else {
                jsonObject = JSONObject.parseObject(clearObjectJson);
                if (1 == (int) jsonObject.get("warn_email_send_state")) {
                    return ResponseUtils.fail(VoiceErrorEnum.VOICE_EMAIL_SEND_ALREADY.getCode(), VoiceErrorEnum.VOICE_EMAIL_SEND_ALREADY.getMsg());
                }
            }
            clearTimes = (int) jsonObject.get("clear_times");
            // 发送报警邮件
            String br = "</br>";
            StringBuilder emailContent = new StringBuilder();
            StringBuilder emailSubject = new StringBuilder();
            emailContent.append("公司 : " + companyName).append(br).append(br);
            emailContent.append("本月可用清零次数 : " + 10).append(br).append(br);
            emailContent.append("本月已使用清零次数 : " + clearTimes).append(br).append(br);
            emailContent.append("剩余可用清零次数 : " + (10 - clearTimes)).append(br);
            emailSubject.append(companyName + "语音上传次数已达上限，请尽快处理。");
            String[] emails = ChatVoiceConstant.WARN_EMAIL_ADDRESS_CS;
            for (String email : emails) {
                EmailSendUtil.sendWarnEmail(email, emailContent.toString(), emailSubject.toString());
            }
            warnEmailSendState = 1;
            jsonObject.put("warn_email_send_state", warnEmailSendState);
            // 将邮件发送状态更新至redis
            redisClient.set(Constant.APPID_ALPHADOG, VOICE_CLEAR_TIMES, String.valueOf(companyId), JSONObject.toJSONString(jsonObject));
            return RespnoseUtil.SUCCESS.toResponse();
        } catch (Exception e) {
            logger.error("==============发送报警邮件失败================");
            e.printStackTrace();
        }
        return RespnoseUtil.PROGRAM_EXCEPTION.toResponse();
    }

    /**
     * 如果是当月第一次使用cleartimes key，初始化该key
     *
     * @param companyId 公司id
     * @return
     * @author cjm
     * @date 2018/5/16
     */
    private JSONObject initRedisClearTimesKeyExpire(int companyId) {
        DateTime now = DateTime.now();
        JSONObject clearObject = new JSONObject();
        clearObject.put("clear_times", 0);
        clearObject.put("warn_email_send_state", 0);
        redisClient.set(Constant.APPID_ALPHADOG, VOICE_CLEAR_TIMES, String.valueOf(companyId), JSONObject.toJSONString(clearObject));
        // 当月剩余时间作为清零记录的过期时间
        int currentMonthMaxDay = now.dayOfMonth().getMaximumValue();
        int currentDayOfMonth = now.getDayOfMonth();
        int clearExpireTime = (currentMonthMaxDay - currentDayOfMonth) * 24 * 60 * 60 + (24 * 60 * 60 * 1000 - now.millisOfDay().get()) / 1000;
        redisClient.expire(Constant.APPID_ALPHADOG, VOICE_CLEAR_TIMES, String.valueOf(companyId), clearExpireTime);
        return clearObject;
    }

    private UserInfoVO getUserInfa(String unioid){
        UserInfoVO userInfoVO = new UserInfoVO();
        List<UserWxUserDO> userWxUserDOS = wxUserDao.getWXUsersByUnionid(unioid);
        List<UserUserRecord> userUserRecords = userUserDao.fetchByUnionid(unioid);
        if (userWxUserDOS.size()!=0 && userWxUserDOS != null){
            //userInfoVO.setAbbreviation(userUserRecords.get(1).getActivation());
        }
        if (userUserRecords != null && userUserRecords.size()!= 0){

        }
        return null;
    }


}
