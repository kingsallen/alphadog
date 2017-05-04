namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserCompanyVisitReqDO {

	1: optional i32 id,	//id
	2: optional i32 companyId,	//hr_company.id
	3: optional i32 userId,	//user_user.id
	4: optional i32 status,	//0: 取消申请参观 1：申请参观
	5: optional i32 source,	//操作来源 0: 未知 1：微信端 2：PC 端
	6: optional string createTime,	//关注时间
	7: optional string updateTime	//null

}
