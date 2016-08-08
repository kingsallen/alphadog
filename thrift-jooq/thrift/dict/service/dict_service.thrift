include "../struct/dict_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.dict.service

service CityServices {
    common_struct.Response getAllCities(1:i32 level);
    common_struct.Response getCitiesById(1:i32 id);
    common_struct.Response getResources(1:common_struct.CommonQuery query);
}

service CollegeServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
}

/*
  获取国家字典数据服务
*/
service DictCountryService {
    common_struct.Response getDictCountry();
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
