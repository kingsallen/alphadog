package com.moseeker.position.service.appbs;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
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

	/**
	 * 批量同步职位
	 * @param positions 职位信息
	 * @return
	 */
	@CounterIface
	public Response synchronizePositions(List<PositionForSynchronizationPojo> positions) {
		try {
			if(positions != null) {
				positions.forEach(position -> {
					synchronizePosition(position);
				});
			}
			return ResponseUtils.success(null);
		} catch (Exception e) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
		} finally {
			//do nothing
		}
	}

	/**
	 * 同步职位
	 * @param position
	 */
	@CounterIface
	public void synchronizePosition(PositionForSynchronizationPojo position) {
		//校验必填项信息
		//todo
		if(positionService.verifySynchronizePosition(position)) {
			positionService.getPositionById(position.getId());
		}
	}

	public PositionService getPositionService() {
		return positionService;
	}

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}
}
