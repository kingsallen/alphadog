namespace java com.moseeker.thrift.gen.apps.positionbs.struct

struct ScraperHtmlParam{

    1:required string url,      //要访问的url
    2:required i32 positionId,  //职位ID
    3:required i32 channel      //渠道号
}

