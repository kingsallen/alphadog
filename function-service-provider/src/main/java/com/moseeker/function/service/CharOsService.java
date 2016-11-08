package com.moseeker.function.service;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartParamer;

public interface CharOsService {
	public Response getInformationFromChaos(ThirdPartParamer param);
}
