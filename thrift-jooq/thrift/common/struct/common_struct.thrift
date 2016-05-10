# file: common.struct

namespace java com.moseeker.thrift.gen.common.struct

struct Response { 
    1: i32 status,
    2: string message,
    3: optional string data
}
