namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigSysThemeDO {

	1: optional i32 id,	//null
	2: optional string backgroundColor,	//背景色
	3: optional string titleColor,	//标题
	4: optional string buttonColor,	//按钮
	5: optional string otherColor,	//other
	6: optional double free,	//是否免费 0：免费 1：收费，只能在大岂后台操作收费主题
	7: optional double prority,	//排序优先级
	8: optional double disable,	//是否禁用 0：可用1：不可用
	9: optional string createTime,	//null
	10: optional string updateTime,	//null
	11: optional i32 companyId	//hr_company.id, 用于隔离企业自定义的颜色

}
