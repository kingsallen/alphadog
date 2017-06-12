namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrImporterMonitorDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//公司ID，hr_company.id
	3: optional i32 hraccountId,	//hr_account.id 账号编号
	4: optional double type,	//要导入的表：0：user_employee 1: job_position 2:hr_company
	5: optional string file,	//导入文件的绝对路径
	6: optional double status,	//0：待处理 1：处理失败 2：处理成功
	7: optional string message,	//操作提示信息
	8: optional string createTime,	//null
	9: optional string updateTime,	//null
	10: optional string name,	//导入的文件名
	11: optional double sys	//1:mp, 2:hr

}
