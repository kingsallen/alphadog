namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserFavPositionDO {

	1: optional i32 sysuserId,	//用户ID
	2: optional i32 positionId,	//职位ID
	3: optional double favorite,	//0:收藏, 1:取消收藏, 2:感兴趣
	4: optional string createTime,	//null
	5: optional string updateTime,	//null
	6: optional string mobile,	//感兴趣的手机号
	7: optional i32 id,	//ID
	8: optional i32 wxuserId,	//浏览者user_wx_user.id。已经废弃，浏览者微信编号由浏览者C端账号编号替代，请参考sysuser_id
	9: optional i32 recomId,	//推荐者user_wx_user.id。已经废弃，推荐者微信编号由推荐者C端账号编号替代，请参考recom_user_id
	10: optional i32 recomUserId	//userdb.user_user.id 推荐者C端账号编号

}
