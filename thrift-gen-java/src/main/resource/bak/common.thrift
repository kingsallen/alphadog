namespace java com.moseeker.thrift.gen.common

struct CommonQueryFields {
    1: i32 appid,
    2: optional i32 limit=10,
    3: optional i32 offset,
    4: optional i32 page,
    5: optional i32 per_page,
    6: optional string sortby,
    7: optional string order,
    8: optional string fields,
    9: optional bool nocache=false
}
