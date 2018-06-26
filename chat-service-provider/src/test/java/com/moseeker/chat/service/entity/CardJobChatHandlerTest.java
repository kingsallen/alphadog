package com.moseeker.chat.service.entity;

import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.chat.config.AppConfig;
import com.moseeker.thrift.gen.chat.struct.ChatVO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class CardJobChatHandlerTest {

    @Autowired
    CardJobChatHandler cardJobChatHandler;

    private int positionId = 19494215;
    private int companyId = 39978;
    private String content;

    @Before
    public void before(){
        cardJobChatHandler = new CardJobChatHandler();
        ChatDao chatDao = mock(ChatDao.class);
        HrCompanyDao companyDao = mock(HrCompanyDao.class);
        JobPositionCityDao positionCityDao = mock(JobPositionCityDao.class);

        JobPositionDO positionDO = new JobPositionDO();
        positionDO.setId(positionId);
        positionDO.setTitle("职位编辑后上架1");
        positionDO.setSalary("面议");
        positionDO.setSalaryTop(20);
        positionDO.setSalaryBottom(10);
        positionDO.setId(positionId);
        positionDO.setCompanyId(companyId);
        when(chatDao.getPositionById(19494215)).thenReturn(positionDO);

        HrCompanyDO companyDO = new HrCompanyDO();
        companyDO.setAbbreviation("寻仟信息");
        companyDO.setName("上海大岂网络科技有限公司1");
        when(companyDao.getCompanyById(companyId)).thenReturn(companyDO);

        List<DictCityDO> cities = new ArrayList<>();
        cities.add(new DictCityDO(){{setName("大连");}});
        cities.add(new DictCityDO(){{setName("合肥");}});
        when(positionCityDao.getPositionCitys(19494215)).thenReturn(cities);

        cardJobChatHandler.positionCityDao = positionCityDao;
        cardJobChatHandler.chatDao = chatDao;
        cardJobChatHandler.companyDao = companyDao;
    }


    @Test
    public void outputHandler() throws Exception {
        ChatVO chat = new ChatVO();
        chat.setContent(String.valueOf(positionId));
        chat = cardJobChatHandler.outputHandler(chat);
        content=chat.getContent();
        System.out.println(chat.getContent());
    }

    @Test
    public void beforeSave() throws Exception {
        HrWxHrChatDO chat = new HrWxHrChatDO();
        chat.setContent("{\"city\":[\"大连\",\"合肥\"],\"company_name\":\"寻仟信息\",\"id\":19494215,\"salary\":\"面议\",\"title\":\"职位编辑后上架1\"}");
        chat = cardJobChatHandler.beforeSave(chat);
        System.out.println(chat.getContent());
    }

}