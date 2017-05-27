namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserCompanyFollowDO {

	1: optional i32 id,	//id
	2: optional i32 companyId,	//hr_company.id
	3: optional i32 userId,	//user_user.id
	4: optional i32 status,	//0: 关注 1：取消关注
	5: optional i32 source,	//关注来源 0: 未知 1：微信端 2：PC 端
	6: optional string createTime,	//关注时间
	7: optional string updateTime,	//null
	8: optional string unfollowTime	//取消关注时间

}
