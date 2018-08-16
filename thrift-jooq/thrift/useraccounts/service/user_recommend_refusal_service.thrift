namespace java com.moseeker.thrift.gen.useraccounts.service

include "../../dao/struct/userdb/user_recommend_refusal_struct.thrift"


service UserRecommendRefusalService {

    // C端用户拒绝某个公众号推荐职位
    void refuseRecommend(1: user_recommend_refusal_struct.UserRecommendRefusalDO userRecommendRefusal);

    // 获取最近一条拒绝推荐记录
    user_recommend_refusal_struct.UserRecommendRefusalDO getLastestRecommendRefusal(1: i32 userId,2: i32 wechatId);

}