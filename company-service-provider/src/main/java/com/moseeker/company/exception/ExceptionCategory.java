package com.moseeker.company.exception;

/**
 * 已经废弃，改用ProfileException
 * @see CompanyException
 * Created by jack on 03/07/2017.
 */
@Deprecated
public enum ExceptionCategory {

    COMPANY_NOT_BELONG_GROUPCOMPANY(32010, "并非是集团公司！"),
    COMPANY_NAME_REPEAT(33001, "不允许和拥有超级帐号的公司的公司名称重名！"),
    COMPANY_SCALE_ELLEGAL(33002, "公司规模不合法!"),
    COMPANY_PROPERTIY_ELLEGAL(33003, "公司属性不合法!"),
    ADD_IMPORTERMONITOR_PARAMETER(33004, "添加公司认证配置参数设置有误:{MESSAGE}"),
    ADD_IMPORTERMONITOR_FAILED(33005, "添加公司认证配置文件失败"),
    IMPORTERMONITOR_EMPTY(33006, "公司认证模板配置文件为空"),
    COMPANY_ID_EMPTY(33007, "公司ID不能为空"),
    HREMPLOYEECERTCONF_EMPTY(33008, "员工认证配置为空");

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
