namespace java com.moseeker.thrift.gen.dao.struct.malldb

struct MallGoodsInfoDO {

	1: optional i32 id,	//主key
	2: optional i32 company_id,	//主key
	3: optional string title,	//商品名称
	4: optional string pic_url,	//商品图片
	5: optional i32 credit,	//商品积分
	6: optional i32 stock,	//商品库存
	7: optional i32 exchange_num,	//商品已兑换数量
	8: optional i32 exchange_order,	//商品已兑换次数
	9: optional string detail,	//商品详情
	10: optional i8 state,	//商品上架状态
	11: optional string rule,	//商品领取规则
	12: optional string create_time,	//创建时间
	13: optional string update_time	//更新时间

}
