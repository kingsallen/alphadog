package com.moseeker.useraccounts.exception;

/**
 * Created by jack on 03/07/2017.
 */
public enum ExceptionCategory {

    USEREMPLOYEES_DATE_EMPTY(42006, "员工ID为空！"),
    USEREMPLOYEES_WRONG(42007, "员工ID设置错误！"),
    USEREMPLOYEES_EMPTY(42008, "员工数据不存在！"),
    IMPORT_DATA_WRONG(42009, "导入员工数据有误！"),
    IMPORT_DATA_EMPTY(42012, "导入员工数据为空！"),
    PERMISSION_DENIED(42010, "员工ID和公司ID不匹配！"),
    COMPANYID_ENPTY(42011, "公司ID不能为空！"),
    ADD_IMPORTERMONITOR_FAILED(42012, "添加公司认证配置文件失败"),
    ADD_IMPORTERMONITOR_PARAMETER(42013, "添加公司认证配置参数设置有误:{MESSAGE}"),
    ORDER_ERROR(42014, "排序条件设置错误"),
    EMAIL_REPETITION(42015, "员工邮箱信息重复"),
    CUSTOM_FIELD_REPETITION(42016, "员工姓名和自定义字段信息重复"),
    REFERRAL_CONF_DATA_EMPTY(42017, "公司內推政策保存数据为空！"),
    REFERRAL_POLICY_UPDATE_FIALED(42018, "员工想要了解内推政策点击次数更新失败！"),
    ADD_REDERRALPOLICY_PARAMETER(42013, "{MESSAGE}"),
    COMPANY_DATA_EMPTY(90010, "公司信息不存在！"),
    PROGRAM_PARAM_NOTEXIST(90015, "参数不正确！"),
    PROGRAM_EXCEPTION(99999, "发生异常，请稍候再试！");


    private ExceptionCategory(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
