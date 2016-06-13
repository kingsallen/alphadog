# file: company_service.thrift


include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.company.service

service CompanyServices {
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getAllCompanies(1:common_struct.CommonQuery query);
}
