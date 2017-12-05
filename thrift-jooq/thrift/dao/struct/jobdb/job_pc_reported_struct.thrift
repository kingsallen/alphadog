namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobPcReportedDO {

	1: optional i32 id,	//自增主键
	2: optional i32 userId,	//用户id
	3: optional i32 positionId,	//职位id
	4: optional i32 type,	//举报原因：0：公司信息不真实， 1：职位实际已停止招聘， 2：其他
	5: optional string description,	//举报详情描述信息
	6: optional string createTime,	//举报时间
	7: optional string updateTime	//更新时间

}
