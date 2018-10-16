# file: mall.thrift
namespace java com.moseeker.thrift.gen.mall.struct

struct OrderSearchForm {
    1:  optional i32 page_number,
    2:  optional i32 page_size,
    3:  optional i8 state,
    4:  optional string keyword
    5:  optional i32 company_id
}

struct GoodSearchForm {
    1:  optional i32 page_number,
    2:  optional i32 page_size,
    3:  optional i32 company_id,
    4:  optional i8 state // 0 未上架 1 上架 9 全部
}

struct OrderForm {
    1:  optional i32 employee_id,
    2:  optional i32 company_id,
    3:  optional i32 count,
    4:  optional i32 goods_id
}