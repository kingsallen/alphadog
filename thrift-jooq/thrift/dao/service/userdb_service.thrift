namespace java com.moseeker.thrift.gen.dao.service
namespace py thrift_gen.gen.dao.service.userdb

include "../../common/struct/common_struct.thrift"
include "../struct/userdb_struct.thrift"
include "../../useraccounts/struct/useraccounts_struct.thrift"

service UserDBDao {
    list<userdb_struct.UserFavPositionDO> getUserFavPositions(1:common_struct.CommonQuery query) throws (1: common_struct.CURDException e);
    
    //查询用户
    userdb_struct.UserUserDO getUser(1:common_struct.CommonQuery query) throws (1: common_struct.CURDException e);
    list<userdb_struct.UserUserDO> listUser(1:common_struct.CommonQuery query) throws (1: common_struct.CURDException e);
    //保存用户
    userdb_struct.UserUserDO saveUser(1:userdb_struct.UserUserDO user) throws (1: common_struct.CURDException e);
    //查找公司下的HR
    list<userdb_struct.UserHrAccountDO> listHRFromCompany(1: i32 comanyId) throws (1: common_struct.CURDException e);
    list<userdb_struct.UserHrAccountDO> listUserHrAccount(1: common_struct.CommonQuery query) throws (1: common_struct.CURDException e);
    userdb_struct.UserHrAccountDO getUserHrAccount(1: common_struct.CommonQuery query) throws (1: common_struct.CURDException e);
    userdb_struct.UserHrAccountDO updateUserHrAccount(1: userdb_struct.UserHrAccountDO userHrAccountDO) throws (1: common_struct.CURDException e);
    i32 deleteUserHrAccount(1: i32 id) throws (1: common_struct.CURDException e);

    userdb_struct.UserEmployeeDO getEmployee(1:common_struct.CommonQuery query) throws (1: common_struct.CURDException e);
    common_struct.Response putUserEmployee(1: userdb_struct.UserEmployeePointsRecordDO employeeDo) throws (1: common_struct.CURDException e);

    common_struct.Response getUserEmployee(1:i32 companyId,2:list<i32> weChatIds) throws (1: common_struct.CURDException e);
    common_struct.Response postUserEmployeePoints(1:list<useraccounts_struct.UserEmployeePointStruct> records) throws (1: common_struct.CURDException e);
    i32 updateUserEmployeePoint(1 : i32 id) throws (1:common_struct.BIZException e)

    common_struct.Response getPointSum(1:list<i64> record) throws (1: common_struct.CURDException e);
    common_struct.Response putUserEmployees(1:list<useraccounts_struct.UserEmployeeStruct> records) throws (1: common_struct.CURDException e);
    common_struct.Response putUserEmployeePoints(1:list<useraccounts_struct.UserEmployeePointStruct> records) throws (1: common_struct.CURDException e);

    list<userdb_struct.UserEmployeePointsRecordDO> getUserEmployeePoints(1: i32 employeeId) throws (1: common_struct.CURDException e);
    userdb_struct.UserEmployeePointsRecordDO saveUserEmployeePoints(1: userdb_struct.UserEmployeePointsRecordDO employeePoint) throws (1: common_struct.BIZException e);

    list<userdb_struct.UserEmployeeDO> getUserEmployeesDO(1: common_struct.CommonQuery query) throws (1: common_struct.CURDException e);
    common_struct.Response putUserEmployeesDO(1: list<userdb_struct.UserEmployeeDO> employeeDoList) throws (1: common_struct.CURDException e);
    i32 postUserEmployeeDO(1: userdb_struct.UserEmployeeDO userEmployee) throws (1: common_struct.CURDException e)

    list<userdb_struct.UserWxUserDO> listUserWxUserDO(1: common_struct.CommonQuery query) throws (1:common_struct.CURDException e)
    userdb_struct.UserWxUserDO getUserWxUserDO(1: common_struct.CommonQuery query) throws (1:common_struct.CURDException e)
}
