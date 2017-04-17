namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobPositionShareTplConfDO {

	1: optional i32 id,	//PK
	2: optional double type,	//模板分类 1：雇主分享模板 2：员工分享模板
	3: optional string name,	//模板名称
	4: optional string title,	//分享标题
	5: optional string description,	//分享简介
	6: optional double disable,	//是否禁用0：可用， 1：禁用
	7: optional string remark,	//备注
	8: optional double priority	//优先级

}
