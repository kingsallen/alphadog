# file: useraccounts.thrift

include "../struct/useraccounts_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.useraccounts.service

service UseraccountsServices {
    common_struct.Response postuserlogin(1: useraccounts_struct.userloginreq userloginreq);
    common_struct.Response postuserlogout(1: i32 userid);
    common_struct.Response postsendsignupcode(1: string mobile);
    common_struct.Response postusermobilesignup(1: string mobile, 2: string code, 3: string password);
    common_struct.Response postuserwxbindmobile(1: i32 appid, 2: string unionid, 3: string code,4: string mobile);
    common_struct.Response postuserchangepassword(1: i32 user_id, 2: string old_password,  3: string password);
    common_struct.Response postusersendpasswordforgotcode(1: string mobile);
    common_struct.Response postuserresetpassword(1: string mobile, 2: string code, 3: string password);
    common_struct.Response postusermergebymobile(1: i32 appid, 2: string mobile);
}
