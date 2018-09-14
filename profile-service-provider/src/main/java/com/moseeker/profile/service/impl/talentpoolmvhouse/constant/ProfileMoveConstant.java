package com.moseeker.profile.service.impl.talentpoolmvhouse.constant;

/**
 * 简历搬家常量
 * @author cjm
 * @date 2018-07-19 17:59
 **/
public class ProfileMoveConstant {
    /**
     * 简历搬家rabbitmq交换机
     */
    public final static String PROFILE_MOVE_EXCHANGE_NAME="chaos";
    /**
     * 简历搬家rabbitmq队列
     */
    public final static String PROFILE_MOVE_QUEUE="mvhouse_queue";
    /**
     * 简历搬家状态rabbitmq队列
     */
    public final static String PROFILE_MOVE_STATUS_QUEUE="mvhouse_status_queue";
    /**
     * 简历搬家用户登录队列路由key
     */
    public final static String PROFILE_MOVE_ROUTING_KEY_REQUEST="mvhouse.request";
    /**
     * 简历搬家用户登录队列路由key
     */
    public final static String PROFILE_MOVE_ROUTING_KEY_RESPONSE="mvhouse.response";
}
