namespace java com.moseeker.thrift.gen.dict.struct

typedef string Timestamp;

/*
  城市字典实体
*/
struct City {
    1: i32 code,
    2: string name,
    3: byte level,
    4: byte hot_city, // 热门城市 0:否 1：是
    5: string ename, // 英文名称
    6: byte is_using // 正在使用 0:没在用 1:在使用
}

/*
  院校字典实体
*/
struct College {
    1: i32 college_code,
    2: string collge_name,  //
    3: i32 province_code, // 所在地 code
    4: string province_name // 所在地 名称
}

/*
  常量字典实体
*/
struct DictConstant {
    1: i32 code,                // 常量code
    2: string name,             // 常量名称
    3: i32 priority,            // 优先级
    4: i32 parent_code,         // 常量类型
    5: Timestamp create_time,   // 表记录创建时间
    6: Timestamp update_time    // 最新更新时间
}