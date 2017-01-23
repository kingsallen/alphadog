# file:userdb_struct.thrift 

namespace java com.moseeker.thrift.gen.dao.struct

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
typedef string Timestamp;

struct UserFavPositionPojo  {
    1: optional i32 sysuserId,          //用户编号 userdb.user_user.id
    2: optional i32 positionId,         //职位编号 jobdb.job_position.id
    3: optional i8 favorite,            //职位编号
    4: optional Timestamp createTime,   //推荐用户编号user_user.id
    5: optional Timestamp update_time,  //申请时间
    6: optional string mobile,          //申请状态ID（可能已经废弃）
    7: optional i32 id,                 //编号 数据库表标志
    8: optional i32 recomUserId        //推荐者C端账号 userdb.user_user.id
}
