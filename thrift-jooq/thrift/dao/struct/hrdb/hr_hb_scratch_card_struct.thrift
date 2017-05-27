namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrHbScratchCardDO {

	1: optional i32 id,	//null
	2: optional i32 wechatId,	//null
	3: optional string cardno,	//随机字符串
	4: optional i32 status,	//状态: 0：未拆开 1：已拆开
	5: optional double amount,	//红包金额： 0.00 表示不发红包
	6: optional i32 hbConfigId,	//null
	7: optional string baggingOpenid,	//聚合号的 openid
	8: optional string createTime,	//null
	9: optional i32 hbItemId,	//null
	10: optional i8 tips	//是否是小费 0:不是，1:是

}
