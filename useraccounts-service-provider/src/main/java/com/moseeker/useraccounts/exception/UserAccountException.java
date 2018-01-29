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
