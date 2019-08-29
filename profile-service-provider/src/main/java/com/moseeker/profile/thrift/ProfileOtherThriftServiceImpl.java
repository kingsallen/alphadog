package com.moseeker.profile.thrift;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.moseeker.baseorm.dao.configdb.ConfigSysCvTplDao;
import com.moseeker.baseorm.dao.dictdb.DictConstantDao;
import com.moseeker.baseorm.dao.profiledb.ProfileOtherDao;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysCvTplRecord;
import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.OmsSwitchEnum;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.profile.service.ProfileOtherService;
import com.moseeker.entity.pojos.RequireFieldInfo;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.company.struct.CompanySwitchVO;
import com.moseeker.thrift.gen.config.ConfigCustomMetaVO;
import com.moseeker.profile.service.impl.ProfileService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictConstantDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import com.moseeker.thrift.gen.profile.service.ProfileOtherThriftService;
import com.moseeker.thrift.gen.profile.struct.RequiredFieldInfo;
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

    CompanyServices.Iface companyServices = ServiceManager.SERVICE_MANAGER.getService(CompanyServices.Iface.class);


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
                List<CompanySwitchVO> switches = companyServices.switchCheck(companyId, Arrays.asList(OmsSwitchEnum.instanceFromValue(15).getName()));
                if(switches!=null&&switches.size()>0&&switches.get(0).getValid()==0){
                    configSysCvTplRecordList = configSysCvTplRecordList.stream().filter(e->{
                        //若oms开关状态为关，过滤掉身份证识别组件
                        String fieldName = e.getFieldName();
                       return !(Constant.IDCARD_RECOG.equals(fieldName)||Constant.IDPHOTO_FRONT.equals(fieldName)||
                               Constant.IDPHOTO_BACK.equals(fieldName));
                    }).collect(Collectors.toList());
                }
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
        try {
            return profileService.checkProfileOther(userId, positionId);
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
    public Response getProfileOther(String params) throws BIZException, TException {
        try {
            return profileService.getProfileOther(params);
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
    public Response otherFieldsCheck(int positionId, String fields) throws BIZException, TException {
        try {
            return profileService.otherFieldsCheck(positionId, fields);
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
    public Response getProfileOtherByPosition(int userId, int accountId, int positionId) throws BIZException, TException {
        try {
            long start = System.currentTimeMillis();
            Map<String, Object> others = profileService.getApplicationOtherCommon(userId, accountId, positionId);
            List<Integer> updateList = (List<Integer>)others.getOrDefault("updateList", null);
            profileService.viewApplicationsByApplication(accountId, updateList);
            List<Integer> positionList = (List<Integer>)others.getOrDefault("positionList", null);
            Integer profile_id = (Integer)others.getOrDefault("profile_id", 0);
            Map<String, Object> result = profileService.getProfileOther(positionList, profile_id, userId);
            Map<String, Object> profilrCamle = StringUtils.convertUnderKeyToCamel(result);
            long end = System.currentTimeMillis();
            logger.info("getProfileOtherByPosition others time :{}", end-start);
            return ResponseUtils.success(profilrCamle);
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
    public Response getProfileOtherByPositionNotViewApplication(int userId, int accountId, int positionId) throws BIZException, TException {
        try {
            long start = System.currentTimeMillis();
            Map<String, Object> others = profileService.getApplicationOtherCommon(userId, accountId, positionId);
            logger.info("=================others:{}", JSONObject.toJSONString(others));
            List<Integer> positionList = (List<Integer>)others.getOrDefault("positionList", null);
            logger.info("=================positionList:{}", JSONObject.toJSONString(positionList));
            Integer profile_id = (Integer)others.getOrDefault("profile_id", 0);
            logger.info("=================profile_id:{}", profile_id);
            Map<String, Object> result = profileService.getProfileOther(positionList, profile_id, userId);
            Map<String, Object> profilrCamle = StringUtils.convertUnderKeyToCamel(result);
            long end = System.currentTimeMillis();
            logger.info("getProfileOtherByPosition others time :{}", end-start);
            return ResponseUtils.success(profilrCamle);
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
    public Response updateSpecificResource(String params) throws TException {
        try {
            Map<String, Object> resume = JSON.parseObject(params);
            int result = otherService.putSpecificOther((Map<String, Object>) resume.get("data"), (Integer) resume.get("profile_id"));
            return ResponseUtils.success(result);
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
    public RequiredFieldInfo fetchRequireField(int positionId) throws BIZException, TException {
        try {
            RequireFieldInfo result = profileService.fetchRequireField(positionId);
            RequiredFieldInfo fieldInfo = new RequiredFieldInfo();
            org.springframework.beans.BeanUtils.copyProperties(result, fieldInfo);
            return fieldInfo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                throw (BIZException) e;
            } else {
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
            }
        }
    }


}
