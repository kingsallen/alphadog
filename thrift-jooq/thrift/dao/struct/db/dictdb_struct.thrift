namespace java com.moseeker.thrift.gen.dao.struct.dictdb
namespace py thrift_gen.gen.dao.struct.dictdb


struct Dict51jobOccupationDO {

	1: optional i32 code,	//职能id
	2: optional i32 parentId,	//父Id，上一级职能的ID
	3: optional string name,	//职能名称
	4: optional i32 codeOther,	//null
	5: optional i32 level,	//职能级别 1是一级2是
	6: optional i32 status,	//只能状态 0 是无效 1是有效
	7: optional string createtime	//创建时间

}


struct DictCityDO {

	1: optional i32 code,	//字典code
	2: optional string name,	//字典name
	3: optional i8 level,	//字典level
	4: optional i8 hotCity,	//热门城市 0:否 1：是
	5: optional string ename,	//英文名称
	6: optional i8 isUsing	//正在使用 0:没在用 1:在使用

}


struct DictCityMapDO {

	1: optional i32 id,	//null
	2: optional i32 code,	//null
	3: optional i32 codeOther,	//null
	4: optional i32 channel,	//null
	5: optional i32 status,	//null
	6: optional string createTime	//null

}


struct DictCollegeDO {

	1: optional i32 code,	//字典code
	2: optional string name,	//字典name
	3: optional i32 province,	//院校所在地
	4: optional string logo	//院校logo

}


struct DictConstantDO {

	1: optional i32 id,	//主key
	2: optional i32 code,	//字典code
	3: optional string name,	//字典name
	4: optional i8 priority,	//优先级
	5: optional i32 parentCode,	//父级字典code
	6: optional string createTime,	//null
	7: optional string updateTime	//null

}


struct DictCountryDO {

	1: optional i32 id,	//主key
	2: optional string name,	//国家中文名称
	3: optional string ename,	//国家英文名称
	4: optional string isoCode2,	//iso_code_2
	5: optional string isoCode3,	//iso_code_3
	6: optional string code,	//COUNTRY CODE
	7: optional string iconClass,	//国旗样式
	8: optional i8 hotCountry,	//热门国家 0=否 1=是
	9: optional i32 continentCode	//7大洲code, dict_constant.parent_code: 9103

}


struct DictIndustryDO {

	1: optional i32 code,	//字典code
	2: optional string name,	//字典name
	3: optional i32 type	//字典分类code

}


struct DictIndustryTypeDO {

	1: optional i32 code,	//字典code
	2: optional string name	//字典name

}


struct DictMajorDO {

	1: optional string code,	//字典code
	2: optional string name,	//字典name
	3: optional i8 level	//字典level

}


struct DictPositionDO {

	1: optional i32 code,	//字典code
	2: optional string name,	//字典name
	3: optional i32 parent,	//父编码
	4: optional i8 level	//字典level

}


struct DictZhilianOccupationDO {

	1: optional i32 code,	//职能id
	2: optional i32 parentId,	//父Id，上一级职能的ID
	3: optional string name,	//职能名称
	4: optional i32 codeOther,	//null
	5: optional i32 level,	//职能级别 1是一级2是
	6: optional i32 status,	//只能状态 0 是无效 1是有效
	7: optional string createtime	//创建时间

}


