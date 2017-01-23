namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"
include "../struct/userdb_struct.thrift"
include "../../useraccounts/struct/useraccounts_struct.thrift"

service UserDBDao {
    list<userdb_struct.UserFavPositionPojo> getUserFavPositions(1:common_struct.CommonQuery query);
    
    //查询用户
    useraccounts_struct.User getUser(1:common_struct.CommonQuery query);
    //保存用户
    useraccounts_struct.User saveUser(1:useraccounts_struct.User user);

    userdb_struct.UserEmployeePojo getEmployee(1:common_struct.CommonQuery query);
}
