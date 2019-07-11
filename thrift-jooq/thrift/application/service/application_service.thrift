include "../struct/application_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.application.service

/*
   申请服务
*/
service JobApplicationServices {


    bool healthCheck();

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
    common_struct.Response validateUserApplicationCheckCountAtCompany(1: i64 userId, 2: i64 companyId, 3: i64 poisiotnId);

    // 校验社招和校招类型的申请是否可用
    common_struct.Response validateUserApplicationTypeCheckCountAtCompany(1: i64 userId, 2: i64 companyId);

    // 通过application获取accout_id 和company_id
    application_struct.ApplicationResponse  getAccountIdAndCompanyId(1:i64 jobApplicationId);

    // 根据指定渠道 channel=5（支付宝），指定时间段（"2017-05-10 14:57:14"）， 返回第三方渠道同步的申请状态。
    common_struct.Response getApplicationListForThirdParty(1:i32 channel, 2:string start_time, 3:string end_time);

    // 根据指定渠道 channel=5（支付宝），指定时间段（"2017-05-10 14:57:14"）， 返回第三方渠道同步的申请状态。
    common_struct.Response getHrIsViewApplication(1:i32 user_id);

    //查看申请
    void viewApplications(1:i32 hrId, 2:list<i32> applicationIdList) throws (1: common_struct.BIZException e);
    
    //员工代理投递
   list<i32> employeeProxyApply(1:i32 referenceId, 2: i32 applierId, 3: list<i32> positionIdList) throws (1: common_struct.BIZException e);
   //查找申请记录
   list<application_struct.ApplicationRecordsForm> getApplications(1: i32 userId, 2: i32 companyId) throws (1: common_struct.BIZException e);
   //通过申请id发送邮件
   i32 appSendEmail(1: i32 appId)throws (1: common_struct.BIZException e);
   //校验公司下面的appid是否存在
   i32 validateAppid(1: i32 appId, 2: i32 companyId)throws (1: common_struct.BIZException e);
}
