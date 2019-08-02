namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrCompanyWorkWxConfDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//所属公司 hr_company.id
	3: optional string corpid ,// 企业微信corpid，来源于企业微信后台配置
    4: optional string secret,// 企业微信corpsecret，来源于企业微信后台配置
    5: optional string accessToken,// access_token
    6: optional i64 tokenUpdateTime, // access_token最近获取时间
    7: optional i64 tokenExpireTime, // access_token最近获取时间
    8: optional i32 errorCode,
    9: optional string errorMsg,
    10: optional double disable	//是否启用 0：启用1：禁用

}
