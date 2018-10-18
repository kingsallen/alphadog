include "../struct/mall_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.mall.service

/*
  商城管理服务接口
*/
service MallService {

   i32 getMallSwitch(1: mall_struct.BaseMallForm baseMallForm) throws (1: common_struct.BIZException e);

   void openOrCloseMall(1: mall_struct.MallSwitchForm mallSwitchForm) throws (1: common_struct.BIZException e);

   string getDefaultRule(1: mall_struct.BaseMallForm baseMallForm) throws (1: common_struct.BIZException e);

   void updateDefaultRule(1: mall_struct.MallRuleForm mallRuleForm) throws (1: common_struct.BIZException e);

}
