package com.moseeker.useraccounts.thrift;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.userdb.UserSearchConditionDO;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.service.impl.UserPositionEmailService;
import com.moseeker.useraccounts.service.impl.UserQxService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lucky8987 on 17/4/20.
 */
@Service
public class UserQxServiceImpl implements com.moseeker.thrift.gen.useraccounts.service.UserQxService.Iface {

    @Autowired
    private UserQxService service;
    @Autowired
    private UserPositionEmailService emailService;
	static Logger logger = LoggerFactory.getLogger(UserQxServiceImpl.class);
    @Override
    public UserSearchConditionListVO userSearchConditionList(int userId) throws TException {
        return service.userSearchConditionList(userId);
    }

    @Override
    public UserSearchConditionVO postUserSearchCondition(UserSearchConditionDO userSearchCondition) throws TException {
        return service.postUserSearchCondition(userSearchCondition);
    }

    @Override
    public UserSearchConditionVO delUserSearchCondition(int userId, int id) throws TException {
        return service.delUserSearchCondition(userId, id);
    }

    @Override
    public UserCollectPositionVO getUserCollectPosition(int userId, int positionId) throws TException {
        return service.getUserCollectPosition(userId, positionId);
    }

    @Override
    public UserCollectPositionListVO getUserCollectPositions(int userId) throws TException {
        return service.getUserCollectPositions(userId);
    }

    @Override
    public UserCollectPositionVO postUserCollectPosition(int userId, int positionId) throws TException {
        return service.putUserCollectPosition(userId, positionId, 0);
    }

    @Override
    public UserCollectPositionVO delUserCollectPosition(int userId, int positionId) throws TException {
        return service.putUserCollectPosition(userId, positionId, 1);
    }

    @Override
    public UserPositionStatusVO getUserPositionStatus(int userId, List<Integer> positionIds) throws TException {
        return service.getUserPositionStatus(userId, positionIds);
    }

    @Override
    public UserViewedPositionVO userViewedPosition(int userId, int positionId) throws TException {
        return service.userViewedPosition(userId, positionId);
    }

	@Override
	public Response sendRecommendPosition(int userId) throws TException {
		// TODO Auto-generated method stub
		try{
			int result=emailService.sendEmailPosition(userId);
			if(result==0){
				return ResponseUtils.fail(1, "搜索条件或者邮箱不存在");
			}
		    return ResponseUtils.success("");
		}catch(Exception e){
			logger.info(e.getMessage(),e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
		}
		
	}

	@Override
	public Response postUserEmailPosition(int userId, String conditions) throws TException {
		// TODO Auto-generated method stub
		try{
			int result=emailService.postUserPositionEmail(userId, conditions);
			logger.info("UserEmailPosition的处理结果========={} ",result);
		    return ResponseUtils.success(""); 
		}catch(Exception e){
			logger.info(e.getMessage(),e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
		}
	}

	@Override
	public Response sendValiddateEmail(String email, int userId, String conditions) throws TException {
		// TODO Auto-generated method stub
		try{
			int result=emailService.sendEmailvalidation(email, userId, conditions);
			if(result==0){
				return ResponseUtils.fail(1, "搜索条件或者邮箱不存在");
			}
		    return ResponseUtils.success(""); 
			
		}catch(Exception e){
			logger.info(e.getMessage(),e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
		}
	}
}
