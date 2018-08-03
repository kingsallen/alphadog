namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


//员工内推配置信息
struct HrCompanyReferralConfDO {
    1: optional i32 id,
    2: optional i32 companyId,
    3: optional string link,
    4: optional string text,
    5: optional i8 priority,
    6: optional string textUpdateTime,
    7: optional string createTime,
    8: optional string updateTime
}
