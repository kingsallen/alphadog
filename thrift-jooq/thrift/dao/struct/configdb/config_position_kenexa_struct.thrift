namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigPositionKenexaDO {

	1: optional i32 id,	//null
	2: optional i32 sourceId,	//ATS来源ID
	3: optional string positionColumn,	//职位表字段名,  _ 暂未设置
	4: optional string kenexaJobId,	//kenexa对应的JOB字段ID
	5: optional i32 disable,	//是否有效，0：有效，1：无效
	6: optional string createTime,	//创建时间
	7: optional string updateTime,	//修改时间
	8: optional i32 appTplId	//模板Id

}
