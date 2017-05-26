namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigCacheconfigRediskeyDO {

	1: optional i32 id,	//null
	2: optional i32 projectAppid,	//项目id 0 基础服务
	3: optional string keyIdentifier,	//标识符， 大写英文字母
	4: optional i8 type,	//缓存类型 1 data  2. session 3 log
	5: optional string pattern,	//格式， 形如 ip_limit_%s
	6: optional string jsonExtraparams,	//额外参数， 如{'maxPerHour':100}
	7: optional i32 ttl,	//生存时间， 单位秒
	8: optional string desc,	//备注， 包含json_extraparams的解释
	9: optional string createTime	//null

}
