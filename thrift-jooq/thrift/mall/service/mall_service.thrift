include "../struct/mall_struct.thrift"
include "../../dao/struct/malldb/mall_goods_info_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.mall.service

/*
  商城管理服务接口
*/
service MallService {

   i32 getMallSwitch(1: i32 companyId) throws (1: common_struct.BIZException e);

   void openOrCloseMall(1: i32 companyId, 2: i32 state) throws (1: common_struct.BIZException e);

   string getDefaultRule(1: i32 companyIdO) throws (1: common_struct.BIZException e);

   void updateDefaultRule(1: i32 companyId, 2: i32 state, 3: string rule) throws (1: common_struct.BIZException e);

}
