include "../struct/application_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.application.service

service JobApplicationServices {

    common_struct.Response postApplication(1: application_struct.JobApplication application);

    common_struct.Response postJobResumeOther(1: application_struct.JobResumeOther jobResumeOther);
}
