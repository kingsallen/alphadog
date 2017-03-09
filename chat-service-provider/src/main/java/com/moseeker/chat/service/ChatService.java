package com.moseeker.chat.service;

import com.moseeker.chat.service.entity.ChatRoomDAO;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.chat.struct.*;
import com.moseeker.thrift.gen.dao.service.HrDBDao;
import com.moseeker.thrift.gen.dao.struct.HrChatUnreadCountDO;
import com.moseeker.thrift.gen.dao.struct.HrWxHrChatListDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jack on 08/03/2017.
 */
@Service
public class ChatService {

    private ChatRoomDAO chatRoomDAO = new ChatRoomDAO();

    /**
     * HR查找聊天室列表
     * @param hrId HR编号 user_hr_account.id
     * @param pageNo 页码
     * @param pageSize 每页信息数量
     * @return 聊天室分页信息
     */
    public HRChatRoomsVO listHRChatRoom(int hrId, int pageNo, int pageSize) {
        HRChatRoomsVO rooms = new HRChatRoomsVO();
        if(pageNo == 0) {
            pageNo = 1;
        }
        if(pageSize <= 0) {
            pageSize = 10;
        }
        List<HrChatUnreadCountDO> chatUnreadCountDOlist = chatRoomDAO.listHRUnreadCount(hrId, 1, pageNo, pageSize);

        //List<HrWxHrChatListDO> roomVOS = chatRoomDAO.listHRChatRoom(StringUt);
        int count = chatRoomDAO.countHRChatRoom(hrId);
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

    }

    /**
     * 创建聊天室
     * @param userId 用户编号 user_user.id
     * @param hrId 员工编号 user_hr_account.id
     * @return 聊天室创建信息
     */
    public ResultOfSaveRoomVO saveChatRoom(int userId, int hrId) {
        ResultOfSaveRoomVO resultOfSaveRoomVO = new ResultOfSaveRoomVO();
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
