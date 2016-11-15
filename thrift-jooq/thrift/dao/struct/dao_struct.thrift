# file: profile.thrift

namespace java com.moseeker.thrift.gen.dao.struct

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
typedef string Timestamp;

struct WordpressPosts { 
    1: i64 id,
    2: i64 postAuthor,
    3: string postDate,
    4: string postContent,
    5: string postTitle,
    6: string postExcerpt,
    7: string postStatus,
    8: string postModified,
    9: string version,
    10: string plateform,
    11: string postName
}

struct WordpressTermRelationships { 
    1: i64 objectId,
    2: i64 termTaxonomyId
}

struct PostExt { 
    1: i64 objectId,
    2: string version,
    3: string platform
}

//第三方帐号
struct ThirdPartAccountData{
        1: i32 id,
        2: string name,
        3: i32 channel,
        4: string username,
        5: string password,
        6: string membername,
        7: i32 binding,
        8: i32 company_id,
        9: i32 remain_num,
        10: Timestamp sync_time
}
