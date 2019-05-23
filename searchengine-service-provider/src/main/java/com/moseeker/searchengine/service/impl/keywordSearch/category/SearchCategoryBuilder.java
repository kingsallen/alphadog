package com.moseeker.searchengine.service.impl.keywordSearch.category;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearTypeEnum;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;


public class SearchCategoryBuilder implements SearchCategory {
    private SearchUtil searchUtil;
    public  SearchCategoryBuilder(){

    }
    @Override
    public int getSearchCataGoery(KeywordSearchParams keywordSearchParams, TransportClient client){
        ValidateCitySearch validateCitySearch=new ValidateCitySearch();
        boolean isCity=validateCitySearch.getValidateSearch(keywordSearchParams,client);
        if(isCity){
            return SearTypeEnum.SEARCH_CITY.getValue();
        }
        ValidatePositionSearch validatePositionSearch=new ValidatePositionSearch();
        boolean isPosition=validatePositionSearch.getValidateSearch(keywordSearchParams,client);
        if(isPosition){
            return SearTypeEnum.SEARCH_POSITION.getValue();
        }
        ValidateCompanySearch validateCompanySearch=new ValidateCompanySearch();
        boolean isCompany=validateCompanySearch.getValidateSearch(keywordSearchParams,client);
        if(isCompany){
            return SearTypeEnum.SEARCH_COMAPNY.getValue();
        }
        ValidateNameSearch validateNameSearch=new ValidateNameSearch();
        boolean isName=validateNameSearch.getValidateSearch(keywordSearchParams,client);
        if(isName){
            return SearTypeEnum.SEARCH_NAME.getValue();
        }
        return -1;
    }
}
