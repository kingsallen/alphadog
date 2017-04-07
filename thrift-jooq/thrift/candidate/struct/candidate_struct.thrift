# file: candidate_struct.thrift

namespace java com.moseeker.thrift.gen.candidate.struct

//推荐人列表接口中的推荐人信息
struct Candidate {
    1:  optional i32 id,                    //candidate_recom_record.id
    2:  optional i32 presenteeUserId,       //被动求职者编号
    3:  optional string presenteeName,      //被动求职者称呼
    4:  optional i32 presenteeFriendId,     //一度朋友编号
    5:  optional string presenteeFriendName,//一度朋友称呼
    6:  optional string presenteeLogo,      //头像
    7:  optional i32 isRecom                //推荐状态
}

//推荐人列表接口中的推荐人列表
struct CandidateList {
    1:  optional i32 positionId,            //职位编号
    2:  optional string positionName,       //职位名称
    3:  optional list<Candidate> candidates  //职位名称
}

//推荐人列表接口调用参数
struct CandidateListParam {
    1:  optional i32 postUserId,            //推荐者编号 user_user.id
    2:  optional string clickTime,          //点击职位信息的时间
    3:  optional list<i32> recoms,          //是否推荐
    4:  optional i32 companyId              //公司编号
}
