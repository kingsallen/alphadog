namespace java com.moseeker.thrift.gen.dao.struct.thirdpartydb
namespace py thrift_gen.gen.dao.struct.thirdpartydb


struct ThirdpartyZhilianPositionAddressDO {
	1: optional i32 id,	//主键
	2: optional i32 pid,	//第三方渠道职位编号
	3: optional i32 cityCode,	//仟寻职位发布城市code
	4: optional string  address,	//页面手动填写的地址
	6: optional string createTime,	//创建时间
}
