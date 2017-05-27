namespace java com.moseeker.thrift.gen.dao.struct.logdb
namespace py thrift_gen.gen.dao.struct.logdb


struct LogEmailSendrecordDO {

	1: optional i32 id,	//null
	2: optional i32 tplId,	//邮件模板ID
	3: optional i8 sys,	//来自系统，0:未知 1:platform 2:qx 3:hr 4:官网 9:script
	4: optional string email,	//邮箱地址
	5: optional string content,	//邮件变量部分内容以json方式
	6: optional string createTime	//null

}
