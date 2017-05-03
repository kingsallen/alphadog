package com.moseeker.baseorm.tool;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.CommonQuery;

/**
 * Created by jack on 03/05/2017.
 */
public class QueryConvert {

    public static Query commonQueryConvertToQuery(CommonQuery commonQuery) {
        if (commonQuery != null) {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();

            if (commonQuery.getAttributes() != null) {
                commonQuery.getAttributes().forEach(attribute -> {
                    queryBuilder.select(attribute);
                });
            }

            if (commonQuery.getEqualFilter() != null && commonQuery.getEqualFilter().size() > 0) {
                commonQuery.getEqualFilter().forEach((key, value) -> {
                    queryBuilder.and(key, value);
                });
            }

            if (commonQuery.getGrouops() != null && commonQuery.getGrouops().size() > 0) {
                commonQuery.getGrouops().forEach(group -> {
                    queryBuilder.groupBy(group);
                });
            }

            if (StringUtils.isNotNullOrEmpty(commonQuery.getSortby())) {
                String[] sortByArray = commonQuery.getSortby().split(",");
                String[] orderArray = null;
                if (commonQuery.getOrder() != null) {
                    orderArray = commonQuery.getSortby().split(",");
                }
                for (int i=0; i<sortByArray.length; i++) {
                    if (StringUtils.isNullOrEmpty(sortByArray[i])) {
                        continue;
                    }
                    Order order = Order.ASC;
                    if (orderArray.length > i && orderArray[i].toLowerCase().equals("desc")) {
                        order = Order.DESC;
                    }
                    queryBuilder.orderBy(sortByArray[i].trim(), order);
                }
            }

            queryBuilder.setPageSize(commonQuery.getPer_page());
            queryBuilder.setPageNum(commonQuery.getPage());

            return queryBuilder.buildQuery();
        }
        return null;
    }
}
