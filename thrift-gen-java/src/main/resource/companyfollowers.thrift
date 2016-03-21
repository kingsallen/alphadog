# file: companyfollowers.thrift

namespace java com.moseeker.thrift.gen.companyfollowers

struct Companyfollower { 
    1: i32 id,
    2: i32 userid,
    3: i32 companyid
}

struct CompanyfollowerQuery {
    1: i32 appid,
    2: optional i32 limit=10,
    3: optional i32 offset,
    4: optional i32 page,
    5: optional i32 per_page,
    6: optional string sortby,
    7: optional string order,
    8: optional string fields,
    9: optional bool nocache=false
    10: optional i32 id,
    11: optional i32 userid,
    12: optional i32 companyid
}


service CompanyfollowerServices {
    list<Companyfollower> getCompanyfollowers(1: CompanyfollowerQuery query);
    i32 postCompanyfollowers(1: i32 userid,2: i32 companyid);
    i32 delCompanyfollowers(1: i32 userid,2: i32 companyid);
}
