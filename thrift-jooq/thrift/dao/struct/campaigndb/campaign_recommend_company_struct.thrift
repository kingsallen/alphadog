namespace java com.moseeker.thrift.gen.dao.struct.campaigndb
namespace py thrift_gen.gen.dao.struct.campaigndb


struct CampaignRecommendCompanyDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//hr_company.id 公司id
	3: optional i32 weight,	//权重值
	4: optional i8 disable,	//是否禁用(0: 启用，1: 禁用)
	5: optional string createTime,	//创建时间
	6: optional string updateTime	//修改时间

}
