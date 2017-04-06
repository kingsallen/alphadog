package com.moseeker.baseorm.Thriftservice;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.logdb.SmsSendrecordDao;
import com.moseeker.baseorm.db.logdb.tables.records.LogSmsSendrecordRecord;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.dao.service.LogDBDao.Iface;
import com.moseeker.thrift.gen.dao.struct.LogSmsSendRecordDO;

@Service
public class LogDBDaoThriftService implements Iface {
	
	private static final Logger log = LoggerFactory.getLogger(LogDBDaoThriftService.class);
	
	@Autowired
	private SmsSendrecordDao smsDao;
	
	@Override
	public int saveSmsSenderRecord(LogSmsSendRecordDO smsSendRecordDO) throws TException {
		try {
			return smsDao.postResource(BeanUtils.structToDB(smsSendRecordDO, LogSmsSendrecordRecord.class));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}
}
