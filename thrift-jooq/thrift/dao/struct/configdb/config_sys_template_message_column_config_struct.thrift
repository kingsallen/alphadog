namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigSysTemplateMessageColumnConfigDO {

	1: optional i32 id,	//主key
	2: optional string templateId,	//模板ID
	3: optional string enname,	//字段英文名
	4: optional string chname,	//字段中文名
	5: optional i32 display,	//是否显示
	6: optional i32 priority,	//排序
	7: optional string defMsg	//default value

}
