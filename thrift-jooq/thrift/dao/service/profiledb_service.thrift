namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"
include "../struct/profiledb_struct.thrift"
include "../struct/profiledb/profile_basic_struct.thrift"

service ProfileDBDao {
    //查询HR标记的候选人信息  
	list<profiledb_struct.ProfileOtherDO> listProfileOther (1:common_struct.CommonQuery query) throws (1:common_struct.CURDException e)
	profiledb_struct.ProfileOtherDO getProfileOther (1:common_struct.CommonQuery query) throws (1:common_struct.CURDException e)
	profiledb_struct.ProfileOtherDO updateProfileOther (1:profiledb_struct.ProfileOtherDO profileOther) throws (1:common_struct.CURDException e)
	profiledb_struct.ProfileOtherDO saveProfileOther (1:profiledb_struct.ProfileOtherDO profileOther) throws (1:common_struct.CURDException e)
	i32 deleteProfileOther (1:profiledb_struct.ProfileOtherDO profileOther) throws (1:common_struct.CURDException e)
    profile_basic_struct.ProfileBasicDO getProfileBasic(1:common_struct.CommonQuery query) throws (1:common_struct.CURDException e)
}
