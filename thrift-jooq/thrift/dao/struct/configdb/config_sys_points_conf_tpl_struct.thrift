namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigSysPointsConfTplDO {

	1: optional i32 id,	//主Key
	2: optional string status,	//申请状态
	3: optional i32 award,	//奖励积分
	4: optional string description,	//描述
	5: optional i32 disable,	//是否可用
	6: optional i32 priority,	//排序
	7: optional i32 typeId,	//多套模板
	8: optional double tag,	//inteview tag
	9: optional double isInitAward,	//推荐积分初始化0:需要 1:不需要
	10: optional i32 recruitOrder,	//招聘进度顺序
	11: optional string applierView	//求职者的文案

}
