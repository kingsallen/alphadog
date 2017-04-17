namespace java com.moseeker.thrift.gen.dao.struct.candidatedb
namespace py thrift_gen.gen.dao.struct.candidatedb


struct CandidateRemarkDO {

	1: optional i32 id,	//null
	2: optional i32 hraccountId,	//做候选人标记的账号编号 hr_account.id
	3: optional i32 wxuserId,	//user_wx_user.id 被推荐者 微信 ID。已经废弃，微信用户信息由C端账号信息代替
	4: optional i32 gender,	//0：未知，1：男，2：女
	5: optional string age,	//年龄
	6: optional string mobile,	//联系方式
	7: optional string email,	//邮箱
	8: optional string currentCompany,	//现处公司
	9: optional string currentPosition,	//职务
	10: optional string education,	//毕业院校
	11: optional string degree,	//学历
	12: optional string graduateAt,	//毕业时间
	13: optional i8 isStar,	//0: 星标 1: 没有星标
	14: optional string remark,	//备注
	15: optional string createTime,	//创建时间
	16: optional string updateTime,	//修改时间
	17: optional i32 status,	//0: 新数据 1: 正常 2:被忽略
	18: optional string name,	//候选人姓名
	19: optional i32 userId	//userdb.user_user.id 候选人关联的C端账号

}
