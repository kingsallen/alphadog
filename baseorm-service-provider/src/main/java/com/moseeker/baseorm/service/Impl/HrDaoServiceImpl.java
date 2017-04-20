package com.moseeker.baseorm.service.Impl;

import com.moseeker.baseorm.dao.hrdb.HrEmployeeCertConfDao;
import com.moseeker.baseorm.dao.hrdb.HrEmployeeCustomFieldsDao;
import com.moseeker.baseorm.dao.hrdb.HrHbConfigDao;
import com.moseeker.baseorm.dao.hrdb.HrHbItemsDao;
import com.moseeker.baseorm.dao.hrdb.HrHbPositionBindingDao;
import com.moseeker.baseorm.dao.hrdb.HrHbScratchCardDao;
import com.moseeker.baseorm.dao.hrdb.HrHbSendRecordDao;
import com.moseeker.baseorm.dao.hrdb.HrPointsConfDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCertConfRecord;
import com.moseeker.baseorm.service.HrDaoService;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;

import com.moseeker.thrift.gen.dao.struct.hrdb.*;
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

    @Autowired
    private HrEmployeeCertConfDao hrEmployeeCertConfDao;

    @Autowired
    private HrEmployeeCustomFieldsDao hrEmployeeCustomFieldsDao;

    @Autowired
    private HrPointsConfDao hrPointsConfDao;

    @Override
    public HrHbConfigDO getHbConfig(CommonQuery query) throws TException {
    	HrHbConfigDO result = new HrHbConfigDO();
        try {
            result = BeanUtils.DBToStruct(HrHbConfigDO.class, hrHbConfigDao.getResource(query));
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }
    
    @Override
	public List<HrHbConfigDO> getHbConfigs(CommonQuery query) throws TException {
    	List<HrHbConfigDO> result = new ArrayList<HrHbConfigDO>();
        try {
            result = BeanUtils.DBToStruct(HrHbConfigDO.class, hrHbConfigDao.getResources(query));

        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
	}

    @Override
    public HrHbPositionBindingDO getHbPositionBinding(CommonQuery query) throws TException {
    	HrHbPositionBindingDO result = new HrHbPositionBindingDO();
        try {
        	result = BeanUtils.DBToStruct(HrHbPositionBindingDO.class, hrHbPositionBindingDao.getResource(query));
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<HrHbPositionBindingDO> getHbPositionBindings(CommonQuery query) throws TException {
        List<HrHbPositionBindingDO> result = new ArrayList<HrHbPositionBindingDO>();
        try {
            result = BeanUtils.DBToStruct(HrHbPositionBindingDO.class, hrHbPositionBindingDao.getResources(query));

        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public HrHbItemsDO getHbItem(CommonQuery query) throws TException {
    	HrHbItemsDO result = new HrHbItemsDO();
        try {
            result = BeanUtils.DBToStruct(HrHbItemsDO.class, hrHbItemsDao.getResource(query));
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<HrHbItemsDO> getHbItems(CommonQuery query) throws TException {
        List<HrHbItemsDO> result = new ArrayList<HrHbItemsDO>();
        try {
            List<com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord> records = hrHbItemsDao.getResources(query);
            result = BeanUtils.DBToStruct(HrHbItemsDO.class, records);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public HrHbScratchCardDO getHbScratchCard(CommonQuery query) throws TException {
        HrHbScratchCardDO result = new HrHbScratchCardDO();
        try {
            result = BeanUtils.DBToStruct(HrHbScratchCardDO.class, hrHbScratchCardDao.getResource(query));
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public HrHbSendRecordDO getHbSendRecord(CommonQuery query) throws TException {
        HrHbSendRecordDO result = new HrHbSendRecordDO();
        try {
            result = BeanUtils.DBToStruct(HrHbSendRecordDO.class, hrHbSendRecordDao.getResource(query));
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public HrEmployeeCertConfDO getEmployeeCertConf(CommonQuery query) throws TException {
        HrEmployeeCertConfDO result = new HrEmployeeCertConfDO();
        try {
        		HrEmployeeCertConfRecord record = hrEmployeeCertConfDao.getResource(query);
        		logger.info("HrEmployeeCertConfRecord: {}", record.intoMap());
            result = BeanUtils.DBToStruct(HrEmployeeCertConfDO.class, record);
            logger.info("HrEmployeeCertConfDO: {}", result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<HrEmployeeCustomFieldsDO> getEmployeeCustomFields(CommonQuery query) throws TException {
        List<HrEmployeeCustomFieldsDO> result = new ArrayList<HrEmployeeCustomFieldsDO>();
        try {
            List<com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCustomFieldsRecord> records = hrEmployeeCustomFieldsDao.getResources(query);
            result = BeanUtils.DBToStruct(HrEmployeeCustomFieldsDO.class, records);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<HrPointsConfDO> getPointsConfs(CommonQuery query) throws TException {
        List<HrPointsConfDO> result = new ArrayList<>();
        try {
            List<com.moseeker.baseorm.db.hrdb.tables.records.HrPointsConfRecord> records =
                    hrPointsConfDao.getResources(query);
            result = BeanUtils.DBToStruct(HrPointsConfDO.class, records);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
