# file: jobdb_struct 

namespace java com.moseeker.thrift.gen.dao.struct

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
typedef string Timestamp;

struct JobApplicationPojo {
    1: optional i32 id,                 //编号 唯一标识
    2: optional i32 wechatId,           //微信公众号编号
    3: optional i32 positionId,         //职位编号
    4: optional i32 recommendUserId,    //推荐用户编号user_user.id
    5: optional Timestamp submitTime,   //申请时间
    6: optional i32 statusId,           //申请状态ID（可能已经废弃）
    7: optional i32 lApplicationId,     //ATS的申请ID
    8: optional i32 reward,             //当前申请的积分记录
    9: optional i32 sourceId,           //对应的ATS ID
    10: optional Timestamp createTime,  //创建时间
    11: optional i32 applierId,         //申请人对应的userdb.user_user.id
    12: optional i32 interviewId,       //面试ID（可能已经废弃）
    13: optional string resumeId,       //对应简历数据的编号（mongodb application.id 可能已经废弃）
    14: optional i32 atsStatus,         //ats对接状态
    15: optional string applierName,    //申请者姓名或者微信昵称
    16: optional i32 disable,           //是否有效 0 :有效 1：无效
    17: optional i32 routine,           //申请来源:0 微信端企业号，1：微信端聚合号，10 PC端
    18: optional i32 isViewed,          //申请是否被浏览0 已浏览 1 未浏览
    19: optional i32 notSuitable,       //是否不合适 0 合适 1 不合适
    20: optional i32 companyId,         //公司编号hrdb.hr_company.id
    21: optional Timestamp updateTime,  //修改时间
    22: optional i32 appTplId,          //招聘进度的状态 configdb.config_sys_points_conf_tpl.id
    23: optional i8 proxy,              //是否代理投递 0 正常数据，1代理投递
    24: optional i8 applyType,          //投递区分 0 profile投递，1 email投递 
    25: optional i8 emailStatus,        //email投递状态 0，有效；1,未收到回复邮件；2，文件格式不支持；3，附件超过10M；9，提取邮件失败
    26: optional i32 viewCount          //profile浏览次数
}

struct JobPositionPojo {
    1: optional i32 id,                     //数据库标志
    2: optional string jobnumber,           //用户上传的职位编号
    3: optional i32 companyId,              //职位所属公司编号hrdb.hr_company.id
    4: optional string title,               // 职位标题
    5: optional string city,                //职位指定的城市信息（真正的城市数据在职位城市关系表中）
    6: optional string department,          //职位指定的所属部门
    7: optional i32 lJobid,                 //ATS对接的其他平台的职位标志
    8: optional string publishDate,         //发布日期
    9: optional string stopDate,            // 截止日期（新功能的职位下线是按照职位状态，并且也兼容这个字段）
    10: optional string accountabilities,   // 职位描述
    11: optional string experience,         // 经验要求
    12: optional string requirement,        // 任职条件
}
