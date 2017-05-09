package com.moseeker.chat.thriftservice;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.chat.service.ChatService;
import com.moseeker.thrift.gen.chat.struct.*;
import org.junit.Test;

import java.util.List;

/**
 * Created by jack on 13/03/2017.
 */
public class ChatThriftServiceTest {

    ChatService.Iface chatService = ServiceManager.SERVICEMANAGER.getService(ChatService.Iface.class);

    @Test
    public void listHRChatRoom() throws Exception {

        HRChatRoomsVO chatRoomsVO = chatService.listHRChatRoom(82689, 1, 10);
        System.out.println("pageNo : "+chatRoomsVO.getPageNo());
        System.out.println("pageSize : " + chatRoomsVO.getPageSize());
        System.out.println("totalRow : " + chatRoomsVO.getTotalRow());
        System.out.println("totalPage : " + chatRoomsVO.getTotalPage());
        List<HRChatRoomVO> roomVOList = chatRoomsVO.getRooms();
        if(roomVOList != null) {
            roomVOList.forEach(room -> {
                System.out.println("name:"+room.getName());
                System.out.println("id:"+room.getId());
                System.out.println("userId:"+room.getUserId());
                System.out.println("headImgUrl:"+room.getHeadImgUrl());
                System.out.println("createTime:"+room.getCreateTime());
                System.out.println("status:"+room.getUnReadNum());
            });
        }
    }

    //@Test
    public void listUserChatRoom() throws Exception {
        UserChatRoomsVO roomsVO = chatService.listUserChatRoom(1, 0, 10);
        System.out.println("pageNo : "+roomsVO.getPageNo());
        System.out.println("pageSize : " + roomsVO.getPageSize());
        System.out.println("totalRow : " + roomsVO.getTotalRow());
        System.out.println("totalPage : " + roomsVO.getTotalPage());
        List<UserChatRoomVO> roomVOList = roomsVO.getRooms();
        if(roomVOList != null) {
            roomVOList.forEach(room -> {
                System.out.println("companyName:"+room.getCompanyName());
                System.out.println("companyLogo:"+room.getCompanyLogo());
                System.out.println("id:"+room.getId());
                System.out.println("userId:"+room.getHrId());
                System.out.println("name:"+room.getName());
                System.out.println("headImgUrl:"+room.getHeadImgUrl());
                System.out.println("createTime:"+room.getCreateTime());
                System.out.println("status:"+room.getUnReadNum());
            });
        }
    }

    //@Test
    public void listChatLogs() throws Exception {
        ChatsVO chatsVO = chatService.listChatLogs(5, 2, 10);
        System.out.println("pageNo : "+chatsVO.getPageNo());
        System.out.println("pageSize : " + chatsVO.getPageSize());
        System.out.println("totalRow : " + chatsVO.getTotalRow());
        System.out.println("totalPage : " + chatsVO.getTotalPage());
        List<ChatVO> chatVOList = chatsVO.getChatLogs();
        if(chatVOList != null) {
            chatVOList.forEach(chatVO -> {
                System.out.println("id:"+chatVO.getId());
                System.out.println("content:"+chatVO.getContent());
                System.out.println("createTime:"+chatVO.getCreate_time());
                System.out.println("speaker:"+chatVO.getSpeaker());
            });
        }
    }

    //@Test
    public void saveChat() throws Exception {
        chatService.saveChat(5, "test", 3, (byte) 0);
    }

    //@Test
    public void enterRoom() throws Exception {
        ResultOfSaveRoomVO resultOfSaveRoomVO = chatService.enterRoom(1, 1, 1, 17);
        System.out.println("roomId : "+resultOfSaveRoomVO.getRoomId());
        System.out.println(resultOfSaveRoomVO.getHr());
        System.out.println(resultOfSaveRoomVO.getPosition());
        System.out.println(resultOfSaveRoomVO.getUser());
    }

    //@Test
    public void getChat() throws Exception {

    }

    //@Test
    public void leaveChatRoom() throws Exception {
        chatService.leaveChatRoom(12, (byte) 0);
    }

    //@Test
    public void leaveChatRoom1() throws Exception {
        chatService.leaveChatRoom(12, (byte) 1);
    }

}