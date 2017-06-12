namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrHbPositionBindingDO {

	1: optional i32 id,	//null
	2: optional i32 hbConfigId,	//hr_hb_config.id
	3: optional i32 positionId,	//hr_position.id
	4: optional i8 triggerWay,	//领取条件：1=职位被转发 2=职位被申请
	5: optional double totalAmount,	//总金额
	6: optional i32 totalNum,	//红包总数
	7: optional string createTime,	//创建时间
	8: optional string updateTime	//更新时间

}
