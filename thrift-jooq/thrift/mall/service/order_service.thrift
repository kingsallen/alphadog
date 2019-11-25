include "../struct/mall_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.mall.service

/*
  订单管理服务接口
*/
service OrderService {

   map<string, string> getCompanyOrderList(1: mall_struct.OrderSearchForm orderSearchForm) throws (1: common_struct.BIZException e);

   string getEmployeeOrderList(1: mall_struct.BaseMallForm baseMallForm) throws (1: common_struct.BIZException e);

   void confirmOrder(1: mall_struct.OrderForm orderForm) throws (1: common_struct.BIZException e);

   void updateOrder(1: mall_struct.MallGoodsOrderUpdateForm updateForm) throws (1: common_struct.BIZException e);

   string exportOrder(1: mall_struct.BaseMallForm baseMallForm) throws (1: common_struct.BIZException e);

   mall_struct.MallMailAddressForm getAddressById(1: i32 mailId) throws (1: common_struct.BIZException e);
}
