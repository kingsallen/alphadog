namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"
include "../struct/userdb_struct.thrift"
include "../../useraccounts/struct/useraccounts_struct.thrift"

service UserDBDao {
    list<userdb_struct.UserFavPositionDTO> getUserFavPositions(1:common_struct.CommonQuery query);
    
    //查询用户
    useraccounts_struct.User getUser(1:common_struct.CommonQuery query);
    //保存用户
    useraccounts_struct.User saveUser(1:useraccounts_struct.User user);

    userdb_struct.UserEmployeeDTO getEmployee(1:common_struct.CommonQuery query);
    common_struct.Response getUserEmployee(1:i32 companyId,2:list<i32> weChatIds);
    common_struct.Response postUserEmployeePoints(1:list<useraccounts_struct.UserEmployeePointStruct> records);
    common_struct.Response getPointSum(1:list<i64> record);
    common_struct.Response putUserEmployees(1:list<useraccounts_struct.UserEmployeeStruct> records);
    common_struct.Response putUserEmployeePoints(1:list<useraccounts_struct.UserEmployeePointStruct> records);
}
