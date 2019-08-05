package com.moseeker.company.thrift;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.Category;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.company.exception.ExceptionFactory;
import com.moseeker.company.service.impl.CompanyPcService;
import com.moseeker.company.service.impl.CompanyService;
import com.moseeker.company.service.impl.vo.GDPRProtectedInfoVO;
import com.moseeker.entity.CompanyConfigEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.company.service.CompanyServices.Iface;
import com.moseeker.thrift.gen.company.struct.*;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyMobotConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrImporterMonitorDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.employee.struct.RewardConfig;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompanyServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public CompanyServicesImpl(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @Autowired
    private CompanyService service;

    @Autowired
    private CompanyConfigEntity companyConfigEntity;
    @Autowired
    private CompanyPcService companyPcService;

    public Response getAllCompanies(CommonQuery query) {
        try{
            return service.getAllCompanies(query);
        }catch(Exception e){
            return ResponseUtils.fail(e.getMessage());
        }
    }

	@Override
	public Response add(Hrcompany company) throws TException {
		return service.add(company);
	}

	@Override
	public Response getResource(CommonQuery query) throws TException {
		return service.getResource(query);
	}

	@Override
	public Response getResources(CommonQuery query) throws TException {
		return service.getResources(query);
	}



	@Override
	public Response getPcBanner(int page, int pageSize){
		// TODO Auto-generated method stub
		try{
			return service.getPcBannerByPage(page,pageSize);
		}catch(Exception e){
			logger.info(e.getMessage(),e);
		}
		return  ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
	}
    @Override
    public Response getWechat(long companyId, long wechatId) throws TException {
        // TODO Auto-generated method stub
        return service.getWechat(companyId, wechatId);
    }

    @Override
    public List<CompanyForVerifyEmployee> getGroupCompanies(int companyId) throws BIZException, TException {
        try {
            return service.getGroupCompanies(companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public boolean isGroupCompanies(int companyId) throws BIZException, TException {
        try {
            return service.isGroupCompanies(companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 更新公司员工认证配置
     *
     * @param companyId
     * @param authMode
     * @param emailSuffix
     * @param custom
     * @param customHint
     * @param questions
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public boolean updateEmployeeBindConf(int companyId, int authMode, String emailSuffix, String custom, String customHint, String questions, String filePath, String fileName, int type, int hraccountId) throws BIZException, TException {
        try {
            int result = service.updateHrEmployeeCertConf(companyId, authMode, emailSuffix, custom, customHint, questions, filePath, fileName, type, hraccountId);
            if (result > 0) {
                return true;
            }
            return false;
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public boolean setWorkWechatEmployeeBindConf(int companyId, int hraccountId, String corpId, String secret) throws BIZException, TException {
        try {
            return service.setWorkWechatEmployeeBindConf(companyId, hraccountId, corpId, secret);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 重新获取企业微信员工认证配置的access token
     * @param companyId
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public boolean updateWorkWeChatConfToken(int companyId) throws BIZException, TException {
        try {
            return service.updateWorkWeChatConfToken(companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public WorkWxCertConf getWorkWechatEmployeeBindConf(int companyId) throws BIZException, TException {
        try {
            return service.getWorkWechatEmployeeBindConf(companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 获取公司员工认证配置
     *
     * @param companyId 公司ID
     * @return
     * @throws
     */
    @Override
    public CompanyCertConf getHrEmployeeCertConf(int companyId, int type, int accountId) throws BIZException, TException {
        try {
            return service.getHrEmployeeCertConf(companyId, type, accountId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 获取公司积分配置
     *
     * @param companyId
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public List<RewardConfig> getCompanyRewardConf(int companyId) throws BIZException, TException {
        List<RewardConfig> result = new ArrayList<>();
        try {
            result = companyConfigEntity.getRerawConfig(companyId, false);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionFactory.buildException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS);
        }
        return result;
    }

    /**
     * 更新公司积分配置
     *
     * @param companyId
     * @param rewardConfigs
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public Response updateCompanyRewardConf(int companyId, List<RewardConfig> rewardConfigs) throws BIZException, TException {
        try {
            return service.updateCompanyRewardConf(companyId, rewardConfigs);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }


    /**
     * 获取公司部门与职能信息(员工认证补填字段显示)
     *
     * @param companyId
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public CompanyOptions getCompanyOptions(int companyId) throws BIZException, TException {
        try {
            return service.getCompanyOptions(companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 添加公司员工认证模板数据
     *
     * @param comanyId    公司编号
     * @param hraccountId HR ID
     * @param type        导入的数据类型 要导入的表：0：user_employee 1: job_position 2:hr_company
     * @param file        文件的绝对路径
     * @param status      导入的状态
     * @param message     操作信息
     * @param fileName    导入的文件名
     * @return response
     */
    @Override
    public Response addImporterMonitor(int comanyId, int hraccountId, int type, String file, int status, String message, String fileName) throws BIZException, TException {
        try {
            return service.addImporterMonitor(comanyId, hraccountId, type, file, status, message, fileName);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }

    }

    /**
     * 查找公司认证模板数据（取最新一条数据）
     *
     * @param comanyId    公司Id
     * @param hraccountId hrId
     * @param type        要导入的表：0：user_employee 1: job_position 2:hr_company
     * @return
     * @throws BIZException
     */
    @Override
    public HrImporterMonitorDO getImporterMonitor(int comanyId, int hraccountId, int type) throws BIZException, TException {
        try {
            return service.getImporterMonitor(comanyId, hraccountId, type);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 员工认证开关
     *
     * @param companyId 公司Id
     * @param disable   是否开启 0开启 1关闭
     * @return
     */
    @Override
    public Response bindingSwitch(int companyId, int disable) throws BIZException, TException {
        try {
            return service.bindingSwitch(companyId, disable);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }
    /*
        获取company的details,包含team
     */
    @Override
    public Response companyDetails(int companyId){
        try{
            Map<String,Object> map=companyPcService.getCompanyDetail(companyId);
            return ResponseUtils.success(map);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }
    /*
    获取公司信息，不带团队信息的
     */
    @Override
    public Response companyMessage(int companyId) throws BIZException, TException {
        try{
            Map<String,Object> map=companyPcService.getCompanyMessage(companyId);
            return ResponseUtils.success(map);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }
    /*
      获取付费公司和世界五百强公司
     */
    @Override
    public Response companyPaidOrFortune() throws BIZException, TException {
        try{
            List<Map<String,Object>> list=companyPcService.getCompanyFourtuneAndPaid();
            if(StringUtils.isEmptyList(list)){
                return ResponseUtils.success("");
            }
            return ResponseUtils.success(list);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getTalentPoolStatus(int hrId, int companyId) throws BIZException, TException {
        try{
            int result=service.getTalentPoolSwitch(hrId,companyId);
            Map<String,Object> map=new HashMap<>();
            if(result==-1){
                return ResponseUtils.fail(1,"此账号不是此公司的账号");
            }
            if(result==-2){
                return ResponseUtils.fail(1,"此公司无配置,联系客服人员");
            }
            if(result==-3){
                return ResponseUtils.fail(1,"该公司不是付费公司，无法使用该功能");
            }
            map.put("open",result);
            return ResponseUtils.success(map);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public HrCompanyConfDO getCompanyConfById(int companyId) throws BIZException {
        return service.getHrCompanyConfById(companyId);
    }


    @Override
    public Response updateHrCompanyConf(HrCompanyConf hrCompanyConf) throws BIZException, TException {
        try{
            return service.updateHrCompanyConf(hrCompanyConf);

        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response addHrAccountAndCompany(String companyName, String mobile, int wxuserId, String remoteIp, int source, int hr_source) throws BIZException, TException {
        try {
            return service.addHrAccountAndCompany(companyName, mobile, wxuserId, remoteIp, (byte)source, (byte)hr_source);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response getFeatureById(int id) throws BIZException, TException {
        try {
            HrCompanyFeature hrCompanyFeature=service.getCompanyFeatureById(id);
            if(hrCompanyFeature==null){
                hrCompanyFeature=new HrCompanyFeature();
            }
            String result=JSON.toJSONString(hrCompanyFeature,serializeConfig);
            return ResponseUtils.successWithoutStringify(result);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response getFeatureByCompanyId(int companyId) throws BIZException, TException {
        try {
            List<HrCompanyFeature> list=service.getCompanyFeatureByCompanyId(companyId);
            if(StringUtils.isEmptyList(list)){
                list=new ArrayList<>();
            }
            String result=JSON.toJSONString(list,serializeConfig);
            return ResponseUtils.successWithoutStringify(result);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response updateCompanyFeature(HrCompanyFeatureDO data) throws BIZException, TException {
        try {
            int result= service.updateCompanyFeature(data);
            return ResponseUtils.success(result);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response updateCompanyFeatures(List<HrCompanyFeatureDO> dataList) throws BIZException, TException {
        try {
            int result= service.updateCompanyFeatureList(dataList);
            return ResponseUtils.success(result);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response addCompanyFeature(HrCompanyFeatureDO data) throws BIZException, TException {
        try {
            int result= service.addCompanyFeature(data);
            return ResponseUtils.success(result);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response addCompanyFeatures(List<HrCompanyFeatureDO> dataList) throws BIZException, TException {
        try {
            int result= service.addCompanyFeatureList(dataList);
            if(result>0){
                return ResponseUtils.success(result);
            }else{
                return ResponseUtils.fail("福利特色最多只能有8个");
            }

        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response getCompanyFeatureIdList(List<Integer> dataList) throws BIZException, TException {
        try {
            List<HrCompanyFeature> list=service.getCompanyFeatureByIdList(dataList);
            if(StringUtils.isEmptyList(list)){
                list=new ArrayList<>();
            }
            String result=JSON.toJSONString(list,serializeConfig);
            return ResponseUtils.successWithoutStringify(result);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response getWechatBySignature(String signature, int companyId) throws BIZException, TException {
        HrWxWechatDO wechatDO = companyPcService.getHrWxWechatDOBySignature(signature, companyId);
        return ResponseUtils.success(wechatDO);
    }

    @Override
    public Response updateHrCompanyConfStatus(int status, int companyId) throws BIZException, TException {
        try {
            Response result= companyPcService.updateComapnyConfStatus(status, companyId);
            return result;
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response findSubAccountNum(int companyId) throws BIZException, TException {
        try {
            Response result= service.findSubAccountNum(companyId);
            return result;
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response updateWechatThenm(int status, int companyId) throws BIZException, TException {
        try {
            Response result= service.upsertWechatTheme(companyId, status);
            return result;
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response getCompanyWechatList(int companyId) throws BIZException, TException {
        try {
            List<HrCompanyWechatDO> infoList = service.getCompanyInfoByTemplateRank(companyId);
            String result= JSON.toJSONString(infoList,serializeConfig);
            return ResponseUtils.successWithoutStringify(result);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public List<GDPRProtectedInfo> validateGDPR(List<Integer> userIds, int companyId) throws BIZException, TException {
        try {
            List<GDPRProtectedInfoVO> vos = service.validateGDPR(userIds, companyId);

            return vos.stream().map(gdprProtectedInfoVO -> {
                GDPRProtectedInfo gdprProtectedInfo = new GDPRProtectedInfo();
                BeanUtils.copyProperties(gdprProtectedInfoVO, gdprProtectedInfo);
                return gdprProtectedInfo;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public boolean fetchGDPRSwitch(int companyId) throws BIZException, TException {
        try {
            return service.fetchGDPRSwitch(companyId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public boolean fetchGDPRSwitchByHR(int hrId) throws BIZException, TException {
        try {
            return service.fetchGDPRSwitchByHR(hrId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public HrCompanyMobotConfDO getMobotConf(int companyId) throws BIZException, TException {
        try {
            return companyPcService.getMobotConf(companyId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public HrCompanyMobotConfDO updateMobotConf(HrCompanyMobotConfDO mobotConf) throws BIZException, TException {
        try {
            return companyPcService.updateMobotConf(mobotConf);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }


    /*
     *
     *获取当前公司的开关权限
     *@Param  moduleNames 模块名
     *@Param  companyId 公司Id
     * */
    @Override
    public List<CompanySwitchVO> switchCheck(int companyId, List<String> moduleNames) throws BIZException, TException {
        try {
            return service.switchCheck(companyId,moduleNames);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }
    /*
     *
     *获取当前公司的单个开关权限
     *@Param  moduleNames 模块名
     *@Param  companyId 公司Id
     * */
    @Override
    public CompanySwitchVO companySwitch(int companyId, String moduleNames) throws BIZException, TException {
        try {
            return service.companySwitch(companyId,moduleNames);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    /*
     *
     *添加新的公司开关权限
     *@Param appid
     *@Param CompanySwitchVO 公司开关对象
     *
     *
     * */
    @Override
    public CompanySwitchVO switchPost(CompanySwitchVO companySwitchVO) throws BIZException, TException {
        try {
            return service.switchPost(companySwitchVO);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    /*
     *
     *更新当前公司的开关权限
     *@Param appid
     *@Param CompanySwitchVO 公司开关对象
     *
     * */
    @Override
    public CompanySwitchVO switchPatch(CompanySwitchVO companySwitchVO) throws BIZException, TException {
        try {
            return service.switchPatch(companySwitchVO);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    /**
     * 获取公司员工认证后补填字段配置信息列表
     *
     * @param companyId
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public List<HrEmployeeCustomFieldsVO> getHrEmployeeCustomFields(int companyId) throws BIZException, TException {
        try {
            return service.getHrEmployeeCustomFields(companyId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }
}

