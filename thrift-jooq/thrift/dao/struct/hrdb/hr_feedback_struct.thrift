namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrFeedbackDO {

	1: optional i32 id,	//ID
	2: optional i32 hraccountId,	//hr_account.id 账号编号
	3: optional string name,	//姓名
	4: optional string email,	//邮箱
	5: optional string images,	//反馈图片
	6: optional string content,	//反馈内容
	7: optional string createTime,	//null
	8: optional string updateTime	//null

}
