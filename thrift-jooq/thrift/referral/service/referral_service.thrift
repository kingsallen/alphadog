namespace java com.moseeker.thrift.gen.referral.service

include "../../common/struct/common_struct.thrift"
include "../struct/referral_struct.thrift"

/*
*  员工服务接口
*/
service ReferralService {
    // 获取公司员工认证配置信息
    referral_struct.RedPackets getRedPackets(1: i32 userId, 2: i32 companyId, 3 : i32 pageNum, 4: i32 pageSize) throws (1: common_struct.BIZException e);
    // 获取奖金列表
    referral_struct.BonusList getBonus(1: i32 userId, 2 : i32 companyId, 3 : i32 pageNum, 4: i32 pageSize) throws (1: common_struct.BIZException e);
    //修改红包活动
    void updateActivity(1: referral_struct.ActivityDTO activityDTO) throws (1: common_struct.BIZException e);
    //获取用户的推荐简历列表
    list<referral_struct.ReferralProfileTab> getReferralProfileList(1: i32 userId, 2: i32 companyId) throws (1: common_struct.BIZException e);
}
