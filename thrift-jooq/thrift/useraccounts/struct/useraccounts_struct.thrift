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
  用户实体
*/
struct User {
     1: i64        id              ,    // 主key
     2: string     username        ,    // 用户名，比如手机号、邮箱等
     3: string     password        ,    // 密码
     4: byte       is_disable      ,    // 是否禁用，0：可用，1：禁用
     5: i64        rank            ,    // 用户等级
     6: Timestamp  register_time   ,    // 注册时间
     7: string     register_ip     ,    // 注册IP
     8: Timestamp  last_login_time ,    // 最近登录时间
     9: string     last_login_ip   ,    // 最近登录IP
    10: i64        login_count     ,    // 登录次数
    11: i64        mobile          ,    // user pass mobile registe
    12: string     email           ,    // user pass email registe
    13: byte       activation      ,    // is not activation 0:no 1:yes
    14: string     activation_code ,    // activation code
    15: string     token           ,    // 用户校验token
    16: string     name            ,    // 姓名或微信昵称
    17: string     headimg         ,    // 头像
    18: i64        country_id      ,    // 国家字典表ID, dict_country.id
    19: i64        wechat_id       ,    // 注册用户来自于哪个公众号, 0:默认为来自浏览器的用户
    20: string     unionid         ,    // 存储仟寻服务号的unionid
    21: byte       source          ,    // 来源：0:手机注册, 1:聚合号一键登录, 2:企业号一键登录, 7:PC(正常添加), 8:PC(我要投递), 9: PC(我感兴趣)
    22: string     company         ,    // 点击我感兴趣时填写的公司
    23: string     position        ,    // 点击我感兴趣时填写的职位
    24: i64        parentid             // 合并到了新用户的id
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

