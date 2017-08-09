namespace java com.moseeker.thrift.gen.dao.struct.campaigndb
namespace py thrift_gen.gen.dao.struct.campaigndb


struct CampaignPcRecommendPositionDO {

	1: optional i32 id,	//null
	2: optional i32 positionId,	//job_position.id
	3: optional i32 disable,	//是否可用，0：可用，1：不可用
	4: optional string createTime,	//null
	5: optional string updateTime	//null

}
