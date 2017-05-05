package com.moseeker.baseorm.service.Impl;

import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCertConfRecord;
import com.moseeker.baseorm.service.HrDaoService;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Query;
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
    private HrCompanyAccountDao hrCompanyAccountDao;

    @Autowired
    private HrEmployeeCustomFieldsDao hrEmployeeCustomFieldsDao;

    @Autowired
    private HrPointsConfDao hrPointsConfDao;

    @Override
    public HrHbConfigDO getHbConfig(Query query) throws TException {
        return hrHbConfigDao.getData(query);
    }
    
    @Override
	public List<HrHbConfigDO> getHbConfigs(Query query) throws TException {
        return hrHbConfigDao.getDatas(query);
	}

    @Override
    public HrHbPositionBindingDO getHbPositionBinding(Query query) throws TException {
        return hrHbPositionBindingDao.getData(query);
    }

    @Override
    public List<HrHbPositionBindingDO> getHbPositionBindings(Query query) throws TException {
        return hrHbPositionBindingDao.getDatas(query);
    }

    @Override
    public HrHbItemsDO getHbItem(Query query) throws TException {
        return hrHbItemsDao.getData(query);
    }

    @Override
    public List<HrHbItemsDO> getHbItems(Query query) throws TException {
        return hrHbItemsDao.getDatas(query);
    }

    @Override
    public HrHbScratchCardDO getHbScratchCard(Query query) throws TException {
        return hrHbScratchCardDao.getData(query);
    }

    @Override
    public HrHbSendRecordDO getHbSendRecord(Query query) throws TException {
        HrHbSendRecordDO result = new HrHbSendRecordDO();
        try {
            result = BeanUtils.DBToStruct(HrHbSendRecordDO.class, hrHbSendRecordDao.getRecord(query));
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public HrEmployeeCertConfDO getEmployeeCertConf(Query query) throws TException {
        HrEmployeeCertConfDO result = new HrEmployeeCertConfDO();
        try {
        		HrEmployeeCertConfRecord record = hrEmployeeCertConfDao.getRecord(query);
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
    public List<HrEmployeeCustomFieldsDO> getEmployeeCustomFields(Query query) throws TException {
        return hrEmployeeCustomFieldsDao.getDatas(query);
    }

    @Override
    public List<HrCompanyAccountDO> getHrCompanyAccounts(Query query) throws TException {
        return hrCompanyAccountDao.getDatas(query);
    }

    @Override
    public List<HrPointsConfDO> getPointsConfs(Query query) throws TException {
        return hrPointsConfDao.getDatas(query);
    }
}
