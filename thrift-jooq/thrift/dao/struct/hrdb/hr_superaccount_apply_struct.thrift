namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrSuperaccountApplyDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//hr_company.id
	3: optional string licence,	//营业执照
	4: optional string createTime,	//创建时间
	5: optional string updateTime,	//修改时间
	6: optional i32 operate,	//config_sys_administrator.id
	7: optional i32 status,	//申请状态 0表示已经通过，1表示未处理，2表示未通过
	8: optional string message,	//审核留言
	9: optional string childCompanyId,	//合并的其他公司的编号：[1,2,3]
	10: optional string migrateTime,	//迁移时间
	11: optional i32 accountLimit	//子账号数量限制

}
