package com.moseeker.function.thrift.service;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.function.service.SensitiveWordService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.function.service.FunctionServices.Iface;
import com.mysql.jdbc.util.ResultSetUtil;

/**
 * 
 * 通用服务响应客户端接口请求
 *  
 * <p>Company: MoSeeker</P>  
 * <p>date: Jul 29, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Service
public class FunctionService implements Iface {

	@Autowired
	private SensitiveWordService sensitiveWordService;
	
	@Override
	public Response verifySensitiveWords(List<String> contents) throws TException {
		List<Boolean> result = sensitiveWordService.verifySensitiveWords(contents);
		return ResponseUtils.success(result);
	}

	public SensitiveWordService getSensitiveWordService() {
		return sensitiveWordService;
	}

	public void setSensitiveWordService(SensitiveWordService sensitiveWordService) {
		this.sensitiveWordService = sensitiveWordService;
	}
}
