
include "../../dao/struct/userdb/user_user_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.useraccounts.service

/**
* 所有用户通用的服务
**/
service UserProviderService {

    bool healthCheck();

    // 获取新版本内容
    user_user_struct.UserUserDO getCompanyUser(1: i32 appid,2: string phone,3: i32 companyId) throws (1: common_struct.BIZException e);
}