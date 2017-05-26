namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileProfileDO {

	1: optional i32 id,	//主key
	2: optional string uuid,	//profile的uuid标识,与主键一一对应
	3: optional i8 lang,	//profile语言 1:chinese 2:english
	4: optional i32 source,	//Profile的创建来源, 0:未知, 或者mongo合并来的 1:微信企业端(正常), 2:微信企业端(我要投递), 3:微信企业端(我感兴趣), 4:微信聚合端(正常), 5:微信聚合端(我要投递), 6:微信聚合端(我感兴趣), 100:微信企业端Email申请, 101:微信聚合端Email申请, 150:微信企业端导入, 151:微信聚合端导入, 152:PC导入, 200:PC(正常添加) 201:PC(我要投递) 202: PC(我感兴趣)
	5: optional i8 completeness,	//Profile完整度
	6: optional i32 userId,	//用户ID
	7: optional i8 disable,	//是否有效，0：无效 1：有效
	8: optional string createTime,	//创建时间
	9: optional string updateTime,	//更新时间
	10: optional string origin	//简历来源二进制数值,1表示维系企业端（正常），10表示微信企业端(我要投递)，100表示微信企业端(我感兴趣)，1000表示微信企业端(我感兴趣)，10000表示微信聚合端(正常)，100000表示微信聚合端(我要投递)，1000000表示微信聚合端(我感兴趣)，1000000表示微信企业端Email申请，100000000表示微信聚合端Email申请，1000000000表示微信企业端导入，10000000000表示微信聚合端导入，10000000000表示PC导入，100000000000表示PC(正常添加)，1000000000000表示PC(我要投递)，10000000000000表示PC(我感兴趣)，1000000000000000表示51job，10000000000000000表示智联

}
