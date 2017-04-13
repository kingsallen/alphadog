package com.moseeker.common.providerutils;

import com.moseeker.thrift.gen.common.struct.CommonQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangdi on 2017/3/28.
 */
public class QueryDao {
    CommonQuery commonQuery;

    public CommonQuery getCommonQuery() {
        return commonQuery;
    }
}
