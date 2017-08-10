namespace java com.moseeker.thrift.gen.dao.struct.logdb
namespace py thrift_gen.gen.dao.struct.logdb


struct LogDeadLetterDO {

	1: optional i32 id,	//null
	2: optional i32 appid,	//appid
	3: optional string queueName,	//队列名称
	4: optional string exchangeName,	//交换机名称
	5: optional string routingKey,	//路由键
	6: optional string msg,	//消息体
	7: optional string errorLog,	//失败日志
	8: optional string createTime,	//创建时间
	9: optional string updateTime	//更新时间

}
