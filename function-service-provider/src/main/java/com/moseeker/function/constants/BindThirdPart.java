package com.moseeker.function.constants;

public class BindThirdPart {
    public final static int APP_ID=0;
    public final static String KEY_IDENTIFIER="BIND_THRID_PART";

    public final static String BIND_CODE_MSG="异地登陆需要验证码";
    public final static String BIND_UP_ERR_MSG="用户名或密码错误";
    public final static String BIND_EXP_MSG="绑定异常，请重新绑定";
    public final static String BIND_ERR_MSG="绑定错误，请重新绑定";
    public final static String BIND_TIMEOUT_MSG="绑定超时，请稍后重试";

    public final static String BIND_EXCHANGE_NAME="chaos";

    //绑定第一次推送第三方账号队列信息(即推送需要绑定的账号和密码)
    public final static String BIND_SEND_ROUTING_KEY="bind.request";
    public final static String BIND_GET_ROUTING_KEY="bind.response";
    public final static String BIND_GET_QUEUE_NAME="bind.response.web";

    //绑定第二次推送第三方账号队列信息（即确认是否需要发送手机验证码）
    public final static String BIND_CONFIRM_SEND_ROUTING_KEY="mobile_confirm.request";
    public final static String BIND_CONFIRM_GET_ROUTING_KEY="mobile_confirm.response";
    public final static String BIND_CONFIRM_GET_QUEUE_NAME="mobile_confirm.response.web";

    //绑定第三次推送第三方账号队列信息（即推送手机验证码）
    public final static String BIND_CODE_SEND_ROUTING_KEY="mobile_code.request";
    public final static String BIND_CODE_GET_ROUTING_KEY="mobile_code.response";
    public final static String BIND_CODE_GET_QUEUE_NAME="mobile_code.response.web";

    //同步职位队列信息
    public final static String SYNC_POSITION_SEND_ROUTING_KEY="publish.request";
    public final static String SYNC_POSITION_GET_ROUTING_KEY="publish.response";
    public final static String SYNC_POSITION_GET_QUEUE_NAME="publish.response.web";

    public final static String CHAOS_ACCOUNTID="accountId";

}
