# file: useraccounts.thrift

include "../struct/useraccounts_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.searchengine.service

service SearchengineServices {

    common_struct.Response query(1: string keywords,2:string cities,3:string industries,4:string occupations,5:string scale,
    6:string employment_type,7:string candidate_source,8:string experience,9:string degree,10:string salary,11:string company_name,
    12:i32 page_from,13: i32 page_size,14:string child_company_name,15:string department,16:bool order_by_priority,
    17:string custom,18:string hb_config_id)throws (1: common_struct.BIZException e);

    common_struct.Response updateposition(1: string position,2:i32 id)throws (1: common_struct.BIZException e);

    common_struct.Response companyQuery(1: string keyWords,2:string citys,3:string industry,4:string scale,5:i32 page,6:i32 pageSize)throws (1: common_struct.BIZException e);

    common_struct.Response positionQuery(1: string keyWords,2:string citys,3:string industry,4:string salaryCode,5:i32 page,6:i32 pageSize,
                                         7:string startTime,8:string endTime,9:i32 companyId,10:i32 teamId,11:i32 motherCompanyId,12:i32 order,
                                         13:i32 moduleId,14:string candidateSource)throws (1: common_struct.BIZException e);

    //目前废弃
    common_struct.Response queryAwardRanking(1: list<i32> companyIds, 2: string timespan, 3: i32 pageSize, 4: i32 pageNum,5:string keyword,6:i32 filter)throws (1: common_struct.BIZException e);

    common_struct.Response queryAwardRankingInWx(1: list<i32> companyIds, 2: string timespan, 3: i32 employeeId)throws (1: common_struct.BIZException e);
    //员工列表
    common_struct.Response fetchEmployees(1: list<i32> companyIds, 2: string keywords, 3: i32 filter, 4: string order, 5: string asc, 6: string emailValidate, 7: i32 pageSize, 8: i32 pageNumber,9:i32 balanceType, 10: string timeSpan, 11: string selectIds)throws (1: common_struct.BIZException e);
    
    common_struct.Response listLeaderBoard(1: list<i32> companyIds, 2: string timespan, 3: i32 employeeId, 4: i32 pageNum, 5: i32 pageSize)throws (1: common_struct.BIZException e);
    
    i32 countLeaderBoard(1: list<i32> companyIds, 2: string timespan)throws (1: common_struct.BIZException e);

    common_struct.Response updateEmployeeAwards(1: list<i32> employeeId)throws (1: common_struct.BIZException e);

    common_struct.Response deleteEmployeeDO(1: list<i32> employeeId)throws (1: common_struct.BIZException e);

    common_struct.Response searchPositionSuggest(1: map<string,string> params)throws (1: common_struct.BIZException e);

    common_struct.Response searchProfileSuggest(1: map<string,string> params)throws (1: common_struct.BIZException e);

    common_struct.Response userQuery(1: map<string,string> params)throws (1: common_struct.BIZException e);

    common_struct.Response userAggInfo(1: map<string,string> params)throws (1: common_struct.BIZException e);

    common_struct.Response queryPositionIndex(1: string keywords,2:string cities,3:string industries,4:string occupations,
    5:string scale,6:string employment_type,7:string candidate_source,8:string experience,9:string degree,
    10:string salary,11:string company_name,12:i32 page_from,13: i32 page_size,
    14:string child_company_name,15:string department,16:bool order_by_priority,
    17:string custom,18:string hb_config_id,19:string is_referral)throws (1: common_struct.BIZException e);

    common_struct.Response queryPositionMini(1: map<string,string> params) throws (1: common_struct.BIZException e);

    list<i32> queryCompanyTagUserIdList(1: map<string,string> params) throws (1: common_struct.BIZException e);

    i32 queryCompanyTagUserIdListCount(1: map<string,string> params) throws (1: common_struct.BIZException e);

    i32 talentSearchNum(1: map<string,string> params) throws (1: common_struct.BIZException e);

    common_struct.Response queryProfileFilterUserIdList(1:list<map<string,string>> filterMapList, 2:i32 page_number, 3:i32 page_size) throws (1: common_struct.BIZException e);

    common_struct.Response userQueryById(1: list<i32> userIdlist) throws (1: common_struct.BIZException e);

    //获取人才库的id
    list<i32> getTalentUserIdList(1: map<string,string> params);

    //获取曾任职位
    common_struct.Response searchpastPosition(1: map<string,string> params) throws (1: common_struct.BIZException e);

    //获取曾任职位
    common_struct.Response searchpastCompany(1: map<string,string> params) throws (1: common_struct.BIZException e);

    //mobot查询职位
    common_struct.Response mobotSearchPosition(1: map<string,string> params) throws (1: common_struct.BIZException e);

}
