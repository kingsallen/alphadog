namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrHbConfigDO {

	1: optional i32 id,	//null
	2: optional i8 type,	//0:员工认证红包，1:推荐评价红包，2:转发被点击红包，3:转发被申请红包
	3: optional i8 target,	//0:员工，1:员工及员工二度，2:粉丝
	4: optional i32 companyId,	//company.id
	5: optional string startTime,	//红包活动开始时间
	6: optional string endTime,	//红包活动结束时间，直到红包用尽：2037-12-31 23:59:59）
	7: optional i32 totalAmount,	//总预算
	8: optional double rangeMin,	//红包最小金额
	9: optional double rangeMax,	//红包最大金额
	10: optional double probability,	//中奖几率: 0 < x <= 100
	11: optional i8 dType,	//分布类型 0:平均分布，1:指数分布
	12: optional string headline,	//抽奖页面标题
	13: optional string headlineFailure,	//抽奖失败页面标题
	14: optional string shareTitle,	//转发消息标题
	15: optional string shareDesc,	//转发消息摘要
	16: optional string shareImg,	//转发消息背景图地址
	17: optional i8 status,	//0:待审核，1:已审核，2:未开始，3:进行中：4:暂停中，5：已完成， －1: 已删除
	18: optional i8 checked,	//0:未审核，1:审核通过，2:审核不通过
	19: optional i32 estimatedTotal,	//预估红包总数
	20: optional string createTime,	//创建时间
	21: optional string updateTime,	//更新时间
	22: optional i32 actualTotal	//实际红包总数

}
