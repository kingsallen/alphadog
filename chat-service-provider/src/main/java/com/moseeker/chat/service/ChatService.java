package com.moseeker.chat.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.db.hrdb.tables.records.HrChatUnreadCountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatListRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatRecord;
import com.moseeker.chat.constant.ChatOrigin;
import com.moseeker.chat.constant.ChatSpeakerType;
import com.moseeker.chat.service.entity.ChatDao;
import com.moseeker.chat.utils.Page;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChatMsgType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.chat.struct.*;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrChatUnreadCountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatListDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by jack on 08/03/2017.
 */
@Service
@CounterIface
@Transactional
public class ChatService {

    Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatDao chaoDao;

    @Autowired
    private UserHrAccountDao hrAccountDao;

    private ThreadPool pool = ThreadPool.Instance;

    private static String AUTO_CONTENT_WITH_HR_NOTEXIST = "您好，我是{companyName}HR，关于职位和公司信息有任何问题请随时和我沟通。";
    private static String AUTO_CONTENT_WITH_HR_EXIST = "您好，我是{hrName}，{companyName}HR，关于职位和公司信息有任何问题请随时和我沟通。";

    /** 聊天页面欢迎语 **/
    private static String WELCOMES_CONTER = "亲爱的%s：\n" +
            "\t仟寻终于等到你啦。你可以在这里直接联系企业的HR，也可以直接和仟寻互动哦。\n" +
            "\t祝你早日找到心仪的公司，加入令人振奋的团队，再次在你的职业之旅上迈步前行！\n";

