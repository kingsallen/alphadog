namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrHbSendRecordDO {

	1: optional i32 id,	//null
	2: optional string returnCode,	//SUCCESS/FAIL 此字段是通信标识,非交易标识,交易是否成功需要查看result_code来判断
	3: optional string returnMsg,	//返回信息,如非空,为错误原因
	4: optional string sign,	//生成签名
	5: optional string resultCode,	//SUCCESS/FAIL
	6: optional string errCode,	//错误码信息
	7: optional string errCodeDes,	//结果信息描述
	8: optional string mchBillno,	//商户订单号
	9: optional string mchId,	//微信支付分配的商户号
	10: optional string wxappid,	//商户appid
	11: optional string reOpenid,	//接受收红包的用户,用户在wxappid下的openid
	12: optional i32 totalAmount,	//付款金额,单位分
	13: optional string sendTime,	//红包发送时间
	14: optional string sendListid,	//红包订单的微信单号
	15: optional string createTime,	//null
	16: optional i32 hbItemId	//hr_hb_items.id 该红包 api 调用所对应的红包记录

}
