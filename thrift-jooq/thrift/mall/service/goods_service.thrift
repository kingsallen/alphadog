include "../struct/mall_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.mall.service

/*
  商城商品服务接口
*/
service GoodsService {

   map<string, string> getGoodsList(1:mall_struct.GoodSearchForm goodSearchForm) throws (1: common_struct.BIZException e);

   void addGood(1: mall_struct.MallGoodsInfoForm mallGoodsInfoForm) throws (1: common_struct.BIZException e);

   void editGood(1: mall_struct.MallGoodsInfoForm mallGoodsInfoForm) throws (1: common_struct.BIZException e);

   list<i32> updateGoodState(1: mall_struct.MallGoodsStateForm mallGoodsStateForm) throws (1: common_struct.BIZException e);

   i32 updateGoodStock(1: mall_struct.MallGoodsStockForm mallGoodsStockForm) throws (1: common_struct.BIZException e);

   string getGoodDetail(1: mall_struct.MallGoodsIdForm mallGoodsIdForm) throws (1: common_struct.BIZException e);
}
