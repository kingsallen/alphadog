package com.moseeker.position.service.position.zhilian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.DegreeChangeUtil;
import com.moseeker.position.service.position.ExperienceChangeUtil;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.zhilian.pojo.PositionZhilian;
import com.moseeker.position.service.position.zhilian.pojo.PositionZhilianForm;
import com.moseeker.position.service.position.zhilian.pojo.PositionZhilianWithAccount;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyZhilianPositionAddressDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ZhilianPositionTransfer extends AbstractPositionTransfer<PositionZhilianForm,PositionZhilianWithAccount,PositionZhilian,List<ThirdpartyZhilianPositionAddressDO>> {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public PositionZhilianWithAccount changeToThirdPartyPosition(PositionZhilianForm positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        PositionZhilianWithAccount positionZhilianWithAccount=createAndInitAccountInfo(positionForm,positionDB,account);

        PositionZhilian positionZhilian=createAndInitPositionInfo(positionForm,positionDB);

        positionZhilianWithAccount.setPosition_info(positionZhilian);

        return positionZhilianWithAccount;
    }

    @Override
    protected PositionZhilianWithAccount createAndInitAccountInfo(PositionZhilianForm positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        PositionZhilianWithAccount position51WithAccount = new PositionZhilianWithAccount();
        position51WithAccount.setUser_name(account.getUsername());
        position51WithAccount.setPassword(account.getPassword());
        position51WithAccount.setPosition_id(String.valueOf(positionDB.getId()));
        position51WithAccount.setChannel(String.valueOf(positionForm.getChannel()));
        position51WithAccount.setAccount_id(String.valueOf(account.getId()));

        //智联有手机验证，所以加上手机号
        UserHrAccountDO userHrAccountDO=getPublisherAccountInfo(positionDB);

        if(userHrAccountDO!=null){
            position51WithAccount.setMobile(userHrAccountDO.getMobile());
        }
        return position51WithAccount;
    }

    @Override
    protected PositionZhilian createAndInitPositionInfo(PositionZhilianForm positionForm, JobPositionDO positionDB) throws Exception {
        PositionZhilian positionZhilian = new PositionZhilian();

        positionZhilian.setTitle(positionDB.getTitle());

        setCities(positionForm,positionZhilian);

        positionZhilian.setOccupation(positionForm.getOccupation());

        positionZhilian.setSalary_low(getSalaryBottom(positionForm.getSalaryBottom())+"");
        positionZhilian.setSalary_high(getSalaryTop(positionForm.getSalaryTop())+"");

        setWorkyears(positionDB,positionZhilian);

        setDegree((int) positionDB.getDegree(),  positionZhilian);

        String description = getDescription(positionDB.getAccountabilities(), positionDB.getRequirement());
        positionZhilian.setDescription(description);

        positionZhilian.setEmail(getEmail(positionDB));

        int quantity=getQuantity(positionForm.getCount(),(int)positionDB.getCount());
        positionZhilian.setCount(quantity+"");

        positionZhilian.setCompany(positionForm.getCompanyName());

        positionZhilian.setDepartment(positionForm.getDepartmentName());

        return positionZhilian;
    }

    protected void setDegree(int degreeInt, PositionZhilian position) {
        Degree degree = Degree.instanceFromCode(String.valueOf(degreeInt));
        position.setDegree(DegreeChangeUtil.getZhilianDegree(degree).getValue());
    }

    protected void setWorkyears(JobPositionDO positionDB, PositionZhilian position) throws BIZException {
        //工作经验要求
        Integer experience = null;
        try {
            if (StringUtils.isNotNullOrEmpty(positionDB.getExperience())) {
                experience = Integer.valueOf(positionDB.getExperience().trim());
            }
        } catch (NumberFormatException e) {
            logger.info("zhilian parse experience error {}",positionDB.getExperience());
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"parse experience error");
        }
        position.setWorkyears(ExperienceChangeUtil.getZhilianExperience(experience).getValue());
    }

    protected void setCities(PositionZhilianForm positionForm, PositionZhilian position) {
        if(StringUtils.isEmptyList(positionForm.getAddress())){
            return;
        }

        // 查询出address里所有的cityCode对应的智联映射code
        List<Integer> cityCodes = positionForm.getAddress().stream().map(a->a.getCityCode()).collect(Collectors.toList());
        List<DictCityMapDO> otherCityCodes = cityMapDao.getOtherCityByCodes(getChannel(), cityCodes);
        logger.info("setCities:otherCityCodes:{}", otherCityCodes);

        List<PositionZhilian.City> cities = new ArrayList<>();

        TypeReference<List<String>> typeRef
                = new TypeReference<List<String>>() {};
        for(ThirdpartyZhilianPositionAddressDO address:positionForm.getAddress()){

            // 设置映射城市code
            Optional<DictCityMapDO> optional = otherCityCodes.stream().filter(m->m.getCode()==address.getCityCode()).findFirst();
            if(!optional.isPresent()){
                continue;
            }
            PositionZhilian.City city = new PositionZhilian.City();

            city.setCode(JSON.parseObject(optional.get().getCodeOther(),typeRef));

            city.setAddress(address.getAddress());
            cities.add(city);
        }
        position.setCities(cities);
    }



    @Override
    public ChannelType getChannel() {
        return ChannelType.ZHILIAN;
    }

    @Override
    public Class<PositionZhilianForm> getFormClass() {
        return PositionZhilianForm.class;
    }

    @Override
    public HrThirdPartyPositionDO toThirdPartyPosition(PositionZhilianForm form,PositionZhilianWithAccount pwa) {
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();

        PositionZhilian p=pwa.position_info;

        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
        data.setSyncTime(syncTime);
        data.setUpdateTime(syncTime);

        data.setChannel(getChannel().getValue());
        data.setIsSynchronization((byte) PositionSync.binding.getValue());
        //将最后一个职能的Code存到数据库
        if (!p.getOccupation().isEmpty() && p.getOccupation().size() > 0) {
            data.setOccupation(p.getOccupation().get(p.getOccupation().size() - 1));
        }

        data.setPositionId(Integer.parseInt(pwa.getPosition_id()));
        data.setThirdPartyAccountId(Integer.parseInt(pwa.getAccount_id()));
        data.setSalaryBottom(Integer.parseInt(p.getSalary_low()));
        data.setSalaryTop(Integer.parseInt(p.getSalary_high()));

        data.setCompanyId(form.getCompanyId());
        data.setCompanyName(form.getCompanyName());
        data.setCount(form.getCount());

        data.setDepartmentId(form.getDepartmentId());
        data.setDepartmentName(form.getDepartmentName());

        logger.info("回写到第三方职位对象:{}",data);
        return data;
    }

    @Override
    public List<ThirdpartyZhilianPositionAddressDO> toExtThirdPartyPosition(PositionZhilianForm thirdPartyPosition, PositionZhilianWithAccount positionZhilianWithAccount) {
        if(StringUtils.isEmptyList(thirdPartyPosition.getAddress())){
            return Collections.emptyList();
        }

        return thirdPartyPosition.getAddress();
    }

    @Override
    public List<ThirdpartyZhilianPositionAddressDO> toExtThirdPartyPosition(Map<String, String> data) {
        TypeReference<List<ThirdpartyZhilianPositionAddressDO>> typeRef = new TypeReference<List<ThirdpartyZhilianPositionAddressDO>>(){};

        String address = data.get("address");
        if(StringUtils.isNullOrEmpty(address)){
            return Collections.emptyList();
        }

        return JSON.parseObject(address,typeRef);
    }

    @Override
    public JSONObject toThirdPartyPositionForm(HrThirdPartyPositionDO thirdPartyPosition, List<ThirdpartyZhilianPositionAddressDO> extPosition) {
        return JSONObject.parseObject(JSON.toJSONString(thirdPartyPosition));
    }
}
