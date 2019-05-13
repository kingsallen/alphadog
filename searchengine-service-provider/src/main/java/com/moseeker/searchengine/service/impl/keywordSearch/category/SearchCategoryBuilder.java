package com.moseeker.searchengine.service.impl.keywordSearch.category;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearTypeEnum;



public class SearchCategoryBuilder implements SearchCategory {
    @Override
    public int getSearchCataGoery(KeywordSearchParams keywordSearchParams){
        ValidateCitySearch validateCitySearch=new ValidateCitySearch();
        boolean isCity=validateCitySearch.getValidateSearch(keywordSearchParams);
        if(isCity){
            return SearTypeEnum.SEARCH_CITY.getValue();
        }
        ValidatePositionSearch validatePositionSearch=new ValidatePositionSearch();
        boolean isPosition=validatePositionSearch.getValidateSearch(keywordSearchParams);
        if(isPosition){
            return SearTypeEnum.SEARCH_POSITION.getValue();
        }
        ValidateCompanySearch validateCompanySearch=new ValidateCompanySearch();
        boolean isCompany=validateCompanySearch.getValidateSearch(keywordSearchParams);
        if(isCompany){
            return SearTypeEnum.SEARCH_COMAPNY.getValue();
        }
        ValidateNameSearch validateNameSearch=new ValidateNameSearch();
        boolean isName=validateNameSearch.getValidateSearch(keywordSearchParams);
        if(isName){
            return SearTypeEnum.SEARCH_NAME.getValue();
        }
        return -1;
    }
}
