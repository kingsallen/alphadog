namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


//员工内推配置信息
struct UserEmployeeRefferalPolicyDO {
    1: optional i32 id,
    2: optional i32 employeeId,
    3: optional i32 count
}
