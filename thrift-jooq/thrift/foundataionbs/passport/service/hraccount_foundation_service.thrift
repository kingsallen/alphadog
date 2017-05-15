# file: function_service.thrift

include "../../../common/struct/common_struct.thrift"
namespace java com.moseeker.thrift.gen.foundation.passport.service

service UserBS {
	//是否拥有profile
	bool isHaveProfile(1:i32 userId);
	
}
