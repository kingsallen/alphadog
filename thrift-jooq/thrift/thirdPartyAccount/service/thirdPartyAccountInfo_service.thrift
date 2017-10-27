namespace java com.moseeker.thrift.gen.thirdpart.service

include "../struct/thirdPartyAccountInfo_struct.thrift"
include "../struct/thirdPartyAccountInfoParam_struct.thrift"


service ThirdPartyAccountInfoService{
    thirdPartyAccountInfo_struct.ThirdPartyAccountInfo  getAllInfo(1:thirdPartyAccountInfoParam_struct.ThirdPartyAccountInfoParam param);
    list<thirdPartyAccountInfo_struct.ThirdPartyAccountInfoCity>  getCity(1:thirdPartyAccountInfoParam_struct.ThirdPartyAccountInfoParam param);
    list<thirdPartyAccountInfo_struct.ThirdPartyAccountInfoAddress>  getCompanyAddress(1:thirdPartyAccountInfoParam_struct.ThirdPartyAccountInfoParam param);
    list<thirdPartyAccountInfo_struct.ThirdPartyAccountInfoCompany>  getCompany(1:thirdPartyAccountInfoParam_struct.ThirdPartyAccountInfoParam param);
    list<thirdPartyAccountInfo_struct.ThirdPartyAccountInfoDepartment>  getDepartment(1:thirdPartyAccountInfoParam_struct.ThirdPartyAccountInfoParam param);
}