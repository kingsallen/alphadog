package com.moseeker.chat.service.entity;

import com.moseeker.chat.constant.ChatSpeakerType;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.service.HrDBDao;
import com.moseeker.thrift.gen.dao.service.JobDBDao;
import com.moseeker.thrift.gen.dao.service.UserDBDao;
import com.moseeker.thrift.gen.dao.struct.*;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrChatUnreadCountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatListDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by jack on 09/03/2017.
 */
public class ChatDao {

    Logger logger = LoggerFactory.getLogger(ChatDao.class);

    HrDBDao.Iface hrDBDao = ServiceManager.SERVICEMANAGER.getService(HrDBDao.Iface.class);
    UserDBDao.Iface userDBDao = ServiceManager.SERVICEMANAGER.getService(UserDBDao.Iface.class);
    JobDBDao.Iface jobDBDao = ServiceManager.SERVICEMANAGER.getService(JobDBDao.Iface.class);

    ThreadPool threadPool = ThreadPool.Instance;

    /**
     * 按照未阅读对聊天室排序
     * @param type 聊天对象类型
     * @param id 编号
     * @param pageNo 页码
     * @param pageSize 每页显示的数量
     * @return 聊天室列表
     */
    public List<HrChatUnreadCountDO> listChatRoomUnreadCount(ChatSpeakerType type, int id, int pageNo, int pageSize) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("room_id");
        queryUtil.setOrder("desc");
        switch (type) {
            case HR:
                queryUtil.addSelectAttribute("user_unread_count").addSelectAttribute("user_id");
                queryUtil.setSortby("user_unread_count");
                queryUtil.addEqualFilter("hr_id", id);
                break;
            case USER:
                queryUtil.addSelectAttribute("hr_unread_count").addSelectAttribute("hr_id");
                queryUtil.setSortby("hr_unread_count");
                queryUtil.addEqualFilter("user_id", id);
                break;
            default:
        }
        queryUtil.setPer_page(pageSize);
        queryUtil.setPage(pageNo);
        try {
            return hrDBDao.listChatRoomUnreadSort(queryUtil);
        } catch (CURDException e) {
            return new ArrayList<>();
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据HR编号查找聊天室列表
     * @param hrId hr编号
     * @return 聊天室列表
     */
    public List<HrWxHrChatListDO> listHRChatRoom(int[] hrId) {

        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("hraccount_id", hrId);

        try {
            return hrDBDao.listChatRooms(queryUtil);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 查找聊天室数量
     * @param hrId HR编号
     * @return 聊天室数量
     */
    public int countHRChatRoom(int hrId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("hraccount_id", hrId);
        try {
            return hrDBDao.countChatRooms(queryUtil);
        } catch (TException e) {
            return 0;
        }
    }

    /**
     * 查找聊天室
     * @param roomIdArray 聊天室编号数组
     * @return 聊天室
     */
    public List<HrWxHrChatListDO> listChatRoom(int[] roomIdArray) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("create_time").addSelectAttribute("wx_chat_time").addSelectAttribute("hr_chat_time")
                .addSelectAttribute("id");

        queryUtil.addEqualFilter("id", StringUtils.converFromArrayToStr(roomIdArray));
        try {
            return hrDBDao.listChatRooms(queryUtil);
        } catch (CURDException e) {
            return null;
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 查找用户信息
     * @param userIdArray 用户编号
     * @return 用户信息列表
     */
    public List<UserUserDO> listUsers(int[] userIdArray) {
        String idStr = StringUtils.converFromArrayToStr(userIdArray);
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("id").addSelectAttribute("name").addSelectAttribute("headimg")
                .addSelectAttribute("nickname");

        queryUtil.addEqualFilter("id", idStr);
        try {
            List<UserUserDO> userUserDOList = userDBDao.listUser(queryUtil);
            if(userUserDOList != null && userUserDOList.size() > 0) {

                /** 如果存在没有头像的用户信息，那么查找绑定的微信账号，并取微信账号的头像 */
                int[] noHeadImgArray = userUserDOList.stream()
                        .filter(userUserDO -> StringUtils.isNullOrEmpty(userUserDO.getHeadimg()))
                        .mapToInt(userUserDO -> userUserDO.getId()).toArray();
                if(noHeadImgArray != null && noHeadImgArray.length > 0) {
                    String wxUserIdStr = StringUtils.converFromArrayToStr(noHeadImgArray);
                    QueryUtil findHeadImg = new QueryUtil();
                    findHeadImg.addSelectAttribute("headimgurl").addSelectAttribute("id");
                    findHeadImg.addEqualFilter("sysuser_id", wxUserIdStr);
                    List<UserWxUserDO> wxUserDOList = userDBDao.listUserWxUserDO(findHeadImg);
                    if(wxUserDOList != null && wxUserDOList.size() > 0) {

                        userUserDOList.stream().filter(userUserDO -> StringUtils.isNullOrEmpty(userUserDO.getHeadimg())).forEach(userUserDO -> {
                            wxUserDOList.forEach(userWxUserDO -> {
                                if(userUserDO.getId() == userWxUserDO.getSysuserId()) {
                                    userUserDO.setHeadimg(userWxUserDO.getHeadimgurl());
                                }
                            });
                        });
                    }
                }

            }

            return userUserDOList;
        } catch (CURDException e) {
            return new ArrayList<>();
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据HR编号查找公司名称和logo
     * @param hrIdArray hr编号
     * @return 公司信息集合
     */
    public List<HrCompanyDO> listCompany(int[] hrIdArray) {
        List<HrCompanyDO> companyDOList = null;

        String idStr = StringUtils.converFromArrayToStr(hrIdArray);
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("company_id");
        queryUtil.addEqualFilter("id", idStr);
        List<UserHrAccountDO> userHrAccountDOList = null;
        try {
            userHrAccountDOList = userDBDao.listUserHrAccount(queryUtil);
            if(userHrAccountDOList != null && userHrAccountDOList.size() > 0) {
                int[] companyIds = userHrAccountDOList.stream().filter(hr -> hr.getCompanyId() > 0).mapToInt(hr -> hr.getCompanyId()).toArray();

                String hrId = StringUtils.converFromArrayToStr(companyIds);
                QueryUtil findCompanyInfo = new QueryUtil();
                findCompanyInfo.addSelectAttribute("id").addSelectAttribute("name").addSelectAttribute("abbreviation")
                        .addSelectAttribute("logo");
                findCompanyInfo.addEqualFilter("id", hrId);
                companyDOList = hrDBDao.listCompany(findCompanyInfo);
            }
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }

        return companyDOList;
    }

    /**
     * 查询用户聊天室数量
     * @param userId 用户编号
     * @return 聊天室数量
     */
    public int countUserChatRoom(int userId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("sysuser_id", userId);
        try {
            return hrDBDao.countChatRooms(queryUtil);
        } catch (TException e) {
            return 0;
        }
    }

    /**
     * 查找HR信息
     * @param hrIdArray hr编号
     * @return HR信息集合
     */
    public List<UserHrAccountDO> listHr(int[] hrIdArray) {


        String idStr = StringUtils.converFromArrayToStr(hrIdArray);
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("id").addSelectAttribute("company_id").addSelectAttribute("username")
                .addSelectAttribute("headimgurl").addSelectAttribute("wxuser_id");
        queryUtil.addEqualFilter("id", idStr);
        List<UserHrAccountDO> userHrAccountDOList = null;
        try {
            userHrAccountDOList = userDBDao.listUserHrAccount(queryUtil);

            /** 需要检查HR的头像是否存在，如果不存在则使用HR绑定的微信账号的头像；如果还是没有，则使用公司的logo */
            if(userHrAccountDOList != null && userHrAccountDOList.size() > 0) {
                //查找头像不存在的HR的微信编号
                int[] wxUserIdArray = userHrAccountDOList.stream()
                        .filter(userHrAccountDO -> StringUtils.isNullOrEmpty(userHrAccountDO.getHeadimgurl()))
                        .mapToInt(userHrAccountDO -> userHrAccountDO.getWxuserId()).toArray();
                //查找头像不存在的公司编号
                int[] companyIdArray = userHrAccountDOList.stream()
                        .filter(userHrAccountDO -> StringUtils.isNullOrEmpty(userHrAccountDO.getHeadimgurl()))
                        .mapToInt(userHrAccountDO -> userHrAccountDO.getCompanyId()).toArray();

                /** 查找微信信息 */
                if(wxUserIdArray.length > 0) {
                    String wxUserIdStr = StringUtils.converFromArrayToStr(wxUserIdArray);
                    QueryUtil findWxUser = new QueryUtil();
                    findWxUser.addSelectAttribute("id").addSelectAttribute("headimgurl").addSelectAttribute("nickname");
                    findWxUser.addEqualFilter("id", wxUserIdStr);
                    Future wxUserFuture = threadPool.startTast(() -> userDBDao.listUserWxUserDO(findWxUser));

                    /** 过滤头像不存在的HR，匹配微信的头像*/
                    userHrAccountDOList.stream()
                            .filter(userHrAccountDO -> StringUtils.isNullOrEmpty(userHrAccountDO.getHeadimgurl()))
                            .forEach(userHrAccountDO -> {
                                try {
                                    List<UserWxUserDO> wxUserDOList = (List<UserWxUserDO>) wxUserFuture.get();
                                    if(wxUserDOList != null && wxUserDOList.size() > 0) {
                                        wxUserDOList.forEach(wxUserDO -> {
                                            if(userHrAccountDO.getWxuserId() == wxUserDO.getWechatId()) {
                                                userHrAccountDO.setHeadimgurl(wxUserDO.getHeadimgurl());
                                            }
                                        });
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    logger.error(e.getMessage(), e);
                                }
                            });
                }

                /** 查找公司信息 */
                if(companyIdArray.length > 0) {
                    String companyIdStr = StringUtils.converFromArrayToStr(companyIdArray);
                    QueryUtil findCompany = new QueryUtil();
                    findCompany.addSelectAttribute("id").addSelectAttribute("logo");
                    findCompany.addEqualFilter("id",companyIdStr);
                    Future companyFuture = threadPool.startTast(() -> hrDBDao.listCompany(queryUtil));


                    /** 过滤头像不存在的HR，匹配公司logo*/
                    userHrAccountDOList.stream()
                            .filter(userHrAccountDO -> StringUtils.isNullOrEmpty(userHrAccountDO.getHeadimgurl()))
                            .forEach(userHrAccountDO -> {
                                try {
                                    List<HrCompanyDO> companyDOList = (List<HrCompanyDO>) companyFuture.get();
                                    if(companyDOList != null && companyDOList.size() > 0) {
                                        companyDOList.forEach(companyDO -> {
                                            if(userHrAccountDO.getCompanyId() == companyDO.getId()) {
                                                userHrAccountDO.setHeadimgurl(companyDO.getLogo());
                                            }
                                        });
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    logger.error(e.getMessage(), e);
                                }
                            });
                }

            }
            return userHrAccountDOList;
        } catch (CURDException e) {
            return new ArrayList<>();
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * 计算聊天室的聊天记录总数
     * @param roomId 聊天室编号
     * @return 聊天室的聊天记录总数
     */
    public int countChatLog(int roomId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("chatlist_id", roomId);
        queryUtil.setOrder("desc");
        queryUtil.setSortby("create_time");
        try {
            return hrDBDao.countChats(queryUtil);
        } catch (TException e) {
            return 0;
        }
    }

    /**
     * 分页查找聊天室下的聊天记录
     *
     * @param roomId 聊天室编号  @return 聊天内容集合
     * @param pageNo 页码
     *@param pageSize 分页信息
     */
    public List<HrWxHrChatDO> listChat(int roomId, int pageNo, int pageSize) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("chatlist_id", roomId);
        queryUtil.setOrder("desc");
        queryUtil.setSortby("create_time");
        queryUtil.setPage(pageNo);
        queryUtil.setPer_page(pageSize);
        try {
            return hrDBDao.listChats(queryUtil);
        } catch (CURDException e) {
            return new ArrayList<>();
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 保存聊天记录
     * @param chatDO 聊天记录
     * @return 聊天记录
     */
    public HrWxHrChatDO saveChat(HrWxHrChatDO chatDO) {
        try {
            return hrDBDao.saveChat(chatDO);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 保存聊天室
     * @param chatRoom 聊天室
     * @return 聊天室
     */
    public HrWxHrChatListDO saveChatRoom(HrWxHrChatListDO chatRoom) {
        try {
            return hrDBDao.saveChatRoom(chatRoom);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据HR查找HR所属公司的信息
     * @param companyId 公司编号
     * @return 公司信息
     */
    public HrCompanyDO getCompany(int companyId) {
        HrCompanyDO companyDO = null;
        try {
            QueryUtil findCompany = new QueryUtil();
            findCompany.addEqualFilter("id", companyId);
            return hrDBDao.getCompany(findCompany);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
        return companyDO;
    }

    /**
     * 查找聊天室最后一条记录，如果包含职位信息，则查找职位信息。
     * @param roomId 聊天室
     * @return 职位信息
     */
    public JobPositionDO getPosition(int roomId) {

        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("chatlist_id", roomId);
        queryUtil.setSortby("create_time");
        queryUtil.setOrder("desc");
        try {
            HrWxHrChatDO chatDO = hrDBDao.getChat(queryUtil);
            if(chatDO != null && chatDO.getPid() > 0) {
                QueryUtil findPosition = new QueryUtil();
                findPosition.addEqualFilter("id", chatDO.getPid());
                JobPositionDO positionDO = jobDBDao.getPosition(findPosition);
                return positionDO;
            }
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据HR编号查找HR信息
     * @param hrId HR编号
     * @return HR信息
     */
    public UserHrAccountDO getHr(int hrId) {

        QueryUtil findHR = new QueryUtil();
        findHR.addSelectAttribute("id").addSelectAttribute("username").addSelectAttribute("wxuser_id")
                .addSelectAttribute("company_id").addSelectAttribute("headimgurl");
        findHR.addEqualFilter("id", hrId);

        try {
            UserHrAccountDO userHrAccountDO = userDBDao.getUserHrAccount(findHR);

            /** 如果HR没有头像信息，则查找微信的头像信息；如果没有微信信息或者微信信息的头像不存在，则查找公司的logo */
            if(userHrAccountDO != null && userHrAccountDO.getId() > 0
                    && StringUtils.isNullOrEmpty(userHrAccountDO.getHeadimgurl())) {

                String headImg = null;
                Future wxUserFuture = null;
                Future companyFuture = null;

                /** 查找微信的头像 */
                if(userHrAccountDO.getWxuserId() > 0) {
                    QueryUtil findWxUser = new QueryUtil();
                    findWxUser.addSelectAttribute("id").addSelectAttribute("headimgurl");
                    findWxUser.addEqualFilter("id", userHrAccountDO.getWxuserId());
                    wxUserFuture = threadPool.startTast(() -> userDBDao.getUserWxUserDO(findWxUser));
                }
                /** 查找公司的logo */
                if(userHrAccountDO.getCompanyId() > 0) {
                    QueryUtil findCompany = new QueryUtil();
                    findCompany.addSelectAttribute("id").addSelectAttribute("logo");
                    findCompany.addEqualFilter("id", userHrAccountDO.getCompanyId());
                    companyFuture = threadPool.startTast(() -> hrDBDao.getCompany(findCompany));
                }

                if(wxUserFuture != null) {
                    try {
                        UserWxUserDO userWxUserDO = (UserWxUserDO) wxUserFuture.get();
                        if(userWxUserDO != null) {
                            headImg = userWxUserDO.getHeadimgurl();
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage(), e);
                    }

                }

                if(companyFuture != null && StringUtils.isNullOrEmpty(headImg)) {
                    try {
                        HrCompanyDO companyDO = (HrCompanyDO) companyFuture.get();
                        if(companyDO != null) {
                            headImg = companyDO.getLogo();
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                userHrAccountDO.setHeadimgurl(headImg);
            }

            return userHrAccountDO;
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        //userDBDao.get
    }

    /**
     * 修改聊天室信息
     * @param chatRoom
     */
    public void updateChatRoom(HrWxHrChatListDO chatRoom) {
        if(chatRoom != null && chatRoom.getId() > 0) {
            try {
                hrDBDao.updateChatRoom(chatRoom);
            } catch (TException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 查找用户信息
     * @param userId 用户编号
     * @return 用户信息
     */
    public UserUserDO getUser(int userId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("id").addSelectAttribute("name").addSelectAttribute("nickname");
        queryUtil.addEqualFilter("id", userId);
        try {
            return userDBDao.getUser(queryUtil);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 判断聊天室是否存在
     * @param roomId 聊天室编号
     * @param userId 用户编号
     * @param hrId HR编号
     * @return true 存在, false 不存在
     */
    public boolean existChatRoom(int roomId, int userId, int hrId) {
        return false;
    }

    /**
     * 查找聊天室
     * @param roomId
     * @param userId
     * @param hrId
     * @return
     */
    public HrWxHrChatListDO getChatRoom(int roomId, int userId, int hrId) {
        HrWxHrChatListDO chatRoom = null;
        if(roomId > 0) {
            QueryUtil queryUtil = new QueryUtil();
            queryUtil.addEqualFilter("id", roomId);
            try {
                chatRoom = hrDBDao.getChatRoom(queryUtil);
                if(chatRoom == null) {
                    chatRoom = findChatRoomByUserIdHrId(userId, hrId);
                }
            } catch (TException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
                chatRoom = findChatRoomByUserIdHrId(userId, hrId);
        }
        if(chatRoom.getId() == 0) {
            return null;
        }
        return chatRoom;
    }

    /**
     * 根据用户编号和HR编号查找聊天室信息
     * @param userId 用户编号
     * @param hrId HR编号
     * @return 聊天室
     */
    private HrWxHrChatListDO findChatRoomByUserIdHrId(int userId, int hrId) {
        HrWxHrChatListDO chatRoom = null;
        QueryUtil findChatRoom = new QueryUtil();
        findChatRoom.addEqualFilter("sysuser_id", userId);
        findChatRoom.addEqualFilter("hraccount_id", hrId);
        try {
            chatRoom = hrDBDao.getChatRoom(findChatRoom);
        } catch (TException e) {
            e.printStackTrace();
        }
        return chatRoom;
    }

    /**
     * 根据职位编号查找职位信息
     * @param positionId
     * @return
     */
    public JobPositionDO getPositionById(int positionId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("id", positionId);
        try {
            return jobDBDao.getPosition(queryUtil);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public HrChatUnreadCountDO saveUnreadCount(HrChatUnreadCountDO unreadCountDO) {
        try {
            return hrDBDao.saveChatUnreadCount(unreadCountDO);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
