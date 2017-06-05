namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrHtml5UniqueStatisticsDO {

	1: optional i32 id,	//primary key
	2: optional i32 topicId,	//wx_topic.id
	3: optional i32 companyId,	//company.id
	4: optional i32 viewNumUv,	//浏览人数
	5: optional i32 recomViewNumUv,	//推荐浏览人数
	6: optional string createDate,	//创建日期
	7: optional i32 infoType	//0: 日数据，1：周数据，2：月数据

}
