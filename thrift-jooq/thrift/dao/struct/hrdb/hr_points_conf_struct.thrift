namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrPointsConfDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//null
	3: optional string statusName,	//name of status defined, if using ATS, please set it the same as ATS
	4: optional double reward,	//积分数量
	5: optional string description,	//null
	6: optional double isUsing,	//是否启用0：启用1：禁用
	7: optional double orderNum,	//优先级
	8: optional string updateTime,	//null
	9: optional string tag,	//null
	10: optional double isApplierSend,	//申请者是否发送消息模板0:发送1:不发送
	11: optional string applierFirst,	//申请状态模板发送问候语
	12: optional string applierRemark,	//申请状态模板发送结束语
	13: optional double isRecomSend,	//推荐者是否发送消息模板0:发送1:不发送
	14: optional string recomFirst,	//推荐者申请状态模板发送问候语
	15: optional string recomRemark,	//推荐者申请状态模板发送结束语
	16: optional i32 templateId	//申请状态模板ID，hr_award_config_template.id

}
