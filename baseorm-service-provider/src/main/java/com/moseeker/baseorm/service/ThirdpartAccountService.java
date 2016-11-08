package com.moseeker.baseorm.service;
import java.util.List;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartPosition;
public interface ThirdpartAccountService {
	public Response add_ThirdPartAccount(ThirdPartAccount account);
	public Response update_ThirdPartAccount(ThirdPartAccount account);
	public Response add_ThirdPartPosition(ThirdPartPosition position);
	public Response update_ThirdPartPosition(ThirdPartPosition position);
	public Response add_ThirdPartPositions(List<ThirdPartPosition> positions);
	public Response update_ThirdPartPositions(List<ThirdPartPosition> positions);
	public Response getSingleAccountByCompanyId(int companyId);
}
