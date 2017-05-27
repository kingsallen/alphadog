namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserSettingsDO {

	1: optional i32 id,	//主key
	2: optional i32 userId,	//user_user.id, 用户id
	3: optional string bannerUrl,	//profile banner 的qiniu 相对url
	4: optional i8 privacyPolicy	//0:公开, 10:仅对hr公开, 20:完全保密

}
