namespace java com.moseeker.thrift.gen.dict.struct

struct City {
    1: i32 code,
    2: string name,
    3: byte level,
    4: byte hot_city, // 热门城市 0:否 1：是
    5: string ename, // 英文名称
    6: byte is_using // 正在使用 0:没在用 1:在使用
}

struct College {
    1: i32 code,
    2: string name,  //
    3: i32 province, // 所在地
    4: string logo
}