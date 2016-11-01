include "../struct/thirdpart_struct.thrift"
include "../../common/struct/common_struct.thrift"
service OrmThirdPartService{
	common_struct.Response addThirdPartAccount(1:thirdpart_struct.ThirdPartAccount account);
}
