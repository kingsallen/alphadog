package com.moseeker.position.constants;

public class VeryEastConstant {
    public final static int APP_ID=0;

    public final static String EXCHANGE="chaos";

    //同步最佳东方职位参数的rabbitMQ配置
    public final static String PARAM_SEND_ROUTING_KEY="environ.request";
    public final static String PARAM_GET_ROUTING_KEY="environ.response";
    public final static String PARAM_GET_QUEUE="environ.response.queue";

    //最佳东方职位参数redis配置
    public final static String REDIS_PARAM_KEY="VERY_EAST_PARAM";
}
