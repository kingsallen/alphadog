# file: useraccounts.thrift

include "../struct/useraccounts_struct.thrift"

namespace java com.moseeker.thrift.gen.useraccounts.service

service UseraccountsServices {
    useraccounts_struct.userloginresp postuserlogin(1: useraccounts_struct.userloginreq userloginreq);
    i32 postuserlogout(1: i32 userid);
    i32 postsendsignupcode(1: string mobile);
    i32 postusermobilesignup(1: string mobile, 2: string code, 3: string password);
    i32 postuserwxsignup(1: string unionid);
    i32 postuserwxbindmobile(1: string unionid, 2: string code,3: string mobile);
    i32 postusermobilebindwx(1: string mobile, 2: string code,3: string unionid);
    i32 postuserchangepassword(1: i32 user_id, 2: string old_password,  3: string password);
    i32 postusersendpasswordforgotcode(1: string mobile);
    i32 postuserresetpassword(1: string mobile, 2: string code, 3: string password);
    i32 postusermergebymobile(1: string mobile);
}
