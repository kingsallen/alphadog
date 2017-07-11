# file: company_service.thrift


include "../../common/struct/common_struct.thrift"
include "../struct/company_struct.thrift"
include "../../dao/struct/hrdb_struct.thrift"
include "../../employee/struct/employee_struct.thrift"

namespace java com.moseeker.thrift.gen.company.service

service CompanyServices {
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getAllCompanies(1:common_struct.CommonQuery query);
    common_struct.Response add(1:company_struct.Hrcompany company);
    common_struct.Response getWechat(1:i64 companyId, 2:i64 wechatId);
    // 更新公司员工认证配置
    bool updateEmployeeBindConf(1:i32 companyId,2:i32 authMode,3:string emailSuffix,4:string custom, 5:string customHint, 6:string questions,7:string filePath,8:string fileName,9:i32 type,10:i32 hraccountId) throws (1: common_struct.BIZException e);
    // 获取公司员工认证配置
    hrdb_struct.HrEmployeeCertConfDO getHrEmployeeCertConf(1:i32 companyId) throws (1: common_struct.BIZException e)
    // 获取公司积分配置信息
    list<employee_struct.RewardConfig> getCompanyRewardConf(1: i32 companyId) throws (1: common_struct.BIZException e);
    // 更新公司积分配置信息
    common_struct.Response updateCompanyRewardConf(1:i32 companyId,2:list<employee_struct.RewardConfig> rewardConfigs) throws (1: common_struct.BIZException e)
    list<company_struct.CompanyForVerifyEmployee> getGroupCompanies(1: i32 companyId) throws (1: common_struct.BIZException e)
    bool isGroupCompanies(1: i32 companyId) throws (1: common_struct.BIZException e)
    // 获取公司部门与职能信息(员工认证补填字段显示)
    company_struct.CompanyOptions getCompanyOptions(1:i32 companyId) throws (1: common_struct.BIZException e)
    // 添加公司员工认证模板数据
    common_struct.Response addImporterMonitor(1:i32 comanyId, 2:i32 hraccountId, 3:i32 type, 4:string file, 5:i32 status, 6:string message, 7:string fileName) throws (1: common_struct.BIZException e)
    // 查找公司认证模板数据（取最新一条数据）
    hrdb_struct.HrImporterMonitorDO getImporterMonitor(1:i32 comanyId, 2:i32 hraccountId, 3:i32 type) throws (1: common_struct.BIZException e)
    // 公司员工认证开关
    common_struct.Response bindingSwitch(1:i32 companyId, 2:i32 disable) throws (1: common_struct.BIZException e)

}

service HrTeamServices {
    list<hrdb_struct.HrTeamDO> getHrTeams(1:map<string,string> query);
}
