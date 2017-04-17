namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"
include "../struct/db/campaigndb_struct.thrift"

service CampaignHeadImageDao {
    // 头图查询接口
    common_struct.Response headImage(1:common_struct.CommonQuery query);
}
