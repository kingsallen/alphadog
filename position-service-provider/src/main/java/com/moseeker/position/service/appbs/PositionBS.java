package com.moseeker.position.service.appbs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.position.pojo.PositionForSynchronizationPojo;
import com.moseeker.position.service.fundationbs.PositionService;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 * 
 * 职位相关的业务服务类 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 7, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Service
public class PositionBS {
	
	@Autowired
	private PositionService positionService;

	@CounterIface
	public Response synchronizePosition(List<PositionForSynchronizationPojo> positions) {
		
		return null;
	}

	public PositionService getPositionService() {
		return positionService;
	}

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}
}
