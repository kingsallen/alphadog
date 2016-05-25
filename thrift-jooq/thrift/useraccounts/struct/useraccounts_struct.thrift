# file: useraccounts.struct

namespace java com.moseeker.thrift.gen.useraccounts.struct

typedef string Timestamp;

struct Userloginreq { 
    1: optional string unionid,
    2: optional string mobile,
    3: optional string password
}

struct Usersetting { 
    1: i32 id,
    2: i32 user_id,
    3: optional string banner_url,
    4: i32 privacy_policy
}

