namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileImportDO {

	1: optional i32 profileId,	//profile.id
	2: optional i8 source,	//来源, 0:无法识别 1:51Job 2:Liepin 3:zhilian 4:linkedin
	3: optional string lastUpdateTime,	//导入简历的最后修改时间
	4: optional string accountId,	//他系统的用户ID
	5: optional string resumeId,	//他系统的简历ID
	6: optional string userName,	//他系统的登陆用户名
	7: optional string createTime,	//创建时间
	8: optional string updateTime,	//更新时间
	9: optional string data	//导入数据

}
