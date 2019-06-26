package com.moseeker.useraccounts.exception;

import com.moseeker.common.exception.CommonException;

/**
 * Created by YYF
 *
 * Date: 2017/7/25
 *
 * Project_name :alphadog
 */
public class UserAccountException extends CommonException {

    public static final UserAccountException USEREMPLOYEES_DATE_EMPTY = new UserAccountException(42006, "员工ID为空！");
    public static final UserAccountException USEREMPLOYEES_WRONG = new UserAccountException(42007, "员工ID设置错误！");
    public static final UserAccountException USEREMPLOYEES_EMPTY = new UserAccountException(42008, "员工数据不存在！");
    public static final UserAccountException IMPORT_DATA_WRONG = new UserAccountException(42009, "导入员工数据有误！");
    public static final UserAccountException IMPORT_DATA_EMPTY = new UserAccountException(42005, "导入员工数据为空！");
    public static final UserAccountException PERMISSION_DENIED = new UserAccountException(42010, "员工ID和公司ID不匹配！");
    public static final UserAccountException COMPANYID_ENPTY = new UserAccountException(42011, "公司ID不能为空！");
    public static final UserAccountException ADD_IMPORTERMONITOR_FAILED = new UserAccountException(42012, "添加公司认证配置文件失败");
    public static final UserAccountException ADD_IMPORTERMONITOR_PARAMETER = new UserAccountException(42013, "添加公司认证配置参数设置有误");
    public static final UserAccountException ORDER_ERROR = new UserAccountException(42014, "排序条件设置错误");
    public static final UserAccountException EMAIL_REPETITION = new UserAccountException(42015, "员工邮箱信息重复");
    public static final UserAccountException CUSTOM_FIELD_REPETITION = new UserAccountException(42016, "员工姓名和自定义字段信息重复");
    public static final UserAccountException COMPANY_DATA_EMPTY = new UserAccountException(90010, "公司信息不存在！");
    public static final UserAccountException SEARCH_ES_ERROR = new UserAccountException(90011, "查询ES数据发生异常!");
    public static final UserAccountException ILLEGAL_MOBILE = new UserAccountException(42017, "手机号码格式不合法!");
    public static final UserAccountException HR_UPDATEMOBILE_FAILED = new UserAccountException(42020, "手机号码修改失败!");
    public static final UserAccountException THIRD_PARTY_ACCOUNT_NOTEXIST = new UserAccountException(42021, "第三方账号信息错误!");
    public static final UserAccountException NOT_ALLOWED_ADD_SUBACCOUNT = new UserAccountException(42022, "不允许继续添加子账号!");
    public static final UserAccountException HRACCOUNT_EXIST = new UserAccountException(42023, "HR账号已经存在!");
    public static final UserAccountException AWARD_POSITION_ALREADY_DELETED = new UserAccountException(42024, "职位已经下架!");
    public static final UserAccountException AWARD_POINTS_CONF_LOST = new UserAccountException(42025, "积分配置确实!");
    public static final UserAccountException AWARD_EMPLOYEE_ELEGAL = new UserAccountException(42026, "员工信息不正确!");
    public static final UserAccountException HRACCOUNT_NOT_EXIST = new UserAccountException(42027, "HR账号不存在!");
    public static final UserAccountException EMPLOYEE_ALREADY_UP_VOTE = new UserAccountException(42028, "已经点过赞!");
    public static final UserAccountException EMPLOYEE_NOT_UP_VOTE = new UserAccountException(42029, "未点过赞!");
    public static final UserAccountException EMPLOYEE_LEADERBOARDER_NOT_EXISTS = new UserAccountException(42030, "榜单类型错误!");
    public static final UserAccountException PROFILE_PARSE_TEXT_FAILED = new UserAccountException(42031, "简历解析失败!");
    public static final UserAccountException ERMPLOYEE_REFERRAL_TYPE_NOT_EXIST = new UserAccountException(42032, "职位信息不存在!");
    public static final UserAccountException ERMPLOYEE_REFERRAL_LOG_NOT_EXIST = new UserAccountException(42033, "推荐记录不存在!");
    public static final UserAccountException ERMPLOYEE_REFERRAL_USER_NOT_EXIST = new UserAccountException(42034, "用户信息不存在!");
    public static final UserAccountException ERMPLOYEE_REFERRAL_USER_NOT_WRITE = new UserAccountException(42035, "姓名和推荐候选人不一致!");
    public static final UserAccountException ERMPLOYEE_REFERRAL_ALREADY_CLAIMED = new UserAccountException(42036, "已经被认领!");
    public static final UserAccountException ERMPLOYEE_REFERRAL_CLAIMED_SINGLE = new UserAccountException(42036, "一次只能认领一个用户!");
    public static final UserAccountException ERMPLOYEE_REFERRAL_EMPLOYEE_CLAIM_FAILED = new UserAccountException(42037, "推荐人无法自己认领!");
    public static final UserAccountException ERMPLOYEE_REFERRAL_EMPLOYEE_REPEAT_CLAIM = new UserAccountException(42038, "重复认领!");
    public static final UserAccountException ERMPLOYEE_REFERRAL_BONUS_NOT_EXIST = new UserAccountException(42039, "内推奖金记录不存在!");
    public static final UserAccountException ERMPLOYEE_REFERRAL_BONUS__REPEAT_CLAIM = new UserAccountException(42040, "重复认领!");
    public static final UserAccountException ERMPLOYEE_EXTINFO_PARAM_ERROR = new UserAccountException(42041, "补填信息不符合要求!");
    public static final UserAccountException ACTIVITY_RUNNING = new UserAccountException(42042, "活动正在进行中!");
    public static final UserAccountException ACTIVITY_UNCHECKED_OR_IN_RUNNING = new UserAccountException(42043, "红包活动正在进行中!");
    public static final UserAccountException ACTIVITY_POSITIONS_ERROR = new UserAccountException(42044, "职位数据无效，请重新选择职位!");
    public static final UserAccountException ACTIVITY_AMOUNT_ERROR = new UserAccountException(42045, "红包列表的红包金额超支!");
    public static final UserAccountException ACTIVITY_NOT_EXISTS = new UserAccountException(42046, "红包活动不存在!");
    public static final UserAccountException ACTIVITY_STATUS_ERROR = new UserAccountException(42047, "红包状态不正确!");
    public static final UserAccountException ACTIVITY_UNCHECKED_OR_FINISHED = new UserAccountException(42048, "红包活动未审核或已经结束!");
    public static final UserAccountException ACTIVITY_POSITION_HB_STATUS_UPDATE_FAILURE = new UserAccountException(42049, "红包选择的职位的红包是否参与活动的状态修改失败，可能是由于已经参加其他的红包活动导致!");
    public static final UserAccountException ACTIVITY_POSITION_ALREADY_IN_ACTIVITY = new UserAccountException(42050, "所选职位已经参与红包活动!");
    public static final UserAccountException ACTIVITY_CONFLICT = new UserAccountException(42051, "红包活动时间重叠，请重新选择时间!");
    public static final UserAccountException REFERRAL_SEEK_RECOMMEND_FAIL = new UserAccountException(42052, "候选人联系内推数据保存失败！");
    public static final UserAccountException REFERRAL_SEEK_RECOMMEND_NULL = new UserAccountException(42053, "候选人联系内推数据不存在！");
    public static final UserAccountException INVALID_SMS_CODE = new UserAccountException(10011, "无效验证码！");
    public static final UserAccountException REFERRAL_CHAIN_NONEXISTS = new UserAccountException(42054, "推荐链路不存在！");
    public static final UserAccountException REFERRAL_SHARE_CHAIN_NONEXISTS = new UserAccountException(42055, "无转发记录！");
    public static final UserAccountException REFERRAL_PROGRESS_ERROR = new UserAccountException(42056, "推进进度异常！");
    public static final UserAccountException REFERRAL_CONNECTION_NONEXISTS = new UserAccountException(42057, "人脉连连看链路不存在！");
    public static final UserAccountException REFERRAL_RECOM_EVALUATION_EXISTS = new UserAccountException(42058, "推荐评价已存在！");
    public static final UserAccountException EMPLOYEE_COMPANY_UNMATCH = new UserAccountException(42059, "员工与公司不匹配！");
    public static final UserAccountException RADAR_STATUS_CLOSE = new UserAccountException(42060, "雷达开关关闭！");
    public static final UserAccountException NEO4J_STATUS_ERROR = new UserAccountException(43001, "Neo4j查询出错！");
    public static final UserAccountException EMPLOYEE_ALREADY_VERIFIED = new UserAccountException(42061, "已经是认证员工！");
    public static final UserAccountException EMPLOYEE_VERIFICATION_NOT_SUPPORT = new UserAccountException(42062, "暂时不支持员工认证！");
    public static final UserAccountException EMPLOYEE_VERIFICATION_INVALID= new UserAccountException(42063, "认证信息失效！");
    public static final UserAccountException IMPORT_DATA_CUSTOM_ERROR = new UserAccountException(42064, "导入员工数据自定义信息有误！");
    public static final UserAccountException EMPLOYEE_CUSTOM_FIELD_ERROR = new UserAccountException(42065, "自定义信息有误！");
    public static final UserAccountException EMPLOYEE_BATCH_UPDAT_OVER_LIMIT = new UserAccountException(42066, "超过允许批量修改的上限！");
    public static final UserAccountException EMPLOYEE_VERIFICATION_ACTIVATION_EXPIRED = new UserAccountException(42067, "邮件认证的认证信息过期！");

    private final int code;

    protected UserAccountException(int code, String message) {
        super(code, message);
        this.code = code;
    }

    public UserAccountException setMess(String message) {
        return new UserAccountException(code, message);
    }

    public int getCode() {
        return code;
    }
}
