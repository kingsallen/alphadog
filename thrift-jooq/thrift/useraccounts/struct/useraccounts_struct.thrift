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

/*
  我感兴趣/职位收藏关系表
*/
struct UserFavoritePosition {
    1:i64 id         ,       // ID
    2:i32 sysuser_id ,       // 用户ID
    3:i32 position_id,       // 职位ID
    4:byte favorite  ,       // 0:收藏, 1:取消收藏, 2:感兴趣
    5:string mobile  ,       // 感兴趣的手机号
    6:i64 wxuser_id  ,       // wx_user.id
    7:i32 recom_id   ,       // 推荐者 fk:wx_user.id
    8:Timestamp create_time, //
    9:Timestamp update_time  //
}

