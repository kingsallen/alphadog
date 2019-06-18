package com.moseeker.position.thrift;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.SyncRequestType;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.pojos.JobPositionRecordWithCityName;
import com.moseeker.position.pojo.JobPostionResponse;
import com.moseeker.position.pojo.PositionMiniBean;
import com.moseeker.position.pojo.PositionSyncResultPojo;
import com.moseeker.position.pojo.SyncFailMessPojo;
import com.moseeker.position.service.JobOccupationService;
import com.moseeker.position.service.appbs.PositionBS;
import com.moseeker.position.service.fundationbs.*;
import com.moseeker.position.service.position.pojo.PositionFeaturePojo;
import com.moseeker.position.service.third.ThirdPositionService;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.CampaignHeadImageVO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPcReportedDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionExtDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import com.moseeker.thrift.gen.position.service.PositionServices.Iface;
import com.moseeker.thrift.gen.position.struct.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PositionServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public PositionServicesImpl(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @Autowired
    private PositionService service;
    @Autowired
    private JobOccupationService customService;
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private PositionQxService positionQxService;
    @Autowired
    private ThirdPositionService thirdPositionService;
    @Autowired
    private PositionPcService positionPcService;
    @Autowired
    private PositionThridService positionThridService;
    @Autowired
    private PositionBS positionBS;
    @Autowired
    private PositionMiniService positionMiniService;

    @Autowired
    private EmployeeEntity employeeEntity;
    /**
     * 获取推荐职位
     * <p></p>
     */
    @Override
    public Response getRecommendedPositions(int pid) {
        return service.getRecommendedPositions(pid);
    }

    @Override
    public Response verifyCustomize(int positionId) throws TException {
        return service.verifyCustomize(positionId);
    }

    /**
     * 根据职位Id获取当前职位信息
     */
    @Override
    public Response getPositionById(int positionId) throws TException {
        try {
            return service.getPositionById(positionId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getResources(CommonQuery query) throws TException {
        try {
            List<JobPositionRecordWithCityName> list = service.getPositionRecords(QueryConvert.commonQueryConvertToQuery(query));
            List<Position> structs = BeanUtils.DBToStruct(Position.class, list);

            if (!structs.isEmpty()) {
                return ResponseUtils.success(structs);
            }

        } catch (Exception e) {
            logger.error("getResources error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    /**
     * @return response
     * @throws TException time 2016-11-21
     * @author zztaiwll
     */
    @Override
    public Response CustomField(String param) throws TException {
        // TODO Auto-generated method stub
        return customService.getCustomField(param);
    }

    @Override
    public boolean ifAllowRefresh(int positionId, int account_id) {
        try {
            return false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<WechatPositionListData> getPositionList(WechatPositionListQuery query) throws TException {
        return service.getPositionList(query);
    }

    @Override
    public List<JobPositionExtDO> getPositionExtList(List<Integer> ids) throws TException {
        return service.getPositionExtList(ids);
    }

    @Override
    public List<RpExtInfo> getPositionListRpExt(List<Integer> pids) throws TException {
        try {
//            return service.getPositionListRpExt(pids);
            return service.getNewPositionListRpExt(pids);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public WechatShareData getShareInfo(int hb_config_id) throws TException {
        return service.getShareInfo(hb_config_id);
    }

    /**
     * 职位列表头图信息
     */
    @Override
    public CampaignHeadImageVO headImage() throws TException {
        return positionQxService.headImage();
    }

    /**
     * 查询单个职位详情
     */
    @Override
    public PositionDetailsVO positionDetails(int positionId) throws TException {
        return positionQxService.positionDetails(positionId);
    }

    /**
     * 查询公司热招职位的详细信息
     */
    @Override
    public PositionDetailsListVO companyHotPositionDetailsList(int companyId, int page, int per_age) throws TException {
        return positionQxService.companyHotPositionDetailsList(companyId, page, per_age);
    }

    /**
     * 职位相关职位接口
     */
    @Override
    public PositionDetailsListVO similarityPositionDetailsList(int pid, int page, int per_age) throws TException {
        return positionQxService.similarityPositionDetailsList(pid, page, per_age);
    }

    @Override
    public List<WechatRpPositionListData> getRpPositionList(int hb_config_id,int pageNum,int pageSize) throws TException {
        try {
            logger.info("PositionServicesImpl getRpPositionList hb_config_id:{}, pageNum:{}, pageSize:{}",
                    hb_config_id, pageNum, pageSize);
            return service.getRpPositionList(hb_config_id,pageNum,pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getThirdPartyPositions(CommonQuery query) throws TException {
        try {
            return service.getThirdPartyPositions(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
    }

    @Override
    public Response batchHandlerJobPostion(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            return ResponseUtils.success(service.batchHandlerJobPostionAdapter(batchHandlerJobPostion));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response saveAndSync(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        logger.info("PositionServicesImpl saveAndSync");
        JobPostionResponse response=service.batchHandlerJobPostionAdapter(batchHandlerJobPostion);

        logger.info("PositionServicesImpl saveAndSync response:{}", JSONObject.toJSONString(response));
        List<SyncFailMessPojo> syncFailMessPojolistList=new ArrayList<>();
        int syncingCounts=0;

        List<ThirdPartyPositionForm> syncDatas=response.getSyncData();
        for (ThirdPartyPositionForm form:syncDatas){
            if(form.getPositionId()==0 || StringUtils.isEmptyList(form.getChannels())){
                continue;
            }

            form.setAppid(batchHandlerJobPostion.getAppid());
            //设置请求端类型为ATS端
            form.setRequestType(SyncRequestType.ATS.code());
        }

        logger.info("PositionServicesImpl saveAndSync syncDatas:{}", JSONObject.toJSONString(syncDatas));
        logger.info("syncDatas: "+JSON.toJSONString(syncDatas));

        try {
            List<PositionSyncResultPojo> syncResults = positionBS.syncPositionToThirdParty(syncDatas);
            for (PositionSyncResultPojo result : syncResults) {
                if (result.getSync_status() == PositionSyncResultPojo.SUCCESS) {
                    syncingCounts++;
                } else {
                    syncFailMessPojolistList.add(new SyncFailMessPojo(result.getPosition_id(), result.getChannel(), result.getSync_fail_reason()));
                }
            }
        } catch (BIZException e) {
            syncFailMessPojolistList.add(new SyncFailMessPojo(-1, -1, e.getMessage()));
        } catch (Exception e) {
            logger.info("save and sync error exception:",e);
        }

        response.setSyncFailMessPojolist(syncFailMessPojolistList);
        response.setSyncingCounts(syncingCounts);
        response.setSyncData(null);

        logger.info("PositionServicesImpl saveAndSync response:{}", JSONObject.toJSONString(response));
        return ResponseUtils.success(response);
    }

    @Override
    public Response deleteJobposition(DelePostion delePostion) throws TException {
        Integer id = null;
        if (delePostion.isSetId()) {
            id = delePostion.getId();
        }
        Integer companyId = null;
        if (delePostion.isSetCompany_id()) {
            companyId = delePostion.getCompany_id();
        }
        Integer sourceId = null;
        if (delePostion.isSetSource_id()) {
            sourceId = delePostion.getSource_id();
        }
        try {
            return service.deleteJobposition(id, companyId, delePostion.getJobnumber(), sourceId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response getTeamIdByDepartmentName(int companyId, String departmentName) throws TException {
        try {
            return service.getTeamIdbyDepartmentName(companyId, departmentName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

	@Override
	public Response getPcRecommand(int page, int pageSize)  {
		// TODO Auto-generated method stub
		try{
			return positionPcService.getRecommendPositionPC(page, pageSize);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
		}
	}

	@Override
	public Response getPcRecommandCompany(int page,int pageSize) throws TException {
		// TODO Auto-generated method stub
		try{
			return positionPcService.getQXRecommendCompanyList(page,pageSize);
		}catch(Exception e){
			logger.info(e.getMessage(),e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
		}
	}

	@Override
	public Response getPcRecommandCompanyAll(int page, int pageSize) throws TException {
		// TODO Auto-generated method stub
		try{
			List<Map<String,Object>> list= positionPcService.getAllCompanyRecommend(page,pageSize);
			Response res= ResponseUtils.success(list);
			return res;
		}catch(Exception e){
			logger.info(e.getMessage(),e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
		}
	}

    @Override
    public Response getPcPositionDetail(int positionId) throws TException {
        try{
            Map<String,Object> map=positionPcService.getPositionDetails(positionId);
            if(map==null||map.isEmpty()){
                Response res= ResponseUtils.success("");
            }
            Response res= ResponseUtils.success(map);
            return res;
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }

    }

    @Override
    public Response getPcRecommendPosition(int positionId, int page, int pageSize) throws TException {
        try {
            List<Map<String, Object>> list = positionPcService.getRecommendPosition(positionId,page,pageSize);
            if(StringUtils.isEmptyList(list)){
                Response res= ResponseUtils.success("");
                return res;
            }
            Response res= ResponseUtils.success(list);
            return res;
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }

    }






    @Override
    public ThirdPartyPositionResult getThirdPartyPositionInfo(ThirdPartyPositionInfoForm infoForm) throws BIZException, TException {
        try {
            return thirdPositionService.getThirdPartyPositionInfo(infoForm);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }
    @Override
    public Response getPositionForThirdParty(int positionId, int channel) throws TException {
        try {
            return service.getPositionForThirdParty(positionId, channel);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, " " + e.getMessage());
        }
    }

    @Override
    public List<Integer> getPositionListForThirdParty(int channel, int type, String start_time, String end_time) throws TException {
        try {
            return service.getPositionListForThirdParty(channel, type, start_time, end_time);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
    }


    @Override
    public int updateThirdPartyPosition(HrThirdPartyPositionDO thirdPartyPosition,Map<String,String> extData) throws BIZException, TException {
        try {
            return thirdPositionService.updateThirdPartyPosition(thirdPartyPosition,extData);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int updateThirdPartyPositionWithAccount(HrThirdPartyPositionDO thirdPartyPosition, HrThirdPartyAccountDO thirdPartyAccount,Map<String,String> extData) throws BIZException, TException {
        try {
            return thirdPositionService.updateThirdPartyPositionWithAccount(thirdPartyPosition, thirdPartyAccount,extData);
        } catch (Exception e) {
            e.printStackTrace();
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response addPcReport(JobPcReportedDO jobPcReportedDO) throws TException {
        try{
            Response result=positionPcService.addPositionReport(jobPcReportedDO);
            return result;
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getPcAdvertisement(int page, int pageSize) throws TException {
        try{
            List<Map<String,Object>> list=positionPcService.getAdvertisement(page,pageSize);
            if(StringUtils.isEmptyList(list)){
                Response res= ResponseUtils.success("");
                return res;
            }
            Response res= ResponseUtils.success(list);
            return res;
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getPositionRecommendByModuleId(int page, int pageSize, int moduleId) throws TException {
        try {
            Map<String,Object> result=positionPcService.getModuleRecommendPosition(page, pageSize, moduleId);
            if(result==null||result.isEmpty()){
                return ResponseUtils.fail(1,"模块不存在或者模块已失效");
            }
            Response res= ResponseUtils.success(result);
            return res;
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getThirdpartySyncedPositions(int channel, int publisher, int companyId, int candidateSource,int page,int pageSize) throws TException {
        try{
            Map<String,Object> map=positionThridService.getThridPositionAlipay(publisher,companyId,candidateSource,page,pageSize);
            if(map==null||map.isEmpty()){
                return  ResponseUtils.success("");
            }
            return  ResponseUtils.success(map);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response putAlipayResult(int channel, int positionId, int alipayJobId) throws TException {
        try {
            int result=positionThridService.putAlipayPositionResult(channel,positionId,alipayJobId);
            if(result>0){
                return  ResponseUtils.success("");
            }else
                return  ResponseUtils.fail(1,"alipay同步结果保存失败");
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }

    }
    /*
      @auth zzt
      @param userId用户id
      @param companyId 公司id
      @param type职位的类型
      功能：获取推送的职位，用于在微信端展示
     */

    @Override
    public Response getPersonaRecomPositionList(int userId,int companyId, int type,int pageNum, int pageSize) throws TException {
        try {
            List<WechatPositionListData> result=service.getPersonaRecomPosition(userId,companyId,type,pageNum,pageSize);
            if(StringUtils.isEmptyList(result)){
                return  ResponseUtils.success("");
            }
            return  ResponseUtils.success(result);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }

    }

    @Override
    public Response positionCvConf(int positionId) throws TException {
        return service.positionCvConf(positionId);
    }

    @Override
    public Response getEmployeeRecomPositionByIds(int recomPushId,int company,int type,int pageNum,int pageSize) throws TException {
        try {
            List<WechatPositionListData> result=service.getEmployeeRecomPositionList(recomPushId,company,type,pageNum,pageSize);
            if(StringUtils.isEmptyList(result)){
                return  ResponseUtils.fail(1,"您所查找的推送不存在");
            }
            return  ResponseUtils.success(result);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response updatePosition(String param) throws TException {
        try {
            return service.updatePosition(param);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }

    }

    @Override
    public Response getMiniPositionList(int accountId, String keyword, int page, int pageSize) throws TException {
        try {
            PositionMiniBean  result=positionMiniService.getPositionMiniList(accountId,keyword,page,pageSize);
            if(result==null){
                return  ResponseUtils.success(new PositionMiniBean());
            }
            return  ResponseUtils.success(result);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getMiniPositionSuggest(int accountId, String keyword, int page, int pageSize) throws TException {
        try {
            Map<String,Object>  result=positionMiniService.getPositionMiniSug(accountId,keyword,page,pageSize);
            if(result==null||result.isEmpty()){
                return  ResponseUtils.success(new HashMap<>());
            }
            return  ResponseUtils.success(result);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getMiniPositionNumStatus(int accountId, String keyword, int page, int pageSize) throws TException {
        try {
            PositionMiniBean  result=positionMiniService.getPositionNumByStatus(accountId,keyword,page,pageSize);
            if(result==null){
                return  ResponseUtils.success(new PositionMiniBean());
            }
            return  ResponseUtils.success(result);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getFeatureByPId(int pid) throws TException {
        try {
            List<HrCompanyFeature> result=positionQxService.getPositionFeature(pid);
            if(StringUtils.isEmptyList(result)){
                result=new ArrayList<>();
            }
            String res=JSON.toJSONString(result,serializeConfig);
            return  ResponseUtils.successWithoutStringify(res);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response updatePositionFeature(int pid, int fid) throws TException {
        try {
            int result=positionQxService.updatePositionFeature(pid,fid);
            return  ResponseUtils.success(result);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response updatePositionFeatures(int pid, List<Integer> fidList) throws TException {
        try {
            int  result=positionQxService.updatePositionFeatureList(pid,fidList);
            return  ResponseUtils.success(result);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response updatePositionFeatureBatch(List<JobPositionHrCompanyFeatureDO> featureList) throws TException {
        try {
            int  result=positionQxService.updatePositionFeatureBatch(featureList);
            return  ResponseUtils.success(result);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getPositionFeatureBetch(List<Integer> pidList) throws TException {
        try {
            List<PositionFeaturePojo> list=positionQxService.getPositionFeatureBatch(pidList);
            String res=JSON.toJSONString(list, serializeConfig, SerializerFeature.DisableCircularReferenceDetect);
            return  ResponseUtils.successWithoutStringify(res);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getMiniPositionDetail(int positionId) throws TException {
        try {
            Map<String,Object>  result=positionPcService.getMiniPositionDetails(positionId);
            if(result==null){
                return  ResponseUtils.success(new HashMap<>());
            }
            return  ResponseUtils.success(result);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getMiniPositionShare(int positionId) throws TException {
        try {
            Map<String,Object>  result= positionMiniService.getPositionShareInfo(positionId);
            if(result==null){
                return  ResponseUtils.success(new HashMap<>());
            }
            return  ResponseUtils.success(result);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public List<JobPositionLiepinMappingDO> getLiepinPositionIds(int userId) throws TException {
        try {
            List<JobPositionLiepinMappingDO> list = positionBS.getLiepinPositionIds(userId);
            if(list==null || list.size() < 1){
                logger.info("==============猎聘职位id查询为空==============");
                return new ArrayList<>();
            }
            return  list;
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }
    @Override
    public List<WechatPositionListData> getReferralPositionList(Map<String, String> query) throws TException {

        return service.getReferralPositionList(query);
    }



}