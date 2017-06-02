namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"
include "../struct/db/campaigndb_struct.thrift"


service CampaignDBDao {
    // 头图信息查询
   campaigndb_struct.CampaignHeadImageDO headImage(1:common_struct.CommonQuery query);
}
