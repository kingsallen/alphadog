namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigSysH5StyleTplDO {

	1: optional i32 id,	//null
	2: optional string name,	//模板名称
	3: optional string logo,	//模板LOGO的相对路径
	4: optional string hrTpl,	//模板风格名称，只允许英文，hr预览用
	5: optional string wxTpl,	//模板风格名称，只允许英文，微信端用
	6: optional double priority,	//排序优先级
	7: optional double disable,	//是否有效  0：有效 1：无效
	8: optional string createTime,	//null
	9: optional string updateTime	//null

}
