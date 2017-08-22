include "../struct/application_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.application.service

/*
   申请服务
*/
service JobApplicationServices {

    // 添加申请
    common_struct.Response postApplication(1: application_struct.JobApplication application);
    //添加申请
    common_struct.Response postApplicationIfNotApply(1: application_struct.JobApplication application);

    // 更新申请
    common_struct.Response putApplication(1: application_struct.JobApplication application);

    // 删除申请
    common_struct.Response deleteApplication(1: i64 applicationId);

    // 添加申请副本信息
    common_struct.Response postJobResumeOther(1: application_struct.JobResumeOther jobResumeOther);

    // 判断当前用户是否申请了该职位
    common_struct.Response getApplicationByUserIdAndPositionId(1: i64 userId, 2: i64 positionId, 3: i64 companyId);

    // 清除一个公司一个人申请次数限制的redis key 给sysplat专用, 其他系统禁止使用
    common_struct.Response deleteRedisKeyApplicationCheckCount(1: i64 userId, 2: i64 companyId);

    // 校验超出申请次数限制, 每月每家公司一个人只能申请3次
    common_struct.Response validateUserApplicationCheckCountAtCompany(1: i64 userId, 2: i64 companyId);

    // 通过application获取accout_id 和company_id
    application_struct.ApplicationResponse  getAccountIdAndCompanyId(1:i64 jobApplicationId);

    // 根据指定渠道 channel=5（支付宝），指定时间段（"2017-05-10 14:57:14"）， 返回第三方渠道同步的申请状态。
    common_struct.Response getApplicationListForThirdParty(1:i32 channel, 2:string start_time, 3:string end_time);


}
