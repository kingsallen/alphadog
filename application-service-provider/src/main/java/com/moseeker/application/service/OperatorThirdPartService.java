package com.moseeker.application.service;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartPosition;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdpartToredis;

public interface OperatorThirdPartService {
	 public Response addPostitonToRedis(ThirdpartToredis  positions);
	 public Response updatePostitonToRedis(ThirdpartToredis poistions);
	 public void addPositionToOrm(ThirdPartPosition position);
	 public void updatePositionOrm(ThirdPartPosition position);
	 public void SynOrUpThirdPartAccount(int type);
}
