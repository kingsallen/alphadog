# file: profile.thrift

namespace java com.moseeker.thrift.gen.position.struct

include "../../dao/struct/jobdb/job_position_struct.thrift"
include "../../dao/struct/hrdb/hr_third_party_account_struct.thrift"
include "../../dao/struct/hrdb/hr_third_party_position_struct.thrift"
include "../../dao/struct/hrdb/hr_company_struct.thrift"
include "../../dao/struct/userdb/user_hr_account_struct.thrift"

struct ThirdPartyPositionInfoForm{
    1:i32 page,
    2:i32 pageSize,
    3:string hrName,                      //hr的名字
    4:string thirdAccountName,            //第三方帐号的名字
    5:string companyAbbr,                 //公司简称
    6:i32 channel,                        //渠道类型
    7:i32 status,                         //操作状态:1同步成功,2同步中,3同步失败,4程序同步失败,5刷新成功,6刷新中,7刷新失败,8程序刷新失败
    8:i32 positionId,                     //职位ID
    9:i32 thirdPositionId,                //第三方职位ID
    10:string thirdPositionNumber         //第三方职位编号
}

struct ThirdPartyPositionInfo{
    1:job_position_struct.JobPositionDO position,
    2:hr_third_party_account_struct.HrThirdPartyAccountDO thirdAccount,
    3:hr_third_party_position_struct.HrThirdPartyPositionDO thirdPosition,
    4:user_hr_account_struct.UserHrAccountDO hr,
    5:hr_company_struct.HrCompanyDO company
}

struct ThirdPartyPositionResult{
    1:i32 page,
    2:i32 pageSize,
    3:i32 total,
    4:list<ThirdPartyPositionInfo> data
}