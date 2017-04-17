namespace java com.moseeker.thrift.gen.dao.struct.campaigndb
namespace py thrift_gen.gen.dao.struct.campaigndb


struct CampaignEdmUsersDO {

	1: optional i32 id,	//null
	2: optional i32 campaignId,	//null
	3: optional i32 userId,	//null
	4: optional string sendTime,	//null
	5: optional i8 sendStatus,	//0.  新建 1 发送成功, 2 发送失败. 3. 不符合发送条件(比如没有推荐职位)
	6: optional string createTime	//null

}
