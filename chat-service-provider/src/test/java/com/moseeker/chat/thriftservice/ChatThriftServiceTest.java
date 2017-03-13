package com.moseeker.chat.thriftservice;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.chat.service.ChatService;
import com.moseeker.thrift.gen.chat.struct.HRChatRoomVO;
import com.moseeker.thrift.gen.chat.struct.HRChatRoomsVO;
import org.junit.Test;

import java.util.List;

/**
 * Created by jack on 13/03/2017.
 */
public class ChatThriftServiceTest {

    ChatService.Iface chatService = ServiceManager.SERVICEMANAGER.getService(ChatService.Iface.class);

    //@Test
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

    @Test
    public void listUserChatRoom() throws Exception {
        //chatService.listUserChatRoom();
    }

    @Test
    public void listChatLogs() throws Exception {

    }

    @Test
    public void saveChat() throws Exception {

    }

    @Test
    public void enterRoom() throws Exception {

    }

    @Test
    public void getChat() throws Exception {

    }

    @Test
    public void leaveChatRoom() throws Exception {

    }

}