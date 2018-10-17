namespace java com.moseeker.thrift.gen.dao.struct.malldb

struct MallOrderDO {

	1: optional i32 id,	//主key
	2: optional i32 order_id,	//订单编号
	3: optional i32 employee_id,	//员工id
	4: optional i32 goods_id,	//商品id
	5: optional i32 company_id,	//商品id
	6: optional i32 credit,	//商品积分
	7: optional string title,	//商品名称
	8: optional string pic_url,	//商品图片
	9: optional i32 count,	//商品兑换数量
	10: optional i8 state,	//订单状态
	11: optional string assign_time,	//订单发放或拒绝时间
	12: optional string createTime,	//创建时间或兑换时间
	13: optional string updateTime	//更新时间
}
