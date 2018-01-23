namespace java com.moseeker.thrift.gen.dao.struct.thirdpartydb
namespace py thrift_gen.gen.dao.struct.thirdpartydb


struct ThirdpartyVeryEastPositionDO {

	1: optional i32 id,	//主键
	2: optional i32 pid,	//关联职位
	3: optional i32 accommodation,	//提供食宿
	4: optional i32 age_top,	//年龄上限
	5: optional i32 age_bottom,	//年龄下限
	6: optional i32 languageType1,	//语言能力类型1
	7: optional i32 languageLevel1,	//语言能力等级1
	8: optional i32 languageType2,	//语言能力类型2
	9: optional i32 languageLevel2,	//语言能力等级2
	10: optional i32 languageType3,	//语言能力类型3
	11: optional i32 languageLevel3,	//语言能力等级3
    12: optional i32 computerLevel,	//计算机能力
    13: optional i32 indate,	//有效期
	14: optional i8 status,	//只能状态 0 是有效 1是无效
	15: optional string createTime,	//创建时间

}
