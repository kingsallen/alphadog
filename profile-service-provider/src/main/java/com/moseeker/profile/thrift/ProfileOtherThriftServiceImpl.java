package com.moseeker.profile.thrift;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.moseeker.baseorm.dao.configdb.ConfigSysCvTplDao;
import com.moseeker.baseorm.dao.dictdb.DictConstantDao;
import com.moseeker.baseorm.dao.profiledb.ProfileOtherDao;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysCvTplRecord;
import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.profile.service.ProfileOtherService;
import com.moseeker.thrift.gen.config.ConfigCustomMetaVO;
import com.moseeker.profile.service.impl.ProfileService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictConstantDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import com.moseeker.thrift.gen.profile.service.ProfileOtherThriftService;
import java.util.Map;
import java.util.stream.Collectors;
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


    @Autowired
    private ConfigSysCvTplDao configSysCvTplDao;

    @Autowired
    private DictConstantDao dictConstantDao;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileOtherService otherService;

    @Override
    public List<ProfileOtherDO> getResources(CommonQuery query) throws TException {
        try {
            return ResponseUtils.getNotNullValue(dao.getDatas(QueryConvert.commonQueryConvertToQuery(query)), new ArrayList<ProfileOtherDO>());
        } catch (Exception e) {
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
            List<ProfileOtherDO> otherDOList = otherService.addOthers(Others);
            return ResponseUtils.getNotNullValue(otherDOList, new ArrayList<ProfileOtherDO>());
        } catch (Exception e) {
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
            ProfileOtherDO otherDO = otherService.addOther(Other);
            return ResponseUtils.getNotNullValue(otherDO, new ProfileOtherDO());
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
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
            int[] others = otherService.putOthers(Others);
            return Arrays.asList(ArrayUtils.toObject(others));
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
            return otherService.putOther(Other);
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

    @Override
    public Response getCustomMetaData(int companyId, boolean selectAll) throws BIZException, TException {
        List<ConfigCustomMetaVO> configCustomMetaDatas = new ArrayList<>();
        try {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            if (!selectAll) {
                queryBuilder.where("company_id", companyId).or("company_id", 0);
            }
            queryBuilder.and("disable", 0);
            queryBuilder.orderBy("priority");
            List<ConfigSysCvTplRecord> configSysCvTplRecordList = configSysCvTplDao.getRecords(queryBuilder.buildQuery());
            if (configSysCvTplRecordList != null && configSysCvTplRecordList.size() > 0) {
                configCustomMetaDatas = configSysCvTplRecordList.stream().map(m -> BeanUtils.DBToBean(m, ConfigCustomMetaVO.class)).collect(Collectors.toList());
                configCustomMetaDatas.stream().filter(f -> f.getConstantParentCode() != 0).forEach(e -> {
                    queryBuilder.clear();
                    queryBuilder.where("parent_code", e.getConstantParentCode());
                    List<DictConstantDO> dictConstantDO = dictConstantDao.getDatas(queryBuilder.buildQuery());
                    String dictconstantValue = JSON.toJSONString(dictConstantDO, new PropertyFilter() {
                            @Override
                            public boolean apply(Object object, String name, Object value) {
                                if ("code".equals(name) || "name".equals(name)){
                                    return true;
                                }
                                return false;
                            }
                        }
                    );
                    e.setConstantValue(dictconstantValue);
                });
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                throw (BIZException) e;
            } else {
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
            }
        }
        return ResponseUtils.success(configCustomMetaDatas);
    }

    @Override
    public Response checkProfileOther(int userId, int positionId) throws TException {
        return profileService.checkProfileOther(userId, positionId);
    }

    @Override
    public Response getProfileOther(String params) throws BIZException, TException {
        return profileService.getProfileOther(params);
    }

    @Override
    public Response otherFieldsCheck(int positionId, String fields) throws BIZException, TException {
        return profileService.otherFieldsCheck(positionId, fields);
    }

    @Override
    public Response getProfileOtherByPosition(int userId, int accountId) throws BIZException, TException {
        try {
            long start = System.currentTimeMillis();
            Map<String, Object> others = profileService.getApplicationOther(userId, accountId);
            Map<String, Object> profilrCamle = StringUtils.convertUnderKeyToCamel(others);
            long end = System.currentTimeMillis();
            logger.info("getProfileOtherByPosition others time :{}", end-start);
            return ResponseUtils.success(profilrCamle);
        }catch (CommonException e){
            return ResponseUtils.fail(e.getCode(),e.getMessage());
        }
    }


}
