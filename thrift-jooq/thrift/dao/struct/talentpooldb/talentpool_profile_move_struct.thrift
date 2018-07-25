namespace java com.moseeker.thrift.gen.dao.struct.talentpooldb
namespace py thrift_gen.gen.dao.struct.talentpooldb

struct TalentPoolProfileMoveDO {

	1: optional i32 id,	//主key
	2: optional i32 hrId,	//hrid
	3: optional i32 companyId,	//公司id
	4: optional i8 channel,	//1=51job,2=猎聘,3=智联,4=linkedin 目前仅显示前程无忧,智联招聘2个渠道
	5: optional string companyName,//简历搬家爬取的公司名称
	6: optional i8 crawlType,	//简历类型 1.主动投递简历  2.已下载简历
	7: optional string startDate,	//操作简历搬家的投递开始时间
	8: optional string endDate,	//操作简历搬家的投递结束时间
	9: optional i32 crawlNum,	//本次简历搬家获取简历数
	10: optional i8 status,	//0.获取中 1.已完成  2.获取失败
	11: optional string statusDisplay,	//状态中文展示
	12: optional i32 currentEmailNum,	//当前邮件数量
	13: optional i32 totalEmailNum,	//邮件数量
	14: optional string createTime,	//
	15: optional string updateTime	//
}
