namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrWxModuleDO {

	1: optional i8 id,	//null
	2: optional string name,	//标识
	3: optional string title,	//名称
	4: optional string version,	//版本
	5: optional string ability,	//功能描述
	6: optional string description,	//介绍
	7: optional string author,	//作者
	8: optional string url,	//发布页面
	9: optional i8 settings,	//扩展设置项
	10: optional string subscribes,	//订阅的消息类型
	11: optional string handles,	//能够直接处理的消息类型
	12: optional i8 isrulefields,	//是否有规则嵌入项
	13: optional i8 home,	//是否微站首页嵌入
	14: optional i8 issystem,	//是否是系统模块
	15: optional string options,	//扩展功能导航项
	16: optional i8 profile,	//是否个人中心嵌入
	17: optional string siteMenus,	//微站功能扩展导航项
	18: optional string platformMenus	//微站功能扩展导航项

}
