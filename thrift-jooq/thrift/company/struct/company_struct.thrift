# file: company.struct

namespace java com.moseeker.thrift.gen.company.struct
include "../../dao/struct/hrdb_struct.thrift"


typedef string Timestamp


struct Hrcompany { 
   1: optional i32 id,
   2: optional i32 type,
   3: optional string name,
   4: optional string introduction,
   5: optional string scale,
   6: optional string address,
   7: optional i32 property,
   8: optional string industry,
   9: optional string homepage,
   10: optional string logo,
   11: optional string abbreviation,
   12: optional string impression,
   13: optional string banner,
   14: optional i32 parent_id,
   15: optional i32 hraccount_id,
   16: optional i32 disable,
   17: optional Timestamp create_time,
   18: optional Timestamp update_time,
   19: optional i32 source,
   20: optional string feature
}

struct CompanyForVerifyEmployee{
    1: optional i32 id,                 //公司编号
    2: optional string name,            //公司名称
    3: optional string abbreviation,    //公司简称
    4: optional string signature        //公司简称
}

// 获取公司部门与职能信息(员工认证补填字段显示)
struct CompanyOptions{
    1:list <hrdb_struct.HrEmployeePositionDO> hrEmployeePosition,
    2:list <hrdb_struct.HrEmployeeSectionDO> hrEmployeeSection
}

// 公司认证配置
struct CompanyCertConf{
    1:list<hrdb_struct.HrEmployeeCertConfDO> hrEmployeeCertConf,
    2:HrImporterMonitorVO hrImporterMonitor
}


struct HrImporterMonitorVO{
	1: optional i32 id,	//null
	2: optional i32 companyId,	//公司ID，hr_company.id
	3: optional i32 hraccountId,	//hr_account.id 账号编号
	4: optional double type,	//要导入的表：0：user_employee 1: job_position 2:hr_company
	5: optional string file,	//导入文件的绝对路径
	6: optional double status,	//0：待处理 1：处理失败 2：处理成功
	7: optional string message,	//操作提示信息
	8: optional string createTime,	//null
	9: optional string updateTime,	//null
	10: optional string fileName,	//导入的文件名
	11: optional double sys	//1:mp, 2:hr

}


struct HrEmployeeCustomFieldsVO{
    1: optional i32 id,	//null
    2: optional string fname,
    3: optional list<string> fvalues
}