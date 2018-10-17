include "../struct/mall_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.mall.service

/*
  订单管理服务接口
*/
service OrderService {

   map<string, string> getCompanyOrderList(1: mall_struct.OrderSearchForm orderSearchForm) throws (1: common_struct.BIZException e);

   string getEmployeeOrderList(1: i32 employeeId) throws (1: common_struct.BIZException e);

   void confirmOrder(1: mall_struct.OrderForm orderForm) throws (1: common_struct.BIZException e);

   void updateOrder(1: list<i32> ids, 2: i32 companyId, 3: i32 state) throws (1: common_struct.BIZException e);

}
