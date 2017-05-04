namespace java com.moseeker.thrift.gen.dao.struct.dictdb
namespace py thrift_gen.gen.dao.struct.dictdb


struct DictCityPostcodeDO {

	1: optional i32 id,	//null
	2: optional string postcode,	//邮编
	3: optional string province,	//省份
	4: optional string city,	//城市
	5: optional string district,	//地区或区县
	6: optional string code	//行政区号

}
