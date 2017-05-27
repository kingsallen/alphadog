namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrWxWechatNoticeSyncStatusDO {

	1: optional i32 id,	//主key
	2: optional i32 wechatId,	//所属公众号
	3: optional i32 status,	//同步状态 0:成功, 1:行业修改失败, 2:模板数量超出上限, 3:接口调用失败
	4: optional i32 count,	//同步状态提示应该删除信息的数量
	5: optional string updateTime	//null

}
