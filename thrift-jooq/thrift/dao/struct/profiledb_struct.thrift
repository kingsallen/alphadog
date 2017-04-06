namespace java com.moseeker.thrift.gen.dao.struct
namespace py thrift_gen.gen.dao.struct.profiledb

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
typedef string Timestamp;

/*
 * 候选人记录
 */
struct ProfileOtherDO { 
    1: optional i32 profile_id,             // 数据库编号
    2: optional string other,               // 公司编号 其他字段
    3: optional Timestamp createTime,       // 创建时间
    4: optional Timestamp updateTime,       // 修改时间
}

