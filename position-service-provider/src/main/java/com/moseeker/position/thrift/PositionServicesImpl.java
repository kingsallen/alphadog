package com.moseeker.position.thrift;

import java.util.ArrayList;
import java.util.List;

import com.moseeker.common.exception.ExceptionType;
import com.moseeker.position.service.fundationbs.PositionQxService;
import com.moseeker.thrift.gen.dao.struct.CampaignHeadImageVO;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.RpExtInfo;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import com.moseeker.thrift.gen.position.struct.WechatPositionListData;
import com.moseeker.thrift.gen.position.struct.WechatPositionListQuery;
import com.moseeker.thrift.gen.position.struct.WechatRpPositionListData;
import com.moseeker.thrift.gen.position.struct.WechatShareData;
import com.moseeker.thrift.gen.position.struct.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
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
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private PositionQxService positionQxService;

    /**
     * 获取推荐职位
     * <p></p>
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
     */
    @Override
    public Response getPositionById(int positionId) throws TException {
        try {
            return service.getPositionById(positionId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getResources(CommonQuery query) throws TException {
    	try {
    		List<JobPositionRecord> list=jobPositionDao.getRecords(QueryConvert.commonQueryConvertToQuery(query));
			List<Position> structs = BeanUtils.DBToStruct(Position.class, list);
			
			if (!structs.isEmpty()){
				return ResponseUtils.success(structs);
			}
			
		} catch (Exception e) {
			logger.error("getResources error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
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
    public boolean ifAllowRefresh(int positionId,int account_id) {
        try {
            return service.ifAllowRefresh(positionId,account_id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public ThirdPartyPositionForSynchronizationWithAccount createRefreshPosition(int positionId, int account_id)
            throws TException {
        try {
            return service.createRefreshPosition(positionId, account_id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ThirdPartyPositionForSynchronizationWithAccount();
        }
    }

    @Override
    public List<WechatPositionListData> getPositionList(WechatPositionListQuery query) throws TException {
        return service.getPositionList(query);
    }

    @Override
    public List<RpExtInfo> getPositionListRpExt(List<Integer> pids) throws TException {
        try {
            return service.getPositionListRpExt(pids);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public WechatShareData getShareInfo(int hb_config_id) throws TException {
        return service.getShareInfo(hb_config_id);
    }

    /**
     * 职位列表头图信息
     */
    @Override
    public CampaignHeadImageVO headImage() throws TException {
        return positionQxService.headImage();
    }

    /**
     * 查询单个职位详情
     */
    @Override
    public PositionDetailsVO positionDetails(int positionId) throws TException {
        return positionQxService.positionDetails(positionId);
    }

    /**
     * 查询公司热招职位的详细信息
     */
    @Override
    public PositionDetailsListVO companyHotPositionDetailsList(int companyId, int page, int per_age) throws TException {
        return positionQxService.companyHotPositionDetailsList(companyId, page, per_age);
    }

    /**
     * 职位相关职位接口
     */
    @Override
    public PositionDetailsListVO similarityPositionDetailsList(int pid, int page, int per_age) throws TException {
        return positionQxService.similarityPositionDetailsList(pid, page, per_age);
    }

    @Override
    public List<WechatRpPositionListData> getRpPositionList(int hb_config_id) throws TException {
        try {
            return service.getRpPositionList(hb_config_id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<ThirdPartyPositionData> getThirdPartyPositions(CommonQuery query) throws TException {
        try {
            return service.getThirdPartyPositions(query);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public Response batchHandlerJobPostion(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            return service.batchHandlerJobPostion(batchHandlerJobPostion);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
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
        try {
            return service.deleteJobposition(id, companyId, delePostion.getJobnumber(), sourceId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response getTeamIdByDepartmentName(int companyId, String departmentName) throws TException {
        try {
            return service.getTeamIdbyDepartmentName(companyId, departmentName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }


}