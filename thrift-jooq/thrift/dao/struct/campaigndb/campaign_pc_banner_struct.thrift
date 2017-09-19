namespace java com.moseeker.thrift.gen.dao.struct.campaigndb
namespace py thrift_gen.gen.dao.struct.campaigndb


struct CampaignPcBannerDO {

	1: optional i32 id,	//主键
	2: optional string href,	//图片链接
	3: optional string imgUrl,	//图片地址
	4: optional i8 disable,	//状态 0：可用，1：不可用
	5: optional string createTime,	//创建时间
	6: optional string updateTime	//更新时间

}
