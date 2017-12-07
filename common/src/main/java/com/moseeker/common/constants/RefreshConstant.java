package com.moseeker.common.constants;

public class RefreshConstant {
    public final static int APP_ID=0;

    public final static String EXCHANGE="chaos";

    //同步最佳东方职位参数的rabbitMQ配置
    public final static String PARAM_SEND_ROUTING_KEY="environ.request";
    public final static String PARAM_GET_ROUTING_KEY="environ.response";
    public final static String PARAM_GET_QUEUE="environ.response.queue";

    //最佳东方职位参数redis配置
    public final static String VERY_EAST_REDIS_PARAM_KEY="VERY_EAST_PARAM";

    //一览人才参数redis配置
    public final static String JOB1001_REDIS_PARAM_KEY="JOB1001_REFRESH_PARAM";
}
