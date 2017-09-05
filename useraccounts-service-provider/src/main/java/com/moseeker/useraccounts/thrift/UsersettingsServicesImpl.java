package com.moseeker.useraccounts.thrift;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.useraccounts.service.UsersettingServices.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.Usersetting;
import com.moseeker.useraccounts.service.impl.UsersettingsService;
import java.util.List;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersettingsServicesImpl implements Iface {

    private Logger log = LoggerFactory.getLogger(UsersettingsServicesImpl.class);

	@Autowired
	private UsersettingsService service;
	
	public Response postResources(List<Usersetting> structs) throws TException {
        try {
            return service.postResources(structs);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

	public Response putResources(List<Usersetting> structs) throws TException {
        try {
            return service.putResources(structs);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

	public Response delResources(List<Usersetting> structs) throws TException {
        try {
            return service.delResources(structs);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

	public Response postResource(Usersetting struct) throws TException {
        try {
            return service.postResource(struct);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

	public Response putResource(Usersetting struct) throws TException {
        try {
            return service.putResource(struct);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

	public Response delResource(Usersetting struct) throws TException {
        try {
            return service.delResource(struct);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

	@Override
	public Response getResource(CommonQuery query) throws TException {
        try {
            return service.getResource(query);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SysBIZException();
        }
	}
}
