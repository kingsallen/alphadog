namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileProjectexpDO {

	1: optional i32 id,	//主key
	2: optional i32 profileId,	//profile.id
	3: optional string startTime,	//起止时间-起 yyyy-mm-dd
	4: optional string endTime,	//起止时间-止 yyyy-mm-dd
	5: optional i8 endUntilNow,	//是否至今 0：否 1：是
	6: optional string name,	//项目名称
	7: optional string companyName,	//公司名称
	8: optional i8 isIt,	//是否IT项目, 0:没填写, 1:是, 2:否
	9: optional string devTool,	//开发工具
	10: optional string hardware,	//硬件环境
	11: optional string software,	//软件环境
	12: optional string url,	//项目网址
	13: optional string description,	//项目描述
	14: optional string role,	//项目角色
	15: optional string responsibility,	//项目职责
	16: optional string achievement,	//项目业绩
	17: optional string member,	//项目成员
	18: optional string createTime,	//创建时间
	19: optional string updateTime	//更新时间

}
