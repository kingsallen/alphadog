# file: company_service.thrift


include "../../common/struct/common_struct.thrift"
include "../struct/company_struct.thrift"
include "../../dao/struct/hrdb_struct.thrift"

namespace java com.moseeker.thrift.gen.company.service

service CompanyServices {
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getAllCompanies(1:common_struct.CommonQuery query);
    common_struct.Response add(1:company_struct.Hrcompany company);
    common_struct.Response getWechat(1:i64 companyId, 2:i64 wechatId);
    common_struct.Response getPcBanner(1:i32 page, 2:i32 pageSize);
}

service HrTeamServices {
    list<hrdb_struct.HrTeamDO> getHrTeams(1:common_struct.CommonQuery query);
}
