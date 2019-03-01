namespace java com.moseeker.thrift.gen.dao.struct.candidatedb
namespace py thrift_gen.gen.dao.struct.candidatedb


struct CandidatePositionShareRecordDO {

	1: optional i32 id,	//null
	2: optional double wechatId,	//所属公众号
	3: optional double positionId,	//position.id 分享职位ID
	4: optional double recomId,	//userdb.user_wx_user.id 分享用户微信ID。现在已经废弃，请参考recom_user_id字段
	5: optional i32 recomUserId,	//userdb.user_user.id 转发者的C端账号编号
	6: optional string viewerId,	//userdb.user_wx_viewer.id 浏览用户ID
	7: optional double viewerIp,	//浏览用户IP
	8: optional double source,	//来源0：企业后台1：聚合平台
	9: optional string createTime,	//创建时间
	10: optional string updateTime,	//修改时间
	11: optional double presenteeId,	//被动求职者,浏览者的微信ID，userdb.user_wx_user.id。现在已经废弃，请参考presentee_user_id
	12: optional double clickFrom,	//来自, 0:未知, 朋友圈(timeline ) 1, 微信群(groupmessage) 2, 个人消息(singlemessage) 3
	13: optional i32 presenteeUserId,	//userdb.user_user.id 浏览者的C端账号编号
    14: optional i32 shareChainId
}
