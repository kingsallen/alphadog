package com.moseeker.position.thrift;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.position.service.impl.PositionService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices.Iface;

@Service
public class PositionServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PositionService service;
    
	/**
	 * 获取推荐职位
	 * <p></p>
	 *
	 * @param pid
	 * @return
     */
	@Override
    public Response getRecommendedPositions(int pid) {
		return service.getRecommendedPositions(pid);
    }

	@Override
	public Response verifyCustomize(int positionId) throws TException {
		return service.verifyCustomize(positionId);
	}

	/**
	 * 根据职位Id获取当前职位信息
	 *
	 * @param positionId
	 * @return
	 * @throws TException
     */
	@Override
	public Response getPositionById(int positionId) throws TException {
		return service.getPositionById(positionId);
	}

	@Override
	public Response getResources(CommonQuery query) throws TException {
		return service.getResources(query);
	}
}