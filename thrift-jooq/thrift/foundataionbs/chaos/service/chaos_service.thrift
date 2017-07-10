# file: function_service.thrift

include "../../../common/struct/common_struct.thrift"
include "../../../dao/struct/hrdb/hr_third_party_account_struct.thrift"
include "../../../dao/struct/hrdb/hr_third_party_position_struct.thrift"
include "../../../position/struct/position_struct.thrift"
namespace java com.moseeker.thrift.gen.foundation.chaos.service

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
 
service ChaosServices {
    //绑定第三方帐号
    hr_third_party_account_struct.HrThirdPartyAccountDO binding(1:hr_third_party_account_struct.HrThirdPartyAccountDO thirdPartyAccount,2:map<string,string> extras) throws (1: common_struct.BIZException e);
    //同步可发布职位数
    hr_third_party_account_struct.HrThirdPartyAccountDO synchronization(1:hr_third_party_account_struct.HrThirdPartyAccountDO thirdPartyAccount,2:map<string,string> extras) throws (1: common_struct.BIZException e);
    //同步职位
    void synchronizePosition(1:list<position_struct.ThirdPartyPositionForSynchronizationWithAccount> positions) throws (1: common_struct.BIZException e);
    //刷新职位
    void refreshPosition(1:position_struct.ThirdPartyPositionForSynchronizationWithAccount position) throws (1: common_struct.BIZException e);
}

