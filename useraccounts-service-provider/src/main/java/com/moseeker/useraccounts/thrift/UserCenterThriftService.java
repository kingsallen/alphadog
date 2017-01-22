package com.moseeker.useraccounts.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.useraccounts.service.UserCenterService.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.ApplicationRecordsForm;
import com.moseeker.useraccounts.service.impl.UserCenterService;
import com.moseeker.useraccounts.service.impl.pojos.ApplicationRecords;

@Service
public class UserCenterThriftService implements Iface {
	
	@Autowired
	private UserCenterService userCenterService;

	@Override
	public List<ApplicationRecordsForm> getApplications(int userId) throws TException {
		List<ApplicationRecords> apps = userCenterService.getApplication(userId);
		return BeanUtils.copies(apps, ApplicationRecordsForm.class);
	}
}
