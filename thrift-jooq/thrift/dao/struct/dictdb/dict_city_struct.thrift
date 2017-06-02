namespace java com.moseeker.thrift.gen.dao.struct.dictdb
namespace py thrift_gen.gen.dao.struct.dictdb


struct DictCityDO {

	1: optional i32 code,	//字典code
	2: optional string name,	//字典name
	3: optional i8 level,	//字典level
	4: optional i8 hotCity,	//热门城市 0:否 1：是
	5: optional string ename,	//英文名称
	6: optional i8 isUsing	//正在使用 0:没在用 1:在使用

}
