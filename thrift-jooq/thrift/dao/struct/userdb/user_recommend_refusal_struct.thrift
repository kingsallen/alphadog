namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserRecommendRefusalDO {

	1: optional i32 id,	//null
	2: optional i32 userId,	//用户id
	3: optional i32 wechatId,	//公众号ID，表示用户在哪个公众号拒绝推送
	4: optional string refuseTime,	//拒绝推荐的时间
	5: optional string refuseTimeout	//拒绝推荐过期时间,默认refuse_time+6个月

}
