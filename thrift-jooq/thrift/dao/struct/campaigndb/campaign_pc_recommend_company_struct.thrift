namespace java com.moseeker.thrift.gen.dao.struct.campaigndb
namespace py thrift_gen.gen.dao.struct.campaigndb


struct CampaignPcRecommendCompanyDO {

	1: optional i32 id,	//null
	2: optional string moduleName,	//模块名
	3: optional string moduleDescription,	//模块描述
	4: optional string companyIds,	//公司的id格式:id1,id2,id3...
	5: optional i32 disable,	//是否可用，0 ：可用， 1 ： 不可用
	6: optional string createTime,	//null
	7: optional string updateTime	//null

}
