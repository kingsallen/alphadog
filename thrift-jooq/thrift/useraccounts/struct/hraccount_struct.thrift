# file: useraccounts.struct

namespace java com.moseeker.thrift.gen.hraccount.struct

typedef string Timestamp;

struct BindAccountStruct { 
    1: string username,
    2: string password,
    3: optional string memberName,
    4: byte channel,
    5: i32 appid,
    6: i32 user_id,
    7: i32 company_id
}

