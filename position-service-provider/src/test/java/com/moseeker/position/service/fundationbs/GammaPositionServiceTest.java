//package com.moseeker.position.service.fundationbs;
//
//import com.moseeker.common.util.BeanUtils;
//
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//
///**
// * Created by YYF
// *
// * Date: 2017/4/18
// *
// * Project_name :alphadog
// */
//public class GammaPositionServiceTest {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    public static AnnotationConfigApplicationContext initSpring() {
//        AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
//        annConfig.scan("com.moseeker.position");
//        annConfig.refresh();
//        return annConfig;
//    }
//
//    /**
//     * 头图信息
//     */
//    @Test
//    public void headImage() throws Exception {
//        AnnotationConfigApplicationContext acac = initSpring();
//        try {
//            GammaPositionService positionService = acac.getBean(GammaPositionService.class);
//            writeLog(BeanUtils.convertStructToJSON(positionService.headImage()), "headImage");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 单个职位详情查询接口
//     */
//    @Test
//    public void positionDetails() throws Exception {
//        AnnotationConfigApplicationContext acac = initSpring();
//        try {
//            GammaPositionService positionService = acac.getBean(GammaPositionService.class);
//            Integer positionId = 20286;
//            String positionDetails = BeanUtils.convertStructToJSON(positionService.positionDetails(positionId));
//            logger.info(positionDetails);
//            writeLog(positionDetails, "positionDetails");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    /**
//     * 查询公司热招职位查询接口
//     */
//    @Test
//    public void companyPositionDetailsList() throws Exception {
//        AnnotationConfigApplicationContext acac = initSpring();
//        try {
//            GammaPositionService positionService = acac.getBean(GammaPositionService.class);
//            String positionDetails = BeanUtils.convertStructToJSON(positionService.companyPositionDetailsList(157, 1, 30));
//            logger.info(positionDetails);
//            writeLog(positionDetails, "companyPositionDetailsList");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    /**
//     * 查询团队热招职位查询接口
//     */
//    @Test
//    public void teamPositionDetailsList() throws Exception {
//        AnnotationConfigApplicationContext acac = initSpring();
//        try {
//            GammaPositionService positionService = acac.getBean(GammaPositionService.class);
//            String positionDetails = BeanUtils.convertStructToJSON(positionService.teamPositionDetailsList(47, 1, 30));
//            logger.info(positionDetails);
//            writeLog(positionDetails, "teamPositionDetailsList");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 将接口返回写入文件，测试用
//     */
//    public static void writeLog(String str, String fileName) {
//        String path = "/Users/yuyunfeng/Documents/workspace-moseeker/responses/" + fileName + ".json";
//        byte[] bytexml = str.getBytes();
//        try {
//            OutputStream os = new FileOutputStream(new File(path), false);
//            os.write(bytexml);
//            os.flush();
//            os.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}