namespace java com.moseeker.thrift.gen.thirdpart.service

include "../struct/thirdPartyAccountInfo_struct.thrift"
include "../struct/thirdPartyAccountInfoParam_struct.thrift"
include "../../dao/struct/thirdpartydb/thirdparty_account_city_struct.thrift"
include "../../dao/struct/thirdpartydb/thirdparty_account_company_address_struct.thrift"
include "../../dao/struct/thirdpartydb/thirdparty_account_company_struct.thrift"
include "../../dao/struct/thirdpartydb/thirdparty_account_department_struct.thrift"

service ThirdPartyAccountInfoService{
    thirdPartyAccountInfo_struct.ThirdPartyAccountInfo  getAllInfo(1:thirdPartyAccountInfoParam_struct.ThirdPartyAccountInfoParam param);
    list<thirdparty_account_city_struct.ThirdpartyAccountCityDO>  getCity(1:thirdPartyAccountInfoParam_struct.ThirdPartyAccountInfoParam param);
    list<thirdparty_account_company_address_struct.ThirdpartyAccountCompanyAddressDO>  getCompanyAddress(1:thirdPartyAccountInfoParam_struct.ThirdPartyAccountInfoParam param);
    list<thirdparty_account_company_struct.ThirdpartyAccountCompanyDO>  getCompany(1:thirdPartyAccountInfoParam_struct.ThirdPartyAccountInfoParam param);
    list<thirdparty_account_department_struct.ThirdpartyAccountDepartmentDO>  getDepartment(1:thirdPartyAccountInfoParam_struct.ThirdPartyAccountInfoParam param);
}