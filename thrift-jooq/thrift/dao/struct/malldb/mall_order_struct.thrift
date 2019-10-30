namespace java com.moseeker.thrift.gen.dao.struct.malldb

struct MallOrderDO {

	1: optional i32 id,	//主key
	2: optional string order_id,	//订单编号
	3: optional i32 employee_id,	//员工id
	4: optional i32 goods_id,	//商品id
	5: optional i32 company_id,	//商品id
	6: optional string name,	//员工姓名
	7: optional i32 credit,	//商品积分
	8: optional string title,	//商品名称
	9: optional string pic_url,	//商品图片
	10: optional i32 count,	//商品兑换数量
	11: optional i8 state,	//订单状态
	12: optional string assign_time,	//订单发放或拒绝时间
	13: optional string createTime,	//创建时间或兑换时间
	14: optional string updateTime,	//更新时间
	15: optional i32 mallId //邮寄地址id mall_mail_address.id
}
