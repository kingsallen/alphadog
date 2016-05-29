package com.moseeker.useraccounts.service.impl;

import com.google.common.collect.Lists;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.sms.SmsSender;
import com.moseeker.common.util.*;
import com.moseeker.db.logdb.tables.records.LogUserloginRecordRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.userdb.tables.records.UserFavPositionRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import com.moseeker.thrift.gen.useraccounts.struct.UserFavoritePosition;
import com.moseeker.thrift.gen.useraccounts.struct.Userloginreq;
import com.moseeker.useraccounts.dao.UserDao;
import com.moseeker.useraccounts.dao.UserFavoritePositionDao;
import com.moseeker.useraccounts.dao.impl.ProfileDaoImpl;
import org.apache.thrift.TException;
import org.jooq.types.UByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登陆， 注册，合并等api的实现
 * 
 * @author yaofeng
 * @email wangyaofeng@moseeker.com
 */
@Service
public class UseraccountsServiceImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected BaseDao<UserWxUserRecord> wxuserdao;

    @Autowired
    protected UserDao userdao;

    @Autowired
    protected BaseDao<LogUserloginRecordRecord> loguserlogindao;

    @Autowired
    protected ProfileDaoImpl profileDao;

    @Autowired
    protected UserFavoritePositionDao userFavoritePositionDao;

    /**
     * 用户登陆， 返回用户登陆后的信息。
     */
    @Override
    public Response postuserlogin(Userloginreq userloginreq) throws TException {
        // TODO to add login log
        CommonQuery query = new CommonQuery();
        int parentid = 0;
        Map<String, String> filters = new HashMap<>();
        
        String code = userloginreq.getCode();
        if ( code != null ){
        	// 存在验证码,就是手机号+验证码登陆.
        	String mobile = userloginreq.getMobile();
            if (validateCode(mobile, code, 1)) {
                filters.put("username", mobile);
                ;
            } else {
                return ResponseUtils.success(ConstantErrorCodeMessage.INVALID_SMS_CODE);
            }        	
        }
        
        
        if (userloginreq.getUnionid() != null) {
            filters.put("unionid", userloginreq.getUnionid());
        } else {
            filters.put("username", userloginreq.getMobile());
            filters.put("password", MD5Util.md5(userloginreq.getPassword()));
        }

        query.setEqualFilter(filters);
        try {
            UserUserRecord user = userdao.getResource(query);
            if (user != null) {
                // login success
                
                if (user.getParentid() != null) {
                    // 当前帐号已经被合并到 parentid.
                    parentid = user.getParentid().intValue();
                    query = new CommonQuery();
                    filters = new HashMap<>();
                    filters.put("id", String.valueOf(parentid));
                    query.setEqualFilter(filters);
                    user = userdao.getResource(query);
                }
                
                if (user != null){
                    Map<String, Object> resp = new HashMap<>();

                    resp.put("user_id", user.getId().intValue());
                    resp.put("union_id", user.getUnionid());
                    resp.put("mobile", user.getMobile());
                    resp.put("last_login_time", user.getLastLoginTime());

                    user.setLastLoginTime(new Timestamp(new Date().getTime()));
                    user.setLoginCount(user.getLoginCount()+1);
                    
                    userdao.putResource(user);

                    return ResponseUtils.success(resp);                    
                }else{
                    // 主 user_id 不存在， 数据异常。
                    logger.error("postuserlogin error: ", parentid);
                    return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);                    
                }
                

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postuserlogin error: ", e);

        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_ACCOUNT_ILLEAGUE);

    }

    /**
     * 记录用户登出时的信息。可能会移到 service-manager 处理。
     * 
     * @param userid
     * @return
     * @throws TException
     */
    @Override
    public Response postuserlogout(int userid) throws TException {
        LogUserloginRecordRecord logout = new LogUserloginRecordRecord();
        logout.setUserId(userid);
        logout.setActiontype(2);
        try {
            loguserlogindao.postResource(logout);
            return ResponseUtils.success(null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postuserlogout error: ", e);
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    /**
     * 发送手机注册的验证码
     */
    @Override
    public Response postsendsignupcode(String mobile) throws TException {
        // TODO 未注册用户才能发送。
        CommonQuery query = new CommonQuery();
        Map<String, String> filters = new HashMap<>();

        /*  以下代码限制未注册用户才能发送。 由于存在 mobile+code的登陆方式, 老用户也可以发送验证码.
        if (mobile.length()>0){
        	filters.put("mobile", mobile);
            query.setEqualFilter(filters);
            try {
                UserUserRecord user = userdao.getResource(query);
                if (user != null) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_EXIST);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("getismobileregisted error: ", e);
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

            }            
        }
        */
        
        if (mobile.length()<10){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        
        if (SmsSender.sendSMS_signup(mobile)) {
            return ResponseUtils.success(null);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 注册手机号用户。
     *       password 为空时， 需要把密码直接发送给用户。
     * <p>
     *
     * @param user 用户实体
     * @param code 验证码
     * @return 新添加用户ID
     *
     * @exception TException
     *
     */
    @Override
    public Response postusermobilesignup(User user, String code) throws TException {

        // 空指针校验
        if(user == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }

        // TODO validate code.
        if (validateCode(String.valueOf(user.mobile), code, 1)) {
            ;
        } else {
            return ResponseUtils.success(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        }

        boolean hasPassword = true;
        String plainpassword = "892304";
        // 没有密码生成6位随机密码, TODO 需要md5加密
        if (user.password == null) {
            hasPassword = false;
            plainpassword = StringUtils.getRandomString(6); 
            user.password = MD5Util.md5(plainpassword); 
            
        }

        // 用户记录转换
        UserUserRecord userUserRecord = (UserUserRecord)BeanUtils.structToDB(user, UserUserRecord.class);

        try {
            // 添加用户
            int newuserid = userdao.postResource(userUserRecord);
            if (newuserid > 0) {
                Map<String, Object> hashmap = new HashMap<>();
                hashmap.put("user_id", newuserid);
                if (!hasPassword) {
                    // 未设置密码， 主动发送给用户。
                    SmsSender.sendSMS_signupRandomPassword(String.valueOf(user.mobile), plainpassword);
                }
                return ResponseUtils.success(hashmap); // 返回 user id
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postusermobilesignup error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }


    /**
     * 绑定用户的手机号和unionid， 如果unionid或者手机号均没有， 则post新增， 如果在一条记录里都有，提示已经绑定成功，
     * 如果在一条记录里有部分，unionid 或者 mobile， 补全。 否则unionid和mobile分别存在2条记录里面， 需要做合并。
     */
    @Override
    public Response postuserwxbindmobile(int appid, String unionid, String code, String mobile) throws TException {
        // TODO validate code.
        if (!StringUtils.isNullOrEmpty(code) && !validateCode(mobile, code, 1)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        }
        try {
            CommonQuery query1 = new CommonQuery();
            Map<String, String> filters1 = new HashMap<>();
            filters1.put("unionid", unionid);
            query1.setEqualFilter(filters1);
            UserUserRecord userUnionid = userdao.getResource(query1);

            CommonQuery query2 = new CommonQuery();
            Map<String, String> filters2 = new HashMap<>();
            filters2.put("mobile", mobile);
            query1.setEqualFilter(filters2);
            UserUserRecord userMobile = userdao.getResource(query2);

            if (userUnionid == null && userMobile == null) {
                // post
                return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_BIND_NONEED);
            } else if (userUnionid != null && userMobile != null
                    && userUnionid.getId().intValue() == userMobile.getId().intValue()) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_BIND_NONEED);
            } else if (userUnionid != null && userMobile == null) {
                userUnionid.setMobile(Long.valueOf(mobile));
                if (userdao.putResource(userUnionid) > 0){
                	return ResponseUtils.success(userUnionid);
                }else{
                    return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
                }
            } else if (userUnionid == null && userMobile != null) {
                userMobile.setUnionid(unionid);
                if (userdao.putResource(userMobile)>0){
                	return ResponseUtils.success(userMobile);
                }else{
                    return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
                }
            } else if (userUnionid != null && userMobile != null
                    && userUnionid.getId().intValue() != userMobile.getId().intValue()) {
                // 2 accounts, one unoinid, one mobile, need to merge.
                new Thread(() -> {
                    combineAccount(appid, userMobile, userUnionid);
                }).start();
                //来源：0:手机注册 1:聚合号一键登录 2:企业号一键登录, 7:PC(正常添加) 8:PC(我要投递) 9: PC(我感兴趣)
            	return ResponseUtils.success(userMobile);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            
        } finally {
            // do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

    }

    private void combineAccount(int appid,UserUserRecord userMobile, UserUserRecord userUnionid) {
        try {
            // unnionid置为子账号
            userUnionid.setParentid(userMobile.getId().intValue());
            if(userdao.putResource(userUnionid)>0){
            	// profile合并成功
            }else{
            	// 合并失败, log.
            }

            // weixin端(聚合号),weixin端（企业号） 发起, 保留微信端 profile; 否则保留pc端(无需处理).
            switch(appid){
            	case Constant.APPID_QX :
            	case Constant.APPID_PLATFORM :
                    ProfileProfileRecord userMobileProfileRecord = profileDao.getProfileByUserId(userMobile.getId().intValue());
                    // pc 端profile 设置为无效
                    if (userMobileProfileRecord != null) {
                        userMobileProfileRecord.setDisable(UByte.valueOf(Constant.DISABLE));
                        profileDao.putResource(userMobileProfileRecord);
                    }
                    
                    // 微信端profile转移到pc用户下.
                    ProfileProfileRecord userUnionProfileRecord = profileDao.getProfileByUserId(userUnionid.getId().intValue());
                    if (userUnionProfileRecord != null ){
                    	userUnionProfileRecord.setUserId(userMobile.getId());
                    	profileDao.putResource(userUnionProfileRecord);
                    }
                    
                    break;
            	case Constant.APPID_C:
            	default:
            		break;
            }


            // 合并业务代码
            // 合并sys_user_id表数据
            List<String> sysUserIds = getSystemUserIdTable();
            userdao.combineAccount(sysUserIds, "sys_user_id", userMobile.getId().intValue(),
                    userUnionid.getId().intValue());

            // 合并user_id表数据
            List<String> userIds = getUserIdTable();
            userdao.combineAccount(userIds, "user_id", userMobile.getId().intValue(), userUnionid.getId().intValue());

            // 合并sysuser_id数据
            List<String> syssUserIds = getSysUserIdTable();
            userdao.combineAccount(syssUserIds, "sysuser_id", userMobile.getId().intValue(),
                    userUnionid.getId().intValue());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private List<String> getSysUserIdTable() {
        List<String> tables = Lists.newArrayList("candidateDB.candidate_position_share_record", "hrDB.hr_wx_hr_chat_list",
                "userDB.user_fav_position", "userDB.user_intention", "userDB.user_wx_user", "userDB.user_wx_viewer");
        return tables;
    }

    private List<String> getUserIdTable() {
        List<String> tables = Lists.newArrayList("profile_profile");
        return tables;
    }

    private List<String> getSystemUserIdTable() {
        List<String> tables = Lists.newArrayList("candidate_company");
        return tables;
    }

    /**
     * 修改现有密码 
     * @param user_id
     * @param old_password 
     * @param password
     * @return
     * @throws TException
     */
    @Override
    public Response postuserchangepassword(int user_id, String old_password, String password) throws TException {
        CommonQuery query = new CommonQuery();
        Map<String, String> filters = new HashMap<>();
        filters.put("id", String.valueOf(user_id));
        filters.put("password", MD5Util.md5(old_password));
        query.setEqualFilter(filters);

        int result = 0;
        try {
            UserUserRecord user = userdao.getResource(query);

            if (user != null) {
                // login success
                if (user.getParentid() != null) {
                    // 当前帐号已经被合并到 parentid.
                    int parentid = user.getParentid().intValue();
                    query = new CommonQuery();
                    filters = new HashMap<>();
                    filters.put("id", String.valueOf(parentid));
                    query.setEqualFilter(filters);
                    UserUserRecord userParent = userdao.getResource(query);
                    userParent.setPassword(MD5Util.md5(password));
                    result = userdao.putResource(userParent);
                }
                user.setPassword(MD5Util.md5(password));
                result = userdao.putResource(user);
                if (result > 0) {
                    return ResponseUtils.success(null);
                }
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_PASSWORD_UNLEGAL);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postuserchangepassword error: ", e);

        } finally {
            // do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_UPDATE_PASSWORD_FAILED);

    }

    /**
     * 发送忘记密码的验证码
     */
    @Override
    public Response postusersendpasswordforgotcode(String mobile) throws TException {
        // TODO 只有已经存在的用户才能发验证码。
        CommonQuery query = new CommonQuery();
        Map<String, String> filters = new HashMap<>();
        
        if (mobile.length()>0){
            filters.put("mobile", mobile);
            query.setEqualFilter(filters);
            try {
                UserUserRecord user = userdao.getResource(query);
                if (user == null) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_MOBILE_NOTEXIST);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("getismobileregisted error: ", e);
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

            }            
        }
        
        if (SmsSender.sendSMS_passwordforgot(mobile)) {
            return ResponseUtils.success(null);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 忘记密码后重置密码,
     * @param code 验证码，可选， 填写时必须判断。不填时， 请先调用postvalidatepasswordforgotcode 进行验证。
     */
    @Override
    public Response postuserresetpassword(String mobile, String password,  String code) throws TException {

        if (code!=null && !validateCode(mobile, code, 2)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        }

        CommonQuery query = new CommonQuery();
        Map<String, String> filters = new HashMap<>();
        filters.put("mobile", mobile);
        query.setEqualFilter(filters);

        int result = 0;
        try {
            UserUserRecord user = userdao.getResource(query);

            if (user != null) {
                // login success
                if (user.getParentid() != null) {
                    // 当前帐号已经被合并到 parentid.
                    int parentid = user.getParentid().intValue();
                    query = new CommonQuery();
                    filters = new HashMap<>();
                    filters.put("id", String.valueOf(parentid));
                    query.setEqualFilter(filters);
                    UserUserRecord userParent = userdao.getResource(query);
                    userParent.setPassword(MD5Util.md5(password));
                    result = userdao.putResource(userParent);
                }
                user.setPassword(MD5Util.md5(password));
                result = userdao.putResource(user);
                if (result > 0) {
                    return ResponseUtils.success(null);
                }
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_MOBILE_NOTEXIST);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postuserresetpassword error: ", e);

        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_UPDATE_PASSWORD_FAILED);

    }

    @Override
    public Response postusermergebymobile(int appid, String mobile) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 获取用户数据
     *
     * @param userId 用户ID
     *
     * */
    @Override
    public Response getUserById(long userId) throws TException {
        try{
            if(userId > 0){
                return ResponseUtils.success(userdao.getUserById(userId));
            }else{
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        }catch (Exception e){
            // TODO Auto-generated catch block
            logger.error("getUserById error: ", e);
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    /**
     * 检查手机号是否已经注册。 exist: true 已经存在， exist：false 不存在。
     * @param mobile
     * @return
     * @throws TException
     */
    @Override
    public Response getismobileregisted(String mobile) throws TException {
        CommonQuery query = new CommonQuery();
        Map<String, String> filters = new HashMap<>();
        if (mobile.length()>0){
            filters.put("mobile", mobile);
            query.setEqualFilter(filters);
            try {
                UserUserRecord user = userdao.getResource(query);
                Map<String, Boolean> hashmap = new HashMap<>();
                if (user == null) {
                    hashmap.put("exist", false);
                }else{
                    hashmap.put("exist", true);
                }
                return ResponseUtils.success(hashmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("getismobileregisted error: ", e);
            }            
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXHAUSTED);
    }

    /**
     * 修改手机号时， 先要向当前手机号发送验证码。
     *
     */
    @Override
    public Response postsendchangemobilecode(String oldmobile) throws TException {
        // TODO 只有已经存在的用户才能发验证码。
        CommonQuery query = new CommonQuery();
        Map<String, String> filters = new HashMap<>();
        
        if (oldmobile.length()>0){
            filters.put("mobile", oldmobile);
            query.setEqualFilter(filters);
            try {
                UserUserRecord user = userdao.getResource(query);
                if (user == null) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_MOBILE_NOTEXIST);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("postsendchangemobilecode error: ", e);
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

            }            
        }
        
        if (SmsSender.sendSMS_changemobilecode(oldmobile)) {
            return ResponseUtils.success(null);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 修改手机号时， 验证现有手机号的验证码。
     *
     */
    @Override
    public Response postvalidatechangemobilecode(String oldmobile, String code) throws TException {
        if ( !validateCode(oldmobile, code, 3)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        }else{
            return ResponseUtils.success(null);
        }
    }

    /**
     * 修改手机号时，  向新手机号发送验证码。
     *
     */
    @Override
    public Response postsendresetmobilecode(String newmobile) throws TException {
        CommonQuery query = new CommonQuery();
        Map<String, String> filters = new HashMap<>();
        
        if (newmobile.length()>0){
            filters.put("mobile", newmobile);
            query.setEqualFilter(filters);
            try {
                UserUserRecord user = userdao.getResource(query);
                if (user != null) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_EXIST);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("postsendresetmobilecode error: ", e);
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

            }            
        }
        
        if (SmsSender.sendSMS_resetmobilecode(newmobile)) {
            return ResponseUtils.success(null);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 修改当前用户手机号。
     * @param user_id  
     * @param newmobile 新手机号
     * @param code  新手机号的验证码
     */
    @Override
    public Response postresetmobile(int user_id, String newmobile, String code) throws TException {
        if (code!=null && !validateCode(newmobile, code, 2)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        }

        CommonQuery query = new CommonQuery();
        Map<String, String> filters = new HashMap<>();
        filters.put("id", String.valueOf(user_id));
        query.setEqualFilter(filters);

        int result = 0;
        try {
            UserUserRecord user = userdao.getResource(query);

            if (user != null) {
                // login success
                if (user.getParentid() != null) {
                    // 当前帐号已经被合并到 parentid.
                    int parentid = user.getParentid().intValue();
                    query = new CommonQuery();
                    filters = new HashMap<>();
                    filters.put("id", String.valueOf(parentid));
                    query.setEqualFilter(filters);
                    UserUserRecord userParent = userdao.getResource(query);
                    userParent.setMobile(Long.parseLong(newmobile));
                    result = userdao.putResource(userParent);
                }
                user.setMobile(Long.parseLong(newmobile));
                result = userdao.putResource(user);
                if (result > 0) {
                    return ResponseUtils.success(null);
                }
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_NOTEXIST);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postuserresetpassword error: ", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    /**
     * 获取我感兴趣
     * <p>
     *
     * @param userId 用户ID
     * @param positionId 职位ID
     * //@param favorite 0:收藏, 1:取消收藏, 2:感兴趣
     *
     * @return data : {true //感兴趣} {false //不感兴趣}
     *
     * TODO : 不知道以后职位收藏啥逻辑, 赞不支持
     */
    @Override
    public Response getUserFavPositionCountByUserIdAndPositionId(int userId, int positionId) throws TException {
        try {
            byte favorite = 2;
            Integer count = userFavoritePositionDao.getUserFavPositionCountByUserIdAndPositionId(userId, positionId, favorite);
            return ResponseUtils.success(count > 0?true:false);
        }catch (Exception e){
            return ResponseUtils.success(ConstantErrorCodeMessage.USER_FAV_POSITION_FAILED);
        }
    }

    /**
     * 标记我感兴趣/职位收藏/取消职位收藏
     * <p>
     *
     * @param userFavoritePosition 用户职位关系实体
     * @return 关系表主键Id
     * @exception TException
     * */
    @Override
    public Response postUserFavoritePosition(UserFavoritePosition userFavoritePosition) throws TException {
        try {
            // 必填项验证
            Response response = validateUserFavoritePosition(userFavoritePosition);
            if (response.status > 0){
                return response;
            }

            // 添加用户感兴趣的职位
            UserFavPositionRecord userFavPositionRecord = (UserFavPositionRecord) BeanUtils.structToDB(userFavoritePosition,
                    UserFavPositionRecord.class);

            int userFavoritePositionId = userFavoritePositionDao.postResource(userFavPositionRecord);
            if (userFavoritePositionId > 0) {
                Map<String, Object> hashmap = new HashMap<>();
                hashmap.put("userFavoritePositionId", userFavoritePositionId);
                return ResponseUtils.success(hashmap); // 返回 userFavoritePositionId
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postUserFavoritePosition UserFavPositionRecord error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    /**
     * 验证我感兴趣
     * <p>
     *
     * */
    private Response validateUserFavoritePosition(UserFavoritePosition userFavoritePosition){

        Response response = new Response(0, "ok");
        if(userFavoritePosition.sysuser_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "user_id"));
        }

        if(userFavoritePosition.position_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "position_id"));
        }
        return response;
    }

    /**
     * 验证忘记密码的验证码是否正确
     */
    @Override
    public Response postvalidatepasswordforgotcode(String mobile, String code) throws TException {
        if ( !validateCode(mobile, code, 2)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        }else{
            return ResponseUtils.success(null);
        }    
    }
    
    /**
     * 返回手机验证码的正确性, true 验证码正确。
     * 
     * @param mobile
     *            手机号
     * @param code
     *            验证码
     * @param type
     *            1:注册 2:忘记密码
     */
    private boolean validateCode(String mobile, String code, int type) {
        String codeinRedis = null;
        RedisClient redisclient = RedisClientFactory.getCacheClient();
        switch (type) {
        case 1:
            codeinRedis = redisclient.get(0, "SMS_SIGNUP", mobile);
            if (code.equals(codeinRedis)) {
                redisclient.del(0, "SMS_SIGNUP", mobile);
                return true;
            }
            break;
        case 2:
            codeinRedis = redisclient.get(0, "SMS_PWD_FORGOT", mobile);
            if (code.equals(codeinRedis)) {
                redisclient.del(0, "SMS_PWD_FORGOT", mobile);
                return true;
            }
        case 3:
            codeinRedis = redisclient.get(0, "SMS_CHANGEMOBILE_CODE", mobile);
            if (code.equals(codeinRedis)) {
                redisclient.del(0, "SMS_CHANGEMOBILE_CODE", mobile);
                return true;
            }
        case 4:
            codeinRedis = redisclient.get(0, "SMS_RESETMOBILE_CODE", mobile);
            if (code.equals(codeinRedis)) {
                redisclient.del(0, "SMS_RESETMOBILE_CODE", mobile);
                return true;
            }
            break;
        }

        return false;
    }
    
}