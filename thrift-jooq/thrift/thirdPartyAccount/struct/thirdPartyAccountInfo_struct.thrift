namespace java com.moseeker.thrift.gen.thirdpart.struct


struct ThirdPartyAccountInfo{
    3:optional list<ThirdPartyAccountInfoCity> city,
    4:optional list<ThirdPartyAccountInfoAddress> address,
    5:optional list<ThirdPartyAccountInfoCompany> company,
    6:optional list<ThirdPartyAccountInfoDepartment> department
}


struct ThirdPartyAccountInfoCity{
    1: optional i32 id,
    2: optional i32 code,
    3: optional string name,
    4: optional i8 jobType,	//1 社招， 2 校招
    5: optional i32 remainNum,	//可发布数
}

struct ThirdPartyAccountInfoAddress{
    1: optional i32 id,	//主键
    3: optional string city,	//上班城市
    4: optional string name,	//上班地址
}

struct ThirdPartyAccountInfoCompany{
    1: optional i32 id,	//主键
	3: optional string name,	//公司名称
}

struct ThirdPartyAccountInfoDepartment{
	1: optional i32 id,	//主键
	3: optional string name,	//部门名称
}