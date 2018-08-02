namespace java com.moseeker.thrift.gen.dao.struct.dictdb
namespace py thrift_gen.gen.dao.struct.dictdb


struct DictCollegeDO {

	1: optional i32 code,	//字典code
	2: optional string name,	//字典name
	3: optional i32 province,	//院校所在地
	4: optional string logo,	//院校logo
	5: optional i32 country_code //国家字典表中的id

}
