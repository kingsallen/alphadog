
package com.moseeker.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.company.bean.email.TalentEmailInviteToDelivyInfo;
import com.moseeker.company.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.TalentpoolCompanyTagDO;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by zztaiwll on 17/12/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TalentPoolServiceTest {
    @Autowired
    private TalentPoolService talentPoolService;
    @Autowired
    private CompanyTagService companyTagService;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private TalentpoolEmailService talentpoolEmailService;
    @Autowired
    private JobPositionDao jobPositionDao;

    @Test
    public void testBatchAddTalent() throws TException {
        int hrId = 90915;
        int companyId = 39978;
        Set<Integer> userIdList = new HashSet<>();
        userIdList.add(2191508);
        userIdList.add(2191513);
        userIdList.add(2191525);
        userIdList.add(2191558);
        userIdList.add(1234);
        int isGDPR = 1;
        Response res = talentPoolService.batchAddTalent(hrId, userIdList, companyId, isGDPR);
        System.out.println(res);
    }

    @Test
    public void testBatchCancleTalent() throws TException {
        int hrId = 82752;
        int companyId = 39978;
        Set<Integer> userIdList = new HashSet<>();
        userIdList.add(2191508);
        userIdList.add(2191513);
        userIdList.add(2191525);
        userIdList.add(2191558);
        userIdList.add(1234);
        int isGDPR = 1;
        Response res = talentPoolService.batchCancelTalent(hrId, userIdList, companyId, isGDPR);
        System.out.println(res);
    }

    @Test
    public void testAddTag() throws TException {
        int hrId = 82752;
        int companyId = 39978;
        String name = "dddd";
        Response res = talentPoolService.addHrTag(hrId, companyId, name);
        System.out.println(res);

    }

    @Test
    public void testDelTag() throws TException {
        int hrId = 82752;
        int companyId = 39978;
        int tagId = 2;
        Response res = talentPoolService.deleteHrTag(hrId, companyId, tagId);
        System.out.println(res);

    }

    @Test
    public void testupdateTag() throws TException {
        int hrId = 82752;
        int companyId = 39978;
        int tagId = 1;
        String name = "mmmm";
        Response res = talentPoolService.updateHrTag(hrId, companyId, tagId, name);
        System.out.println(res);

    }

    @Test
    public void testGetAllHrTag() throws TException {
        int hrId = 82690;
        int companyId = 4;
        Map<String, Object> res = talentPoolService.getAllHrTag(hrId, companyId, 1, 100);
        System.out.println(res);

    }

    @Test
    public void testBatchCancleTalentTag() throws TException {
        int hrId = 82752;
        int companyId = 39978;
        Set<Integer> userIdList = new HashSet<>();
        userIdList.add(2191508);
        userIdList.add(2191513);
        userIdList.add(2191525);
        userIdList.add(2191558);
        Set<Integer> tagIdList = new HashSet<>();
        tagIdList.add(1);
        tagIdList.add(2);
        Response res = talentPoolService.batchCancelTalentTag(hrId, userIdList, tagIdList, companyId);
        System.out.println(res);

    }

    @Test
    public void testBatchAddTalentTag() throws TException {
        int hrId = 82752;
        int companyId = 39978;
        Set<Integer> userIdList = new HashSet<>();
        userIdList.add(2191508);
        userIdList.add(2191513);
        userIdList.add(2191525);
        userIdList.add(2191558);
        Set<Integer> tagIdList = new HashSet<>();
        tagIdList.add(1);
        tagIdList.add(2);
        Response res = talentPoolService.addBatchTalentTag(hrId, userIdList, tagIdList, companyId);
        System.out.println(res);
    }

    @Test
    public void testBatchAddNewTalentTag() throws TException {
        int hrId = 82690;
        int companyId = 4;
        Set<Integer> userIdList = new HashSet<>();
        userIdList.add(393846);
//        userIdList.add(2191513);
//        userIdList.add(2191525);
//        userIdList.add(2191558);
        Set<Integer> tagIdList = new HashSet<>();
        tagIdList.add(5);
        tagIdList.add(3);
        Response res = talentPoolService.addNewBatchTalentTag(hrId, userIdList, tagIdList, companyId);
        System.out.println(res);
    }

    @Test
    public void testAddTalentComment() throws TException {
        int hrId = 82752;
        int companyId = 39978;
        int userId = 2195000;
        String content = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
        Response res = talentPoolService.addTalentComment(hrId, companyId, userId, content);
        System.out.println(res);
    }

    @Test
    public void testCancleTalentComment() throws TException {
        int hrId = 82752;
        int companyId = 39978;
        int tagId = 1;
        Response res = talentPoolService.delTalentComment(hrId, companyId, tagId);
        System.out.println(res);
    }

    @Test
    public void testBatchTalentPublic() throws TException {
        int hrId = 82752;
        int companyId = 39978;
        Set<Integer> userIdList = new HashSet<>();
        userIdList.add(2191558);
        int isGDPR = 1;
        Response res = talentPoolService.AddbatchPublicTalent(hrId, companyId, userIdList, isGDPR);
        System.out.println(res);
    }

    @Test
    public void testBatchCancleTalentPublic() throws TException {
        int hrId = 90915;
        int companyId = 39978;
        List<Integer> userIdList = new ArrayList<>();
        userIdList.add(2191558);
        Set<Integer> set = new HashSet<>();
        set.addAll(userIdList);
        Response res = talentPoolService.cancelBatchPublicTalent(hrId, companyId, set);
        System.out.println(res);
    }

    @Test
    public void getAllPublic() {
        int hrId = 82752;
        int companyId = 39978;
        Response res = talentPoolService.getCompanyPublic(hrId, companyId, 1, 100);
        System.out.println(res);
    }

    @Test
    public void getAllComment() {
        int userId = 2191508;
        int hrId = 91337;
        int companyId = 39978;
        Response res = talentPoolService.getAllTalentComment(hrId, companyId, userId, 1, 10);
        System.out.println(res);
    }

    @Test
    public void testGetTalentState() {
        int hrId = 82752;
        int companyId = 39978;
        Response res = talentPoolService.getTalentState(hrId, companyId, 0);
        System.out.println(res);
    }

    @Test
    public void testGetCompanyTalent() {
        int hrId = 82752;
        int companyId = 39978;
        int userId = 2191508;
        Response res = talentPoolService.getCompanyTalent(hrId, companyId, userId);
        System.out.println(res);
    }

    @Test
    public void testGetHrUserTag() {
        int hrId = 82752;
        int companyId = 39978;
        int userId = 2191508;
        Response res = talentPoolService.getHrUserTag(hrId, companyId, userId);
        System.out.println(res);
    }

    @Test
    public void testGetCompanyUserPublic() {
        int hrId = 82752;
        int companyId = 39978;
        int userId = 2191508;
        Response res = talentPoolService.getCompanyUserPublic(hrId, companyId, userId);
        System.out.println(res);
    }

    @Test
    public void testGetOrigin() {
        int hrId = 82752;
        int companyId = 39978;
        int userId = 2191508;
        Response res = talentPoolService.getUserOrigin(hrId, companyId, userId);
        System.out.println(res);
    }

    @Test
    public void testGetPublicAndHrTalentByUserIdList() {
        int hrId = 82690;
        int companyId = 4;
        int userId = 393846;
        Set<Integer> userIdSet = new HashSet<>();
        userIdSet.add(userId);
        Response res = talentPoolService.getPublicAndHrTalentByUserIdList(hrId, companyId, userIdSet);
        System.out.println(res);
    }

    @Test
    public void testCompanyTagIdList() throws TException {
        int hrId = 82752;
        int companyId = 39978;
        Response res = talentPoolService.getCompanyTagList(hrId, companyId, 0, 0);
        System.out.println(res);
    }

    @Test
    public void testDeleteCompanyTagIdList() throws TException {
        int hrId = 82752;
        int companyId = 39978;
        List<Integer> companyTagIds = new ArrayList<>();
        companyTagIds.add(2);
        Response res = talentPoolService.deleteCompanyTags(hrId, companyId, companyTagIds);
        System.out.println(res);
    }

    @Test
    public void testaddCompanyTagIdList() throws TException {
        int hrId = 82752;
        TalentpoolCompanyTagDO companyTagDO = new TalentpoolCompanyTagDO();
        companyTagDO.setCompany_id(39978);
        companyTagDO.setName("assdkahsdagdjkhajksdajkhdjkadjkah");
        companyTagDO.setSex(2);
        companyTagDO.setCity_name("上海");
        Response res = talentPoolService.addCompanyTag(companyTagDO, hrId);
        System.out.println(res + "");
    }

}