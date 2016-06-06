# file: useraccounts.thrift

#include "../struct/useraccounts_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.searchengine.service

service SearchengineServices {
    common_struct.Response query(1: string keywords,2:string cities,3:string industries,4:string occupations,5:string scale,6:string employment_type,7:string candidate_source,8:string experience,9:string degree,10:string salary,11:string company_id,12:i32 page_from,13: i32 page_size,14:string child_company_id);
    common_struct.Response updateposition(1: string positionid );
}
