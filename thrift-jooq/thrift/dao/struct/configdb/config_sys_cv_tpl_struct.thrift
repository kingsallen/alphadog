namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigSysCvTplDO {

	1: optional i32 id,	//null
	2: optional string fieldName,	//属性含义
	3: optional string fieldTitle,	//属性标题
	4: optional i32 fieldType,	//属性类型 0:短文本, 1:长文本,  2:多选, 3:单选, 4:长tag, 5:tag:, 6:时间, 7:img, 8:复合字段-单条, 9:复合字段-多条,10:下拉列表, 11:数字输入框(用于校验) 12:城市选择控件 13:英文文本
	5: optional string fieldValue,	//微信端页面标签默认值
	6: optional i32 priority,	//排序字段
	7: optional i32 isBasic,	//属性类型 0：常用字段 1：校招常用 2：蓝领常用
	8: optional string createTime,	//null
	9: optional string updateTime,	//null
	10: optional i32 disable,	//是否禁用 0：可用1：不可用
	11: optional i32 companyId,	//所属公司。如果是私有属性，则存在所属公司部门编号；如果不是则为0
	12: optional i32 needed,	//是否必填项 0：必填项 1：选填项
	13: optional string fieldDescription,	//雇主平台页面显示值
	14: optional string map	//与profile的映射关系

}
