package com.moseeker.chat.service.entity;

import com.moseeker.baseorm.dao.hrdb.HrChatUnreadCountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrWxHrChatDao;
import com.moseeker.baseorm.dao.hrdb.HrWxHrChatListDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.chat.constant.ChatSpeakerType;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrChatUnreadCountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatListDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jack on 09/03/2017.
 */
@Service
public class ChatDao {

    Logger logger = LoggerFactory.getLogger(ChatDao.class);

    @Autowired
    HrChatUnreadCountDao hrChatUnreadCountDao;

    @Autowired
    HrWxHrChatListDao hrWxHrChatListDao;

    @Autowired
    UserUserDao userUserDao;

    @Autowired
    UserWxUserDao userWxUserDao;

    @Autowired
    UserHrAccountDao userHrAccountDao;

    @Autowired
    HrCompanyDao hrCompanyDao;

    @Autowired
    HrWxHrChatDao hrWxHrChatDao;

    @Autowired
    JobPositionDao jobPositionDao;

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
        switch (type) {
            case HR:
                queryUtil.addSelectAttribute("user_have_unread_msg").addSelectAttribute("hr_have_unread_msg").addSelectAttribute("user_id");
                queryUtil.addEqualFilter("hr_id", id);
                queryUtil.orderBy("hr_have_unread_msg", Order.DESC).orderBy("wx_chat_time", Order.DESC);
                break;
            case USER:
                queryUtil.addSelectAttribute("user_have_unread_msg").addSelectAttribute("hr_have_unread_msg").addSelectAttribute("hr_id");
                queryUtil.addEqualFilter("user_id", id);
                queryUtil.orderBy("user_have_unread_msg", Order.DESC).orderBy("hr_chat_time", Order.DESC);
                break;
            default:
        }
        queryUtil.setPer_page(pageSize);
        queryUtil.setPageNo(pageNo);
        return hrChatUnreadCountDao.getDatas(queryUtil);
    }

    /**
     * 根据HR编号查找聊天室列表
     * @param hrId hr编号
     * @return 聊天室列表
     */
    public List<HrWxHrChatListDO> listHRChatRoom(int[] hrId) {

        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("hraccount_id", hrId);
        return hrWxHrChatListDao.getDatas(queryUtil);
    }

    /**
     * 查找聊天室数量
     * @param hrId HR编号
     * @return 聊天室数量
     */
    public int countHRChatRoom(int hrId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("hraccount_id", hrId);
        return hrWxHrChatListDao.getCount(queryUtil);
    }

    /**
     * 查找聊天室
     * @param roomIdArray 聊天室编号数组
     * @return 聊天室
     */
    public List<HrWxHrChatListDO> listChatRoom(int[] roomIdArray) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("create_time").addSelectAttribute("wx_chat_time").addSelectAttribute("hr_chat_time")
                .addSelectAttribute("id").addSelectAttribute("update_time");
        queryUtil.addEqualFilter("id", StringUtils.converFromArrayToStr(roomIdArray));
        return hrWxHrChatListDao.getDatas(queryUtil);
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
        List<UserUserDO> userUserDOList = userUserDao.getDatas(queryUtil);
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
                List<UserWxUserDO> wxUserDOList = userWxUserDao.getDatas(findHeadImg);
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
        userHrAccountDOList = userHrAccountDao.getDatas(queryUtil);
        if(userHrAccountDOList != null && userHrAccountDOList.size() > 0) {
            int[] companyIds = userHrAccountDOList.stream().filter(hr -> hr.getCompanyId() > 0).mapToInt(hr -> hr.getCompanyId()).toArray();

            String hrId = StringUtils.converFromArrayToStr(companyIds);
            QueryUtil findCompanyInfo = new QueryUtil();
            findCompanyInfo.addSelectAttribute("id").addSelectAttribute("name").addSelectAttribute("abbreviation")
                    .addSelectAttribute("logo");
            findCompanyInfo.addEqualFilter("id", hrId);
            companyDOList = hrCompanyDao.getDatas(findCompanyInfo);
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
        return hrWxHrChatListDao.getCount(queryUtil);
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
        userHrAccountDOList = userHrAccountDao.getDatas(queryUtil);

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
                Future wxUserFuture = threadPool.startTast(() -> userWxUserDao.getDatas(findWxUser));

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
                Future companyFuture = threadPool.startTast(() -> hrCompanyDao.getDatas(queryUtil));


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
    }

    /**
     * 计算聊天室的聊天记录总数
     * @param roomId 聊天室编号
     * @return 聊天室的聊天记录总数
     */
    public int countChatLog(int roomId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("chatlist_id", roomId);
        queryUtil.orderBy("create_time", Order.DESC);
        return hrWxHrChatDao.getCount(queryUtil);
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
        queryUtil.orderBy("create_time", Order.DESC);
        queryUtil.orderBy("id", Order.DESC);
        queryUtil.setPageNo(pageNo);
        queryUtil.setPer_page(pageSize);
        return hrWxHrChatDao.getDatas(queryUtil);
    }

    /**
     * 保存聊天记录
     * @param chatDO 聊天记录
     * @return 聊天记录
     */
    public HrWxHrChatDO saveChat(HrWxHrChatDO chatDO) {
        try {
            return hrWxHrChatDao.addData(chatDO);
        } catch (Exception e) {
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
        return hrWxHrChatListDao.addData(chatRoom);
    }

    /**
     * 根据HR查找HR所属公司的信息
     * @param companyId 公司编号
     * @return 公司信息
     */
    public HrCompanyDO getCompany(int companyId) {
        QueryUtil findCompany = new QueryUtil();
        findCompany.addEqualFilter("id", companyId);
        return hrCompanyDao.getData(findCompany);
    }

    /**
     * 查找聊天室最后一条记录，如果包含职位信息，则查找职位信息。
     * @param roomId 聊天室
     * @return 职位信息
     */
    public JobPositionDO getPosition(int roomId) {

        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("chatlist_id", roomId);
        queryUtil.orderBy("create_time", Order.DESC);
        HrWxHrChatDO chatDO = hrWxHrChatDao.getData(queryUtil);
        if(chatDO != null && chatDO.getPid() > 0) {
            QueryUtil findPosition = new QueryUtil();
            findPosition.addEqualFilter("id", chatDO.getPid());
            JobPositionDO positionDO = jobPositionDao.getData(findPosition);
            return positionDO;
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

        UserHrAccountDO userHrAccountDO = userHrAccountDao.getData(findHR);

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
                wxUserFuture = threadPool.startTast(() -> userWxUserDao.getData(findWxUser));
            }
            /** 查找公司的logo */
            if(userHrAccountDO.getCompanyId() > 0) {
                QueryUtil findCompany = new QueryUtil();
                findCompany.addSelectAttribute("id").addSelectAttribute("logo");
                findCompany.addEqualFilter("id", userHrAccountDO.getCompanyId());
                companyFuture = threadPool.startTast(() -> hrCompanyDao.getData(findCompany));
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
    }

    /**
     * 修改聊天室信息
     * @param chatRoom
     */
    public void updateChatRoom(HrWxHrChatListDO chatRoom) {
        hrWxHrChatListDao.updateData(chatRoom);
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
        return userUserDao.getData(queryUtil);
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
            chatRoom = hrWxHrChatListDao.getData(queryUtil);
            if(chatRoom == null) {
                chatRoom = findChatRoomByUserIdHrId(userId, hrId);
            }
        } else {
                chatRoom = findChatRoomByUserIdHrId(userId, hrId);
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
        QueryUtil findChatRoom = new QueryUtil();
        findChatRoom.addEqualFilter("sysuser_id", userId);
        findChatRoom.addEqualFilter("hraccount_id", hrId);
        return hrWxHrChatListDao.getData(findChatRoom);
    }

    /**
     * 根据职位编号查找职位信息
     * @param positionId
     * @return
     */
    public JobPositionDO getPositionById(int positionId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("id", positionId);
        return jobPositionDao.getData(queryUtil);
    }

    public HrChatUnreadCountDO saveUnreadCount(HrChatUnreadCountDO unreadCountDO) {
        return hrChatUnreadCountDao.addData(unreadCountDO);
    }

    /**
     * 清空C端账号未读消息
     * @param chatRoomId
     * @param hrId
     * @param userId
     * @return
     */
    public HrChatUnreadCountDO clearUserUnreadCount(int chatRoomId, int hrId, int userId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("room_id", chatRoomId);
        try {
            HrChatUnreadCountDO hrChatUnreadCountDO =  hrChatUnreadCountDao.getData(queryUtil);
            HrWxHrChatListDO chatRoom = new HrWxHrChatListDO();
            chatRoom.setId(chatRoomId);
            chatRoom.setUserUnreadCount(0);
            hrWxHrChatListDao.updateData(chatRoom);
            if(hrChatUnreadCountDO.getRoomId() > 0) {
                hrChatUnreadCountDO.setUserHaveUnreadMsg((byte)0);
                hrChatUnreadCountDao.updateData(hrChatUnreadCountDO);
            } else {
                hrChatUnreadCountDO.setRoomId(chatRoomId);
                hrChatUnreadCountDO.setHrId(hrId);
                hrChatUnreadCountDO.setUserId(userId);
                hrChatUnreadCountDO.setUserHaveUnreadMsg((byte)0);
                hrChatUnreadCountDO = hrChatUnreadCountDao.addData(hrChatUnreadCountDO);
            }
            return hrChatUnreadCountDO;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public HrChatUnreadCountDO addUnreadCount(int roomId, byte speaker, String date) {
        logger.info("ChatDao addUnreadCount roomId:{}, speaker:{}, date:{}", roomId, speaker, date);
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("room_id", roomId);
        try {
            HrChatUnreadCountDO hrChatUnreadCountDO =  hrChatUnreadCountDao.getData(queryUtil);

            if(hrChatUnreadCountDO.getRoomId() > 0) {
                switch (speaker) {
                    case 1:
                        hrChatUnreadCountDO.setHrChatTime(date);
                        hrChatUnreadCountDO.setUserHaveUnreadMsg((byte)1);
                        break;
                    case 0:
                        hrChatUnreadCountDO.setWxChatTime(date);
                        hrChatUnreadCountDO.setHrHaveUnreadMsg((byte)1);
                        break;
                    default:
                }
                logger.info("ChatDao addUnreadCount hrChatUnreadCountDO:",hrChatUnreadCountDO);
                hrChatUnreadCountDao.updateData(hrChatUnreadCountDO);
            }
            return hrChatUnreadCountDO;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public void updateChatUnreadCount(HrChatUnreadCountDO hrChatUnreadCountDO) {
        try {
            hrChatUnreadCountDao.updateData(hrChatUnreadCountDO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void addChatTOChatRoom(HrWxHrChatDO chatDO) {
        logger.info("ChatDao addChatTOChatRoom chatDO:",chatDO);
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("id", chatDO.getChatlistId());
        try {
            HrWxHrChatListDO chatRoomDO = hrWxHrChatListDao.getData(queryUtil);
            if (chatRoomDO != null) {
                if (chatDO.getSpeaker() == 0) {
                    chatRoomDO.setWxChatTime(chatDO.getCreateTime());
                    chatRoomDO.setHrUnreadCount(chatRoomDO.getHrUnreadCount()+1);
                } else {
                    chatRoomDO.setHrChatTime(chatDO.getCreateTime());
                    chatRoomDO.setUserUnreadCount(chatRoomDO.getUserUnreadCount()+1);
                }

                logger.info("ChatDao addChatTOChatRoom chatRoomDO:",chatRoomDO);
                hrWxHrChatListDao.updateData(chatRoomDO);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
