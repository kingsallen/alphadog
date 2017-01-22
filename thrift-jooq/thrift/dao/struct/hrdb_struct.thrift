# file: hrdb_struct

namespace java com.moseeker.thrift.gen.dao.struct

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
typedef string Timestamp

struct HrHbConfigPojo {
    1:  optional i32 id,
    2:  optional i32 type,
    3:  optional i32 target,
    4:  optional i32 company_id,
    5:  optional Timestamp start_time,
    6:  optional Timestamp end_time,
    7:  optional i32 total_amount,
    8:  optional double range_min,
    9:  optional double range_max,
    10: optional i32 probability,
    11: optional i32 d_type,
    12: optional string headline,
    13: optional string headline_failure,
    14: optional string share_title,
    15: optional string share_desc,
    16: optional string share_img,
    17: optional i32 status,
    18: optional i32 checked,
    19: optional i32 estimated_total,
    20: optional Timestamp create_time,
    21: optional Timestamp update_time,
    22: optional i32 actual_total
}

struct HrHbPositionBindingPojo {
    1: optional i32 id,
    2: optional i32 hb_config_id,
    3: optional i32 position_id,
    4: optional i32 trigger_way,
    5: optional double total_amount,
    6: optional i32 total_num,
    7: optional Timestamp create_time,
    8: optional Timestamp update_time
}

struct HrHbItemsPojo {
    1:  optional i32 id,
    2:  optional i32 hb_config_id,
    3:  optional i32 binding_id,
    4:  optional i32 index,
    5:  optional double amount,
    6:  optional i32 status,
    7:  optional i32 wxuser_id,
    8:  optional Timestamp open_time,
    9:  optional Timestamp create_time,
    10: optional Timestamp update_time,
    11: optional i32 trigger_wxuser_id
}

struct HrHbScratchCardPojo {
    1:  optional i32 id,
    2:  optional i32 wechat_id,
    3:  optional string cardno,
    4:  optional i32 status,
    5:  optional double amount,
    6:  optional i32 hb_config_id,
    7:  optional string bagging_openid,
    8:  optional Timestamp create_time,
    9:  optional i32 hb_item_id,
    10: optional i32 tips
}

struct HrHbSendRecordPojo {
    1:  optional i32 id,
    2:  optional string return_code,
    3:  optional string return_msg,
    4:  optional string sign,
    5:  optional string resule_code,
    6:  optional string err_code,
    7:  optional string err_code_des,
    8:  optional string mch_billno,
    9:  optional string mch_id,
    10: optional string wxappid,
    11: optional string re_openid,
    12: optional string total_amount,
    13: optional string send_time,
    14: optional string send_listid,
    15: optional Timestamp create_time,
    16: optional i32 hb_item_id
}