package com.moseeker.function.service.choas;

import java.net.ConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.ThirdPartyAccountPojo;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.UrlUtil;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.foundation.chaos.struct.ThirdPartyAccountStruct;

/**
 * 
 * 第三方渠道（比如51，智联）服务 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 6, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Service
public class ChaosServiceImpl {
	
	Logger logger = LoggerFactory.getLogger(ChaosServiceImpl.class);

	public Response bind(String username, String password, String memberName, byte channel) {
		try {
			ChannelType chnnelType = ChannelType.instaceFromInteger(channel);
			String bindURI = chnnelType.getBindURI();
			String params = ChaosTool.getParams(username, password, memberName, chnnelType);
			String data = UrlUtil.sendPost(bindURI, params, Constant.CONNECTION_TIME_OUT, Constant.READ_TIME_OUT);
			//String data = "{\"status\":0,\"message\":\"success\", \"data\":3}";
			Response response = ChaosTool.createResponse(data);
			return response;
		} catch (ConnectException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
	}

	public Response synchronizePosition(ThirdPartyAccountPojo account, String positionJson) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response refreshPosition(ThirdPartyAccountPojo account, int positionId, String jobNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response distortPosition(int positionId, ChannelType channel) {
		return null;
	}

	public ThirdPartyAccountStruct synchronization(ThirdPartyAccountStruct thirdPartyAccount) {
		
		if(thirdPartyAccount != null) {
			ChannelType chnnelType = ChannelType.instaceFromInteger(thirdPartyAccount.getChannel());
			String synchronizationURI = chnnelType.getRemain();
			String params = ChaosTool.getParams(thirdPartyAccount.getUsername(), thirdPartyAccount.getPassword(), thirdPartyAccount.getMemberName(), chnnelType);
			try {
				//String data = UrlUtil.sendPost(synchronizationURI, params, Constant.CONNECTION_TIME_OUT, Constant.READ_TIME_OUT);
				String data = "{\"status\":0,\"message\":\"success\", \"data\":100}";
				if(data != null) {
					JSONObject result = JSON.parseObject(data);
					if(result.getInteger("status") != null && result.getInteger("status") == 0) {
						thirdPartyAccount.setRemainNum(result.getIntValue("data"));
					} else {
						thirdPartyAccount.setStatus(result.getInteger("status"));
					}
				}
			} catch (Exception e) {
			//} catch (ConnectException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				thirdPartyAccount.setStatus(-1);
			} finally {
				//do nothing
			}
			//String data = "{\"status\":0,\"message\":\"success\", \"data\":3}";
		}
		return thirdPartyAccount;
	}

}
