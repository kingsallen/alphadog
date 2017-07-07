namespace java com.moseeker.thrift.gen.dao.struct.dictdb
namespace py thrift_gen.gen.dao.struct.dictdb


struct DictCityMapDO {

	1: optional i32 id,	//主键id
	2: optional i32 code,	//千寻城市字典code
	3: optional string codeOther,	//第三方城市字典code
	4: optional i32 channel,	//渠道 1 51job 2 猎聘 3 智联 4 linkedin
	5: optional i32 status,	//状态 0 是有效 1是无效
	6: optional string createTime	//创建时间

}
