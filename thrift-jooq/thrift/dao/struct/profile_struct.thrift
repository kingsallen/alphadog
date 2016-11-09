# file: profile.thrift

namespace java com.moseeker.thrift.gen.orm.struct

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
typedef string Timestamp;

struct HRThirdPartyAccount { 
    1: optional i32 id,
    2: optional short channel,
    3: optional string username,
    4: optional string password,
    5: optional string membername,
    6: optional short binding,
    7: optional i32 companyId,
    8: optional i32 remainNum,
    9: optional Timestamp sync_time,
    10: optional Timestamp create_time,
    11: optional Timestamp update_time
}

