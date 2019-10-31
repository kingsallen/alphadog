package com.moseeker.useraccounts.thrift;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserQxService service;
    @Autowired
    private UserPositionEmailService emailService;
    @Override
    public UserSearchConditionListVO userSearchConditionList(int userId) throws TException {
        try {
            return service.userSearchConditionList(userId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public UserSearchConditionVO postUserSearchCondition(UserSearchConditionDO userSearchCondition) throws TException {
        try {
            return service.postUserSearchCondition(userSearchCondition);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public UserSearchConditionVO delUserSearchCondition(int userId, int id) throws TException {
        try {
            return service.delUserSearchCondition(userId, id);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public UserCollectPositionVO getUserCollectPosition(int userId, int positionId) throws TException {
        try {
            return service.getUserCollectPosition(userId, positionId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public UserCollectPositionListVO getUserCollectPositions(int userId) throws TException {
        try {
            return service.getUserCollectPositions(userId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     *
     * @param userId
     * @param employeeId
     * @param positionId
     * @return
     * @throws TException
     */
    @Override
    public UserCollectPositionVO postUserCollectReferredPosition(int userId,int employeeId,int positionId,int parentShareChainId) throws TException {
        try {
            return service.putUserCollectPosition(userId, positionId, employeeId, parentShareChainId,0);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public UserCollectPositionVO postUserCollectPosition(int userId, int positionId) throws TException {
        try {
            return service.putUserCollectPosition(userId, positionId,0, 0,0);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public UserCollectPositionVO delUserCollectPosition(int userId, int positionId) throws TException {
        try {
            return service.putUserCollectPosition(userId, positionId,0, 0,1);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public UserPositionStatusVO getUserPositionStatus(int userId, List<Integer> positionIds) throws TException {
        try {
            return service.getUserPositionStatus(userId, positionIds);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public UserViewedPositionVO userViewedPosition(int userId, int positionId) throws TException {
        try {
            return service.userViewedPosition(userId, positionId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
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
	public Response sendValiddateEmail(String email, int userId, String conditions,String urls) throws TException {
		// TODO Auto-generated method stub
		try{
			int result=emailService.sendEmailvalidation(email, userId, conditions,urls);
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
