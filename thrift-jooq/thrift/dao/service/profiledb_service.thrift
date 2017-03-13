namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"

service ProfileDao{

    common_struct.Response getResourceByApplication(1:i32 companyId,2:i32 sourceId,3:i32 atsStatus,4:bool recommender);
}