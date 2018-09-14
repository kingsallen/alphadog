package com.moseeker.profile.thrift;


import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.profile.service.impl.talentpoolmvhouse.service.AbstractProfileMoveService;
import com.moseeker.profile.service.impl.talentpoolmvhouse.service.ProfileMoveServiceFactory;
import com.moseeker.entity.biz.ProfileMailUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.talentpool.service.ProfileMoveThriftService.Iface;
import com.moseeker.thrift.gen.talentpool.struct.ProfileMoveForm;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 简历搬家service实现类
 *
 * @author cjm
 * @date 2018-07-18 11:54
 **/
@Service
public class ProfileMoveThriftServiceImpl implements Iface {

    private Logger logger = LoggerFactory.getLogger(ProfileMoveThriftServiceImpl.class);

    private final ProfileMoveServiceFactory profileMoveServiceFactory;

    private final ProfileMailUtil mailUtil;

    @Autowired
    public ProfileMoveThriftServiceImpl(ProfileMailUtil mailUtil, ProfileMoveServiceFactory profileMoveServiceFactory) {
        this.mailUtil = mailUtil;
        this.profileMoveServiceFactory = profileMoveServiceFactory;
    }

    @Override
    public Response moveHouseLogin(ProfileMoveForm form) throws TException {
        try {
            int channel = form.getChannel();
            AbstractProfileMoveService profileMoveService = profileMoveServiceFactory.getSerivce(channel);
            return profileMoveService.moveHouseLogin(form);
        } catch (Exception e) {
            mailUtil.sendMvHouseFailedEmail(e, "简历搬家用户登录时发生异常" + form.toString());
            logger.info(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response getMoveOperationList(int hrId, int pageNumber, int pageSize) throws TException{
        try {
            int channel = 0;
            AbstractProfileMoveService profileMoveService = profileMoveServiceFactory.getSerivce(channel);
            return profileMoveService.getMoveOperationList(hrId, pageNumber, pageSize);
        } catch (Exception e){
            mailUtil.sendMvHouseFailedEmail(e, "获取简历搬家操作记录时发生异常hrId:"+ hrId + ",pageNumber:"+ pageNumber + ",pageSize:" + pageSize);
            logger.info(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response moveHouse(String profile, int operationId, int currentEmailNum) throws TException {
        try {
            int channel = 0;
            AbstractProfileMoveService profileMoveService = profileMoveServiceFactory.getSerivce(channel);
            return profileMoveService.profileMove(profile, operationId, currentEmailNum);
        } catch (BIZException e) {
            mailUtil.sendMvHouseFailedEmail(e, "简历搬家简历合并入库时发生异常profile:"+ profile + ",operationId:"+ operationId + ",currentEmailNum:" + currentEmailNum);
            logger.info(e.getMessage(), e);
            throw e;
        } catch (Exception e){
            mailUtil.sendMvHouseFailedEmail(e, "简历搬家简历合并入库时发生异常profile:"+ profile + ",operationId:"+ operationId + ",currentEmailNum:" + currentEmailNum);
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response getMoveOperationState(int hrId) throws TException {
        try {
            int channel = 0;
            AbstractProfileMoveService profileMoveService = profileMoveServiceFactory.getSerivce(channel);
            return profileMoveService.getMoveOperationState(hrId);
        } catch (Exception e){
            mailUtil.sendMvHouseFailedEmail(e, "获取简历搬家状态异常hrId:" + hrId);
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }
}
