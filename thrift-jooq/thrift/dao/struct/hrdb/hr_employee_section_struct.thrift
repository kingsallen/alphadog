namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrEmployeeSectionDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//sys_company.id, 部门ID
	3: optional string name,	//部门名称
	4: optional i32 priority,	//排序优先级
	5: optional i8 status	//1:有效, 0:无效

}
