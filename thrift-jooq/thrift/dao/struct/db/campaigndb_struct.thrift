namespace java com.moseeker.thrift.gen.dao.struct.campaigndb
namespace py thrift_gen.gen.dao.struct.campaigndb


struct CampaignBaiduUsersDO {

	1: optional i32 id,	//null
	2: optional i32 userId,	//null
	3: optional i32 uid,	//baidu的uid
	4: optional string createTime	//null

}


struct CampaignEdmCampaignDO {

	1: optional i32 id,	//null
	2: optional string name,	//活动名称
	3: optional string desc,	//活动描述
	4: optional string sendTime,	//null
	5: optional string createTime	//null

}


struct CampaignEdmUserrecommendedPositionsDO {

	1: optional i32 id,	//null
	2: optional i32 campaignId,	//null
	3: optional i32 userId,	//null
	4: optional i32 positionId,	//null
	5: optional double score,	//推荐分
	6: optional string createTime	//null

}


struct CampaignEdmUsersDO {

	1: optional i32 id,	//null
	2: optional i32 campaignId,	//null
	3: optional i32 userId,	//null
	4: optional string sendTime,	//null
	5: optional i8 sendStatus,	//0.  新建 1 发送成功, 2 发送失败. 3. 不符合发送条件(比如没有推荐职位)
	6: optional string createTime	//null

}


struct CampaignHeadImageDO {

	1: optional i32 id,	//null
	2: optional string imageUrl,	//图片地址
	3: optional string hrefUrl,	//超链接地址
	4: optional string createTime,	//创建时间
	5: optional string updateTime	//创建时间

}


struct CampaignRecommedCompanyDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//hr_company.id 公司id
	3: optional string companyName,	//公司名称
	4: optional i32 weight,	//权重值
	5: optional i8 disable,	//是否禁用(0: 启用，1: 禁用)
	6: optional string createTime,	//创建时间
	7: optional string updateTime	//修改时间

}


struct CampaignRecommedPositionDO {

	1: optional i32 id,	//null
	2: optional i32 positionId,	//job_position.id 职位id
	3: optional string positionName,	//职位名称
	4: optional i32 weight,	//权重值
	5: optional i8 disable,	//是否禁用(0: 启用，1: 禁用)
	6: optional string createTime,	//创建时间
	7: optional string updateTime	//修改时间

}
