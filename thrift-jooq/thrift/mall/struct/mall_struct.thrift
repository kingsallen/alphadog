# file: mall.thrift
namespace java com.moseeker.thrift.gen.mall.struct

struct OrderSearchForm {
    1:  optional i32 page_number,
    2:  optional i32 page_size,
    3:  optional i8 state,
    4:  optional string keyword,
    5:  optional i32 company_id,
    6:  optional i32 hr_id
}

struct GoodSearchForm {
    1:  optional i32 page_number,
    2:  optional i32 page_size,
    3:  optional i32 company_id,
    4:  optional i8 state, // 0 未上架 1 上架 9 全部
    5:  optional i32 hr_id
}

struct OrderForm {
    1:  optional i32 employee_id,
    2:  optional i32 company_id,
    3:  optional i32 count,
    4:  optional i32 goods_id,
    5:  optional string addressee,
    6:  optional string mobile,
    7:  optional i32 province,
    8:  optional i32 city,
    9:  optional i32 region,
    10: optional string address,
    11: optional i32 userId,
    12: optional i32 deliverType
}

struct MallSwitchForm {
    1:  optional i32 company_id,
    2:  optional i32 hr_id,
    3:  optional i8 state
}

struct BaseMallForm {
    1:  optional i32 employee_id,
    2:  optional i32 company_id,
    3:  optional i32 hr_id
}

struct MallRuleForm {
    1:  optional i32 company_id,
    2:  optional i32 hr_id,
    3:  optional string rule,
    4:  optional i8 state
}

struct MallGoodsInfoForm {
	1: optional i32 id,	//主key
	2: optional i32 company_id,	//主key
	3: optional i32 hr_id,
	4: optional string title,	//商品名称
	5: optional string pic_url,	//商品图片
	6: optional i32 credit,	//商品积分
	7: optional i32 stock,	//商品库存
	8: optional i32 exchange_num,	//商品已兑换数量
	9: optional i32 exchange_order,	//商品已兑换次数
	10: optional string detail,	//商品详情
	11: optional i8 state,	//商品上架状态
	12: optional string rule,	//商品领取规则
	13: optional string createTime,	//创建时间
	14: optional string updateTime	//更新时间
}

struct MallGoodsStateForm {
    1:  optional i32 company_id,
    2:  optional i32 hr_id,
    3:  optional list<i32> ids,
    4:  optional i8 state
}

struct MallGoodsStockForm {
    1:  optional i32 company_id,
    2:  optional i32 hr_id,
    3:  optional i32 good_id,
    4:  optional i32 stock
}

struct MallGoodsIdForm {
    1:  optional i32 company_id,
    2:  optional i32 hr_id,
    3:  optional i32 good_id
}

struct MallGoodsOrderForm {
    1:  optional i32 company_id,
    2:  optional i32 employee_id,
    3:  optional i32 good_id,
    4:  optional i32 count
}

struct MallGoodsOrderUpdateForm {
    1:  optional i32 company_id,
    2:  optional i32 hr_id,
    3:  optional list<i32> ids,
    4:  optional i32 state
}

struct MallMailAddressForm{
    1: optional i32 id,
    2: optional i32 userId;
    3: optional string addressee,
    4: optional string mobile,
    5: optional i32 province,
    6: optional i32 city,
    7: optional i32 region,
    8: optional string provinceName,
    9: optional string cityName,
    10: optional string regionName,
    11: optional string address
}