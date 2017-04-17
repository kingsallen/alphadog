namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrWxRuleDO {

	1: optional i32 id,	//null
	2: optional i32 wechatId,	//null
	3: optional i32 cid,	//null
	4: optional string name,	//null
	5: optional string component,	//模块名称
	6: optional i32 displayorder,	//排序
	7: optional double status,	//规则状态，0禁用，1启用，2置顶
	8: optional i32 accessLevel,	//规则获取权限，0：所有，1：员工
	9: optional string keywords	//关键字

}
