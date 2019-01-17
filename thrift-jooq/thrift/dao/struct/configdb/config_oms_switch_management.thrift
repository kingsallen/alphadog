namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb

struct ConfigOmsSwitchManagementDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//公司ID
	3: optional i32 moduleName,	//各产品定义标识
	4: optional string moduleParam,	//各产品所需附加参数，json格式 (只用于oms后端配置的参数)
	5: optional i8 isValid,	//是否可用 0：不可用，1：可用
	6: optional i32 version,	//版本号
	7: optional string updateTime,	//更新时间
	8: optional string createTime	//创建时间

}
