package com.moseeker.chat.service;

import com.moseeker.chat.constant.ChatSpeakerType;
import com.moseeker.chat.service.entity.ChatDao;
import com.moseeker.chat.utils.Page;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.chat.struct.*;
import com.moseeker.thrift.gen.dao.struct.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by jack on 08/03/2017.
 */
@Service
public class ChatService {

    Logger logger = LoggerFactory.getLogger(ChatService.class);

    private ChatDao chaoDao = new ChatDao();
    private ThreadPool pool = ThreadPool.Instance;

    /**
     * HR查找聊天室列表
     * //todo hr_chat_unread_count的初始化脚本和定时更新脚本
     * @param hrId HR编号 user_hr_account.id
     * @param pageNo 页码
     * @param pageSize 每页信息数量
     * @return 聊天室分页信息
     */
    public HRChatRoomsVO listHRChatRoom(int hrId, int pageNo, int pageSize) {
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
                chatUnreadCountDOlist.forEach(chatUnreadCountDO -> {
                    HRChatRoomVO hrChatRoomVO = new HRChatRoomVO();
                    hrChatRoomVO.setId(chatUnreadCountDO.getRoomId());
                    hrChatRoomVO.setUserId(chatUnreadCountDO.getUserId());
                    hrChatRoomVO.setUnReadNum(chatUnreadCountDO.getHrUnreadCount());

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
                            hrChatRoomVO.setCreateTime(chatRoomDOOptional.get().getHrChatTime());
                            int status = chatRoomDOOptional.get().isStatus()?1:0;
                            hrChatRoomVO.setStatus(status);
                        }
                    }
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
                            String name = StringUtils.isNullOrEmpty(userUserDOOptional.get().getName())
                                    ? userUserDOOptional.get().getName():userUserDOOptional.get().getNickname();
                            hrChatRoomVO.setName(name);
                        }
                    }
                });
            }
        }

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

                /** 异步查找聊天室内容，HR信息，HR所属的公司信息 */
                Future chatRoomsFuture = pool.startTast(() -> chaoDao.listChatRoom(roomIdArray));
                Future hrsFuture = pool.startTast(() -> chaoDao.listHr(hrIdArray));
                Future companyFuture = pool.startTast(() -> chaoDao.listCompany(hrIdArray));

                chatUnreadCountDOlist.forEach(hrChatUnreadCountDO -> {
                    UserChatRoomVO userChatRoomVO = new UserChatRoomVO();
                    userChatRoomVO.setId(hrChatUnreadCountDO.getRoomId());
                    userChatRoomVO.setUnReadNum(hrChatUnreadCountDO.getUserUnreadCount());

                    /** 匹配聊天室的状态的和最后发送消息的时间 */
                    try {
                        List<HrWxHrChatListDO> chatRooms = (List<HrWxHrChatListDO>) chatRoomsFuture.get();
                        if(chatRooms != null && chatRooms.size() > 0) {
                            Optional<HrWxHrChatListDO> chatRoomOptional = chatRooms.stream()
                                    .filter(chatRoom -> chatRoom.getId() == hrChatUnreadCountDO.getRoomId()).findFirst();
                            if(chatRoomOptional.isPresent()) {
                                int status = chatRoomOptional.get().isStatus() ? 1:0;
                                userChatRoomVO.setStatus(status);
                                userChatRoomVO.setCreateTime(chatRoomOptional.get().getWxChatTime());
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
                                        if(StringUtils.isNullOrEmpty(companyDOOptional.get().getAbbreviation())) {
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

                });
            }
        }

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
        ChatsVO chatsVO = new ChatsVO();

        int count = 0;
        Future<Integer> countFuture = pool.startTast(() -> chaoDao.countChatLog(roomId));
        Future chatFuture = pool.startTast(() -> chaoDao.listChat(roomId));
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
                    byte speaker = (byte) (chatDO.isSpeaker() ? 1:0);
                    chatVO.setSpeaker(speaker);

                    chatVOList.add(chatVO);
                });
                chatsVO.setChatLogs(chatVOList);
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }
        return chatsVO;
    }

    /**
     * 添加聊天内容
     * @param roomId 聊天室编号
     * @param content 聊天内容
     * @param positionId 职位编号
     * @param speaker 消息发送人标记
     */
    public void saveChat(int roomId, String content, int positionId, byte speaker) {
        HrWxHrChatDO chatDO = new HrWxHrChatDO();
        String date = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        chatDO.setCreateTime(date);
        chatDO.setContent(content);
        chatDO.setPid(positionId);
        boolean spk = speaker == 0 ? false : true;
        chatDO.setSpeaker(spk);
        chatDO.setChatlistId(roomId);
        chaoDao.saveChat(chatDO);
    }

    /**
     * 创建聊天室
     * @param userId 用户编号 user_user.id
     * @param hrId 员工编号 user_hr_account.id
     * @return 聊天室创建信息
     */
    public ResultOfSaveRoomVO saveChatRoom(int userId, int hrId) {
        ResultOfSaveRoomVO resultOfSaveRoomVO = new ResultOfSaveRoomVO();
        String createTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        HrWxHrChatListDO chatRoom = new HrWxHrChatListDO();
        chatRoom.setCreateTime(createTime);
        chatRoom.setHraccountId(hrId);
        chatRoom.setSysuserId(userId);
        chatRoom.setStatus(false);
        chatRoom = chaoDao.saveChatRoom(chatRoom);
        
        return resultOfSaveRoomVO;
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
}
