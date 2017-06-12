namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrHtml5StatisticsDO {

	1: optional i32 id,	//primary key
	2: optional i32 topicId,	//wx_topic.id
	3: optional i32 companyId,	//company.id
	4: optional i32 viewNumPv,	//浏览次数
	5: optional i32 recomViewNumPv,	//推荐浏览次数
	6: optional string createDate	//创建日期

}
