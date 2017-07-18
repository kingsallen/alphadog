# file: profile.thrift

namespace java com.moseeker.thrift.gen.position.struct

include "../../dao/struct/jobdb/job_position_struct.thrift"
include "../../dao/struct/hrdb/hr_third_party_account_struct.thrift"
include "../../dao/struct/hrdb/hr_third_party_position_struct.thrift"
include "../../dao/struct/hrdb/hr_company_struct.thrift"
include "../../dao/struct/userdb/user_hr_account_struct.thrift"

struct ThirdPartyPositionInfoForm{
    1:optional i32 page,
    2:optional i32 pageSize,
    3:optional string hrName,                      //hr的名字
    4:optional string thirdAccountName,            //第三方帐号的名字
    5:optional string companyAbbr,                 //公司简称
    6:optional i32 channel,                        //渠道类型
    7:optional i32 status,                         //操作状态:1同步成功,2同步中,3同步失败,4程序同步失败,5刷新成功,6刷新中,7刷新失败,8程序刷新失败
    8:optional i32 positionId,                     //职位ID
    9:optional i32 thirdPositionId,                //第三方职位ID
    10:optional string thirdPositionNumber         //第三方职位编号
}

struct ThirdPartyPositionInfo{
    1:optional job_position_struct.JobPositionDO position,
    2:optional hr_third_party_account_struct.HrThirdPartyAccountDO thirdAccount,
    3:optional hr_third_party_position_struct.HrThirdPartyPositionDO thirdPosition,
    4:optional user_hr_account_struct.UserHrAccountDO hr,
    5:optional hr_company_struct.HrCompanyDO company
}

struct ThirdPartyPositionResult{
    1:optional i32 page,
    2:optional i32 pageSize,
    3:optional i32 total,
    4:optional list<ThirdPartyPositionInfo> data
}