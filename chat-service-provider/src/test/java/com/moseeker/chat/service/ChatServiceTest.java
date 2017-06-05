package com.moseeker.chat.service;

import com.moseeker.chat.constant.ChatSpeakerType;
import com.moseeker.chat.service.entity.ChatDao;
import com.moseeker.thrift.gen.chat.struct.*;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrChatUnreadCountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatListDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by jack on 13/03/2017.
 */
public class ChatServiceTest {

    @Mock
    private ChatDao chatDao;

    @InjectMocks
    private ChatService chatService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        Mockito.when(chatDao.countHRChatRoom(3)).thenReturn(3);
        Mockito.when(chatDao.countUserChatRoom(1)).thenReturn(3);

        List<HrChatUnreadCountDO> hrChatUnreadCountDOListALL = new ArrayList<>();
        List<HrChatUnreadCountDO> user1UnreadCountDOList = new ArrayList<>();
        List<HrChatUnreadCountDO> hr1UnreadCountDOList = new ArrayList<>();
        List<HrChatUnreadCountDO> hr3UnreadCountDOList = new ArrayList<>();


        HrChatUnreadCountDO unreadCountDO1 = new HrChatUnreadCountDO();
        unreadCountDO1.setUserId(1);
        unreadCountDO1.setHrId(1);
        unreadCountDO1.setRoomId(1);
        hrChatUnreadCountDOListALL.add(unreadCountDO1);
        user1UnreadCountDOList.add(unreadCountDO1);
        hr1UnreadCountDOList.add(unreadCountDO1);

        HrChatUnreadCountDO unreadCountDO2 = new HrChatUnreadCountDO();
        unreadCountDO2.setUserId(2);
        unreadCountDO2.setHrId(1);
        unreadCountDO2.setRoomId(2);
        hrChatUnreadCountDOListALL.add(unreadCountDO2);
        hr1UnreadCountDOList.add(unreadCountDO2);

        HrChatUnreadCountDO unreadCountDO3 = new HrChatUnreadCountDO();
        unreadCountDO3.setUserId(3);
        unreadCountDO3.setHrId(1);
        unreadCountDO3.setRoomId(3);
        hrChatUnreadCountDOListALL.add(unreadCountDO3);
        hr1UnreadCountDOList.add(unreadCountDO3);

        HrChatUnreadCountDO unreadCountDO4 = new HrChatUnreadCountDO();
        unreadCountDO4.setUserId(2);
        unreadCountDO4.setHrId(2);
        unreadCountDO4.setRoomId(4);
        hrChatUnreadCountDOListALL.add(unreadCountDO4);

        HrChatUnreadCountDO unreadCountDO7 = new HrChatUnreadCountDO();
        unreadCountDO7.setUserId(1);
        unreadCountDO7.setHrId(2);
        unreadCountDO7.setRoomId(7);
        hrChatUnreadCountDOListALL.add(unreadCountDO7);
        user1UnreadCountDOList.add(unreadCountDO7);

        HrChatUnreadCountDO unreadCountDO5 = new HrChatUnreadCountDO();
        unreadCountDO5.setUserId(3);
        unreadCountDO5.setHrId(2);
        unreadCountDO5.setRoomId(5);
        hrChatUnreadCountDOListALL.add(unreadCountDO5);

        HrChatUnreadCountDO unreadCountDO6 = new HrChatUnreadCountDO();
        unreadCountDO6.setUserId(3);
        unreadCountDO6.setHrId(3);
        unreadCountDO6.setRoomId(6);
        hrChatUnreadCountDOListALL.add(unreadCountDO6);
        hr3UnreadCountDOList.add(unreadCountDO6);

        HrChatUnreadCountDO unreadCountDO8 = new HrChatUnreadCountDO();
        unreadCountDO8.setUserId(1);
        unreadCountDO8.setHrId(3);
        unreadCountDO8.setRoomId(8);
        hrChatUnreadCountDOListALL.add(unreadCountDO8);
        hr3UnreadCountDOList.add(unreadCountDO8);
        user1UnreadCountDOList.add(unreadCountDO8);

        HrChatUnreadCountDO unreadCountDO9 = new HrChatUnreadCountDO();
        unreadCountDO9.setUserId(2);
        unreadCountDO9.setHrId(3);
        unreadCountDO9.setRoomId(9);
        hrChatUnreadCountDOListALL.add(unreadCountDO9);
        hr3UnreadCountDOList.add(unreadCountDO9);

