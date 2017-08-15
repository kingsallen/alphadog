namespace java com.moseeker.thrift.gen.dao.struct


struct DictAlipaycampusJobcategoryDO {

	1: optional i32 id,	//null
	2: optional string code,	//code
	3: optional string parentCode,	//上级code
	4: optional string name,	//名称
	5: optional i32 level,	//层级
	6: optional i32 isHot,	//是否热门，1热门
	7: optional i32 sort,	//排序
	8: optional i32 status,	//0删除1正常
	9: optional string createTime,	//null
	10: optional string updateTime	//null

}
