package com.moseeker.company.thrift;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.Category;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.company.exception.ExceptionFactory;
import com.moseeker.company.service.impl.CompanyPcService;
import com.moseeker.company.service.impl.CompanyService;
import com.moseeker.company.service.impl.TalentPoolService;
import com.moseeker.entity.CompanyConfigEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.company.service.TalentpoolNewServices.Iface;
import com.moseeker.thrift.gen.company.struct.*;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrImporterMonitorDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.employee.struct.RewardConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TalentpoolNewThriftServiceImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public TalentpoolNewThriftServiceImpl(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @Autowired
    private TalentPoolService service;


    @Override
    public Response addProfileContent(int userId, int accountId, String content) throws BIZException, TException {
        return service.addProfileComment(userId, accountId, content);
    }
}

