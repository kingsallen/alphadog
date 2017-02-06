package com.moseeker.baseorm.service.Impl;

import com.moseeker.baseorm.dao.hrdb.HrHbConfigDao;
import com.moseeker.baseorm.dao.hrdb.HrHbItemsDao;
import com.moseeker.baseorm.dao.hrdb.HrHbPositionBindingDao;
import com.moseeker.baseorm.dao.hrdb.HrHbScratchCardDao;
import com.moseeker.baseorm.dao.hrdb.HrHbSendRecordDao;
import com.moseeker.baseorm.service.HrDaoService;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.HrHbConfigPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbItemsPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbPositionBindingPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbScratchCardPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbSendRecordPojo;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class HrDaoServiceImpl implements HrDaoService {

    private Logger logger = LoggerFactory.getLogger(HrDaoServiceImpl.class);

    @Autowired
    private HrHbConfigDao hrHbConfigDao;

    @Autowired
    private HrHbPositionBindingDao hrHbPositionBindingDao;

    @Autowired
    private HrHbItemsDao hrHbItemsDao;

    @Autowired
    private HrHbScratchCardDao hrHbScratchCardDao;

    @Autowired
    private HrHbSendRecordDao hrHbSendRecordDao;

    @Override
    public HrHbConfigPojo getHbConfig(CommonQuery query) throws TException {
    	HrHbConfigPojo result = new HrHbConfigPojo();
        try {
            result = BeanUtils.DBToStruct(HrHbConfigPojo.class, hrHbConfigDao.getResource(query));
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }
    
    @Override
	public List<HrHbConfigPojo> getHbConfigs(CommonQuery query) throws TException {
    	List<HrHbConfigPojo> result = new ArrayList<HrHbConfigPojo>();
        try {
            result = BeanUtils.DBToStruct(HrHbConfigPojo.class, hrHbConfigDao.getResources(query));

        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
	}

    @Override
    public HrHbPositionBindingPojo getHbPositionBinding(CommonQuery query) throws TException {
    	HrHbPositionBindingPojo result = new HrHbPositionBindingPojo();
        try {
        	result = BeanUtils.DBToStruct(HrHbPositionBindingPojo.class, hrHbPositionBindingDao.getResource(query));
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<HrHbPositionBindingPojo> getHbPositionBindings(CommonQuery query) throws TException {
        List<HrHbPositionBindingPojo> result = new ArrayList<HrHbPositionBindingPojo>();
        try {
            result = BeanUtils.DBToStruct(HrHbPositionBindingPojo.class, hrHbPositionBindingDao.getResources(query));

        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public HrHbItemsPojo getHbItem(CommonQuery query) throws TException {
    	HrHbItemsPojo result = new HrHbItemsPojo();
        try {
            result = BeanUtils.DBToStruct(HrHbItemsPojo.class, hrHbItemsDao.getResource(query));
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<HrHbItemsPojo> getHbItems(CommonQuery query) throws TException {
        List<HrHbItemsPojo> result = new ArrayList<HrHbItemsPojo>();
        try {
            List<com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord> records = hrHbItemsDao.getResources(query);
            result = BeanUtils.DBToStruct(HrHbItemsPojo.class, records);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public HrHbScratchCardPojo getHbScratchCard(CommonQuery query) throws TException {
        HrHbScratchCardPojo result = new HrHbScratchCardPojo();
        try {
            result = BeanUtils.DBToStruct(HrHbScratchCardPojo.class, hrHbScratchCardDao.getResource(query));
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public HrHbSendRecordPojo getHbSendRecord(CommonQuery query) throws TException {
        HrHbSendRecordPojo result = new HrHbSendRecordPojo();
        try {
            result = BeanUtils.DBToStruct(HrHbSendRecordPojo.class, hrHbSendRecordDao.getResource(query));
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
