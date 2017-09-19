# file: useraccounts.thrift

#include "../struct/useraccounts_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.searchengine.service

service SearchengineServices {
    common_struct.Response query(1: string keywords,2:string cities,3:string industries,4:string occupations,5:string scale,6:string employment_type,
    7:string candidate_source,8:string experience,9:string degree,10:string salary,11:string company_name,12:i32 page_from,13: i32 page_size,14:string child_company_name,15:string department,16:bool order_by_priority,17:string custom);
    common_struct.Response updateposition(1: string position,2:i32 id);
    common_struct.Response companyQuery(1: string keyWords,2:string citys,3:string industry,4:string scale,5:i32 page,6:i32 pageSize);
    common_struct.Response positionQuery(1: string keyWords,2:string citys,3:string industry,4:string salaryCode,5:i32 page,6:i32 pageSize,
    7:string startTime,8:string endTime,9:i32 companyId,10:i32 teamId,11:i32 motherCompanyId,12:i32 order);
    common_struct.Response queryAwardRanking(1: list<i32> companyIds, 2: string timespan, 3: i32 pageSize, 4: i32 pageNum,5:string keyword,6:i32 filter);
    common_struct.Response queryAwardRankingInWx(1: list<i32> companyIds, 2: string timespan, 3: i32 employeeId);
    common_struct.Response updateEmployeeAwards(1: list<i32> employeeId);
    common_struct.Response deleteEmployeeDO(1: list<i32> employeeId);
}
