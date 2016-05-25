include "../struct/application_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.application.service

service ApplicationServices {
    common_struct.Response postResource(1: application_struct.Application application);
}
