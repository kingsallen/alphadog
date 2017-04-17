package com.moseeker.position.service.fundationbs;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.CampaignDBDao;
import com.moseeker.thrift.gen.dao.struct.CampaignHeadImageVO;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignHeadImageDO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by YYF
 *
 * Date: 2017/4/17
 *
 * Project_name :alphadog
 */
@Service
public class GammaPositionService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CampaignDBDao.Iface campaignDBDao = ServiceManager.SERVICEMANAGER.getService(CampaignDBDao.Iface.class);

    /**
     * 职位头图查询
     */
    public CampaignHeadImageVO headImage() {
        CampaignHeadImageVO campaignHeadImageVO = new CampaignHeadImageVO();
        try {
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setSortby("create_time");
            commonQuery.setOrder("desc");
            CampaignHeadImageDO campaignHeadImageDO = campaignDBDao.headImage(commonQuery);
            if (!com.moseeker.common.util.StringUtils.isEmptyObject(campaignHeadImageDO)) {
                campaignHeadImageVO.setMessage("SUCCESS");
                campaignHeadImageVO.setStatus(0);
                campaignHeadImageVO.setData(campaignHeadImageDO);
            } else {
                campaignHeadImageVO.setMessage("无头图信息");
                campaignHeadImageVO.setStatus(-1);
            }
        } catch (Exception e) {
            campaignHeadImageVO.setMessage("系统错误，请重试");
            campaignHeadImageVO.setStatus(-1);
            logger.error(e.getMessage(), e);
        }
        return campaignHeadImageVO;
    }



}
