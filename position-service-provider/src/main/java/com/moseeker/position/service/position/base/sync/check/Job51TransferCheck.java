package com.moseeker.position.service.position.base.sync.check;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Job51TransferCheck extends AbstractTransferCheck<ThirdPartyPosition> {
    Logger logger = LoggerFactory.getLogger(Job51TransferCheck.class);

    CompanyServices.Iface companyServices = ServiceManager.SERVICE_MANAGER.getService(CompanyServices.Iface.class);

    private static String SALARY_MUST_LT_ZERO = "薪资必须大于零!";

    @Override
    public Class<ThirdPartyPosition> getFormClass() {
        return ThirdPartyPosition.class;
    }

    @Override
    public List<String> getError(ThirdPartyPosition thirdPartyPosition, JobPositionDO moseekerPosition){
        List<String> errorMsg=new ArrayList<>();

        HrCompanyConfDO companyConfDO = null;
        try {
            companyConfDO = companyServices.getCompanyConfById(moseekerPosition.getCompanyId());
        } catch (BIZException e) {
            logger.error("Job51 transfer check get company_conf bizerror positionId: " + moseekerPosition.getId(),e);
            errorMsg.add(e.getMessage());
            return errorMsg;
        } catch (TException e) {
            logger.error("Job51 transfer check get company_conf error positionId: " + moseekerPosition.getId(),e);
            errorMsg.add(CHECK_ERROR);
            return errorMsg;
        }

        // 薪资上下限必须都不为0
        if((companyConfDO == null || companyConfDO.getJob51SalaryDiscuss() == 0) &&
                (thirdPartyPosition.getSalaryTop() == 0 || thirdPartyPosition.getSalaryBottom() == 0)){
            errorMsg.add(SALARY_MUST_LT_ZERO);
        }

        return errorMsg;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB51;
    }
}
