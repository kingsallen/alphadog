package com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword;

import com.moseeker.common.util.StringUtils;
import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.service.impl.keywordSearch.category.SearchCategoryBuilder;
import com.moseeker.searchengine.util.SearTypeEnum;

public class KeywordSearchFactory {

 private KeywordSearch factory;

 public void search(KeywordSearchParams keywordSearchParams){
       SearchCategoryBuilder searchCategoryBuilder =new SearchCategoryBuilder();
       int flag= searchCategoryBuilder.getSearchCataGoery(keywordSearchParams);

       if(flag== SearTypeEnum.SEARCH_CITY.getValue()){
          factory=new CitySearchBuilder();
       }else if(flag== SearTypeEnum.SEARCH_POSITION.getValue()){
          factory=new PositionSearchBuilder();
       }else if(flag== SearTypeEnum.SEARCH_COMAPNY.getValue()){
          factory=new CompanySearchBuilder();
       }else if(flag== SearTypeEnum.SEARCH_NAME.getValue()){
          factory=new NameSearchBuilder();
       }
       factory.queryNewKeyWords(keywordSearchParams);
 }



}
