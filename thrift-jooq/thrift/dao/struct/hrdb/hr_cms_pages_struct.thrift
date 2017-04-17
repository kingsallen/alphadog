namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrCmsPagesDO {

	1: optional i32 id,	//null
	2: optional i32 configId,	//null
	3: optional i32 type,	//0,默认值暂无意义，1为企业首页(config_id为company_id)2，团队详情（config_id为team_id） ，3，详情职位详情(config_id为team_id)
	4: optional i32 disable,	//状态 0 是有效 1是无效
	5: optional string createTime,	//null
	6: optional string updateTime	//null

}
