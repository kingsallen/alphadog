package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.schedule.RefreshLiepinTokenSchedule;
import com.moseeker.useraccounts.service.impl.LiePinUserAccountBindHandler;
import org.jooq.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 猎聘用户绑定
 *
 * @author cjm
 * @date 2018-05-28 16:16
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class LPUserAccountBindTest {

    @Autowired
    HRThirdPartyAccountDao dao;

    @Autowired
    DictLiepinOccupationDao liepinOccupationDao;

    @Autowired
    LiePinUserAccountBindHandler handler;

    @Test
    public void testBind() throws Exception {
        HrThirdPartyAccountDO user = new HrThirdPartyAccountDO();
        user.setUsername("mayflower");
        user.setPassword("20180612");
        user = handler.bind(user, null);
    }

//    @Test
    public void testGet() throws Exception {

        List<HrThirdPartyAccountDO>  list = dao.getBoundThirdPartyAccountDO(2);
    }

//    @Test
private String requireValidOccupation(List<String> occupationList) throws BIZException {
    StringBuilder occupation = new StringBuilder();
    List<DictLiepinOccupationDO> allSocialOccupation = liepinOccupationDao.getAllSocialOccupation();
    List<Integer> allSocialCode = allSocialOccupation.stream().map(socialOccupation -> socialOccupation.getCode()).collect(Collectors.toList());
    List<String> moseekerCodeList = new ArrayList<>();
    int index = 0;

    for(String moseekerCode : occupationList){
        if(StringUtils.isNullOrEmpty(moseekerCode)){
            continue;
        }
        moseekerCodeList = Arrays.asList(moseekerCode.substring(1, moseekerCode.length() - 1).split("[,，]"));
        if(moseekerCodeList.size() < 1){
            continue;
        }
        String code = moseekerCodeList.get(1).trim();
        if(allSocialCode.contains(Integer.parseInt(code)) && index < 3 && code.length() > 3){
            occupation.append(moseekerCodeList.get(1)).append(",");
            index++;
        }
    }
    if(occupation.length() > 0){
        return occupation.substring(0, occupation.length() - 1);
    }
    throw ExceptionUtils.getBizException("传入职能中没有有效的职能code");
}

    @Test
    public void test1() throws BIZException {
        List<String> list = new ArrayList<>();
        list.add("[100, 100060]");
        list.add("[100, 100060]");
        requireValidOccupation(list);
    }

    @Autowired
    RefreshLiepinTokenSchedule schedule;

    @Test
    public void testFresh(){
        schedule.refreshLiepinToken();
    }
}
