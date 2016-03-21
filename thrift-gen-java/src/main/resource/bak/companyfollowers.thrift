# file: companyfollowers.thrift
include "common.thrift"

namespace java com.moseeker.thrift.gen.companyfollowers

struct Companyfollower { 
    1: i32 id,
    2: i32 userid,
    3: i32 companyid
}

struct CompanyfollowerQuery {
    1: optional common.CommonQueryFields commonfields,
    2: optional i32 id,
    3: optional i32 userid,
    4: optional i32 companyid
}


service CompanyfollowerServices {
    list<Companyfollower> getCompanyfollowers(1: CompanyfollowerQuery query);
    i32 postCompanyfollowers(1: i32 userid,2: i32 companyid);
    i32 delCompanyfollowers(1: i32 userid,2: i32 companyid);
}
