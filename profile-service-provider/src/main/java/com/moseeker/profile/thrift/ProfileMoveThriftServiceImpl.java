package com.moseeker.profile.thrift;


import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.entity.biz.ProfileMailUtil;
import com.moseeker.profile.service.impl.talentpoolmvhouse.service.AbstractProfileMoveService;
import com.moseeker.profile.service.impl.talentpoolmvhouse.service.ProfileMoveServiceFactory;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.talentpool.service.ProfileMoveThriftService.Iface;
import com.moseeker.thrift.gen.talentpool.struct.ProfileMoveForm;
import org.apache.thrift.TException;
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
        }catch (BIZException e){
            throw ExceptionUtils.convertException(e);
        } catch (Exception e) {
            mailUtil.sendMvHouseFailedEmail(e, "简历搬家用户登录时发生异常" + form.toString());
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getMoveOperationList(int hrId, int pageNumber, int pageSize) throws TException{
        try {
            int channel = 0;
            AbstractProfileMoveService profileMoveService = profileMoveServiceFactory.getSerivce(channel);
            return profileMoveService.getMoveOperationList(hrId, pageNumber, pageSize);
        } catch (BIZException e){
            throw ExceptionUtils.convertException(e);
        } catch (Exception e){
            mailUtil.sendMvHouseFailedEmail(e, "获取简历搬家操作记录时发生异常hrId:"+ hrId + ",pageNumber:"+ pageNumber + ",pageSize:" + pageSize);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response moveHouse(String profile, int operationId, int currentEmailNum) throws TException {
        try {
            int channel = 0;
            AbstractProfileMoveService profileMoveService = profileMoveServiceFactory.getSerivce(channel);
            return profileMoveService.profileMove(profile, operationId, currentEmailNum);
        } catch (BIZException e) {
            throw ExceptionUtils.convertException(e);
        } catch (Exception e){
            mailUtil.sendMvHouseFailedEmail(e, "简历搬家简历合并入库时发生异常profile:"+ profile + ",operationId:"+ operationId + ",currentEmailNum:" + currentEmailNum);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getMoveOperationState(int hrId) throws TException {
        try {
            int channel = 0;
            AbstractProfileMoveService profileMoveService = profileMoveServiceFactory.getSerivce(channel);
            return profileMoveService.getMoveOperationState(hrId);
        } catch (BIZException e){
            throw ExceptionUtils.convertException(e);
        } catch (Exception e){
            mailUtil.sendMvHouseFailedEmail(e, "获取简历搬家状态异常hrId:" + hrId);
            throw ExceptionUtils.convertException(e);
        }
    }
}
