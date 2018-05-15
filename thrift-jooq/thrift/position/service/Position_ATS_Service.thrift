include "../struct/position_struct.thrift"
include "../../common/struct/common_struct.thrift"
include "../../dao/struct/thirdpartydb/thirdparty_company_channel_conf.thrift"

namespace java com.moseeker.thrift.gen.position.service
/*
    查询第三方职位职能
*/
service PositionATSServices {
    // 获取所有可同步渠道
    common_struct.Response getSyncChannel() throws (1: common_struct.BIZException e);

    // 配置公司可同步渠道
    list<thirdparty_company_channel_conf.ThirdpartyCompanyChannelConfDO> updateCompanyChannelConf(1:i32 company_id,2:list<i32> channel) throws (1: common_struct.BIZException e);

    // 查询公司可同步渠道
    list<i32> getCompanyChannelConfByCompanyId(1:i32 company_id) throws (1: common_struct.BIZException e);

    // 谷露新增职位
    common_struct.Response insertGlluePosition(1:position_struct.BatchHandlerJobPostion batchHandlerJobPostion);

    // 谷露修改职位
    common_struct.Response updateGlluePosition(1:position_struct.BatchHandlerJobPostion batchHandlerJobPostion);

    // 谷露下架的职位重新发布
    common_struct.Response republishPosition(1:position_struct.BatchHandlerJobPostion batchHandlerJobPostion);

    // 谷露下架职位
    common_struct.Response revokeGlluePosition(1:position_struct.BatchHandlerJobPostion batchHandlerJobPostion);

    // ATS更新职位福利特色
    common_struct.Response atsUpdatePositionFeature(1:position_struct.BatchHandlerJobPostion batchHandlerJobPostion);
}