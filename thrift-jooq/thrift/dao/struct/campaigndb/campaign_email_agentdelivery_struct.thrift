namespace java com.moseeker.thrift.gen.dao.struct.campaigndb
namespace py thrift_gen.gen.dao.struct.campaigndb


struct CampaignEmailAgentdeliveryDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//hr_company.id
	3: optional i32 positionId,	//job_position.id
	4: optional i32 employeeId,	//hr_employee.id
	5: optional string friendname,	//推荐朋友姓名
	6: optional string email,	//推荐朋友的邮
	7: optional i32 status,	//该条记录的状态
	8: optional string fname,	//附件原始名称
	9: optional string uname,	//附件存储名称
	10: optional string code,	//发送邮件时携带的code
	11: optional string createTime,	//记录创建时间
	12: optional string updateTime,	//更新时间
	13: optional string description	//上传文件描述

}
