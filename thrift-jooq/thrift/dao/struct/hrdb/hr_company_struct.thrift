namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrCompanyDO {

	1: optional i32 id,	//null
	2: optional i8 type,	//公司区分(其它:2,免费用户:1,企业用户:0)
	3: optional string name,	//公司注册名称
	4: optional string introduction,	//公司介绍
	5: optional i8 scale,	//公司规模, dict_constant.parent_code=1102
	6: optional string address,	//公司地址
	7: optional i8 property,	//公司性质 0:未填写 1:外商独资 3:国企 4:合资 5:民营公司 6:事业单位 7:上市公司 8:政府机关/非盈利机构 10:代表处 11:股份制企业 12:创业公司 13:其它
	8: optional string industry,	//所属行业
	9: optional string homepage,	//公司主页
	10: optional string logo,	//公司logo的网络cdn地址
	11: optional string abbreviation,	//公司简称
	12: optional string impression,	//json格式的企业印象
	13: optional string banner,	//json格式的企业banner
	14: optional i32 parentId,	//上级公司
	15: optional i32 hraccountId,	//公司联系人, hr_account.id
	16: optional i8 disable,	//0:无效 1:有效, 删除子公司使用， 母公司目前没有禁用功能
	17: optional string createTime,	//创建时间
	18: optional string updateTime,	//更新时间
	19: optional i8 source,	//添加来源 0:hr系统, 1:官网下载行业报告, 6:无线官网添加, 7:PC端 添加, 8:微信端添加, 9:PC导入, 10:微信端导入
	20: optional string slogan,	//公司口号
	21: optional string feature,	//公司福利特色， 由公司下的职位的福利特色每天跑脚本合并而来，目前供支付宝使用
	22: optional i8 isTop500	//是否世界500强，0：不是 1：是

}

