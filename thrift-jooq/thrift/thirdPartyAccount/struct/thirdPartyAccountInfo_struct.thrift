namespace java com.moseeker.thrift.gen.thirdpart.struct

include "../../dao/struct/thirdpartydb/thirdparty_account_city_struct.thrift"
include "../../dao/struct/thirdpartydb/thirdparty_account_company_address_struct.thrift"
include "../../dao/struct/thirdpartydb/thirdparty_account_company_struct.thrift"
include "../../dao/struct/thirdpartydb/thirdparty_account_department_struct.thrift"


struct ThirdPartyAccountInfo{
    1:i32 status,
    2:string message,
    3:optional list<thirdparty_account_city_struct.ThirdpartyAccountCityDO> city,
    4:optional list<thirdparty_account_company_address_struct.ThirdpartyAccountCompanyAddressDO> address,
    5:optional list<thirdparty_account_company_struct.ThirdpartyAccountCompanyDO> company,
    6:optional list<thirdparty_account_department_struct.ThirdpartyAccountDepartmentDO> department
}
