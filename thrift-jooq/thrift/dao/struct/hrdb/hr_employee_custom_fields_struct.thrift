namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrEmployeeCustomFieldsDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//sys_company.id, 部门ID
	3: optional string fname,	//自定义字段名
	4: optional string fvalues,	//自定义字段可选值
	5: optional i32 forder,	//排序优先级，0:不显示, 正整数由小到大排序
	6: optional i8 disable,	//是否停用 0:不停用(有效)， 1:停用(无效)
	7: optional i32 mandatory,	//是否必填 0:不是必填, 1:必填
	8: optional i32 status	//0: 正常 1: 被删除

}
