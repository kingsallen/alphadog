
include "../../dao/struct/userdb/user_user_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.useraccounts.service

/**
* 所有用户通用的服务
**/
service UserProviderService {

    // 获取新版本内容
    user_user_struct.UserUserDO getCompanyUser(1: i32 appid,2: string phone,3: i32 companyId) throws (1: common_struct.BIZException e);
    user_user_struct.UserUserDO storeChatBotUser(1: string profilePojo,2: i32 reference,3: i32 companyId,4: i32 source,5: i32 appid) throws (1: common_struct.BIZException e);
}