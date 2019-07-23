package com.moseeker.chat.service.entity;

import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatList;
import com.moseeker.baseorm.db.hrdb.tables.records.HrChatUnreadCountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatListRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatRecord;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.chat.constant.ChatSpeakerType;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.chat.struct.ChatVO;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.moseeker.common.constants.Constant.HR_HEADIMG;

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

    @Autowired
    HrCompanyAccountDao hrCompanyAccountDao;

    @Autowired
    private ChatFactory chatFactory;

    ThreadPool threadPool = ThreadPool.Instance;

    /**
     * 根据聊天室ID获取聊天室
     * @param roomId 聊天室ID
     * @return
     */
    public HrWxHrChatListDO getChatRoomById(int roomId){
        Query query=new Query.QueryBuilder().where(HrWxHrChatList.HR_WX_HR_CHAT_LIST.ID.getName(),roomId).buildQuery();
        return hrWxHrChatListDao.getData(query);
    }

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
                .addSelectAttribute("id").addSelectAttribute("update_time")
                .addSelectAttribute(HrWxHrChatList.HR_WX_HR_CHAT_LIST.HR_UNREAD_COUNT.getName());
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
                findHeadImg.addSelectAttribute("headimgurl").addSelectAttribute("id")
                        .addSelectAttribute(UserWxUser.USER_WX_USER.SYSUSER_ID.getName());
                findHeadImg.addEqualFilter("sysuser_id", wxUserIdStr);
                List<UserWxUserDO> wxUserDOList = userWxUserDao.getDatas(findHeadImg);
                logger.info("listUsers wxUserDOList:{}", wxUserDOList);
                if(wxUserDOList != null && wxUserDOList.size() > 0) {

                    userUserDOList.stream().filter(userUserDO -> StringUtils.isNullOrEmpty(userUserDO.getHeadimg())).forEach(userUserDO -> {
                        Optional<UserWxUserDO> userWxUserDOOptional = wxUserDOList
                                .stream().filter(userWxUserDO1 -> userWxUserDO1.getSysuserId() == userUserDO.getId())
                                .findAny();
                        if (userWxUserDOOptional.isPresent()) {
                            userUserDO.setHeadimg(userWxUserDOOptional.get().getHeadimgurl());
                        } else {
                            logger.info("listUsers userWxUserDOOptional not exist");
                        }
                    });
                }
            }

        }
        logger.info("listUsers wxUserDOList:{}", userUserDOList);
        return userUserDOList;
    }

    /**
     * 根据HR编号查找公司名称和logo
     * @param hrIdArray hr编号
     * @return 公司信息集合
     */
    public Map<Integer, HrCompanyDO> listCompany(int[] hrIdArray) {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where(new Condition("account_id", Arrays.stream(hrIdArray).map(Integer::valueOf).collect(ArrayList::new, List::add, List::addAll), ValueOp.IN));
        List<HrCompanyAccountDO> hrCompanyAccountDOList = hrCompanyAccountDao.getDatas(query.buildQuery());
        if(hrCompanyAccountDOList != null && hrCompanyAccountDOList.size() > 0) {
            Map<Integer, Object> hrCompanyMap = hrCompanyAccountDOList.stream().collect(Collectors.toMap(k -> k.getAccountId(), v -> v.getCompanyId(), (n, o) -> n));
            query.clear();
            query.select("id").select("name").select("abbreviation").select("logo");
            query.where(new Condition("id", new ArrayList<>(hrCompanyMap.values()), ValueOp.IN));
            List<HrCompanyDO> companyDOList = hrCompanyDao.getDatas(query.buildQuery());
            Map<Integer, HrCompanyDO> companyMap = companyDOList.stream().collect(Collectors.toMap(k -> k.getId(), v -> v));
            return hrCompanyMap.keySet().stream().collect(Collectors.toMap(k -> k, v -> companyMap.getOrDefault(hrCompanyMap.get(v), new HrCompanyDO())));
        }
        return null;
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
                .addSelectAttribute("headimgurl").addSelectAttribute("wxuser_id").addSelectAttribute("remark_name");
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
                                        if(userHrAccountDO.getWxuserId() == wxUserDO.getId()) {
                                            userHrAccountDO.setHeadimgurl(wxUserDO.getHeadimgurl());
                                        }
                                    });
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                userHrAccountDO.setHeadimgurl(HR_HEADIMG);
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
                Future companyFuture = threadPool.startTast(() -> hrCompanyDao.getDatas(findCompany));


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
     * @param publisherId 发布人账号
     * @return 公司信息
     */
    public HrCompanyDO getCompany(int publisherId) {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("account_id", publisherId);
        HrCompanyAccountDO hrCompanyAccountDO = hrCompanyAccountDao.getData(query.buildQuery());
        query.clear();
        query.where("id", hrCompanyAccountDO.getCompanyId());
        return hrCompanyDao.getData(query.buildQuery());
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
                .addSelectAttribute("company_id").addSelectAttribute("headimgurl").addSelectAttribute("mobile")
                .addSelectAttribute("remark_name");
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
                Query.QueryBuilder query = new Query.QueryBuilder();
                query.where("account_id", userHrAccountDO.getId());
                HrCompanyAccountDO hrCompanyAccountDO = hrCompanyAccountDao.getData(query.buildQuery());
                query.clear();
                query.where("id", hrCompanyAccountDO.getCompanyId());
                query.select("id").select("logo");
                companyFuture = threadPool.startTast(() -> hrCompanyDao.getData(query.buildQuery()));
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
        return userUserDao.getUser(userId);
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

            // 初次聊天npe问题，由于初次设置未读消息是在savaChat之后处理的
            if(hrChatUnreadCountDO != null
                    && hrChatUnreadCountDO.getRoomId() > 0) {
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
                logger.info("ChatDao addUnreadCount hrChatUnreadCountDO:{}", hrChatUnreadCountDO);
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
        logger.info("ChatDao addChatTOChatRoom chatDO:{}" ,chatDO);
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

                logger.info("ChatDao addChatTOChatRoom chatRoomDO:{}", chatRoomDO);
                hrWxHrChatListDao.updateData(chatRoomDO);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public List<Integer> fetchUserIdByHrId(int hrId, boolean apply) {
        return hrChatUnreadCountDao.fetchUserIdByHRId(hrId, apply);
    }

    public HrChatUnreadCountRecord fetchRoomByHRIdAndUserId(int hrId, int userId) {
        return hrChatUnreadCountDao.fetchByHrIdAndUserId(hrId, userId);
    }

    public int countRoom(int hrId, List<Integer> userIdList, boolean apply) {
        return hrChatUnreadCountDao.countRoom(hrId, userIdList, apply);
    }

    public List<HrChatUnreadCountRecord> fetchRooms(int hrId, List<Integer> userIdList, boolean apply, Timestamp updateTime, int pageSize) {
        return hrChatUnreadCountDao.fetchRooms(hrId, userIdList, apply, updateTime, pageSize);
    }

    public List<ChatVO> listLastMessage(List<Integer> roomIdList) {
        List<Integer> chatIdList = hrWxHrChatDao.lastMessageIdList(roomIdList);
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(new Condition("id", chatIdList, ValueOp.IN));
        List<HrWxHrChatDO> chatDOList = hrWxHrChatDao.getDatas(queryBuilder.buildQuery());
        if (chatDOList != null && chatDOList.size() > 0) {
            return chatDOList.stream().map(hrWxHrChatDO -> {
                ChatVO chatVO = new ChatVO();
                chatVO.setId(hrWxHrChatDO.getId());
                chatVO.setRoomId(hrWxHrChatDO.getChatlistId());
                chatVO.setCreateTime(hrWxHrChatDO.getCreateTime());
                chatVO.setOrigin(hrWxHrChatDO.getOrigin());
                chatVO.setAssetUrl(hrWxHrChatDO.getPicUrl());
                chatVO.setBtnContent(hrWxHrChatDO.getBtnContent());
                chatVO.setContent(hrWxHrChatDO.getContent());
                chatVO.setMsgType(hrWxHrChatDO.getMsgType());
                chatVO.setPositionId(hrWxHrChatDO.getPid());
                chatVO.setSpeaker(hrWxHrChatDO.getSpeaker());
                chatVO.setCompoundContent(hrWxHrChatDO.getCompoundContent());
                return chatFactory.outputHandle(chatVO);
            }).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public Result listMessage(int roomId, int chatId, int pageSize) {
        return hrWxHrChatDao.listMessage(roomId, chatId, pageSize);
    }

    public int countMessage(int roomId, int chatId) {
        return hrWxHrChatDao.countMessage(roomId, chatId);
    }

    public HrChatUnreadCountRecord fetchRoomById(int roomId) {
        return hrChatUnreadCountDao.fetchById(roomId);
    }

    public List<Integer> findUserIdByName(String keyword, List<Integer> chatUserIdList) {

        return userUserDao.fetchIdByIdListAndName(chatUserIdList, keyword);
    }

    public List<String> findUserNameByKeyword(String keyword, List<Integer> chatUserIdList) {

        return userUserDao.fetchIdByIdListAndName(chatUserIdList, keyword, 10);
    }

    public int countUnreadMessage(int hrId) {
        return hrWxHrChatListDao.countUnreadMessage(hrId);
    }

    public int fetchPublisher(int positionId) {
        return jobPositionDao.fetchPublisher(positionId);
    }

    public void updateApplyStatus(int publisher, int userId) {

        hrChatUnreadCountDao.updateApply(publisher, userId);
    }

    public boolean roleExist(int roleId, byte speaker) {
        if (speaker == ChatSpeakerType.USER.getValue()) {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(UserUser.USER_USER.ID.getName(), roleId);
            UserUserDO userUserDO = userUserDao.getData(queryBuilder.buildQuery());
            if (userUserDO != null && userUserDO.getId() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(UserHrAccount.USER_HR_ACCOUNT.ID.getName(), roleId);
            UserHrAccountDO userHrAccountDO = userHrAccountDao.getData(queryBuilder.buildQuery());
            if (userHrAccountDO != null && userHrAccountDO.getId() > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public List<Integer> fetchRoomIdByRole(int roleId, byte speaker) {
        if (speaker == ChatSpeakerType.USER.getValue()) {
            return hrChatUnreadCountDao.fetchRoomIdByUserId(roleId);
        } else {
            return hrChatUnreadCountDao.fetchRoomIdByHRId(roleId);
        }
    }

    public int fetchSuperAccount(int id) {
        return userHrAccountDao.fetchSuperHRByHrId(id);
    }

    public HrWxHrChatRecord getChat(int chatId) {
        return hrWxHrChatDao.fetchById(chatId);
    }

    public void updateChatRoom(HrChatUnreadCountRecord hrChatUnreadCountRecord) {
        hrChatUnreadCountDao.updateRecord(hrChatUnreadCountRecord);
    }

    public void updateChatRoom(HrWxHrChatListRecord hrWxHrChatListRecord) {
        hrWxHrChatListDao.updateRecord(hrWxHrChatListRecord);
    }

    /**
     * @param
     * @return
     * @description 通过roomid获取公司id
     * @author cjm
     * @date 2018/5/12
     */
    public Result getCompanyIdAndTokenByRoomId(int roomId) {
        return hrWxHrChatListDao.getCompanyIdAndTokenByRoomId(roomId);
    }

    /**
     * 查找聊天室
     * @author cjm
     * @param roomId 聊天室id
     * @return HrWxHrChatListDO
     */
    public HrWxHrChatListDO getChatRoomByRoomId(int roomId) {
        HrWxHrChatListDO chatRoom = null;
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("id", roomId);
        chatRoom = hrWxHrChatListDao.getData(queryUtil);
        return chatRoom;
    }


    /**
     * 分页查找聊天室下的聊天记录
     *
     * @param roomId   聊天室编号  @return 聊天内容集合
     * @param pageNo   页码
     * @param pageSize 分页信息
     * @author cjm
     */
    public Result listChatMsg(int roomId, int pageNo, int pageSize) {
        int startIndex = (pageNo - 1) * pageSize;
        return hrWxHrChatDao.listChat(roomId, startIndex, pageSize);
    }
}
