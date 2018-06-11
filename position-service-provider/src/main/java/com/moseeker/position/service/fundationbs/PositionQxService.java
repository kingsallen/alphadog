package com.moseeker.position.service.fundationbs;

import com.moseeker.baseorm.dao.campaigndb.CampaignHeadImageDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyFeatureDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionHrCompanyFeatureDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionHrCompanyFeature;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionHrCompanyFeatureRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.service.position.pojo.PositionFeaturePojo;
import com.moseeker.position.utils.CommonMessage;
import com.moseeker.thrift.gen.dao.struct.CampaignHeadImageVO;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignHeadImageDO;
import com.moseeker.thrift.gen.position.struct.JobPositionHrCompanyFeatureDO;
import com.moseeker.thrift.gen.position.struct.PositionDetails;
import com.moseeker.thrift.gen.position.struct.PositionDetailsListVO;
import com.moseeker.thrift.gen.position.struct.PositionDetailsVO;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YYF
 *
 * Date: 2017/4/17
 *
 * Gamma 0.9 新增接口
 *
 * Project_name :alphadog
 */
@Service
public class PositionQxService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CampaignHeadImageDao campaignHeadImageDao;
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private JobPositionHrCompanyFeatureDao jobPositionHrCompanyFeatureDao;
    @Autowired
    private HrCompanyFeatureDao hrCompanyFeatureDao;

    /**
     * 职位头图查询
     *
     * @return campaignHeadImageVO
     */
    @CounterIface
    public CampaignHeadImageVO headImage() {
        CampaignHeadImageVO campaignHeadImageVO = new CampaignHeadImageVO();
        try {
//            CommonQuery commonQuery = new CommonQuery();
//            commonQuery.setSortby("create_time");
//            commonQuery.setOrder("desc");
            Query query=new Query.QueryBuilder().orderBy("create_time", Order.DESC).buildQuery();
            CampaignHeadImageDO campaignHeadImageDO = campaignHeadImageDao.getData(query);     //campaignDBDao.headImage(commonQuery);
            if (campaignHeadImageDO.getId() != 0) {
                campaignHeadImageVO.setMessage(CommonMessage.SUCCESS.getMessage());
                campaignHeadImageVO.setStatus(CommonMessage.SUCCESS.getStatus());
                campaignHeadImageVO.setData(campaignHeadImageDO);
            } else {
                campaignHeadImageVO.setMessage(CommonMessage.HEAD_IMAGE_BLANK.getMessage());
                campaignHeadImageVO.setStatus(CommonMessage.HEAD_IMAGE_BLANK.getStatus());
            }
        } catch (Exception e) {
            campaignHeadImageVO.setMessage(CommonMessage.EXCEPTION.getMessage());
            campaignHeadImageVO.setStatus(CommonMessage.EXCEPTION.getStatus());
            logger.error(e.getMessage(), e);
        }
        return campaignHeadImageVO;
    }

    /**
     * 查询职位的详细信息
     *
     * @return positionDetailsVO
     */
    @CounterIface
    public PositionDetailsVO positionDetails(Integer positionId){
        PositionDetailsVO positionDetailsVO = new PositionDetailsVO();
        try{
            if (positionId == 0) {
                positionDetailsVO.setStatus(CommonMessage.POSITIONID_BLANK.getStatus());
                positionDetailsVO.setMessage(CommonMessage.POSITIONID_BLANK.getMessage());
                return positionDetailsVO;
            }
            PositionDetails positionDetails = jobPositionDao.positionDetails(positionId);//        jobDbDao.positionDetails(positionId);
            if (positionDetails.getId() != 0) {
                positionDetailsVO.setData(positionDetails);
                positionDetailsVO.setStatus(CommonMessage.SUCCESS.getStatus());
                positionDetailsVO.setMessage(CommonMessage.SUCCESS.getMessage());
            } else {
                positionDetailsVO.setStatus(CommonMessage.POSITION_NONEXIST.getStatus());
                positionDetailsVO.setMessage(CommonMessage.POSITION_NONEXIST.getMessage());
            }
        } catch (Exception e) {
            positionDetailsVO.setMessage(CommonMessage.EXCEPTION.getMessage());
            positionDetailsVO.setStatus(CommonMessage.EXCEPTION.getStatus());
            logger.error(e.getMessage(), e);
        }
        return positionDetailsVO;
    }


    /**
     * 查询公司热招职位的详细信息
     *
     * @return PositionDetailsListVO
     * 需要仔细测试
     */
    @CounterIface
    public PositionDetailsListVO companyHotPositionDetailsList(Integer companyId, Integer page, Integer per_age) {
        logger.info("companyHotPositionDetailsList companyId:{}, page:{}, per_age", companyId, page, per_age);
        PositionDetailsListVO positionDetailsListVO = new PositionDetailsListVO();
        try {
            if (companyId == 0) {
                positionDetailsListVO.setStatus(CommonMessage.COMPANYID_BLANK.getStatus());
                positionDetailsListVO.setMessage(CommonMessage.COMPANYID_BLANK.getMessage());
                return positionDetailsListVO;
            }
            List<PositionDetails> list =jobPositionDao.hotPositionDetailsList(companyId,page,per_age);
            if (list != null && list.size() > 0) {
                list.forEach(positionDetails -> {
                    positionDetails.setSalaryBottom(positionDetails.getSalaryBottom());
                    positionDetails.setSalaryTop(positionDetails.getSalaryTop());
                });
            }
            if (list != null && list.size() > 0) {
                positionDetailsListVO.setData(list);
                positionDetailsListVO.setPage(page);
                positionDetailsListVO.setPer_age(per_age);
                positionDetailsListVO.setStatus(CommonMessage.SUCCESS.getStatus());
                positionDetailsListVO.setMessage(CommonMessage.SUCCESS.getMessage());
            } else {
                positionDetailsListVO.setStatus(CommonMessage.POSITIONLIST_NONEXIST.getStatus());
                positionDetailsListVO.setMessage(CommonMessage.POSITIONLIST_NONEXIST.getMessage());
            }
        } catch (Exception e) {
            positionDetailsListVO.setMessage(CommonMessage.EXCEPTION.getMessage());
            positionDetailsListVO.setStatus(CommonMessage.EXCEPTION.getStatus());
            logger.error(e.getMessage(), e);
        }
        if (positionDetailsListVO.getData() != null && positionDetailsListVO.getData().size() > 0) {
            positionDetailsListVO.getData().forEach(positionDetails -> {
                positionDetails.setSalaryBottom(positionDetails.getSalaryBottom());
                positionDetails.setSalaryTop(positionDetails.getSalaryTop());
            });
        }

        return positionDetailsListVO;
    }


    /**
     * 职位相关职位接口
     *
     * @return positionDetailsVO
     */
    @CounterIface
    public PositionDetailsListVO similarityPositionDetailsList(Integer pid, Integer page, Integer per_age) {
        PositionDetailsListVO positionDetailsList = new PositionDetailsListVO();
        try {
            if (pid == 0) {
                positionDetailsList.setStatus(CommonMessage.POSITIONID_BLANK.getStatus());
                positionDetailsList.setMessage(CommonMessage.POSITIONID_BLANK.getMessage());
                return positionDetailsList;
            }
            List<PositionDetails> list =jobPositionDao.similarityPositionDetailsList(pid, page, per_age);// jobDbDao.similarityPositionDetailsList(commonQuery);
            if (list != null && list.size() > 0) {
                positionDetailsList.setStatus(CommonMessage.SUCCESS.getStatus());
                positionDetailsList.setMessage(CommonMessage.SUCCESS.getMessage());
                positionDetailsList.setData(list);
                positionDetailsList.setPage(page);
                positionDetailsList.setPer_age(per_age);
            } else {
                positionDetailsList.setStatus(CommonMessage.SIMILARITYPOSITIONLIST_NONEXIST.getStatus());
                positionDetailsList.setMessage(CommonMessage.SIMILARITYPOSITIONLIST_NONEXIST.getMessage());
            }
        } catch (Exception e) {
            positionDetailsList.setMessage(CommonMessage.EXCEPTION.getMessage());
            positionDetailsList.setStatus(CommonMessage.EXCEPTION.getStatus());
            logger.error(e.getMessage(), e);
        }
        return positionDetailsList;
    }
    /*
     根据positionId获取公司福利
     */
    @CounterIface
    public List<HrCompanyFeature> getPositionFeature(int pid){
        List<JobPositionHrCompanyFeature> positionFeatureList=jobPositionHrCompanyFeatureDao.getPositionFeatureList(pid);
        List<Integer> fidList=this.getFidList(positionFeatureList);
        if(StringUtils.isEmptyList(fidList)){
            return null;
        }
        List<HrCompanyFeature> result=hrCompanyFeatureDao.getFeatureListByIdList(fidList);
        return result;
    }

    private List<Integer> getFidList(List<JobPositionHrCompanyFeature> positionFeatureList){
        if(StringUtils.isEmptyList(positionFeatureList)){
            return null;
        }
        List<Integer> list=new ArrayList<>();
        for(JobPositionHrCompanyFeature jobPositionHrCompanyFeature:positionFeatureList){
            list.add(jobPositionHrCompanyFeature.getFid());
        }
        return list;
    }
    /*
     更新职位福利特色
     */
    @CounterIface
    @Transactional
    public int updatePositionFeature(int pid,int fid){
        jobPositionHrCompanyFeatureDao.deletePositionFeature(pid);
        jobPositionHrCompanyFeatureDao.addPositionFeature(pid,fid);
        return 1;
    }
    @CounterIface
    @Transactional
    public int updatePositionFeatureList(int pid,List<Integer> fidList){
        jobPositionHrCompanyFeatureDao.deletePositionFeature(pid);
        List<JobPositionHrCompanyFeatureRecord> list=new ArrayList<>();
        for(Integer fid:fidList){
            JobPositionHrCompanyFeatureRecord record=new JobPositionHrCompanyFeatureRecord();
            record.setFid(fid);
            record.setPid(pid);
            list.add(record);
        }
        jobPositionHrCompanyFeatureDao.addAllRecord(list);

        return 0;
    }
    @CounterIface
    @Transactional
    public int updatePositionFeatureBatch(List<JobPositionHrCompanyFeatureDO> list){
        if(StringUtils.isEmptyList(list)){
            return 0;
        }
        List<JobPositionHrCompanyFeatureRecord> featureList=new ArrayList<>();
        List<Integer> pidList=new ArrayList<>();
        for(JobPositionHrCompanyFeatureDO DO:list){
            JobPositionHrCompanyFeatureRecord record=new JobPositionHrCompanyFeatureRecord();
            record.setPid(DO.getPid());
            record.setFid(DO.getFid());
            if(!pidList.contains(DO.getPid())){
                pidList.add(DO.getPid());
            }
            featureList.add(record);
        }
        if(!StringUtils.isEmptyList(pidList)){
            jobPositionHrCompanyFeatureDao.deletePositionFeatureBatch(pidList);
            jobPositionHrCompanyFeatureDao.addAllRecord(featureList);
            return 1;
        }
        return 0;
    }
    @CounterIface
    public List<PositionFeaturePojo> getPositionFeatureBatch(List<Integer> pidList){
        if(StringUtils.isEmptyList(pidList)){
            return new ArrayList<>();
        }
        List<JobPositionHrCompanyFeature> dataList=jobPositionHrCompanyFeatureDao.getPositionFeatureBatch(pidList);
        List<Integer> fidList=this.getFidList(dataList);
        if(!StringUtils.isEmptyList(fidList)){
            List<HrCompanyFeature> data=hrCompanyFeatureDao.getFeatureListByIdList(fidList);
            if(!StringUtils.isEmptyList(data)){
                List<PositionFeaturePojo> result=new ArrayList<>();
                for(Integer pid:pidList){
                    List<HrCompanyFeature> resultElem=new ArrayList<>();
                    for(JobPositionHrCompanyFeature positionFeature:dataList){
                        int positionId=positionFeature.getPid();
                        int featureId=positionFeature.getFid();
                        if(pid==positionId){
                            for(HrCompanyFeature hrCompanyFeature:data){
                                if(hrCompanyFeature.getId()==featureId){
                                    resultElem.add(hrCompanyFeature);
                                    break;
                                }
                            }
                        }
                    }
                    if(!StringUtils.isEmptyList(resultElem)){
                        PositionFeaturePojo pojo=new PositionFeaturePojo();
                        pojo.setFeatureList(resultElem);
                        pojo.setPid(pid);
                        result.add(pojo);
                    }
                }
                return result;
            }
        }
        return new ArrayList<>();

    }



}
