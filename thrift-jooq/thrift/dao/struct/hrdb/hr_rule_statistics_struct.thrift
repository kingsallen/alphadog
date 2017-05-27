namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrRuleStatisticsDO {

	1: optional i32 id,	//primary key
	2: optional i32 wxruleId,	//wx_rule.id
	3: optional string menuName,	//菜单名称
	4: optional i32 type,	//0: wx_rule, 1: menu
	5: optional i32 companyId,	//company.id
	6: optional i32 viewNumPv,	//浏览人数
	7: optional string createDate	//创建日期

}
