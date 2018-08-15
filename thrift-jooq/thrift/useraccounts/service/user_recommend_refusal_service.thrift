namespace java com.moseeker.thrift.gen.useraccounts.service

include "../../dao/struct/userdb/user_recommend_refusal_struct.thrift"


service UserRecommendRefusalService {

    // C端用户拒绝某个公众号推荐职位
    void refuseRecommend(1: user_recommend_refusal_struct.UserRecommendRefusalDO userRecommendRefusal);

}