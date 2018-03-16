package com.moseeker.useraccounts.service.thirdpartyaccount.info;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.baseorm.dao.thirdpartydb.*;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.DictCityMap;
import com.moseeker.baseorm.db.thirdpartydb.tables.*;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.pojos.ThirdPartyInfoData;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.*;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbstractInfoHandler;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 第三方账号实体
 * Created by jack on 27/09/2017.
 */
@Component
public class ThridPartyAcountEntity {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    List<AbstractInfoHandler> list;

    /**
     * 添加第三方渠道账号的扩展信息
     * @param data
     * @param accountDO
     * @throws CommonException
     */
    @Transactional
    public void saveAccountExt(ThirdPartyInfoData data, HrThirdPartyAccountDO accountDO) throws CommonException {
        logger.info("saveAccountExt data:{}", JSON.toJSONString(data));

        list.forEach(e->e.handler(data));
    }
}
