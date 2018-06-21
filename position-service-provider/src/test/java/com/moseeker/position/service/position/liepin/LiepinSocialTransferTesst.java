//package com.moseeker.position.service.position.liepin;
//
//import com.alibaba.fastjson.JSON;
//import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
//import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
//import com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyPosition;
//import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
//import com.moseeker.common.util.query.Query;
//import com.moseeker.position.config.AppConfig;
//import com.moseeker.position.pojo.LiePinPositionVO;
//import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
//import com.moseeker.position.service.position.liepin.LiepinSocialPositionTransfer;
//import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
//import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
//import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
//import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author cjm
// * @date 2018-06-05 14:26
// **/
////@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(classes = AppConfig.class)
//public class LiepinSocialTransferTesst {
//    @Autowired
//    LiepinSocialPositionTransfer liepinSocialPositionTransfer;
//
//    @Autowired
//    JobPositionDao jobPositionDao;
//
//    @Autowired
//    HRThirdPartyPositionDao dao;
//
//    public LiePinPositionVO testChangeToThirdPartyPosition(ThirdPartyPosition positionForm, JobPositionDO moseekerJobPosition) throws Exception {
//        LiePinPositionVO liePinPositionVO = liepinSocialPositionTransfer.changeToThirdPartyPosition(positionForm, moseekerJobPosition, null);
//        System.out.println(liePinPositionVO);
//        return liePinPositionVO;
//    }
//
//
//    public HrThirdPartyPositionDO testToThirdPartyPosition(ThirdPartyPosition positionForm, LiePinPositionVO liePinPositionVO) throws Exception {
//        HrThirdPartyPositionDO hrThirdPartyPositionDO = liepinSocialPositionTransfer.toThirdPartyPosition(positionForm, liePinPositionVO);
//        System.out.println(hrThirdPartyPositionDO);
//        return hrThirdPartyPositionDO;
//    }
//
//    @Test
//    public void testSendSyncRequest() throws Exception {
//        Integer positionId = 19494081;
//        ThirdPartyPosition positionForm = new ThirdPartyPosition();
//        //软件/互联网开发/系统集成
//        List<String> list = new ArrayList<>();
//        list.add("030");
//        list.add("030070");
//        List<String> list1 = new ArrayList<>();
//        list1.add("050");
//        list1.add("050040");
//        List<String> list2 = new ArrayList<>();
//        list2.add("060");
//        list2.add("060070");
//        List<String> end = new ArrayList<>();
//        end.add(JSON.toJSONString(list));
//        end.add(JSON.toJSONString(list1));
//        end.add(JSON.toJSONString(list2));
//        System.out.println(end);
//        positionForm.setOccupation(end);
//        positionForm.setSalaryDiscuss(true);
//        positionForm.setSalaryBottom(4000);
//        positionForm.setSalaryTop(12000);
//        positionForm.setSalaryMonth(12);
//        List<String> featureList = new ArrayList<>();
//        String features = "";
//        featureList.add("五险一金");
//        featureList.add("sadl;sdkas;kdk;addd;a");
//        featureList.add("领导好");
//        featureList.add("15薪");
//        positionForm.setFeature(featureList);
//        Query query = new Query.QueryBuilder().where(JobPosition.JOB_POSITION.ID.getName(), positionId).buildQuery();
//        JobPositionDO moseekerJobPosition = jobPositionDao.getData(query);
//
//        AbstractPositionTransfer.TransferResult<LiePinPositionVO, LiePinPositionVO> result = new AbstractPositionTransfer.TransferResult<LiePinPositionVO, LiePinPositionVO>();
//        LiePinPositionVO liePinPositionVO = testChangeToThirdPartyPosition(positionForm, moseekerJobPosition);
//        HrThirdPartyPositionDO hrThirdPartyPositionDO = testToThirdPartyPosition(positionForm, liePinPositionVO);
//        result.setPositionWithAccount(liePinPositionVO);
//        result.setThirdPartyPositionDO(hrThirdPartyPositionDO);
//        liepinSocialPositionTransfer.sendSyncRequest(result);
//    }
//
//
//    @Test
//    public void toThirdPartyPositionForm(){
//        HrThirdPartyPositionDO hrThirdPartyPositionDO = new HrThirdPartyPositionDO();
//        hrThirdPartyPositionDO.setFeature("福利特色,五险一金");
//        hrThirdPartyPositionDO.setOccupation("123,1231");
//        liepinSocialPositionTransfer.toThirdPartyPositionForm(hrThirdPartyPositionDO, null);
//    }
//
//    @Test
//    public void test(){
//        List<String> cityCodesList = new ArrayList<>();
//        cityCodesList.add("110000");
//        cityCodesList.add("441900");
//        String title =
//        List<JobPositionLiepinMappingDO> liepinMappingDOList = new ArrayList<>();
//        JobPositionLiepinMappingDO jobPositionLiepinMappingDO = new JobPositionLiepinMappingDO();
//        jobPositionLiepinMappingDO.setCityCode(110000);
//
//        JobPositionLiepinMappingDO jobPositionLiepinMappingDO1 = new JobPositionLiepinMappingDO();
//        jobPositionLiepinMappingDO1.setCityCode(441900);
//
//        liepinMappingDOList.add(jobPositionLiepinMappingDO);
//        liepinMappingDOList.add(jobPositionLiepinMappingDO1);
//        for (String cityCode : cityCodesList) {
//
//            for (JobPositionLiepinMappingDO mappingDO : liepinMappingDOList) {
//                log.info("==============当前citycode:{},当前数据库mapping citycode:{}=================", cityCode, mappingDO.getCityCode());
//                // 存在城市，并且状态正常
//                if (cityCode.equals(String.valueOf(mappingDO.getCityCode())) && mappingDO.getState() == 1 && title.equals(mappingDO.getJobTitle())) {
//
//                    if (!cityChangeFlag) {
//                        log.info("===============存在城市，并且状态正常，修改================");
//                        // 修改
//                        editSinglePosition(liePinPositionVO, liePinToken, mappingDO);
//                    }
//                    break;
//
//                } else if (cityCode.equals(String.valueOf(mappingDO.getCityCode())) && mappingDO.getState() == 0 && title.equals(mappingDO.getJobTitle())) {
//
//                    // 存在城市，但是状态为下架，先上架，后修改
//                    if (!cityChangeFlag) {
//                        log.info("============存在城市，但是状态为下架，先上架，后修改============");
//                        upShelfOldSinglePosition(mappingDO, liePinToken);
//
//                        // 修改
//                        editSinglePosition(liePinPositionVO, liePinToken, mappingDO);
//                    }
//
//                    break;
//
//                } else if (cityCodesList.contains(String.valueOf(mappingDO.getCityCode())) && mappingDO.getState() == 1 && !title.equals(mappingDO.getJobTitle())) {
//                    log.info("============如果编辑的城市中存在数据库中的该城市，但是title不相同，并且该城市之前出于上架状态，则将其下架============");
//                    // 如果编辑的城市中存在数据库中的该城市，但是title不相同，并且该城市之前出于上架状态，则将其下架
//                    downShelfOldSinglePosition(mappingDO, liePinToken);
//
//                } else if (!cityCodesList.contains(String.valueOf(mappingDO.getCityCode())) && mappingDO.getState() == 1) {
//                    log.info("============如果编辑的城市中没有数据库中的该城市，并且该城市之前出于上架状态，则将其下架============");
//                    // 如果编辑的城市中没有数据库中的该城市，并且该城市之前出于上架状态，则将其下架
//                    downShelfOldSinglePosition(mappingDO, liePinToken);
//
//                }
//
////                        if (!cityDbList.isEmpty() && !cityDbList.contains(cityCode) && title.equals(mappingDO.getJobTitle())) {
////                            // 如果该职位数据库的发布城市中没有编辑职位中的第i个城市，判定为新城市，需要发布
////                            log.info("================如果该职位数据库的发布城市中没有编辑职位中的当前城市，判定为新城市，需要发布================");
////                            flag = false;
////                        }
//            }
//        }
//    }
//}
