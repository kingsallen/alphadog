package com.moseeker.company.thrift;

import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.exception.Category;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.company.exception.ExceptionFactory;
import com.moseeker.company.service.impl.CompanyPcService;
import com.moseeker.company.service.impl.HrTeamServicesImpl;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.HrTeamServices;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangdi on 2017/5/15.
 */
@Service
public class HrTeamThriftServicesImpl implements HrTeamServices.Iface {


    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HrTeamServicesImpl hrTeamServices;
    @Autowired
    private CompanyPcService companyPcService;

    @Override
    public List<HrTeamDO> getHrTeams(CommonQuery query) throws TException {
        try {
            return hrTeamServices.getHrTeams(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
    }
    @Override
    public Response teamListInfo(int companyId,int page,int pageSize) throws BIZException, TException {
        try{
            Map<String,Object> map=companyPcService.getTeamListinfo(companyId, page, pageSize);
            return ResponseUtils.success(map);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }

    }

    @Override
    public Response teamDeatils(int companyId, int teamId) throws BIZException, TException {
        try{
            Map<String,Object> map=companyPcService.getTeamDetails(teamId,companyId);
            return ResponseUtils.success(map);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

}
