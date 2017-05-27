namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileWorkexpDO {

	1: optional i32 id,	//主key
	2: optional i32 profileId,	//profile.id
	3: optional string startTime,	//起止时间-起 yyyy-mm-dd
	4: optional string endTime,	//起止时间-止 yyyy-mm-dd
	5: optional i8 endUntilNow,	//是否至今 0：否 1：是
	6: optional i8 salaryCode,	//薪资code
	7: optional i32 industryCode,	//行业字典编码
	8: optional string industryName,	//行业名称
	9: optional i32 companyId,	//公司ID, hr_company.id
	10: optional string departmentName,	//部门名称
	11: optional i32 positionCode,	//职能字典编码
	12: optional string positionName,	//职能字典名称
	13: optional string description,	//工作描述
	14: optional i8 type,	//工作类型 0:没选择 1:全职 2:兼职 3:实习
	15: optional i32 cityCode,	//工作地点（城市），字典编码
	16: optional string cityName,	//工作地点（城市）名称
	17: optional string reportTo,	//汇报对象
	18: optional i32 underlings,	//下属人数, 0:没有下属
	19: optional string reference,	//证明人
	20: optional string resignReason,	//离职原因
	21: optional string achievement,	//主要业绩
	22: optional string createTime,	//创建时间
	23: optional string updateTime,	//更新时间
	24: optional string job	//所处职位

}
