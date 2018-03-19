# file: profile_service.thrift

include "../../common/struct/common_struct.thrift"
include "../struct/appbs_struct.thrift"
include "../struct/third_party_struct.thrift"
namespace java com.moseeker.thrift.gen.apps.positionbs.service

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
 
service PositionBS {
   	//职位同步
	common_struct.Response synchronizePositionToThirdPartyPlatform(1: appbs_struct.ThirdPartyPositionForm position) throws (1: common_struct.BIZException e);
	//刷新职位
	common_struct.Response refreshPositionToThirdPartyPlatform(1: i32 positionId, 2:i32 channel) throws (1: common_struct.BIZException e);
	//刷新职位，千寻平台
	common_struct.Response refreshPositionQXPlatform(1: list<i32> positionIds) throws (1: common_struct.BIZException e);
	//刷新第三方信息
	common_struct.Response refreshThirdPartyParam(1:i32 channel) throws (1: common_struct.BIZException e);
	//发送第三方需要的验证信息
    common_struct.Response syncVerifyInfo(1: string info) throws (1: common_struct.BIZException e);
    //获取缓存验证信息
    common_struct.Response getVerifyParam(1: string key) throws (1: common_struct.BIZException e);
    // 获取简历html
    string getThirdPartyHtml(1:third_party_struct.ScraperHtmlParam param) throws (1: common_struct.BIZException e);
}

