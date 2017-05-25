package com.moseeker.chat.service;

import com.moseeker.chat.constant.ChatSpeakerType;
import com.moseeker.chat.service.entity.ChatDao;
import com.moseeker.chat.utils.Page;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.chat.struct.*;
import com.moseeker.thrift.gen.dao.struct.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrChatUnreadCountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatListDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by jack on 08/03/2017.
 */
@Service
@CounterIface
public class ChatService {

    Logger logger = LoggerFactory.getLogger(ChatService.class);

    private ChatDao chaoDao = new ChatDao();
    private ThreadPool pool = ThreadPool.Instance;

    private static String AUTO_CONTENT_WITH_HR_NOTEXIST = "我是{companyName}HR，我可以推荐您或者您的朋友加入我们！";
    private static String AUTO_CONTENT_WITH_HR_EXIST = "我是{hrName}，{companyName}HR，我可以推荐您或者您的朋友加入我们！";

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
                                List<HrCompanyDO> companyDOList = (List<HrCompanyDO>) companyFuture.get();
                                if(companyDOList != null && companyDOList.size() > 0) {
                                    Optional<HrCompanyDO> companyDOOptional = companyDOList.stream()
                                            .filter(companyDO -> companyDO.getId() == hrAccountDOOptional.get().getCompanyId()).findAny();
                                    if(companyDOOptional.isPresent()) {
                                        userChatRoomVO.setCompanyLogo(companyDOOptional.get().getLogo());
                                        String companyName;
                                        if(StringUtils.isNotNullOrEmpty(companyDOOptional.get().getAbbreviation())) {
                                            companyName = companyDOOptional.get().getAbbreviation();
                                        } else {
                                            companyName = companyDOOptional.get().getName();
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
                    byte speaker = chatDO.getSpeaker();
                    chatVO.setSpeaker(speaker);
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

    /**
     * 添加聊天内容，并修改未读消息数量
     * @param roomId 聊天室编号
     * @param content 聊天内容
     * @param positionId 职位编号
     * @param speaker 消息发送人标记
     */
    public void saveChat(int roomId, String content, int positionId, byte speaker) {
        logger.info("saveChat roomId:{} content:{}, positionId:{} speaker:{}", roomId, content, positionId, speaker);
        HrWxHrChatDO chatDO = new HrWxHrChatDO();
        String date = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        chatDO.setCreateTime(date);
        chatDO.setContent(content);
        chatDO.setPid(positionId);
        chatDO.setSpeaker(speaker);
        chatDO.setChatlistId(roomId);
        chaoDao.saveChat(chatDO);

        chaoDao.addChatTOChatRoom(chatDO);

        //修改未读消息数量
        pool.startTast(() -> chaoDao.addUnreadCount(roomId, speaker, date));
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
     * @return ResultOfSaveRoomVO
     */
    public ResultOfSaveRoomVO enterChatRoom(int userId, int hrId, int positionId, int roomId, final boolean is_gamma) {
        logger.info("enterChatRoom userId:{} hrId:{}, positionId:{} roomId:{}, is_gamma:{}", userId, hrId, positionId, roomId, is_gamma);
        final ResultOfSaveRoomVO resultOfSaveRoomVO;

        HrWxHrChatListDO chatRoom = chaoDao.getChatRoom(roomId, userId, hrId);
        boolean chatDebut = false;
        if(chatRoom == null) {
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
            }

            //默认清空C端账号的未读消息
            int chatRoomId = chatRoom.getId();
            pool.startTast(() -> chaoDao.clearUserUnreadCount(chatRoomId, hrId, userId));
        } else {
            resultOfSaveRoomVO = new ResultOfSaveRoomVO();
        }
        resultOfSaveRoomVO.setChatDebut(chatDebut);
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
                    positionVO.setSalaryBottom(positionDO.getSalaryBottom());
                    positionVO.setSalaryTop(positionDO.getSalaryTop());
                    positionVO.setUpdateTime(positionDO.getUpdateTime());
                    positionVO.setCity(positionDO.getCity());

                    if(positionDO.getCompanyId() > 0) {
                        HrCompanyDO companyDO = chaoDao.getCompany(positionDO.getCompanyId());
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
        String createTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        chatDO.setCreateTime(createTime);
        String content;
        if(is_gamma) {
            content = String.format(WELCOMES_CONTER, resultOfSaveRoomVO.getUser().getUserName());
        } else {
            if(resultOfSaveRoomVO.getHr() != null && resultOfSaveRoomVO.getPosition() != null) {
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
        chaoDao.saveChat(chatDO);
        logger.info("createChat result:{}", chatDO);
        return chatDO;
    }

    /**
     * 进入聊天室
     * 如果不存在聊天室，则创建聊天室。
     * 创建聊天室时，需要默认添加一条聊天内容，规则如下：
     *  1.如果
     * @param userId 用户编号 user_user.id
     * @param hrId 员工编号 user_hr_account.id
     * @return 聊天室创建信息
     */
    /*public ResultOfSaveRoomVO saveChatRoom(int userId, int hrId) {
        ResultOfSaveRoomVO resultOfSaveRoomVO = new ResultOfSaveRoomVO();

        *//** 设置聊天室信息，并保存到数据库中 *//*
        String createTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        HrWxHrChatListDO chatRoom = new HrWxHrChatListDO();
        chatRoom.setCreateTime(createTime);
        chatRoom.setHraccountId(hrId);
        chatRoom.setSysuserId(userId);
        chatRoom.setStatus(false);
        chatRoom = chaoDao.saveChatRoom(chatRoom);

        if(chatRoom != null) {

            resultOfSaveRoomVO.setRoomId(chatRoom.getId());

            *//** 并行查询职位信息、hr信息、公司信息以及用户信息 *//*
            final int roomId = chatRoom.getId();
            Future positionFuture = pool.startTast(() -> chaoDao.getPosition(roomId));
            Future hrFuture = pool.startTast(() -> chaoDao.getHr(hrId));
            Future userFuture = pool.startTast(() -> chaoDao.getUser(userId));

            *//** 设置职位信息 *//*
            try {
                JobPositionDO positionDO = (JobPositionDO) positionFuture.get();

                if(positionDO != null) {

                    PositionVO positionVO = new PositionVO();
                    positionVO.setPositionId(positionDO.getId());
                    positionVO.setPositionTitle(positionDO.getTitle());
                    positionVO.setSalaryBottom(positionDO.getSalaryBottom());
                    positionVO.setSalaryTop(positionDO.getSalaryTop());
                    positionVO.setUpdateTime(positionDO.getUpdateTime());

                    if(positionDO.getCompanyId() > 0) {
                        HrCompanyDO companyDO = chaoDao.getCompany(positionDO.getCompanyId());
                        String companyName;
                        if(StringUtils.isNullOrEmpty(companyDO.getAbbreviation())) {
                            companyName = companyDO.getAbbreviation();
                        } else {
                            companyName = companyDO.getName();
                        }
                        positionVO.setCompanyName(companyName);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                logger.error(e.getMessage(), e);
            }

            *//** 设置用户信息 *//*
            try {
                UserUserDO userUserDO = (UserUserDO) userFuture.get();
                if(userUserDO != null) {
                    UserVO userVO = new UserVO();
                    userVO.setUserHeadImg(userUserDO.getHeadimg());
                    userVO.setUserId(userId);
                    String name;
                    if(StringUtils.isNotNullOrEmpty(userUserDO.getName())) {
                        name = userUserDO.getName();
                    } else {
                        name = userUserDO.getNickname();
                    }
                    userVO.setUserName(name);
                }
            } catch (InterruptedException | ExecutionException e) {
                logger.error(e.getMessage(), e);
            }

            *//** 设置HR信息 *//*
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

        } else {
            //throw
            //todo 添加出错异常
        }
        return resultOfSaveRoomVO;
    }*/

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
        if(speaker == 0) {
            chatRoom.setUserUnreadCount(0);
            hrChatUnreadCountDO.setUserHaveUnreadMsg((byte)0);
        } else {
            chatRoom.setHrUnreadCount(0);
            hrChatUnreadCountDO.setHrHaveUnreadMsg((byte)0);
        }
        chaoDao.updateChatRoom(chatRoom);
        chaoDao.updateChatUnreadCount(hrChatUnreadCountDO);
    }
}