    /**
     * HR查找聊天室列表
     * //todo hr_chat_unread_count的初始化脚本和定时更新脚本
     * @param hrId HR编号 user_hr_account.id
     * @param pageNo 页码
     * @param pageSize 每页信息数量
     * @return 聊天室分页信息
     */
    public HRChatRoomsVO listHRChatRoom(int hrId, int pageNo, int pageSize) {
        logger.info("listHRChatRoom hrId:{}, pageNo:{} pageSize:{}", hrId, pageNo, pageSize);
        HRChatRoomsVO rooms = new HRChatRoomsVO();
        int count = chaoDao.countHRChatRoom(hrId);
        Page page = new Page(pageNo, pageSize, count);
        rooms.setPageNo(page.getPageNo());
        rooms.setPageSize(page.getPageSize());
        rooms.setTotalPage(page.getTotalPage());
        rooms.setTotalRow(page.getTotalRow());
        if(count > 0) {
            List<HrChatUnreadCountDO> chatUnreadCountDOlist = chaoDao.listChatRoomUnreadCount(ChatSpeakerType.HR, hrId,
                    page.getPageNo(), page.getPageSize());
            if(chatUnreadCountDOlist != null && chatUnreadCountDOlist.size() > 0) {
                int[] roomIdArray = chatUnreadCountDOlist.stream().mapToInt(chatUnreadCountDO -> chatUnreadCountDO.getRoomId()).toArray();
                int[] userIdArray = chatUnreadCountDOlist.stream().mapToInt(chatUnreadCountDO -> chatUnreadCountDO.getUserId()).toArray();
                Future chatRoomsFuture = pool.startTast(() -> chaoDao.listChatRoom(roomIdArray));
                Future usersFuture = pool.startTast(() -> chaoDao.listUsers(userIdArray));

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
                    if(chatRoomList != null && chatRoomList.size() > 0) {
                        Optional<HrWxHrChatListDO> chatRoomDOOptional = chatRoomList.stream()
                                .filter(chatRoom -> chatRoom.getId() == chatUnreadCountDO.getRoomId()).findFirst();
                        if(chatRoomDOOptional.isPresent()) {
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
                    if(userList != null && userList.size() > 0) {
                        Optional<UserUserDO> userUserDOOptional = userList.stream()
                                .filter(userUserDO -> userUserDO.getId() == chatUnreadCountDO.getUserId()).findAny();
                        if(userUserDOOptional.isPresent()) {
                            hrChatRoomVO.setHeadImgUrl(userUserDOOptional.get().getHeadimg());
                            String name = StringUtils.isNotNullOrEmpty(userUserDOOptional.get().getName())
                                    ? userUserDOOptional.get().getName():userUserDOOptional.get().getNickname();
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
        List<Integer> chatUserIdList = chaoDao.fetchUserIdByHrId(hrId, apply);

        if (chatUserIdList.size() > 0) {

            List<Integer> userIdList;
            if (org.apache.commons.lang.StringUtils.isNotBlank(keyword)) {
                userIdList = chaoDao.findUserIdByName(keyword, chatUserIdList);
            } else {
                userIdList = chatUserIdList;
            }

            if (userIdList.size() > 0) {
                Timestamp update = null;
                if (roomId > 0) {
                    HrChatUnreadCountRecord room = chaoDao.fetchRoomById(roomId);
                    if (room != null) {
                        update = room.getUpdateTime();
                    }
                }

                int total = chaoDao.countRoom(hrId, userIdList, apply);
                hrChatRoomsIndexVO.setTotalRow(total);
                List<HrChatUnreadCountRecord> roomList = chaoDao.fetchRooms(hrId, userIdList, apply, update, pageSize);

                if (roomList.size() > 0) {

                    List<HRChatRoomVO> rooms = new ArrayList<>();

                    int[] roomIdArray = roomList.stream().mapToInt(chatUnreadCountDO -> chatUnreadCountDO.getRoomId()).toArray();
                    int[] userIdArray = roomList.stream().mapToInt(chatUnreadCountDO -> chatUnreadCountDO.getUserId()).toArray();

                    Future chatRoomsFuture = pool.startTast(() -> chaoDao.listChatRoom(roomIdArray));
                    Future usersFuture = pool.startTast(() -> chaoDao.listUsers(userIdArray));

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
                        hrChatRoomVO.setApply(hrChatUnreadCountRecord.getApply() == 1 ? true:false);

                        /** 匹配聊天室的创建时间和状态 */
                            Optional<HrWxHrChatListDO> chatRoomDOOptional = chatRoomList.stream()
                                    .filter(chatRoom -> chatRoom.getId() == hrChatUnreadCountRecord.getRoomId()).findFirst();
                            if(chatRoomDOOptional.isPresent()) {
                                hrChatRoomVO.setCreateTime(chatRoomDOOptional.get().getUpdateTime());
                                hrChatRoomVO.setUnReadNum(chatRoomDOOptional.get().getHrUnreadCount());
                            }

                        /** 匹配C端用户的名称和头像 */
                        if(userList != null && userList.size() > 0) {
                            Optional<UserUserDO> userUserDOOptional = userList.stream()
                                    .filter(userUserDO -> userUserDO.getId() == hrChatUnreadCountRecord.getUserId()).findAny();
                            if(userUserDOOptional.isPresent()) {
                                hrChatRoomVO.setHeadImgUrl(userUserDOOptional.get().getHeadimg());
                                String name = StringUtils.isNotNullOrEmpty(userUserDOOptional.get().getName())
                                        ? userUserDOOptional.get().getName():userUserDOOptional.get().getNickname();
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
        HrChatUnreadCountRecord record = chaoDao.fetchRoomById(roomId);
        if (record != null && record.getHrId() == hrId) {
            roomVO = new HRChatRoomVO();
            roomVO.setId(roomId);
            roomVO.setApply(record.getApply() == 1 ? true:false);
            roomVO.setUserId(record.getUserId());
            HrWxHrChatListDO chatListDO = chaoDao.getChatRoom(roomId, 0, 0);
            if (chatListDO != null) {
                roomVO.setCreateTime(chatListDO.getUpdateTime());
                roomVO.setUnReadNum(chatListDO.getHrUnreadCount());
            }
            UserUserDO userUserDO = chaoDao.getUser(record.getUserId());
            if (userUserDO != null) {
                String name = StringUtils.isNotNullOrEmpty(userUserDO.getName())
                        ? userUserDO.getName():userUserDO.getNickname();
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
     * @param userId 用户编号
     * @param pageNo 页码
     * @param pageSize 分页信息
     * @return 聊天室分页信息
     */
    public UserChatRoomsVO listUserChatRoom(int userId, int pageNo, int pageSize) {
        logger.info("userChatRoomsVO userId:{}, pageNo:{} pageSize:{}", userId, pageNo, pageSize);
        UserChatRoomsVO userChatRoomsVO = new UserChatRoomsVO();

        //计算数量的操作理论上是最快的，所以用它去判断是否有聊天室
        int count = chaoDao.countUserChatRoom(userId);
        Page page = new Page(pageNo, pageSize, count);
        userChatRoomsVO.setPageNo(page.getPageNo());
        userChatRoomsVO.setPageSize(page.getPageSize());
        userChatRoomsVO.setTotalPage(page.getTotalPage());
        userChatRoomsVO.setTotalRow(page.getTotalRow());

        if(count > 0) {
            //按照聊天室未读消息倒序查询聊天室信息
            List<HrChatUnreadCountDO> chatUnreadCountDOlist = chaoDao.listChatRoomUnreadCount(ChatSpeakerType.USER,
                    userId, page.getPageNo(), page.getPageSize());
            if(chatUnreadCountDOlist != null && chatUnreadCountDOlist.size() > 0) {
                int[] roomIdArray = chatUnreadCountDOlist.stream().mapToInt(chatUnreadCountDO -> chatUnreadCountDO.getRoomId()).toArray();
                int[] hrIdArray = chatUnreadCountDOlist.stream().mapToInt(chatUnreadCountDO -> chatUnreadCountDO.getHrId()).toArray();

                logger.info("roomIdArray:{}", roomIdArray);
                logger.info("hrIdArray:{}", hrIdArray);

                /** 异步查找聊天室内容，HR信息，HR所属的公司信息 */
                Future chatRoomsFuture = pool.startTast(() -> chaoDao.listChatRoom(roomIdArray));
                Future hrsFuture = pool.startTast(() -> chaoDao.listHr(hrIdArray));
                Future companyFuture = pool.startTast(() -> chaoDao.listCompany(hrIdArray));

                List<UserChatRoomVO> userChatRoomVOList = new ArrayList<>();
                chatUnreadCountDOlist.forEach(hrChatUnreadCountDO -> {
                    UserChatRoomVO userChatRoomVO = new UserChatRoomVO();
                    userChatRoomVO.setId(hrChatUnreadCountDO.getRoomId());

                    /** 匹配聊天室的状态的和最后发送消息的时间 */
                    try {
                        List<HrWxHrChatListDO> chatRooms = (List<HrWxHrChatListDO>) chatRoomsFuture.get();
                        if(chatRooms != null && chatRooms.size() > 0) {
                            Optional<HrWxHrChatListDO> chatRoomOptional = chatRooms.stream()
                                    .filter(chatRoom -> chatRoom.getId() == hrChatUnreadCountDO.getRoomId()).findFirst();
                            if(chatRoomOptional.isPresent()) {
                                userChatRoomVO.setCreateTime(chatRoomOptional.get().getUpdateTime());
                                userChatRoomVO.setUnReadNum(chatRoomOptional.get().getUserUnreadCount());
                            }
                        }

                    } catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage(), e);
                    }

                    /** 匹配HR的编号、头像和名称 */
                    try {
                        List<UserHrAccountDO> hrAccountDOList = (List<UserHrAccountDO>) hrsFuture.get();
                        if(hrAccountDOList != null && hrAccountDOList.size() > 0) {

                            Optional<UserHrAccountDO> hrAccountDOOptional = hrAccountDOList.stream()
                                    .filter(hrAccountDO -> hrAccountDO.getId() == hrChatUnreadCountDO.getHrId()).findAny();
                            if(hrAccountDOOptional.isPresent()) {
                                userChatRoomVO.setHrId(hrAccountDOOptional.get().getId());
                                userChatRoomVO.setName(hrAccountDOOptional.get().getUsername());
                                userChatRoomVO.setHeadImgUrl(hrAccountDOOptional.get().getHeadimgurl());

                                /** 根据HR所属公司，匹配公司的名称和logo */
                                Map<Integer, HrCompanyDO> companyDOMap = (Map<Integer, HrCompanyDO>) companyFuture.get();
                                if(companyDOMap != null && companyDOMap.size() > 0) {
                                    if(companyDOMap.containsKey(hrAccountDOOptional.get().getId()) && companyDOMap.get(hrAccountDOOptional.get().getId()).getId() > 0) {
                                        userChatRoomVO.setCompanyLogo(companyDOMap.get(hrAccountDOOptional.get().getId()).getLogo());
                                        String companyName;
                                        if(StringUtils.isNotNullOrEmpty(companyDOMap.get(hrAccountDOOptional.get().getId()).getAbbreviation())) {
                                            companyName = companyDOMap.get(hrAccountDOOptional.get().getId()).getAbbreviation();
                                        } else {
                                            companyName = companyDOMap.get(hrAccountDOOptional.get().getId()).getName();
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
     * @param roomId 聊天室编号
     * @param pageNo 页码
     * @param pageSize 每页显示的数量
     * @return
     */
    public ChatsVO listChatLogs(int roomId, int pageNo, int pageSize) {
        logger.info("listChatLogs roomId:{} pageNo:{}, pageSize:{}", roomId, pageNo, pageSize);
        ChatsVO chatsVO = new ChatsVO();

        int count = 0;
        Future<Integer> countFuture = pool.startTast(() -> chaoDao.countChatLog(roomId));
        Future chatFuture = pool.startTast(() -> chaoDao.listChat(roomId, pageNo, pageSize));
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
            List<HrWxHrChatDO> chatDOList = (List<HrWxHrChatDO>) chatFuture.get();
            if(chatDOList != null && chatDOList.size() > 0) {
                List<ChatVO> chatVOList = new ArrayList<>();
                chatDOList.forEach(chatDO -> {
                    ChatVO chatVO = new ChatVO();
                    chatVO.setId(chatDO.getId());
                    chatVO.setContent(chatDO.getContent());
                    chatVO.setCreate_time(chatDO.getCreateTime());

                    chatVO.setMsgType(chatDO.getMsgType());
                    chatVO.setPicUrl(chatDO.getPicUrl());
                    chatVO.setBtnContent(chatDO.getBtnContent());

                    byte speaker = chatDO.getSpeaker();
                    chatVO.setSpeaker(speaker);

                    Optional<ChatOrigin> chatOriginOptional = ChatOrigin.instanceFromValue(chatDO.getOrigin());
                    if (chatOriginOptional.isPresent()) {
                        chatVO.setOrigin(chatOriginOptional.get().getValue());
                        chatVO.setOrigin_str(chatOriginOptional.get().getName());
                    } else {
                        chatVO.setOrigin(ChatOrigin.Human.getValue());
                        chatVO.setOrigin_str(ChatOrigin.Human.getName());
                    }



                    chatVOList.add(chatVO);
                });
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

    private HrWxHrChatListDO requiredNotNullChatRoom(int roomId) throws BIZException {
        HrWxHrChatListDO chatRoom = chaoDao.getChatRoomById(roomId);
        if(chatRoom == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.CHAT_ROOM_NOT_EXIST);
        }
        return chatRoom;
    }

    private UserHrAccountDO requiredNotNullHr(int hraccountId) throws BIZException {
        UserHrAccountDO hrAccountDO = hrAccountDao.getValidAccount(hraccountId);
        if(hrAccountDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.USERACCOUNT_NOTEXIST);
        }
        return hrAccountDO;
    }

    private ChatVO requiredValidChat(ChatVO obj) throws BIZException {
        if (obj == null) {
            logger.error("null ChatVO");
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }

        if (StringUtils.isNullOrEmpty(obj.getContent()) || StringUtils.isNullOrEmpty(obj.getContent().trim())){
            logger.error("empty content ChatVO:{}",obj);
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }

        return obj;
    }

    /**
     * 添加聊天内容，并修改未读消息数量
     * @param chat 聊天信息
     */
    public int saveChat(ChatVO chat) throws BIZException {
        /*requiredValidChat(chat);

        HrWxHrChatListDO chatRoom = requiredNotNullChatRoom(chat.getRoomId());

        requiredNotNullHr(chatRoom.getHraccountId());*/

        logger.info("saveChat chat:{}", JSON.toJSONString(chat));
        HrWxHrChatDO chatDO = new HrWxHrChatDO();
        String date;
        if (org.apache.commons.lang.StringUtils.isNotBlank(chat.getCreate_time())) {
            date = chat.getCreate_time();
            chatDO.setCreateTime(date);
        } else {
            date = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        }
        chatDO.setPid(chat.getPositionId());
        chatDO.setSpeaker(chat.getSpeaker());
        chatDO.setChatlistId(chat.getRoomId());
        chatDO.setOrigin(chat.getOrigin());
        chatDO.setMsgType(chat.getMsgType());
        chatDO.setContent(chat.getContent());
        chatDO.setPicUrl(chat.getPicUrl());
        chatDO.setBtnContent(JSON.toJSONString(chat.getBtnContent()));

        logger.info("saveChat before saveChat chatDO:{}", chatDO);
        int result=0;
        chatDO=chaoDao.saveChat(chatDO);
        if(chatDO!=null){
            result=chatDO.getId();
        }
        logger.info("saveChat after saveChat chatDO:{}", chatDO);
        chaoDao.addChatTOChatRoom(chatDO);

        //修改未读消息数量
        pool.startTast(() -> chaoDao.addUnreadCount(chat.getRoomId(), chat.getSpeaker(), date));
        return result;
    }

    /**
     * 进入聊天室
     * 如果不存在聊天室，则创建聊天室。
     * 创建聊天室时，需要默认添加一条聊天内容，规则如下：
     *  1.如果HR的名称不存在，则存储 "我是{companyName}HR，我可以推荐您或者您的朋友加入我们！"
     *  2.如果HR的名称存在，则存储 "我是{hrName}，{companyName}HR，我可以推荐您或者您的朋友加入我们！"
     *  默认是c端账号进入聊天室，需要清空聊天该聊天室的未读消息
     * @param userId 用户编号
     * @param hrId HR编号
     * @param positionId 职位编号
     * @param roomId 聊天室编号
     * @param is_gamma 是否从聚合号进入
     * @return ResultOfSaveRoomVO
     */
    public ResultOfSaveRoomVO enterChatRoom(int userId, int hrId, int positionId, int roomId, final boolean is_gamma) {
        logger.info("enterChatRoom userId:{} hrId:{}, positionId:{} roomId:{}, is_gamma:{}", userId, hrId, positionId, roomId, is_gamma);
        final ResultOfSaveRoomVO resultOfSaveRoomVO;

        //检测HR是否存在
        boolean isHrDelete=false;
        UserHrAccountDO hrAccountDO = hrAccountDao.getValidAccount(hrId);
        if(hrAccountDO==null){
            isHrDelete=true;
        }

        HrWxHrChatListDO chatRoom = chaoDao.getChatRoom(roomId, userId, hrId);
        boolean chatDebut = false;
        if(chatRoom == null && !isHrDelete) {
            chatRoom = new HrWxHrChatListDO();
            String createTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
            chatRoom.setCreateTime(createTime);
            chatRoom.setHraccountId(hrId);
            chatRoom.setSysuserId(userId);
            chatRoom = chaoDao.saveChatRoom(chatRoom);
            chatDebut = true;
        }

        if(chatRoom != null) {
            resultOfSaveRoomVO = searchResult(chatRoom, positionId);
            if(chatDebut) {
                pool.startTast(() -> createChat(resultOfSaveRoomVO, is_gamma));
                resultOfSaveRoomVO.setChatDebut(chatDebut);
                HrChatUnreadCountDO unreadCountDO = new HrChatUnreadCountDO();
                unreadCountDO.setHrId(hrId);
                unreadCountDO.setUserId(userId);
                unreadCountDO.setHrHaveUnreadMsg((byte)0);
                unreadCountDO.setUserHaveUnreadMsg((byte)1);
                unreadCountDO.setRoomId(chatRoom.getId());
                pool.startTast(() -> chaoDao.saveUnreadCount(unreadCountDO));
            } else {
                //默认清空C端账号的未读消息
                int chatRoomId = chatRoom.getId();
                pool.startTast(() -> chaoDao.clearUserUnreadCount(chatRoomId, hrId, userId));
            }
        } else {
            resultOfSaveRoomVO = new ResultOfSaveRoomVO();
            resultOfSaveRoomVO.setHr(new HrVO());
        }


        resultOfSaveRoomVO.setChatDebut(chatDebut);
        if(resultOfSaveRoomVO.getHr() == null){
            resultOfSaveRoomVO.setHr(new HrVO());
        }
        resultOfSaveRoomVO.getHr().setIsDelete(isHrDelete);
        logger.info("enterChatRoom result:{}", resultOfSaveRoomVO);
        return resultOfSaveRoomVO;
    }

    /**
     * 查找返回值
     * @param chatRoom 聊天室
     * @param positionId 职位编号
     * @return
     */
    private ResultOfSaveRoomVO searchResult(HrWxHrChatListDO chatRoom, int positionId) {
        logger.info("searchResult HrWxHrChatListDO:{} positionId:{}", chatRoom, positionId);
        ResultOfSaveRoomVO resultOfSaveRoomVO = new ResultOfSaveRoomVO();
        resultOfSaveRoomVO.setRoomId(chatRoom.getId());

        /** 并行查询职位信息、hr信息、公司信息以及用户信息 */

        Future positionFuture = null;
        if(positionId > 0) {
            positionFuture = pool.startTast(() -> chaoDao.getPositionById(positionId));
        }
        Future hrFuture = pool.startTast(() -> chaoDao.getHr(chatRoom.getHraccountId()));
        Future userFuture = pool.startTast(() -> chaoDao.getUser(chatRoom.getSysuserId()));

        /** 设置职位信息 */
        try {
            if(positionFuture != null) {
                JobPositionDO positionDO = (JobPositionDO) positionFuture.get();

                if(positionDO != null) {

                    PositionVO positionVO = new PositionVO();
                    positionVO.setPositionId(positionDO.getId());
                    positionVO.setPositionTitle(positionDO.getTitle());
                    positionVO.setSalaryBottom((int)positionDO.getSalaryBottom());
                    positionVO.setSalaryTop((int)positionDO.getSalaryTop());
                    positionVO.setUpdateTime(positionDO.getUpdateTime());
                    positionVO.setCity(positionDO.getCity());

                    if(positionDO.getCompanyId() > 0) {
                        HrCompanyDO companyDO = chaoDao.getCompany(positionDO.getPublisher());
                        String companyName;
                        if(StringUtils.isNotNullOrEmpty(companyDO.getAbbreviation())) {
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
            if(userUserDO != null) {
                UserVO userVO = new UserVO();
                userVO.setUserId(userUserDO.getId());
                userVO.setUserHeadImg(userUserDO.getHeadimg());
                userVO.setUserId(chatRoom.getSysuserId());
                String name;
                if(StringUtils.isNotNullOrEmpty(userUserDO.getName())) {
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
            if(hrAccountDO != null) {
                HrVO hrVO = new HrVO();
                hrVO.setHrId(hrAccountDO.getId());
                hrVO.setHrName(hrAccountDO.getUsername());
                hrVO.setHrHeadImg(hrAccountDO.getHeadimgurl());
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
    private HrWxHrChatDO createChat(ResultOfSaveRoomVO resultOfSaveRoomVO, boolean is_gamma) {

        logger.info("createChat ResultOfSaveRoomVO:{}, is_gamma:{}", resultOfSaveRoomVO, is_gamma);
        //1.如果HR的名称不存在，则存储 "我是{companyName}HR，我可以推荐您或者您的朋友加入我们！"
        //2.如果HR的名称存在，则存储 "我是{hrName}，{companyName}HR，我可以推荐您或者您的朋友加入我们！"
        HrWxHrChatDO chatDO = new HrWxHrChatDO();
        chatDO.setChatlistId(resultOfSaveRoomVO.getRoomId());
        chatDO.setSpeaker((byte)1);
        chatDO.setOrigin(ChatOrigin.System.getValue());
        String createTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        chatDO.setCreateTime(createTime);
        String content;
        if(is_gamma) {
            content = String.format(WELCOMES_CONTER, resultOfSaveRoomVO.getUser().getUserName());
        } else {
            if(resultOfSaveRoomVO.getHr() != null
                    && StringUtils.isNotNullOrEmpty(resultOfSaveRoomVO.getHr().getHrName())
                    && resultOfSaveRoomVO.getPosition() != null) {
                content = AUTO_CONTENT_WITH_HR_EXIST.replace("{hrName}", resultOfSaveRoomVO.getHr()
                        .getHrName()).replace("{companyName}", resultOfSaveRoomVO.getPosition().getCompanyName());
            } else {
                content = AUTO_CONTENT_WITH_HR_NOTEXIST
                        .replace("{companyName}", resultOfSaveRoomVO.getPosition().getCompanyName());
            }
        }
        chatDO.setContent(content);
        if(resultOfSaveRoomVO.getPosition() != null) {
            chatDO.setPid(resultOfSaveRoomVO.getPosition().getPositionId());
        }
        chatDO.setMsgType(ChatMsgType.HTML.value());
        chaoDao.saveChat(chatDO);
        logger.info("createChat result:{}", chatDO);
        return chatDO;
    }

    /**
     * 获取聊天内容
     * @param roomId 聊天室编号
     * @param speaker 聊天者
     * @return
     */
    public ChatVO getChat(int roomId, byte speaker) {
        ChatVO chatVO = new ChatVO();

        return chatVO;
    }

    /**
     * 退出聊天室
     * @param roomId 聊天室编号
     * @param speaker 退出聊天室用户的类型 0表示用户，1表示HR
     */
    public void leaveChatRoom(int roomId, byte speaker) {
        logger.debug("leaveChatRoom roomId:{} speaker:{}", roomId, speaker);
        HrWxHrChatListDO chatRoom = new HrWxHrChatListDO();
        chatRoom.setId(roomId);

        HrChatUnreadCountDO hrChatUnreadCountDO = new HrChatUnreadCountDO();
        hrChatUnreadCountDO.setRoomId(roomId);
        String leaveTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        if(speaker == 0) {
            chatRoom.setUserUnreadCount(0);
            hrChatUnreadCountDO.setUserHaveUnreadMsg((byte)0);
            hrChatUnreadCountDO.setWxChatTime(leaveTime);
            chatRoom.setWxChatTime(leaveTime);
        } else {
            chatRoom.setHrUnreadCount(0);
            hrChatUnreadCountDO.setHrHaveUnreadMsg((byte)0);
            hrChatUnreadCountDO.setHrChatTime(leaveTime);
            chatRoom.setHrChatTime(leaveTime);
        }
        chaoDao.updateChatRoom(chatRoom);
        chaoDao.updateChatUnreadCount(hrChatUnreadCountDO);
    }

    /**
     * 查询聊天室最后聊天记录
     * @param roomIdList 聊天室列表
     * @return 聊天记录列表
     * @throws CommonException
     */
    public List<ChatVO> listLastMessage(List<Integer> roomIdList) throws CommonException {

        return chaoDao.listLastMessage(roomIdList);
    }

    /**
     * 查询聊天历史记录
     * @param roomId 聊天室
     * @param chatId 聊天记录
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
        int count = chaoDao.countMessage(roomId, chatId);
        if (count >= 0) {
            List<ChatVO> chatVOList = chaoDao.listMessage(roomId, chatId, pageSize);
            if (chatVOList != null && chatVOList.size() > 0) {
                chatVOList.forEach(chatVO -> {
                    if (chatVO.getSpeaker() == 2) {
                        chatVO.setSpeaker((byte) 1);
                    }
                });
            }
            chatHistory.setChatList(chatVOList);
            if (count > chatVOList.size()) {
                chatHistory.setHasMore(true);
            } else {
                chatHistory.setHasMore(false);
            }
        }
        HrChatUnreadCountRecord hrChatUnreadCountRecord = chaoDao.fetchRoomById(roomId);
        logger.info("listMessage hrChatUnreadCountRecord:{}", hrChatUnreadCountRecord);
        if (hrChatUnreadCountRecord != null ) {
            if (hrChatUnreadCountRecord.getUpdateTime() != null) {
                chatHistory.setHrLeaveTime(
                        new DateTime(hrChatUnreadCountRecord.getHrChatTime()).toString("YYYY-MM-dd HH:mm:sss"));
            }
            if (hrChatUnreadCountRecord.getUserId() != null) {
                UserUserDO userUserDO = chaoDao.getUser(hrChatUnreadCountRecord.getUserId());
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
            chaoDao.updateChatRoom(hrChatUnreadCountRecord);

            HrWxHrChatListRecord hrWxHrChatListRecord = new HrWxHrChatListRecord();
            hrWxHrChatListRecord.setId(hrChatUnreadCountRecord.getRoomId());
            hrWxHrChatListRecord.setHrUnreadCount(0);
            chaoDao.updateChatRoom(hrWxHrChatListRecord);
        } else {
            HrWxHrChatRecord chatRecord = chaoDao.getChat(chatId);
            if (chatRecord != null) {
                logger.info("ChatService updateLeaveTime chatRecord:{}, chatId:{}", chatRecord, chatId);
                if (hrChatUnreadCountRecord.getHrChatTime() == null || hrChatUnreadCountRecord.getHrChatTime().getTime() < chatRecord.getCreateTime().getTime()) {
                    hrChatUnreadCountRecord.setHrChatTime(chatRecord.getCreateTime());
                    hrChatUnreadCountRecord.setHrHaveUnreadMsg((byte) 0);
                    chaoDao.updateChatRoom(hrChatUnreadCountRecord);

                    HrWxHrChatListRecord hrWxHrChatListRecord = new HrWxHrChatListRecord();
                    hrWxHrChatListRecord.setId(hrChatUnreadCountRecord.getRoomId());
                    hrWxHrChatListRecord.setHrUnreadCount(0);
                    chaoDao.updateChatRoom(hrWxHrChatListRecord);
                }
            }
        }
    }

    public List<String> getChatSug(int hrId, boolean applied, String keyword) {
        List<String> userNames = new ArrayList<>();

        List<Integer> chatUserIdList = chaoDao.fetchUserIdByHrId(hrId, applied);
        if (chatUserIdList != null && chatUserIdList.size() > 0) {
            userNames = chaoDao.findUserNameByKeyword(keyword, chatUserIdList);
        }
        return userNames;
    }

    public int getHRUnreadCount(int hrId) {
        return chaoDao.countUnreadMessage(hrId);
    }

    public HrVO getHrInfo(int roomId) {
        HrVO hrVO = new HrVO();
        HrWxHrChatListDO chatRoom = chaoDao.getChatRoom(roomId, 0, 0);
        if (chatRoom != null && chatRoom.getHraccountId() > 0) {
            UserHrAccountDO hr = chaoDao.getHr(chatRoom.getHraccountId());
            if (hr != null) {
                hrVO.setHrId(hr.getId());
                hrVO.setHrHeadImg(hr.getHeadimgurl());
                hrVO.setHrName(hr.getUsername());
            }
        }
        return hrVO;
    }

    public void updateApplyStatus(int userId, int positionId) {
        logger.info("ChatService updateApplyStatus userId:{}, positionId:{}",userId, positionId);
        int publisher = chaoDao.fetchPublisher(positionId);
        logger.info("ChatService updateApplyStatus publisher: {}", publisher);
        if (publisher > 0) {
            chaoDao.updateApplyStatus(publisher, userId);
        }
        UserHrAccountDO userHrAccountDO = chaoDao.getHr(publisher);
        if (userHrAccountDO != null && userHrAccountDO.getAccountType() == HRAccountType.SubAccount.getType()) {
            int accountId = chaoDao.fetchSuperAccount(userHrAccountDO.getId());
            if (accountId > 0 && accountId != publisher) {
                chaoDao.updateApplyStatus(accountId, userId);
            }
        }
    }

    public void roleLeaveChatRoom(int roleId, byte speaker) {
        logger.info("ChatService roleLeaveChatRoom roleId:{}, speaker:{}",roleId, speaker);
        if (chaoDao.roleExist(roleId, speaker)) {
            List<Integer> roomIdList = chaoDao.fetchRoomIdByRole(roleId, speaker);
            logger.info("ChatService roleLeaveChatRoom roomIdList::{}",roomIdList);
            if (roomIdList != null && roomIdList.size() > 0) {
                pool.startTast(() -> {
                    logger.info("ChatService roleLeaveChatRoom roomIdList::{}",roomIdList);
                    roomIdList.forEach(roomId -> leaveChatRoom(roomId, speaker));
                    return true;
                });
            }
        }
    }
}
