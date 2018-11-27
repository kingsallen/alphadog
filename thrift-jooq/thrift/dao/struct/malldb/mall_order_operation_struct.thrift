namespace java com.moseeker.thrift.gen.dao.struct.malldb

struct MallOrderOperationDO {

	1: optional i32 id,	//主key
	2: optional i32 hr_id,	//hrid
	3: optional i32 order_id,	//商品兑换id
	4: optional i8 operation_state,	//操作状态
	5: optional i32 point_record_id,	//员工积分记录表id
	6: optional string createTime,	//创建时间
	7: optional string updateTime	//更新时间

}
