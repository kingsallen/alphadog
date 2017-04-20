namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"
include "../struct/profiledb_struct.thrift"
include "../struct/profiledb/profile_basic_struct.thrift"

service ProfileProfileDao{
    common_struct.Response getResourceByApplication(1:string downloadApi,2:string password,3:i32 companyId,4:i32 sourceId,5:i32 atsStatus,6:bool recommender,7:bool dl_url_required);
}


service ProfileDBDao {
    //查询HR标记的候选人信息  
	list<profiledb_struct.ProfileOtherDO> listProfileOther (1:common_struct.CommonQuery query) throws (1:common_struct.CURDException e)
	profiledb_struct.ProfileOtherDO getProfileOther (1:common_struct.CommonQuery query) throws (1:common_struct.CURDException e)
	profiledb_struct.ProfileOtherDO updateProfileOther (1:profiledb_struct.ProfileOtherDO profileOther) throws (1:common_struct.CURDException e)
	profiledb_struct.ProfileOtherDO saveProfileOther (1:profiledb_struct.ProfileOtherDO profileOther) throws (1:common_struct.CURDException e)
	i32 deleteProfileOther (1:profiledb_struct.ProfileOtherDO profileOther) throws (1:common_struct.CURDException e)
    profile_basic_struct.ProfileBasicDO getProfileBasic(1:common_struct.CommonQuery query) throws (1:common_struct.CURDException e)
}
