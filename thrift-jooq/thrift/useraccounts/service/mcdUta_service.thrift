# file: mcdUatService.thrift

include "../../common/struct/common_struct.thrift"
namespace java com.moseeker.thrift.gen.useraccounts.service


service McdUatService {

        common_struct.Response getUserEmployeeByuserId(1:i32 userId);
}
