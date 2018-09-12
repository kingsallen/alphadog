namespace java com.moseeker.thrift.gen.dao.struct.talentpooldb
namespace py thrift_gen.gen.dao.struct.talentpooldb

struct TalentPoolProfileMoveDO {

	1: optional i32 id,	//主key
	2: optional i32 hrId,	//hrid
	3: optional i8 channel,	//1=51job,2=猎聘,3=智联,4=linkedin 目前仅显示前程无忧,智联招聘2个渠道
	4: optional string startDate,	//操作简历搬家的投递开始时间
	5: optional string endDate,	//操作简历搬家的投递结束时间
	6: optional string createTime,	//
	7: optional string updateTime	//
}
