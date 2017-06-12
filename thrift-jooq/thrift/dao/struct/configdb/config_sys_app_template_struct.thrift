namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigSysAppTemplateDO {

	1: optional i32 id,	//null
	2: optional string enname,	//申请字段英文名称
	3: optional string chname,	//申请字段中文名称
	4: optional double priority,	//排序
	5: optional double display,	//是否显示0：是，1：否
	6: optional double needed,	//是否必填0：是，1：否
	7: optional double type,	//模板类型
	8: optional string remark,	//备注
	9: optional string entitle,	//字段英文名称
	10: optional i32 parentId	//字段父子关系

}
