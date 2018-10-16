include "../struct/mall_struct.thrift"
include "../../dao/struct/malldb/mall_goods_info_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.mall.service

/*
  商城商品服务接口
*/
service GoodsService {

   map<string, string> getGoodsList(1:mall_struct.GoodSearchForm goodSearchForm) throws (1: common_struct.BIZException e);

   void addGood(1: mall_goods_info_struct.MallGoodsInfoDO mallGoodsInfoDO) throws (1: common_struct.BIZException e);

   void editGood(1: mall_goods_info_struct.MallGoodsInfoDO mallGoodsInfoDO) throws (1: common_struct.BIZException e);

   i32 updateGoodState(1: i32 goodId, 2: i32 companyId, 3: i32 state) throws (1: common_struct.BIZException e);

   i32 updateGoodStock(1: i32 goodId, 2: i32 companyId, 3: i32 stock) throws (1: common_struct.BIZException e);

   string getGoodDetail(1: i32 goodId, 2: i32 companyId) throws (1: common_struct.BIZException e);
}
