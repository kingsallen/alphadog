package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.campaigndb.CampaignHeadImageDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.CampaignDBDao;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignHeadImageDO;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CampaignDaoThriftService implements CampaignDBDao.Iface {
    @Autowired
    private CampaignHeadImageDao campaignHeadImageDao;

    /**
     * 头图查询
     */
    @Override
    public CampaignHeadImageDO headImage(CommonQuery query) throws TException {
        return campaignHeadImageDao.findResource(query);
    }
}
