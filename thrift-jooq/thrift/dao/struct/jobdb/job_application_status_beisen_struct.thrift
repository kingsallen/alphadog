namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobApplicationStatusBeisenDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//company.id, 部门ID
	3: optional double applierMobile,	//关注吉利微信公众账号的申请人的手机号
	4: optional string applierName,	//申请人真实姓名
	5: optional string jobnumber,	//对应beisen投递状态中JobName中的code, 并非对应hr_position中jobnumber
	6: optional string jobtitle,	//对应beisen投递状态中Jobname中的value，对应hr_position中title
	7: optional i32 phasecode,	//对应beisen投递状态中PhaseCode中的code
	8: optional string phasename,	//对应beisen投递状态中PhaseName中的value
	9: optional i32 statuscode,	//对应beisen投递状态中StatusCode中的code
	10: optional string statusname,	//对应beisen投递状态中StatusName中的value
	11: optional string createTime,	//null
	12: optional string updateTime	//null

}
