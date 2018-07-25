# file: profile.thrift

namespace java com.moseeker.thrift.gen.talentpool.struct

struct ProfileMoveForm {
    1:optional i32 hr_id;
    2:optional i32 company_id;
    3:optional i32 channel;
    4:optional i32 crawl_type;
    5:optional string start_date;
    6:optional string end_date;
    7:optional string company_name;
}