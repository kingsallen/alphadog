package com.moseeker.position.service.fundationbs;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.position.utils.CommonMessage;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.CampaignDBDao;
import com.moseeker.thrift.gen.dao.service.JobDBDao;
import com.moseeker.thrift.gen.dao.struct.CampaignHeadImageVO;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignHeadImageDO;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.PositionDetails;
import com.moseeker.thrift.gen.position.struct.PositionDetailsListVO;
import com.moseeker.thrift.gen.position.struct.PositionDetailsVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by YYF
 *
 * Date: 2017/4/17
 *
 * Gamma 0.9 新增接口
 *
 * Project_name :alphadog
 */
@Service
public class PositionQxService extends JOOQBaseServiceImpl<Position, JobPositionRecord> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CampaignDBDao.Iface campaignDBDao = ServiceManager.SERVICEMANAGER.getService(CampaignDBDao.Iface.class);


    private JobDBDao.Iface jobDbDao = ServiceManager.SERVICEMANAGER.getService(JobDBDao.Iface.class);

    /**
     * 职位头图查询
     *
     * @return campaignHeadImageVO
     */
    @CounterIface
    public CampaignHeadImageVO headImage() {
        CampaignHeadImageVO campaignHeadImageVO = new CampaignHeadImageVO();
        try {
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setSortby("create_time");
            commonQuery.setOrder("desc");
            CampaignHeadImageDO campaignHeadImageDO = campaignDBDao.headImage(commonQuery);
            if (campaignHeadImageDO.getId() != 0) {
                campaignHeadImageVO.setMessage(CommonMessage.SUCCESS.getMessage());
                campaignHeadImageVO.setStatus(CommonMessage.SUCCESS.getStatus());
                campaignHeadImageVO.setData(campaignHeadImageDO);
            } else {
                campaignHeadImageVO.setMessage(CommonMessage.HEAD_IMAGE_BLANK.getMessage());
                campaignHeadImageVO.setStatus(CommonMessage.HEAD_IMAGE_BLANK.getStatus());
            }
        } catch (Exception e) {
            campaignHeadImageVO.setMessage(CommonMessage.EXCEPTION.getMessage());
            campaignHeadImageVO.setStatus(CommonMessage.EXCEPTION.getStatus());
            logger.error(e.getMessage(), e);
        }
        return campaignHeadImageVO;
    }

    /**
     * 查询职位的详细信息
     *
     * @return positionDetailsVO
     */
    @CounterIface
    public PositionDetailsVO positionDetails(Integer positionId) {
        PositionDetailsVO positionDetailsVO = new PositionDetailsVO();
        try {
            if (positionId == 0) {
                positionDetailsVO.setStatus(CommonMessage.POSITIONID_BLANK.getStatus());
                positionDetailsVO.setMessage(CommonMessage.POSITIONID_BLANK.getMessage());
                return positionDetailsVO;
            }
            PositionDetails positionDetails = jobDbDao.positionDetails(positionId);
            if (positionDetails.getId() != 0) {
                positionDetailsVO.setData(positionDetails);
                positionDetailsVO.setStatus(CommonMessage.SUCCESS.getStatus());
                positionDetailsVO.setMessage(CommonMessage.SUCCESS.getMessage());
            } else {
                positionDetailsVO.setStatus(CommonMessage.POSITION_NONEXIST.getStatus());
                positionDetailsVO.setMessage(CommonMessage.POSITION_NONEXIST.getMessage());
            }
        } catch (Exception e) {
            positionDetailsVO.setMessage(CommonMessage.EXCEPTION.getMessage());
            positionDetailsVO.setStatus(CommonMessage.EXCEPTION.getStatus());
            logger.error(e.getMessage(), e);
        }
        return positionDetailsVO;
    }


    /**
     * 查询公司热招职位的详细信息
     *
     * @return PositionDetailsListVO
     */
    @CounterIface
    public PositionDetailsListVO companyHotPositionDetailsList(Integer companyId, Integer page, Integer per_age) {
        PositionDetailsListVO positionDetailsListVO = new PositionDetailsListVO();
        try {
            if (companyId == 0) {
                positionDetailsListVO.setStatus(CommonMessage.COMPANYID_BLANK.getStatus());
                positionDetailsListVO.setMessage(CommonMessage.COMPANYID_BLANK.getMessage());
                return positionDetailsListVO;
            }
            CommonQuery commonQuery = new CommonQuery();
            HashMap hashMap = new HashMap();
            hashMap.put("company_id", String.valueOf(companyId));
            commonQuery.setEqualFilter(hashMap);
            commonQuery.setPage(page);
            commonQuery.setPer_page(per_age);
            List<PositionDetails> list = jobDbDao.hotPositionDetailsList(commonQuery);
            if (list != null && list.size() > 0) {
                positionDetailsListVO.setData(list);
                positionDetailsListVO.setPage(page);
                positionDetailsListVO.setPer_age(per_age);
                positionDetailsListVO.setStatus(CommonMessage.SUCCESS.getStatus());
                positionDetailsListVO.setMessage(CommonMessage.SUCCESS.getMessage());
            } else {
                positionDetailsListVO.setStatus(CommonMessage.POSITIONLIST_NONEXIST.getStatus());
                positionDetailsListVO.setMessage(CommonMessage.POSITIONLIST_NONEXIST.getMessage());
            }
        } catch (Exception e) {
            positionDetailsListVO.setMessage(CommonMessage.EXCEPTION.getMessage());
            positionDetailsListVO.setStatus(CommonMessage.EXCEPTION.getStatus());
            logger.error(e.getMessage(), e);
        }
        return positionDetailsListVO;
    }


    /**
     * 职位相关职位接口
     *
     * @return positionDetailsVO
     */
    @CounterIface
    public PositionDetailsListVO similarityPositionDetailsList(Integer pid, Integer page, Integer per_age) {
        PositionDetailsListVO positionDetailsList = new PositionDetailsListVO();
        try {
            if (pid == 0) {
                positionDetailsList.setStatus(CommonMessage.POSITIONID_BLANK.getStatus());
                positionDetailsList.setMessage(CommonMessage.POSITIONID_BLANK.getMessage());
                return positionDetailsList;
            }
            CommonQuery commonQuery = new CommonQuery();
            HashMap hashMap = new HashMap();
            hashMap.put("pid", String.valueOf(pid));
            commonQuery.setEqualFilter(hashMap);
            commonQuery.setPage(page);
            commonQuery.setPer_page(per_age);
            List<PositionDetails> list = jobDbDao.similarityPositionDetailsList(commonQuery);
            if (list != null && list.size() > 0) {
                positionDetailsList.setStatus(CommonMessage.SUCCESS.getStatus());
                positionDetailsList.setMessage(CommonMessage.SUCCESS.getMessage());
                positionDetailsList.setData(list);
                positionDetailsList.setPage(page);
                positionDetailsList.setPer_age(per_age);
            } else {
                positionDetailsList.setStatus(CommonMessage.SIMILARITYPOSITIONLIST_NONEXIST.getStatus());
                positionDetailsList.setMessage(CommonMessage.SIMILARITYPOSITIONLIST_NONEXIST.getMessage());
            }
        } catch (Exception e) {
            positionDetailsList.setMessage(CommonMessage.EXCEPTION.getMessage());
            positionDetailsList.setStatus(CommonMessage.EXCEPTION.getStatus());
            logger.error(e.getMessage(), e);
        }
        return positionDetailsList;
    }

    @Override
    protected void initDao() {
        super.dao = this.dao;
    }

    @Override
    protected Position DBToStruct(JobPositionRecord r) {
        return (Position) BeanUtils.DBToStruct(Position.class, r);
    }

    @Override
    protected JobPositionRecord structToDB(Position p) {
        return (JobPositionRecord) BeanUtils.structToDB(p, JobPositionRecord.class);
    }


}