        Mockito.when(chatDao.listChatRoomUnreadCount(ChatSpeakerType.USER, 1, 1, 10)).thenReturn(user1UnreadCountDOList);
        Mockito.when(chatDao.listChatRoomUnreadCount(ChatSpeakerType.HR, 1, 1, 10)).thenReturn(hr1UnreadCountDOList);
        Mockito.when(chatDao.listChatRoomUnreadCount(ChatSpeakerType.HR, 3, 1, 10)).thenReturn(hr3UnreadCountDOList);

        List<HrWxHrChatListDO> rooms = new ArrayList<>();

        List<HrWxHrChatListDO> roomsUser1 = new ArrayList<>();

        HrWxHrChatListDO room1 = new HrWxHrChatListDO();
        room1.setSysuserId(1);
        room1.setHraccountId(1);
<<<<<<< HEAD
//        room1.setStatus((byte)0);
=======
>>>>>>> master
        room1.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        room1.setHrChatTime(null);
        room1.setId(1);
        room1.setWxChatTime(null);
        roomsUser1.add(room1);


        HrWxHrChatListDO room6 = new HrWxHrChatListDO();
        room6.setSysuserId(3);
        room6.setHraccountId(3);
<<<<<<< HEAD
//        room6.setStatus((byte)0);
=======
>>>>>>> master
        room6.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        room6.setHrChatTime(null);
        room6.setId(6);
        room6.setWxChatTime(null);
        rooms.add(room6);

        HrWxHrChatListDO room7= new HrWxHrChatListDO();
        room7.setSysuserId(1);
        room7.setHraccountId(2);
<<<<<<< HEAD
//        room7.setStatus((byte)0);
=======
>>>>>>> master
        room7.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        room7.setHrChatTime(null);
        room7.setId(7);
        room1.setWxChatTime(null);
        roomsUser1.add(room7);

        HrWxHrChatListDO room8 = new HrWxHrChatListDO();
        room8.setSysuserId(1);
        room8.setHraccountId(3);
<<<<<<< HEAD
//        room8.setStatus((byte)0);
=======
>>>>>>> master
        room8.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        room8.setHrChatTime(null);
        room8.setId(8);
        room8.setWxChatTime(null);
        rooms.add(room8);
        roomsUser1.add(room8);

        HrWxHrChatListDO room9 = new HrWxHrChatListDO();
        room9.setSysuserId(2);
        room9.setHraccountId(3);
<<<<<<< HEAD
//        room9.setStatus((byte)0);
=======
>>>>>>> master
        room9.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        room9.setHrChatTime(null);
        room9.setId(9);
        room9.setWxChatTime(null);
        rooms.add(room9);

        List<UserUserDO> users = new ArrayList<>();
        UserUserDO userDO1 = new UserUserDO();
        userDO1.setId(1);
        userDO1.setHeadimg("1.png");
        userDO1.setName("user1");
        userDO1.setNickname("user1 nickname");
        users.add(userDO1);

        UserUserDO userDO2 = new UserUserDO();
        userDO2.setId(2);
        userDO2.setHeadimg("2.png");
        userDO2.setName("user2");
        userDO2.setNickname("user2 nickname");
        users.add(userDO2);

        UserUserDO userDO3 = new UserUserDO();
        userDO3.setId(3);
        userDO3.setHeadimg("3.png");
        userDO3.setName("user3");
        userDO3.setNickname("user3 nickname");
        users.add(userDO3);

        List<UserHrAccountDO> hrAccountDOList = new ArrayList<>();
        UserHrAccountDO hrAccountDO1 = new UserHrAccountDO();
        hrAccountDO1.setId(1);
        hrAccountDO1.setHeadimgurl("hr1.png");
        hrAccountDO1.setCompanyId(1);
        hrAccountDO1.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        hrAccountDO1.setUsername("hr1");
        hrAccountDO1.setWxuserId(1);
        hrAccountDOList.add(hrAccountDO1);

        UserHrAccountDO hrAccountDO2 = new UserHrAccountDO();
        hrAccountDO2.setId(2);
        hrAccountDO2.setHeadimgurl("hr2.png");
        hrAccountDO2.setCompanyId(2);
        hrAccountDO2.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        hrAccountDO2.setUsername("hr2");
        hrAccountDO2.setWxuserId(2);
        hrAccountDOList.add(hrAccountDO2);

