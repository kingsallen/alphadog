package com.moseeker.searchengine.service.impl.keywordSearch.fieldsearch;

import com.moseeker.common.util.StringUtils;
import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.List;
import java.util.Map;


public class TalentpoolSearchServiceImpl implements FieldSearchService {
    public SearchUtil searchUtil;
    public TalentpoolSearchServiceImpl(SearchUtil searchUtil){
        this.searchUtil=searchUtil;
    }
    @Override
    public QueryBuilder querySearch(KeywordSearchParams keywordSearchParams) {
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        String tagIds=keywordSearchParams.getTagIds();
        String hrId=keywordSearchParams.getHrId();
        String profilePoolId = keywordSearchParams.getProfilePoolId();
        if(StringUtils.isNotNullOrEmpty(profilePoolId)) {
            this.queryByProfilePoolId(profilePoolId,hrId,query);
            if(StringUtils.isNotNullOrEmpty(tagIds)){
                this.queryTagIds(searchUtil.stringConvertList(tagIds),query);
            }

        }
        String favoriteHrs=keywordSearchParams.getFavoriteHrs();
        String isPublic=keywordSearchParams.getIsPublic();
        if (StringUtils.isNullOrEmpty(tagIds)
                && StringUtils.isNullOrEmpty(favoriteHrs)
                && StringUtils.isNullOrEmpty(isPublic)
                && StringUtils.isNullOrEmpty(profilePoolId)) {
            return null;
        }
        String companyId=keywordSearchParams.getCompanyId();
        this.queryByNestCompanyId(Integer.parseInt(companyId),query);

        if(StringUtils.isNotNullOrEmpty(favoriteHrs)){
            this.queryTagHrId(favoriteHrs,query);
        }
        if(StringUtils.isNotNullOrEmpty(isPublic)){
            this.queryByPublic(Integer.parseInt(isPublic),query);
        }
        if(StringUtils.isNotNullOrEmpty(favoriteHrs)||StringUtils.isNotNullOrEmpty(isPublic)){
            this.queryByIstalent(query);
        }
        query=QueryBuilders.nestedQuery("user.talent_pool",query);
        return query;
    }


    private void queryByProfilePoolId(String profilePoolId,String hrId, QueryBuilder queryBuilder){
        if(StringUtils.isNotNullOrEmpty(profilePoolId)){
            List<String> profilePoolIdList=searchUtil.stringConvertList(profilePoolId);
            if(profilePoolIdList.contains("allpublic")){
                //查询自己的和公开的
                this.queryPublic(hrId,queryBuilder);
            }else if(profilePoolIdList.contains("talent")){
                //仅仅只查询自己的
                searchUtil.handleMatch(1,queryBuilder,"user.talent_pool.is_talent");
                searchUtil.handleTerms(hrId,queryBuilder,"user.talent_pool.hr_id");
            }else if(profilePoolIdList.contains("public")){
                searchUtil.handleMatch(1,queryBuilder,"user.talent_pool.is_public");
            }else if(profilePoolIdList.contains("hrpublic")){
                searchUtil.handleMatch(1,queryBuilder,"user.talent_pool.is_public");
                searchUtil.handleTerms(hrId,queryBuilder,"user.talent_pool.hr_id");
            }else{
                if(!profilePoolIdList.contains("alltalent")){
                    //查询简历池关键是权限，查询的是公开或者自己私有下的相关标签
                    this.queryPublic(hrId,queryBuilder);
                    searchUtil.handleTerm(profilePoolId,queryBuilder,"user.talent_pool.profile_pool_id");
                }
            }
        }

    }

    private void queryPublic(String hrId,QueryBuilder queryBuilder){
        QueryBuilder keyand = QueryBuilders.boolQuery();
        searchUtil.handlerShouldTerm("1",keyand,"user.talent_pool.is_public");
        searchUtil.shouldTermsQuery(searchUtil.stringConvertList(hrId),keyand,"user.talent_pool.hr_id");
        ((BoolQueryBuilder) queryBuilder).must(keyand);
    }

    //处理手动标签
    private void queryTagIds(List<String> tagIdList,QueryBuilder queryBuilder){
        searchUtil.handleTagMatch(tagIdList,queryBuilder,"user.talent_pool.tags.id");
    }

    /*
     构建和公司相关的人才库
     */
    private void queryByNestCompanyId(int companyId,QueryBuilder queryBuilder){
        searchUtil.handleMatch(companyId,queryBuilder,"user.talent_pool.company_id");
    }
    /*
         根据hr的标签查询
         */
    private void queryTagHrId(String hrIds,QueryBuilder queryBuilder){
        searchUtil.handleTerms(hrIds,queryBuilder,"user.talent_pool.hr_id");
    }

    /*
     构建是否公开的查询语句,注意这个位置要做成nest的查询
    */
    private void queryByPublic(int isPublic,QueryBuilder queryBuilder){
        searchUtil.handleMatch(isPublic,queryBuilder,"user.talent_pool.is_public");
    }

    /*
     按照是否是收藏的人才搜索
     */
    private void queryByIstalent(QueryBuilder queryBuilder){
        searchUtil.handleMatch(1,queryBuilder,"user.talent_pool.is_talent");
    }

}
