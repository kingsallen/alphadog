
namespace java com.moseeker.thrift.gen.config

struct ConfigSysPointsConfTpl{
 1: optional i32 id,
 2: optional i32 award,
 3: optional i32 disable,
 4: optional i32 priority,
 5: optional i32 type_id,
 6: optional i32 recruit_order
}
struct HrAwardConfigTemplate{
 1: optional i32 id,
 2: optional string status,
 3: optional i32 award,
 4: optional i32 disable,
 5: optional i32 priority,
 6: optional i32 type_id,
 7: optional i32 recruit_order,
 8: optional i64 reward,
 9: optional i32 points_conf_id
}

struct ConfigCustomMetaData {
     1: optional i32 id,
     2: optional string fieldName,
     3: optional string fieldTitle,
     4: optional i32 fieldType,
     5: optional i32 priority,
     6: optional i8 isBasic,
     7: optional i32 companyId,
     8: optional i8 needed,
     9: optional string fieldDescription,
     10: optional string mapping,
     11: optional i32 parentId,
     12: optional string validateRe,
     13: optional i32 constantParentCode,
     14: optional string constantValue
}