        UserHrAccountDO hrAccountDO3 = new UserHrAccountDO();
        hrAccountDO2.setId(3);
        hrAccountDO2.setHeadimgurl("hr3.png");
        hrAccountDO2.setCompanyId(3);
        hrAccountDO2.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        hrAccountDO2.setUsername("hr3");
        hrAccountDO2.setWxuserId(3);
        hrAccountDOList.add(hrAccountDO3);

        List<HrCompanyDO> companyDOList = new ArrayList<>();
        HrCompanyDO companyDO1 = new HrCompanyDO();
        companyDO1.setName("company1");
        companyDO1.setAbbreviation("abbreviation1");
        companyDO1.setLogo("logo1");
        companyDO1.setHraccountId(1);
        companyDOList.add(companyDO1);

        HrCompanyDO companyDO2 = new HrCompanyDO();
        companyDO2.setName("company2");
        companyDO2.setAbbreviation("abbreviation2");
        companyDO2.setLogo("logo2");
        companyDO2.setHraccountId(2);
        companyDOList.add(companyDO2);

        HrCompanyDO companyDO3 = new HrCompanyDO();
        companyDO3.setName("company3");
        companyDO3.setAbbreviation("abbreviation3");
        companyDO3.setLogo("logo3");
        companyDO3.setHraccountId(3);
        companyDOList.add(companyDO3);


        int[] roomId = {6,8,9};
        Mockito.when(chatDao.listChatRoom(roomId)).thenReturn(rooms);
        int[] roomIdUser1 = {1,7,8};
        Mockito.when(chatDao.listChatRoom(roomId)).thenReturn(roomsUser1);

        int[] userId = {3,1,2};
        Mockito.when(chatDao.listUsers(userId)).thenReturn(users);


        int[] hrUser1 = {1,2, 3};
        Mockito.when(chatDao.listHr(hrUser1)).thenReturn(hrAccountDOList);

        int[] companyIdUser1 = {1,2,3};
        Mockito.when(chatDao.listCompany(companyIdUser1)).thenReturn(companyDOList);

        List<HrWxHrChatDO> chatListRoom1 = new ArrayList<>();
        HrWxHrChatDO chatDO1 = new HrWxHrChatDO();
        chatDO1.setContent("chat1");
        chatDO1.setCreateTime(new DateTime().minusDays(1).toString("yyyy-MM-dd HH:mm:ss"));
        chatDO1.setSpeaker((byte)1);
        chatDO1.setChatlistId(1);
        chatDO1.setId(1);
        chatListRoom1.add(chatDO1);

        HrWxHrChatDO chatDO2 = new HrWxHrChatDO();
        chatDO2.setContent("chat2");
        chatDO2.setCreateTime(new DateTime().minusDays(1).toString("yyyy-MM-dd HH:mm:ss"));
        chatDO2.setSpeaker((byte)1);
        chatDO2.setChatlistId(1);
        chatDO2.setId(2);
        chatListRoom1.add(chatDO2);

        HrWxHrChatDO chatDO3 = new HrWxHrChatDO();
        chatDO3.setContent("chat3");
        chatDO3.setCreateTime(new DateTime().minusDays(1).toString("yyyy-MM-dd HH:mm:ss"));
        chatDO3.setSpeaker((byte)1);
        chatDO3.setChatlistId(1);
        chatDO3.setId(3);
        chatListRoom1.add(chatDO3);

        Mockito.when(chatDao.countChatLog(1)).thenReturn(3);
        Mockito.when(chatDao.listChat(1, 1, 10)).thenReturn(chatListRoom1);

        HrWxHrChatListDO room = new HrWxHrChatListDO();
        room.setId(1);
<<<<<<< HEAD
//        room.setStatus((byte)0);
=======
>>>>>>> master
        room.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        room.setHraccountId(1);
        room.setSysuserId(1);
        Mockito.when(chatDao.getChatRoom(1, 1,1)).thenReturn(room);

        JobPositionDO jobPositionDO = new JobPositionDO();
        jobPositionDO.setId(1);
        jobPositionDO.setTitle("职位名称");
        jobPositionDO.setCompanyId(1);

