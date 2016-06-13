include "../struct/application_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.application.service

/*
   申请服务
*/
service JobApplicationServices {

    // 添加申请
    common_struct.Response postApplication(1: application_struct.JobApplication application, 2: application_struct.JobResumeBasic jobResumeBasic);

    // 添加申请副本信息
    common_struct.Response postJobResumeOther(1: application_struct.JobResumeOther jobResumeOther);

    // 判断当前用户是否申请了该职位
    common_struct.Response getApplicationByUserIdAndPositionId(1: i64 userId, 2: i64 positionId, 3: i64 companyId);

}
