# file: mcdUatService.thrift

include "../../useraccounts/struct/mcd_userType_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.useraccounts.service


service McdUatService {
    common_struct.Response getUserEmployeeInfoByUserType(1:mcd_userType_struct.McdUserTypeDO userinfo);
	mcd_userType_struct.McdUserTypeDO getUserEmployeeByuserId(1:i32 userId);
}
