namespace java com.moseeker.thrift.gen.thirdpart.service

include "../struct/thirdPartyAccountInfo_struct.thrift"
include "../struct/thirdPartyAccountInfoParam_struct.thrift"
include "../struct/thirdpartyCommonInfo_struct.thrift"
include "../../common/struct/common_struct.thrift"


service ThirdPartyAccountInfoService{
    thirdPartyAccountInfo_struct.ThirdPartyAccountInfo  getAllInfo(1:thirdPartyAccountInfoParam_struct.ThirdPartyAccountInfoParam param) throws (1: common_struct.BIZException e);

    i32 postThirdPartyCommonInfo(1:thirdpartyCommonInfo_struct.ThirdPartyCommonInfo param) throws (1: common_struct.BIZException e);

    list<thirdpartyCommonInfo_struct.ThirdPartyCommonInfo> getThirdPartyCommonInfo(1:thirdpartyCommonInfo_struct.ThirdPartyCommonInfo param) throws (1: common_struct.BIZException e);
}