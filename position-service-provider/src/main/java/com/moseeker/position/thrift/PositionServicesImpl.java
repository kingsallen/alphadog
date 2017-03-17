package com.moseeker.position.thrift;

import java.util.List;

import com.moseeker.thrift.gen.position.struct.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.position.service.JobOccupationService;
import com.moseeker.position.service.fundationbs.PositionService;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.position.service.PositionServices.Iface;

@Service
public class PositionServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PositionService service;
    @Autowired
    private JobOccupationService customService;

    /**
     * 获取推荐职位
     * <p></p>
     *
     * @param pid
     * @return
     */
    @Override
    public Response getRecommendedPositions(int pid) {
        return service.getRecommendedPositions(pid);
    }

    @Override
    public Response verifyCustomize(int positionId) throws TException {
        return service.verifyCustomize(positionId);
    }

    /**
     * 根据职位Id获取当前职位信息
     *
     * @param positionId
     * @return
     * @throws TException
     */
    @Override
    public Response getPositionById(int positionId) throws TException {
        return service.getPositionById(positionId);
    }

    @Override
    public Response getResources(CommonQuery query) throws TException {
        return service.getResources(query);
    }

    /**
     * @return response
     * @throws TException time 2016-11-21
     * @author zztaiwll
     */
    @Override
    public Response CustomField(String param) throws TException {
        // TODO Auto-generated method stub
        return customService.getCustomField(param);
    }

    @Override
    public List<ThirdPartyPositionForSynchronization> changeToThirdPartyPosition(List<ThirdPartyPosition> forms,
                                                                                 Position position) throws TException {
        return service.changeToThirdPartyPosition(forms, position);
    }

    @Override
    public boolean ifAllowRefresh(int positionId, int channel) throws TException {
        return service.ifAllowRefresh(positionId, channel);
    }

    @Override
    public ThirdPartyPositionForSynchronizationWithAccount createRefreshPosition(int positionId, int channel)
            throws TException {
        return service.createRefreshPosition(positionId, channel);
    }

    @Override
    public List<ThirdPartyPositionData> getThirdPartyPositions(CommonQuery query) throws TException {
        return service.getThirdPartyPositions(query);
    }

    @Override
    public Response batchHandlerJobPostion(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        return service.batchHandlerJobPostion(batchHandlerJobPostion);
    }

    @Override
    public Response deleteJobposition(DelePostion delePostion) throws TException {
        Integer id = null;
        if (delePostion.isSetId()) {
            id = delePostion.getId();
        }
        Integer companyId = null;
        if (delePostion.isSetCompany_id()) {
            companyId = delePostion.getCompany_id();
        }
        Integer sourceId = null;
        if (delePostion.isSetSource_id()) {
            sourceId = delePostion.getSource_id();
        }
        return service.deleteJobposition(id, companyId, delePostion.getJobnumber(), sourceId);
    }
}