        Mockito.when(chatDao.getPosition(1)).thenReturn(jobPositionDO);
        Mockito.when(chatDao.getPositionById(1)).thenReturn(jobPositionDO);

        UserHrAccountDO hrAccountDO = new UserHrAccountDO();
        hrAccountDO.setCompanyId(1);
        hrAccountDO.setWxuserId(1);
        hrAccountDO.setUsername("hr1");
        hrAccountDO.setHeadimgurl("hr1.png");
        hrAccountDO.setId(1);
        Mockito.when(chatDao.getHr(1)).thenReturn(hrAccountDO);


        Mockito.when(chatDao.saveChatRoom(room1)).thenReturn(room1);

        UserUserDO userUserDO = new UserUserDO();
        userUserDO.setId(1);
        userUserDO.setNickname("user1 nickname");
        userUserDO.setName("user1");
        userUserDO.setHeadimg("user1.png");
        Mockito.when(chatDao.getUser(1)).thenReturn(userUserDO);

        HrCompanyDO companyDO = new HrCompanyDO();
        companyDO.setId(1);
        companyDO.setName("company1");
        companyDO.setAbbreviation("company1 abbreviation");
        companyDO.setLogo("company1.logo");
        Mockito.when(chatDao.getCompany(1)).thenReturn(companyDO);
    }

    @Test
    public void listHRChatRoom() throws Exception {
        HRChatRoomsVO chatRoomsVO = chatService.listHRChatRoom(3,1, 10);
        assertEquals(1, chatRoomsVO.getPageNo());
        assertEquals(10, chatRoomsVO.getPageSize());
        assertEquals(1, chatRoomsVO.getTotalPage());
        assertEquals(3, chatRoomsVO.getTotalRow());
        List<HRChatRoomVO> roomVOList = chatRoomsVO.getRooms();
        HRChatRoomVO room6 = roomVOList.get(0);
        assertEquals(6, room6.getId());

        HRChatRoomVO room8 = roomVOList.get(1);
        assertEquals(8, room8.getId());
        HRChatRoomVO room9 = roomVOList.get(2);
        assertEquals(9, room9.getId());
    }

    @Test
    public void listUserChatRoom() throws Exception {
        UserChatRoomsVO userChatRoomsVO = chatService.listUserChatRoom(1, 1, 10);
        assertEquals(1, userChatRoomsVO.getPageNo());
        assertEquals(10, userChatRoomsVO.getPageSize());
        assertEquals(1, userChatRoomsVO.getTotalPage());
        assertEquals(3, userChatRoomsVO.getTotalRow());
        List<UserChatRoomVO> roomVOList = userChatRoomsVO.getRooms();
        UserChatRoomVO room1 = roomVOList.get(0);
        assertEquals(1, room1.getId());

        UserChatRoomVO room7 = roomVOList.get(1);
        assertEquals(7, room7.getId());
        UserChatRoomVO room8 = roomVOList.get(2);
        assertEquals(8, room8.getId());
    }

    @Test
    public void listChatLogs() throws Exception {
        ChatsVO chatsVO = chatService.listChatLogs(1, 1, 10);
        assertEquals(1, chatsVO.getPageNo());
        assertEquals(10, chatsVO.getPageSize());
        assertEquals(1, chatsVO.getTotalPage());
        assertEquals(3, chatsVO.getTotalRow());
        List<ChatVO> chatVOList = chatsVO.getChatLogs();
        ChatVO chatVO1 = chatVOList.get(0);
        assertEquals(3, chatVO1.getId());
        ChatVO chatVO2 = chatVOList.get(1);
        assertEquals(2, chatVO2.getId());
        ChatVO chatVO3 = chatVOList.get(2);
        assertEquals(1, chatVO3.getId());
    }

    @Test
    public void saveChat() throws Exception {
        //chatService.saveChat(1, "test", 1, (byte)0);
    }

    @Test
    public void enterChatRoom() throws Exception {

        ResultOfSaveRoomVO result = chatService.enterChatRoom(1, 1, 1, 1, false);
        assertEquals(1, result.getRoomId());
        assertEquals(false, result.isChatDebut());

        HrVO hrVO = result.getHr();
        assertEquals(1, hrVO.getHrId());

        PositionVO positionVO = result.getPosition();
        assertEquals(1, positionVO.getPositionId());

        UserVO userVO = result.getUser();
        assertEquals(1, userVO.getUserId());
    }
}