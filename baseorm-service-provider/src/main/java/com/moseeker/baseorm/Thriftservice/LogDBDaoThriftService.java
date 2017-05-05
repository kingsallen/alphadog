package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.logdb.SmsSendrecordDao;
import com.moseeker.thrift.gen.dao.service.LogDBDao.Iface;
import com.moseeker.thrift.gen.dao.struct.LogSmsSendRecordDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogDBDaoThriftService implements Iface {
	
	private static final Logger log = LoggerFactory.getLogger(LogDBDaoThriftService.class);
	
	@Autowired
	private SmsSendrecordDao smsDao;
	
	@Override
	public int saveSmsSenderRecord(LogSmsSendRecordDO smsSendRecordDO) throws TException {
		try {
			com.moseeker.thrift.gen.dao.struct.logdb.LogSmsSendrecordDO sms = new com.moseeker.thrift.gen.dao.struct.logdb.LogSmsSendrecordDO();
			return smsDao.addData(sms).getId();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}
}
