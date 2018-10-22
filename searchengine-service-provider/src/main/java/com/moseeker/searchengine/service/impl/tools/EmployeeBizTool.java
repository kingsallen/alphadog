package com.moseeker.searchengine.service.impl.tools;

import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.searchengine.SearchEngineException;
import com.moseeker.searchengine.util.SearchUtil;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Author: jack
 * @Date: 2018/9/26
 */
public class EmployeeBizTool {

    private static Logger logger = LoggerFactory.getLogger(EmployeeBizTool.class);
    /**
     *  解析员工类型参数
     * @param defaultquery 查询工具
     * @param filter 过滤条件，0：全部，1：已认证，2：未认证， 3 撤销认证,默认：0
     * @param searchUtil ES查询条件生成工具
     */
    public static void addFilter(QueryBuilder defaultquery, int filter, SearchUtil searchUtil) {

        if (filter > 0) {
            String activation = "";
            if (filter == 1) {
                activation = String.valueOf(EmployeeActiveState.Actived.getState());
            } else if (filter == 2) {
                activation = String.valueOf(EmployeeActiveState.Init.getState());
            } else if ((filter == 3)) {
                activation = EmployeeActiveState.Cancel.getState()+", "+
                        EmployeeActiveState.Failure.getState()+", "+
                        EmployeeActiveState.MigrateToOtherCompany.getState()+", "+
                        EmployeeActiveState.UnFollow.getState();
            }
            if (StringUtils.isNotBlank(activation)) {
                searchUtil.handleTerms(activation, defaultquery, "activation");
            }
        }
    }

    public static void addNotEmployeeIds(QueryBuilder defaultquery, List<Integer> employeeIds, SearchUtil searchUtil){
        if(!com.moseeker.common.util.StringUtils.isEmptyList(employeeIds)){
            searchUtil.handlerNotTerms(employeeIds, defaultquery, "id");
        }
    }

    /**
     * 解析公司参数
     * @param defaultquery 查询工具
     * @param companyIds 公司编号集合
     * @param searchUtil ES查询条件生成工具
     */
    public static void addCompanyIds(QueryBuilder defaultquery, List<Integer> companyIds, SearchUtil searchUtil) {
        searchUtil.handleTerms(Arrays.toString(companyIds.toArray()).replaceAll("\\[|\\]| ", ""), defaultquery, "company_id");
    }

    /**
     * 解析关键词
     * @param defaultquery 查询工具
     * @param keyword 关键词
     * @param searchUtil ES查询帮助类
     */
    public static void addKeywords(QueryBuilder defaultquery, String keyword, SearchUtil searchUtil) {
        if (StringUtils.isNotBlank(keyword)) {
            /*searchUtil.shouldMatchQuery(
                    new ArrayList<String>(){{
                        add("email");add("mobile");add("nickname");add("custom_field");add("cname");
                    }},
                    keyword,
                    defaultquery);*/
            searchUtil.matchPhrasePrefixQuery(new ArrayList<String>(){{
                                                 add("email");add("mobile");add("nickname");add("custom_field");add("cname");
                                             }},
                    keyword,
                    defaultquery);
        }
    }

    /**
     * 增加邮箱认证状态查询条件
     * @param defaultquery 查询工具
     * @param emailValidate 邮箱是否认证
     * @param searchUtil ES查询帮助类
     */
    public static void addEmailValidate(QueryBuilder defaultquery, String emailValidate, SearchUtil searchUtil) {
        if (StringUtils.isNotBlank(emailValidate)) {
            searchUtil.handleTerm(emailValidate, defaultquery, UserEmployee.USER_EMPLOYEE.EMAIL_ISVALID.getName());
        }
    }

    /**
     * 增加排序
     * @param searchRequestBuilder 查询工具
     * @param order 排序字段，多个字段使用","隔开
     * @param asc 升序降序字段，多个使用","隔开
     * @throws SearchEngineException
     */
    public static void addOrder(SearchRequestBuilder searchRequestBuilder, String order, String asc) throws SearchEngineException {
        if (StringUtils.isNotBlank(order)) {
            // 多个条件
            if (order.indexOf(",") > -1 && asc.indexOf(",") > -1) {
                String[] orders = order.split(",");
                String[] ascs = asc.split(",");
                // 排序条件设置错误
                if (orders.length != ascs.length) {
                    throw SearchEngineException.PROGRAM_PARAM_NOTEXIST;
                }

                for (int i = 0; i < orders.length; i++) {
                    // 首先判断排序的条件是否正确
                    if (UserEmployee.USER_EMPLOYEE.field(orders[i]) != null) {
                        if (Integer.valueOf(ascs[i]).intValue() == 1) {   //升序
                            searchRequestBuilder.addSort(UserEmployee.USER_EMPLOYEE.field(orders[i]).getName(), SortOrder.ASC);
                        } else if (Integer.valueOf(ascs[i]).intValue() == 0) {// 倒序
                            searchRequestBuilder.addSort(UserEmployee.USER_EMPLOYEE.field(orders[i]).getName(), SortOrder.DESC);
                        }
                    }
                }
            } else {
                // 首先判断排序的条件是否正确
                if (UserEmployee.USER_EMPLOYEE.field(order) != null) {
                    if (Integer.valueOf(asc).intValue() == 0) {  // 倒序
                        searchRequestBuilder.addSort(UserEmployee.USER_EMPLOYEE.field(order).getName(), SortOrder.DESC);
                    } else if (Integer.valueOf(asc).intValue() == 1) { //升序
                        searchRequestBuilder.addSort(UserEmployee.USER_EMPLOYEE.field(order).getName(), SortOrder.ASC);
                    }
                }
            }

        }
    }

    /**
     * 设置分页
     * @param searchRequestBuilder 查询工具
     * @param pageNumber 页码
     * @param pageSize 分页
     */
    public static void addPagination(SearchRequestBuilder searchRequestBuilder, int pageNumber, int pageSize) {
        if (pageNumber <= 0) {
            pageNumber = 1;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        searchRequestBuilder.setFrom((pageNumber-1)*pageSize).setSize(pageSize);
    }

    /**
     * balanceType =2 返回员工奖金大于0的记录
     * @param defaultquery
     * @param balanceType
     * @param searchUtil
     */
    public static void addBalanceTypeFilter(QueryBuilder defaultquery, Integer balanceType, SearchUtil searchUtil) {
        if (balanceType !=null && balanceType == 2) {
            searchUtil.hanleRange(0,defaultquery,"bonus");
        }
    }
}
