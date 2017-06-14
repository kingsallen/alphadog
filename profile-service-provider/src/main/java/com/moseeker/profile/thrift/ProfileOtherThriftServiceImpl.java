package com.moseeker.profile.thrift;

import com.moseeker.baseorm.dao.profiledb.ProfileOtherDao;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import com.moseeker.thrift.gen.profile.service.ProfileOtherThriftService;

import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jack on 15/03/2017.
 */
@Service
public class ProfileOtherThriftServiceImpl implements ProfileOtherThriftService.Iface {

    Logger logger = LoggerFactory.getLogger(ProfileOtherThriftServiceImpl.class);

    @Autowired
    private ProfileOtherDao dao;


    @Override
    public List<ProfileOtherDO> getResources(CommonQuery query) throws TException {
        try {
            return ResponseUtils.getNotNullValue(dao.getDatas(QueryConvert.commonQueryConvertToQuery(query)), new ArrayList<ProfileOtherDO>());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                throw (BIZException) e;
            } else {
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
            }
        }
    }

    @Override
    public ProfileOtherDO getResource(CommonQuery query) throws TException {
        try {
            return ResponseUtils.getNotNullValue(dao.getData(QueryConvert.commonQueryConvertToQuery(query)), new ProfileOtherDO());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                throw (BIZException) e;
            } else {
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
            }
        }
    }

    @Override
    public List<ProfileOtherDO> postResources(List<ProfileOtherDO> Others) throws TException {
        try {
            return ResponseUtils.getNotNullValue(dao.addAllData(Others), new ArrayList<ProfileOtherDO>());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                throw (BIZException) e;
            } else {
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
            }
        }
    }

    @Override
    public ProfileOtherDO postResource(ProfileOtherDO Other) throws TException {
        try {
            return ResponseUtils.getNotNullValue(dao.addData(Other), new ProfileOtherDO());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                throw (BIZException) e;
            } else {
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
            }
        }
    }

    @Override
    public List<Integer> putResources(List<ProfileOtherDO> Others) throws TException {
        try {
            return Arrays.asList(ArrayUtils.toObject(dao.updateDatas(Others)));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                throw (BIZException) e;
            } else {
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
            }
        }
    }

    @Override
    public int putResource(ProfileOtherDO Other) throws TException {
        try {
            return dao.updateData(Other);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                throw (BIZException) e;
            } else {
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
            }
        }
    }

    @Override
    public List<Integer> delResources(List<ProfileOtherDO> Others) throws TException {
        try {
            return Arrays.asList(ArrayUtils.toObject(dao.deleteDatas(Others)));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                throw (BIZException) e;
            } else {
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
            }
        }
    }

    @Override
    public int delResource(ProfileOtherDO Other) throws TException {
        try {
            return dao.deleteData(Other);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                throw (BIZException) e;
            } else {
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
            }
        }
    }
}
