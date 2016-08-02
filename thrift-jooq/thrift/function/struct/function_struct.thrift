# file: function.thrift

namespace java com.moseeker.thrift.gen.function.struct

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */

struct SensitiveWord { 
    1: i32 appid,
    2: list<string> contents
}

