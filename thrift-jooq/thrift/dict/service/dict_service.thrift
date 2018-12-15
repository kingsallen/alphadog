include "../struct/dict_struct.thrift"
include "../../dao/struct/db/dictdb_struct.thrift"
include "../../dao/struct/dictdb/dict_referral_evaluate_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.dict.service

service CityServices {
    common_struct.Response getAllCities(1:i32 level);
    common_struct.Response getCitiesById(1:i32 id);
    common_struct.Response getAllCitiesByLevelOrUsing(1:string level, 2:i32 is_using, 3:i32 hot_city);
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getProvinceAndCity();
    common_struct.Response getCityByProvince(1:list<i32> codeList);
}

service CollegeServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getCollegeByDomestic();
    common_struct.Response getCollegeByAbroad(1:i32 countryCode);
}

/*
  获取国家字典数据服务
*/
service DictCountryService {
    common_struct.Response getDictCountry(1:common_struct.CommonQuery query);
}

/*
  获取常量字典json数据服务
*/
service DictConstanService {
    common_struct.Response getDictConstantJsonByParentCode(1:list<i32> parentCodeList);
}

/*
  行业服务接口
*/
service IndustryService {
    common_struct.Response getIndustriesByCode(1: string code);
}

/*
  职能服务接口
*/
service PositionService {
   common_struct.Response getPositionsByCode(1:string code);
}
/*
	orm层第三方职位职能查询
*/
service DictOccupationDao{
	common_struct.Response getOccupations51();
	common_struct.Response getOccupationsZPin();
	common_struct.Response getOccupation51(1:common_struct.CommonQuery query);
	common_struct.Response getOccupationZPin(1:common_struct.CommonQuery query);
    dictdb_struct.DictCityDO dictCityDO(1:common_struct.CommonQuery query);

}
/*
 service层第三方职位职能查询
*/
service DictOccupationService{
	common_struct.Response getDictOccupation(1:string param);
}

/*
 service层第三方职位职能查询
*/
service DictReferralEvaluateService{
	list<dict_referral_evaluate_struct.DictReferralEvaluateDO> getDictReferralEvalute(1: i32 code);
}
