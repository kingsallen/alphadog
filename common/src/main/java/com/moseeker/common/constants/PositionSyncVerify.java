package com.moseeker.common.constants;

public class PositionSyncVerify {
    public static final String MOBILE_VERIFY_EXCHANGE="chaos_mobile";

    public static final String MOBILE_VERIFY_ROUTING_KEY="request";

    public static final String MOBILE_VERIFY_RESPONSE_ROUTING_KEY="{}_response";

    public static final String MOBILE_VERIFY_QUEUE="mobile_verify_queue";

    public static final String MOBILE_VERIFY_SUCCESS="success";

    public static final String MOBILE_VERIFY_SUCCESS_MSG="验证成功";


    /**
     * 该交换机用户猎聘api对接时从das传来的数据处理
     */

    public static final String POSITION_OPERATE_LIEPIN_EXCHANGE = "position_operate_liepin_exchange";

    public static final String POSITION_OPERATE_ROUTEKEY_EDIT = "position_operate_routekey_edit";

    public static final String POSITION_OPERATE_ROUTEKEY_RESYNC = "position_operate_routekey_resync";

    public static final String POSITION_OPERATE_ROUTEKEY_DOWNSHELF = "position_operate_routekey_downshelf";

    public static final String POSITION_OPERATE_ROUTEKEY_DEL = "position_operate_routekey_del";

    public static final String POSITION_QUEUE_RESYNC = "position_que_resync";

    public static final String POSITION_QUEUE_DOWNSHELF = "position_que_downshelf";

    public static final String POSITION_QUEUE_DELETE = "position_que_delete";

    public static final String POSITION_QUEUE_EDIT = "position_que_edit";
//    --------------------------------------------------------------------------

}
