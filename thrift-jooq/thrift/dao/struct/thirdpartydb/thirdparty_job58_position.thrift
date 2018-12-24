namespace java com.moseeker.thrift.gen.dao.struct.thirdpartydb
namespace py thrift_gen.gen.dao.struct.thirdpartydb


struct ThirdpartyJob58PositionDO {

	1: optional i32 id,	//主键
	2: optional i32 pid,	//关联职位
	3: optional i32 addressId,	//工作地址
	4: optional string addressName,	//工作地址名称
	5: optional i32 occupation,	//职能
	6: optional string features,	//福利特色json串
	7: optional i32 salaryTop,	//薪资上限
	8: optional i32 salaryBottom,  //薪资下限
	9: optional i8 salaryDiscuss,    //是否面议 0 否1 是
	10: optional i8 freshGraduate,	//是否接受应届生
	11: optional i8 showContact,   // 是否显示联系方式
	12: optional i8 status,	//只能状态 0 是有效 1是无效
	13: optional string createTime,	//创建时间
	14: optional string updateTime	//更新时间

}
