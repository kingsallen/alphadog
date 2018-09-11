package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.userdb.UserFavPositionDao;
import com.moseeker.baseorm.dao.userdb.UserSettingsDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserFavPositionRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.baseorm.util.SmsSender;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.*;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.common.weixin.AccountMng;
import com.moseeker.common.weixin.QrcodeType;
import com.moseeker.common.weixin.WeixinTicketBean;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.useraccounts.struct.BindType;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import com.moseeker.thrift.gen.useraccounts.struct.UserFavoritePosition;
import com.moseeker.thrift.gen.useraccounts.struct.Userloginreq;
import com.moseeker.useraccounts.pojo.MessageTemplate;
import com.moseeker.useraccounts.service.BindOnAccountService;
import java.util.*;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * 用户登陆， 注册，合并等api的实现
 *
 * @author yaofeng
 * @email wangyaofeng@moseeker.com
 */
@Service
@CounterIface
public class UseraccountsService {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);

    @Autowired
    protected UserWxUserDao wxuserdao;

    @Autowired
    protected UserUserDao userdao;

    @Autowired
    protected ProfileProfileDao profileDao;

    @Autowired
    protected UserSettingsDao userSettingDao;

    @Autowired
    protected UserFavPositionDao userFavoritePositionDao;


    @Autowired
    protected SmsSender smsSender;

    @Autowired
    protected HrWxWechatDao wechatDao;

    @Autowired
    protected Map<String, BindOnAccountService> bindOnAccount;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    private ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();

    /**
     * 账号换绑操作
     */
    public Response userChangeBind(String unionid, String mobile,String countryCode) {
        try {
            // 通过unionid查询，查询新微信是否已经被绑定
            try {
                configUtils.loadResource("service.properties");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            String wechatId = configUtils.get("wechat_id", String.class, "42");
            Query.QueryBuilder query = new Query.QueryBuilder();
            query.where("unionid", unionid).and("wechat_id", wechatId);
            UserWxUserDO userWxUserDO = wxuserdao.getData(query.buildQuery());
            // 新微信未找到，说明绑定有问题
            if (userWxUserDO == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.WEXIN_IS_INVALID);
            }
            // 需要新绑定的微信已经被其他用户绑定
            if (userWxUserDO.getSysuserId() != 0) {
                UserUserRecord user = userdao.getUserById(userWxUserDO.getSysuserId());
                if(!user.getUsername().equals(unionid)){
                    return ResponseUtils.fail(ConstantErrorCodeMessage.WEIXIN_HASBEEN_BIND);
                }

                postuserbindmobile(1,unionid,"",countryCode,mobile,BindType.WECHAT);

            } else {  // 新绑定的微信未被其他用户绑定
                query.clear();
                query.where("username", mobile).and("country_code",countryCode);
                UserUserDO userUserDO = userdao.getData(query.buildQuery());
                // 验证手机号码是否正确
                if (userUserDO == null) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.MOBILE_IS_INVALID);
                }
                // 验证新绑定的微信是否和之前的一样，如果一样则不需要换绑
                if (userUserDO.getUnionid().equals(unionid)) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.WEXIN_IS_SAME);
                }
                // 更新user_user
                userUserDO.setUnionid(unionid);
                userdao.updateData(userUserDO);

                // 更新UserWxUser
                userWxUserDO.setSysuserId(userUserDO.getId());
                userWxUserDO.setUnionid(unionid);
                wxuserdao.updateData(userWxUserDO);
            }
            return ResponseUtils.success(null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }


    /**
     * 用户登陆， 返回用户登陆后的信息。
     */
    public Response postuserlogin(Userloginreq userloginreq) throws TException {
        // TODO to add login log
        Query.QueryBuilder query = new Query.QueryBuilder();
        int parentid = 0;
        String code = userloginreq.getCode();
        if (code != null) {
            // 存在验证码,就是手机号+验证码登陆.
            String mobile = userloginreq.getMobile();
            if(StringUtils.isNotNullOrEmpty(userloginreq.getCountryCode()) && !"86".equals(userloginreq.getCountryCode())){
                mobile=userloginreq.getCountryCode()+mobile;
            }
            if (validateCode(mobile, code, 1)) {
                query.where("username", userloginreq.getMobile()).and("country_code",userloginreq.getCountryCode());
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
            }
        } else if (userloginreq.getUnionid() != null) {
            query.where("unionid", userloginreq.getUnionid());
        } else {
            query.where("username", userloginreq.getMobile()).and("country_code",userloginreq.getCountryCode()).and("password", MD5Util.encryptSHA(userloginreq.getPassword()));
        }

        try {
            UserUserRecord user = userdao.getRecord(query.buildQuery());
            if (user != null) {
                // login success
                parentid = user.getParentid().intValue();
                if (parentid > 0) {
                    // 当前帐号已经被合并到 parentid.
                    query.clear();
                    query.where("id", String.valueOf(parentid));
                    user = userdao.getRecord(query.buildQuery());
                }

                if (user != null) {
                    Map<String, Object> resp = new HashMap<>();

                    resp.put("user_id", user.getId().intValue());
                    resp.put("unionid", user.getUnionid());

                    if (user.getUsername().length() < 12) {
                        resp.put("mobile", user.getUsername());
                        resp.put("countryCode", user.getCountryCode());
                    } else {
                        resp.put("mobile", "");
                    }

                    resp.put("last_login_time", user.getLastLoginTime());
                    resp.put("name", user.getName());
                    resp.put("headimg", user.getHeadimg());

                    user.setLastLoginTime(new Timestamp(new Date().getTime()));
                    user.setLoginCount(user.getLoginCount() + 1);

                    userdao.updateRecord(user);

                    return ResponseUtils.success(resp);
                } else {
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
     */
    public Response postuserlogout(int userid) throws TException {
        try {
            logger.info("userid:{} log out", userid);
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
    public Response postsendsignupcode(String countryCode, String mobile) throws TException {
        // TODO 未注册用户才能发送。
        /*
         * Query query = new Query(); Map<String, String> filters =
		 * new HashMap<>();
		 */

		/*
         * 以下代码限制未注册用户才能发送。 由于存在 mobile+code的登陆方式, 老用户也可以发送验证码. if
		 * (mobile.length()>0){ filters.put("mobile", mobile);
		 * query.setEqualFilter(filters); try { UserUserRecord user =
		 * userdao.getResource(query); if (user != null) { return
		 * ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_EXIST); } }
		 * catch (Exception e) { // TODO Auto-generated catch block
		 * logger.error("getismobileregisted error: ", e); return
		 * ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		 *
		 * } }
		 */

        boolean result=false;
        if (mobile.length() < 5) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        try {
            result = smsSender.sendSMS(mobile, 1,countryCode);
            if (result) {
                return ResponseUtils.success("success");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
            }
        } catch (Exception e) {
            logger.info(e.toString());
            return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);

        }

    }

    /**
     * 发送语音验证码
     * @param mobile
     * @return
     */
    public Response postsendsignupcodeVoice(String mobile) {

        if (mobile.length() < 10) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }

        String code = redisClient.get(0, "SMS_SIGNUP", mobile);
        if(StringUtils.isNotNullOrEmpty(code)) {
            int count  = NumberUtils.toInt(redisClient.get(0, "SMS_SIGNUP", mobile.concat("_").concat(String.valueOf(code))), 0);
            if (count == 0) {
                if (smsSender.sendSMS_signup_voice(mobile, code)) {
                    redisClient.set(0, "SMS_SIGNUP", mobile.concat("_").concat(String.valueOf(code)), "1");
                    return ResponseUtils.success(null);
                } else {
                    logger.info("语音验证码发送失败，mobile={}，code={}", mobile, code);
                    return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
                }
            } else {
                logger.info("用户：{}，已发送过语音验证码：{}，请勿重复操作", mobile, code);
                return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
            }
        } else {
            logger.info("用户：{}，请确认发送过短信验证码再进行该操作", mobile);
            return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
        }
    }

    /**
     * 注册手机号用户。 password 为空时， 需要把密码直接发送给用户。
     * <p>
     *
     * @param user 用户实体
     * @param code 验证码 , 可选, 有的时候必须验证.
     * @return 新添加用户ID
     */
    public Response postusermobilesignup(User user, String code) throws TException {

        boolean hasPassword = true; // 判断是否需要生成密码
        String plainPassword = "892304"; // 没有密码用户的初始密码, 随机数替换

        // 空指针校验
        if (user == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }

        String validateMobile=String.valueOf(user.mobile);
        if(StringUtils.isNotNullOrEmpty(user.getCountryCode()) && !"86".equals(user.getCountryCode())){
            validateMobile=user.getCountryCode()+user.mobile;
        }

        if (!StringUtils.isNullOrEmpty(code) && !validateCode(validateMobile, code, 1)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        }

        // 没有密码生成6位随机密码
        if (user.password == null) {
            hasPassword = false;
            plainPassword = StringUtils.getRandomString(6);
            user.password = MD5Util.encryptSHA(plainPassword);
        } else {
            user.password = MD5Util.encryptSHA(user.password);
        }

        try {
            // 添加用户
            int newCreateUserId = userdao.createUser(user);
            if (newCreateUserId > 0) {

                // 未设置密码, 主动短信通知用户
                if (!hasPassword) {
                    smsSender.sendSMS_signupRandomPassword(String.valueOf(user.mobile), plainPassword,String.valueOf(user.countryCode));
                }

                // // 初始化 user_setting 表.
                // UserSettingsRecord userSettingsRecord = new
                // UserSettingsRecord();
                // userSettingsRecord.setUserId((int)(newCreateUserId));
                // userSettingsRecord.setPrivacyPolicy((byte)(0));
                // userSettingDao.postResource(userSettingsRecord);

                return ResponseUtils.success(new HashMap<String, Object>() {

                    private static final long serialVersionUID = -5518436764754085050L;

                    {
                        put("user_id", newCreateUserId);
                    }
                }); // 返回 user id
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postusermobilesignup error: ", e);

        } finally {
            // do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_EXIST);
    }

    /**
     * 绑定用户的手机号和unionid， 如果在一条记录里都有，提示已经绑定成功， 如果在一条记录里有部分，unionid 或者 mobile， 补全。
     * 否则unionid和mobile分别存在2条记录里面， 需要做合并。 如果unionid或者手机号均没有， 应该在之前先注册.
     * code验证码可选.
     */
    @Deprecated
    public Response postuserwxbindmobile(int appid,String countryCode, String unionid, String code, String mobile) throws TException {
        try {
            return bindOnAccount.get("wechat").handler(appid, unionid, mobile,countryCode);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

    }

    /**
     * 绑定用户的手机号和userid， 如果在一条记录里都有，提示已经绑定成功， 如果在一条记录里有部分，userid 或者 mobile， 补全。
     * 否则userid和mobile分别存在2条记录里面， 需要做合并。 如果userid或者手机号均没有， 应该在之前先注册.
     */
    @Deprecated
    public Response postuserbdbindmobile(int appid,String countryCode, String userid, String mobile) throws TException {
        try {
            return bindOnAccount.get("baidu").handler(appid, userid, mobile,countryCode);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    public Response postuserbindmobile(int appid, String unionid, String code,String countryCode, String mobile, BindType bindType) throws TException {
        try {
            return bindOnAccount.get(String.valueOf(bindType).toLowerCase()).handler(appid, unionid, mobile,countryCode);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    /**
     * 修改现有密码
     */
    public Response postuserchangepassword(int user_id, String old_password, String password) throws TException {


        if (StringUtils.isNullOrEmpty(password) || StringUtils.isNullOrEmpty(old_password)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }

        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("id", String.valueOf(user_id)).and("password", MD5Util.encryptSHA(old_password));

        int result;
        try {
            UserUserRecord user = userdao.getRecord(query.buildQuery());

            if (user != null) {
                // login success
                int parentid = user.getParentid().intValue();
                if (parentid > 0) {
                    // 当前帐号已经被合并到 parentid.
                    query.clear();
                    query.where("id", String.valueOf(parentid));
                    UserUserRecord userParent = userdao.getRecord(query.buildQuery());
                    userParent.setPassword(MD5Util.encryptSHA(password));
                    userdao.updateRecord(userParent);
                }
                user.setPassword(MD5Util.encryptSHA(password));
                result = userdao.updateRecord(user);
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
    public Response postusersendpasswordforgotcode(String countryCode, String mobile) throws TException {
        // TODO 只有已经存在的用户才能发验证码。
        Query.QueryBuilder query = new Query.QueryBuilder();

        if (mobile.length() > 0) {
            query.where("username", mobile).and("country_code",countryCode);
            try {
                UserUserRecord user = userdao.getRecord(query.buildQuery());
                if (user == null) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_MOBILE_NOTEXIST);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("getismobileregisted error: ", e);
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

            }
        }


        boolean result=false;
        if (mobile.length() < 5) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        try {
            result = smsSender.sendSMS(mobile, 2,countryCode);
            if (result) {
                return ResponseUtils.success("success");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
            }
        } catch (Exception e) {
            logger.info(e.toString());
            return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);

        }
    }

    /**
     * 忘记密码后重置密码,
     *
     * @param code 验证码，可选， 填写时必须判断。不填时， 请先调用postvalidatepasswordforgotcode 进行验证。
     */
    public Response postuserresetpassword(String mobile, String password, String code,String countryCode) throws TException {
        String validateMobile=mobile;
        if(StringUtils.isNotNullOrEmpty(countryCode) && !"86".equals(countryCode)){
            validateMobile=countryCode+mobile;
        }
        if (code != null && !validateCode(validateMobile, code, 2)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        }

        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("username", mobile).and("country_code",countryCode);
        int result;
        try {
            UserUserRecord user = userdao.getRecord(query.buildQuery());

            if (user != null) {
                // login success
                int parentid = user.getParentid().intValue();
                String newPassword = MD5Util.encryptSHA(password);
                if (parentid > 0) {
                    // 当前帐号已经被合并到 parentid.
                    query.clear();
                    query.where("id", String.valueOf(parentid));
                    UserUserRecord userParent = userdao.getRecord(query.buildQuery());
                    if (newPassword.equals(userParent.getPassword())) {
                        return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_PASSWORD_REPEATPASSWORD);
                    }
                    userParent.setPassword(newPassword);
                    userdao.updateRecord(userParent);
                }
                if (newPassword.equals(user.getPassword())) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_PASSWORD_REPEATPASSWORD);
                }
                user.setPassword(newPassword);
                result = userdao.updateRecord(user);
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

    public Response postusermergebymobile(int appid, String mobile) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 获取用户数据
     *
     * @param userId 用户ID
     */
    public Response getUserById(long userId) throws TException {
        try {
            if (userId > 0) {
                return ResponseUtils.success(userdao.getUserById(userId));
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("getUserById error: ", e);
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    public Response getUsers(CommonQuery query) throws TException {
        try {
            List<User> users = userdao.getDatas(QueryConvert.commonQueryConvertToQuery(query), User.class);
            return ResponseUtils.success(users);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 更新用户数据
     *
     * @param user 用户实体
     */
    public Response updateUser(User user) throws TException {
        try {
            if (user != null && user.getId() > 0) {
                if (StringUtils.isNotNullOrEmpty(user.getPassword())) {
                    user.setPassword(MD5Util.encryptSHA(user.getPassword()));
                }
                // 用户记录转换
                UserUserRecord userUserRecord = (UserUserRecord) BeanUtils.structToDB(user, UserUserRecord.class);
                Timestamp updateTime = new Timestamp(System.currentTimeMillis());
                userUserRecord.setUpdateTime(updateTime);
                if (userdao.updateRecord(userUserRecord) > 0) {
                    if (user.isSetUsername() || user.isSetMobile() || user.isSetEmail() || user.isSetName()
                            || user.isSetHeadimg()) {
                        profileDao.updateUpdateTimeByUserId((int) user.getId());
                    }
                    return ResponseUtils.success(null);
                } else {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
                }
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            logger.error("updateUser error: ", e);

        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    /**
     * 检查手机号是否已经注册。 exist: true 已经存在， exist：false 不存在。
     */
    public Response getismobileregisted(String mobile,String countryCode) throws TException {
        Query.QueryBuilder query = new Query.QueryBuilder();
        if (mobile != null && mobile.length() > 0) {
            query.where("username", mobile).and("country_code",countryCode);

            try {
                UserUserRecord user = userdao.getRecord(query.buildQuery());
                Map<String, Boolean> hashmap = new HashMap<>();
                if (user == null) {
                    hashmap.put("exist", false);
                } else {
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
     */
    public Response postsendchangemobilecode(String countryCode,String oldmobile) throws TException {
        // TODO 只有已经存在的用户才能发验证码。
        Query.QueryBuilder query = new Query.QueryBuilder();

        if (oldmobile.length() > 0) {
            query.where("username", oldmobile).and("country_code",countryCode);
            try {
                UserUserRecord user = userdao.getRecord(query.buildQuery());
                if (user == null) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_MOBILE_NOTEXIST);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("postsendchangemobilecode error: ", e);
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

            }
        }

        boolean result=false;
        if (oldmobile.length() < 5) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        try {
            result = smsSender.sendSMS(oldmobile, 3,countryCode);
            if (result) {
                return ResponseUtils.success("success");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
            }
        } catch (Exception e) {
            logger.info(e.toString());
            return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);

        }
    }

    /**
     * 修改手机号时， 验证现有手机号的验证码。
     */
    public Response postvalidatechangemobilecode(String countryCode, String oldmobile, String code) throws TException {

        if(StringUtils.isNotNullOrEmpty(countryCode) && !"86".equals(countryCode)){
            oldmobile=countryCode+oldmobile;
        }
        if (!validateCode(oldmobile, code, 3)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        } else {
            return ResponseUtils.success(null);
        }
    }

    /**
     * 修改手机号时， 向新手机号发送验证码。
     */
    public Response postsendresetmobilecode(String countryCode, String newmobile) throws TException {
        Query.QueryBuilder query = new Query.QueryBuilder();

        if (newmobile.length() > 0) {
            query.where("username", newmobile).and("country_code",countryCode);
            try {
                UserUserRecord user = userdao.getRecord(query.buildQuery());
                if (user != null) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_EXIST);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("postsendresetmobilecode error: ", e);
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

            }
        }
        boolean result=false;
        if (newmobile.length() < 5) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        try {
            result = smsSender.sendSMS(newmobile, 4,countryCode);
            if (result) {
                return ResponseUtils.success("success");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
            }
        } catch (Exception e) {
            logger.info(e.toString());
            return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);

        }
    }

    /**
     * 修改当前用户手机号。
     *
     * @param newmobile 新手机号
     * @param code      新手机号的验证码
     */
    public Response postresetmobile(int user_id, String countryCode, String newmobile, String code) throws TException {
        String verifynewmobile = newmobile;
        if(StringUtils.isNotNullOrEmpty(countryCode) && !"86".equals(countryCode)){
            verifynewmobile = countryCode + newmobile;
        }
        if (code != null && !validateCode(verifynewmobile, code, 4)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        }

        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("id", String.valueOf(user_id));

        int result;
        try {
            UserUserRecord user = userdao.getRecord(query.buildQuery());

            if (user != null) {
                // login success
                int parentid = user.getParentid().intValue();
                if (parentid > 0) {
                    // 当前帐号已经被合并到 parentid.
                    query.clear();
                    query.where("id", String.valueOf(parentid));
                    UserUserRecord userParent = userdao.getRecord(query.buildQuery());
                    userParent.setMobile(Long.parseLong(newmobile));
                    userParent.setUsername(newmobile);
                    userParent.setCountryCode(countryCode);
                    userdao.updateRecord(userParent);
                }
                user.setMobile(Long.parseLong(newmobile));
                user.setUsername(newmobile);
                user.setCountryCode(countryCode);

                result = userdao.updateRecord(user);
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
     * @param userId     用户ID
     * @param positionId 职位ID //@param favorite 0:收藏, 1:取消收藏, 2:感兴趣
     * @return data : {true //感兴趣} {false //不感兴趣} <p> TODO : 不知道以后职位收藏啥逻辑, 赞不支持
     */
    public Response getUserFavPositionCountByUserIdAndPositionId(int userId, int positionId) throws TException {
        try {
            byte favorite = 2;
            Integer count = userFavoritePositionDao.getUserFavPositionCountByUserIdAndPositionId(userId, positionId,
                    favorite);
            return ResponseUtils.success(count > 0 ? true : false);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.USER_FAV_POSITION_FAILED);
        }
    }

    /**
     * 标记我感兴趣/职位收藏/取消职位收藏
     * <p>
     *
     * @param userFavoritePosition 用户职位关系实体
     * @return 关系表主键Id
     */
    public Response postUserFavoritePosition(UserFavoritePosition userFavoritePosition) throws TException {
        try {
            // 必填项验证
            Response response = validateUserFavoritePosition(userFavoritePosition);
            if (response.status > 0) {
                return response;
            }

            // 添加用户感兴趣的职位
            UserFavPositionRecord userFavPositionRecord = (UserFavPositionRecord) BeanUtils
                    .structToDB(userFavoritePosition, UserFavPositionRecord.class);

            int userFavoritePositionId = userFavoritePositionDao.addRecord(userFavPositionRecord).getId();
            if (userFavoritePositionId > 0) {
                Map<String, Object> hashmap = new HashMap<>();
                hashmap.put("userFavoritePositionId", userFavoritePositionId);
//				Thread t = new Thread(() -> {
//					try {
//						MessageTemplate messageTemplate = fetchMessageTemplate(userFavoritePosition.getPosition_id(), userFavoritePosition.getSysuser_id());
//						MessageTemplateNoticeStruct mtns = createMessageTemplate(messageTemplate);
//
//						mqService.messageTemplateNotice(mtns);
//					} catch (Exception e) {
//						logger.error(e.getMessage(), e);
//					}
//				});
//				t.start();
//				MessageTemplateNoticeStruct messageTemplateNoticeStruct = new MessageTemplateNoticeStruct();

                return ResponseUtils.success(hashmap); // 返回
                // userFavoritePositionId
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postUserFavoritePosition UserFavPositionRecord error: ", e);
        } finally {
            // do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    private MessageTemplateNoticeStruct createMessageTemplate(MessageTemplate messageTemplate) {
        if (messageTemplate != null) {
            MessageTemplateNoticeStruct message = new MessageTemplateNoticeStruct();
            StringBuffer content = new StringBuffer();
            content.append("您发布的“");
            content.append(messageTemplate.getPositionTitle() + "”");
            content.append("职位有了一位新候选人，请及时与TA联系。");
            message.setUser_id(messageTemplate.getHrAccountId());
            message.setSys_template_id(TemplateId.TEMPLATE_MESSAGE_FAV_HR.getValue());
            message.setType(UserType.PC.getValueToByte());
            HashMap<String, Object> data = new HashMap<String, Object>();

            //message.setData(data);

        }
        return null;
    }

    private MessageTemplate fetchMessageTemplate(int positionId, int userId) {
        MessageTemplate messageTemplate = null;
        try {
            JobPositionRecord position = userFavoritePositionDao.getUserFavPositiond(positionId);
            UserUserRecord user = userdao.getUserById(userId);
            if (position != null && user != null) {
                messageTemplate = new MessageTemplate();
                messageTemplate.setPositionTitle(position.getTitle());
                messageTemplate.setHrAccountId(position.getPublisher());
                if (StringUtils.isNotNullOrEmpty(user.getName())) {
                    messageTemplate.setName(user.getName());
                } else if (StringUtils.isNotNullOrEmpty(user.getNickname())) {
                    messageTemplate.setName(user.getNickname());
                } else {
                    messageTemplate.setName(user.getUsername());
                }
                messageTemplate.setContact(String.valueOf(user.getMobile()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {

        }
        return messageTemplate;
    }

    /**
     * 验证我感兴趣
     * <p>
     */
    private Response validateUserFavoritePosition(UserFavoritePosition userFavoritePosition) {

        Response response = new Response(0, "ok");
        if (userFavoritePosition.sysuser_id == 0) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "user_id"));
        }

        if (userFavoritePosition.position_id == 0) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "position_id"));
        }
        return response;
    }

    /**
     * 验证忘记密码的验证码是否正确
     */
    public Response postvalidatepasswordforgotcode(String countryCode, String mobile, String code) throws TException {
        if(StringUtils.isNotNullOrEmpty(countryCode) && !"86".equals(countryCode)){
            mobile=countryCode+mobile;
        }

        if (!validateCode(mobile, code, 2)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        } else {
            return ResponseUtils.success(null);
        }
    }

    /**
     * 返回手机验证码的正确性, true 验证码正确。
     *
     * @param mobile 手机号
     * @param code   验证码
     * @param type   1:注册 2:忘记密码
     */
    private boolean validateCode(String mobile, String code, int type) {
        String codeinRedis = null;
        try {
            switch (type) {
                case 1:
                    codeinRedis = redisClient.get(0, "SMS_SIGNUP", mobile);
                    if (code.equals(codeinRedis)) {
                        redisClient.del(0, "SMS_SIGNUP", mobile);
                        return true;
                    }
                    break;
                case 2:
                    codeinRedis = redisClient.get(0, "SMS_PWD_FORGOT", mobile);
                    if (code.equals(codeinRedis)) {
                        redisClient.del(0, "SMS_PWD_FORGOT", mobile);
                        return true;
                    }
                case 3:
                    codeinRedis = redisClient.get(0, "SMS_CHANGEMOBILE_CODE",
                            mobile);
                    if (code.equals(codeinRedis)) {
                        redisClient.del(0, "SMS_CHANGEMOBILE_CODE", mobile);
                        return true;
                    }
                case 4:
                    codeinRedis = redisClient
                            .get(0, "SMS_RESETMOBILE_CODE", mobile);
                    if (code.equals(codeinRedis)) {
                        redisClient.del(0, "SMS_RESETMOBILE_CODE", mobile);
                        return true;
                    }
                    break;
                default:
            }
        } catch (RedisException e) {
            WarnService.notify(e);
        }
        return false;
    }

    public Response validateVerifyCode(String mobile, String code, int type,String countryCode) throws TException {
        ValidateUtil vu = new ValidateUtil();
        vu.addRequiredStringValidate("手机号码", mobile, null, null);
        vu.addRequiredStringValidate("验证码", code, null, null);
        String message = vu.validate();
        if (StringUtils.isNullOrEmpty(message)) {
            if(StringUtils.isNotNullOrEmpty(countryCode) && !"86".equals(countryCode)){
                mobile=countryCode+mobile;
            }
            boolean flag = validateCode(mobile, code, type);
            if (flag) {
                return ResponseUtils.success(1);
            } else {
                return ResponseUtils.success(0);
            }
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", message));
        }
    }

    public Response sendVerifyCode(String mobile, int type,String countryCode) throws Exception {
        boolean result=false;
        result = smsSender.sendSMS(mobile, type,countryCode);
        if (result) {
            return ResponseUtils.success("success");
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
        }
    }

    public Response checkEmail(String email) throws TException {
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("email", email).and("email_verified", "1");
        try {
            UserUserRecord record = userdao.getRecord(qu.buildQuery());
            if (record == null) {
                return ResponseUtils.success(1);
            } else {
                return ResponseUtils.success(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        } finally {

        }
    }

    /**
     * 创建微信二维码
     */
    public Response cerateQrcode(int wechatId, long sceneId, int expireSeconds, int action_name) throws TException {

        try {
            Query.QueryBuilder qu = new Query.QueryBuilder();
            qu.where("id", String.valueOf(wechatId));
            HrWxWechatRecord record = wechatDao.getRecord(qu.buildQuery());
            if (record == null) {
                return RespnoseUtil.USERACCOUNT_WECHAT_NOTEXISTS.toResponse();
            } else {
                String accessToken = record.getAccessToken();
                if (StringUtils.isNotNullOrEmpty(accessToken)) {
                    WeixinTicketBean bean = AccountMng.createTicket(accessToken, expireSeconds, QrcodeType.fromInt(action_name), sceneId, null);
                    if (bean != null) {
                        return RespnoseUtil.SUCCESS.toResponse(bean);
                    } else {
                        return RespnoseUtil.USERACCOUNT_WECHAT_GETQRCODE_FAILED.toResponse();
                    }
                } else {
                    return RespnoseUtil.USERACCOUNT_WECHAT_ACCESSTOKEN_NOTEXISTS.toResponse();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return RespnoseUtil.PROGRAM_EXCEPTION.setMessage(e.getMessage()).toResponse();
        } finally {
            //do nothing
        }
    }

    /**
     * 利用票据获取二维码数据
     */
    public Response getQrcode(String ticket) throws TException {
        if (StringUtils.isNullOrEmpty(ticket)) {
            return RespnoseUtil.USERACCOUNT_WECHAT_TICKET_NOTEXISTS.toResponse();
        } else {
            String qrcode = AccountMng.getQrcode(ticket);
            return RespnoseUtil.SUCCESS.toResponse(qrcode);
        }
    }

    /**
     * 获取扫码信息
     *
     * @param wechatId 公众号信息
     * @param sceneId  场景编号
     */
    public Response getScanResult(int wechatId, long sceneId) throws TException {
        String result = redisClient.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.WEIXIN_SCANRESULT.toString(), String.valueOf(wechatId), String.valueOf(sceneId));
        if (StringUtils.isNotNullOrEmpty(result)) {
            redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.WEIXIN_SCANRESULT.toString(), String.valueOf(wechatId), String.valueOf(sceneId));
            JSONObject json = JSON.parseObject(result);
            if (json.get("status") != null && (Integer) json.get("status") == 0) {
                return RespnoseUtil.SUCCESS.toResponse(json.get("data"));
            } else {
                return RespnoseUtil.USERACCOUNT_WECHAT_SCAN_ERROR.setMessage((String) json.get("message")).toResponse(json.get("data"));
            }
        } else {
            return RespnoseUtil.SUCCESS.toResponse(2);
        }
    }

    /**
     * 设置用户扫码信息
     *
     * @param wechatId 公众号编号
     * @param sceneId  场景编号
     * @param value    扫码结果
     */
    public Response setScanResult(int wechatId, long sceneId, String value) throws TException {
        redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.WEIXIN_SCANRESULT.toString(), String.valueOf(wechatId), String.valueOf(sceneId), value);
        return RespnoseUtil.SUCCESS.toResponse();
    }

    public UserUserDO ifExistUser(String mobile) {
        UserUserDO user = new UserUserDO();
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("mobile", mobile).and("source", String.valueOf(UserSource.RETRIEVE_PROFILE.getValue()));
        try {
            user = userdao.getData(qu.buildQuery(), UserUserDO.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        return user;
    }

    public int createRetrieveProfileUser(UserUserDO user) {
        logger.info("UseraccountsService createRetrieveProfileUser user:{}", user);
        int userId = 0;
        if (user.getMobile() == 0) {
            logger.info("UseraccountsService createRetrieveProfileUser mobile not exist");
            return 0;
        }
        // 如果不是简历搬家来源，按之前逻辑
        if(user.getSource() != UserSource.MV_HOUSE.getValue()){
            user.setSource((byte) UserSource.RETRIEVE_PROFILE.getValue());
        }

        try {
            if (user.getPassword() == null) {
                user.setPassword("");
            }
            UserUserRecord userUserRecord = userdao.addRecord(BeanUtils.structToDB(user, UserUserRecord.class));

            userId = userUserRecord.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return userId;
    }

    public boolean ifExistProfile(String countryCode, String mobile) {
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where(UserUser.USER_USER.MOBILE.getName(), mobile)
                .and(UserUser.USER_USER.COUNTRY_CODE.getName(), countryCode)
                .and(UserUser.USER_USER.SOURCE.getName(), String.valueOf(UserSource.RETRIEVE_PROFILE.getValue()));
        UserUserDO user = userdao.getData(qu.buildQuery());
        ProfileProfileRecord record = profileDao.getProfileByUserId(user.getId());
        if (record != null) {
            return true;
        }
        return false;
    }

    public Response getUserSearchPositionHistory(int userId) throws BIZException {
        String info = redisClient.get(Constant.APPID_ALPHADOG, KeyIdentifier.USER_POSITION_SEARCH.toString(), String.valueOf(userId));
        List<String> history = null;
        if(StringUtils.isNotNullOrEmpty(info)){
            history = (List)JSONObject.parse(info);
        }
        return ResponseUtils.success(history);
    }


    public Response deleteUserSearchPositionHistory(int userId) throws BIZException {
        redisClient.del(Constant.APPID_ALPHADOG, KeyIdentifier.USER_POSITION_SEARCH.toString(), String.valueOf(userId));
        return ResponseUtils.success("");
    }

}