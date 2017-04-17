namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrHbItemsDO {

	1: optional i32 id,	//null
	2: optional i32 hbConfigId,	//hr_hb_config.id
	3: optional i32 bindingId,	//position_hb_binding.id
	4: optional i32 index,	//这条数据是第几个红包 0 < x <= 总红包数
	5: optional double amount,	//红包金额
	6: optional i8 status,	//0:还未送出，1:已经送出
	7: optional i32 wxuserId,	//获取红包的用户
	8: optional string openTime,	//红包打开时间
	9: optional string createTime,	//创建时间
	10: optional string updateTime,	//更新时间
	11: optional i32 triggerWxuserId	//触发发送红包行为时的当前用户, 即 JD 页点击者或职位申请者 wx_group_user.id

}
