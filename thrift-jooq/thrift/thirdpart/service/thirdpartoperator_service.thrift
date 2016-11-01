include "../struct/thirdpart_struct.thrift"
include "../../common/struct/common_struct.thrift"
service BindThirdPartService{
	common_struct.Response sendParamForChaos(1:thirdpart_struct.ThirdPartParamer param);
}
