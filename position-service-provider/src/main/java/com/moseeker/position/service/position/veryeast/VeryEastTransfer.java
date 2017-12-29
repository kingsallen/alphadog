package com.moseeker.position.service.position.veryeast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.veryeast.pojo.PositionVeryEast;
import com.moseeker.position.service.position.veryeast.pojo.PositionVeryEastForm;
import com.moseeker.position.service.position.veryeast.pojo.PositionVeryEastWithAccount;
import com.moseeker.position.service.position.veryeast.pojo.VeryEastTransferStrategy;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyVeryEastPositionDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class VeryEastTransfer extends AbstractPositionTransfer<PositionVeryEastForm,PositionVeryEastWithAccount,PositionVeryEast,ThirdpartyVeryEastPositionDO> {
    private static final String salaryPattern="[0-9.]*~[0-9].*";

    Logger logger= LoggerFactory.getLogger(VeryEastTransfer.class);

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Override
    public PositionVeryEastWithAccount changeToThirdPartyPosition(PositionVeryEastForm positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        PositionVeryEastWithAccount positionVeryEastWithAccount=createAndInitAccountInfo(positionForm,positionDB,account);

        PositionVeryEast veryEast=createAndInitPositionInfo(positionForm,positionDB);
        positionVeryEastWithAccount.setPosition_info(veryEast);

        return positionVeryEastWithAccount;
    }

    @Override
    protected PositionVeryEastWithAccount createAndInitAccountInfo(PositionVeryEastForm positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        PositionVeryEastWithAccount positionWithAccount=new PositionVeryEastWithAccount();

        positionWithAccount.setUser_name(account.getUsername());
        positionWithAccount.setPassword(account.getPassword());
        positionWithAccount.setPosition_id(String.valueOf(positionDB.getId()));
        positionWithAccount.setChannel(String.valueOf(positionForm.getChannel()));
        positionWithAccount.setAccount_id(String.valueOf(account.getId()));
        return positionWithAccount;
    }

    @Override
    protected PositionVeryEast createAndInitPositionInfo(PositionVeryEastForm positionForm, JobPositionDO positionDB) throws Exception {
        PositionVeryEast positionInfo=new PositionVeryEast();

        positionInfo.setCompany(positionForm.getCompanyName());
        positionInfo.setTitle(positionDB.getTitle());
        positionInfo.setRegion(getCities(positionDB));
        positionInfo.setQuantity(getQuantity(positionForm.getQuantity(),(int)positionDB.getCount()));
        positionInfo.setIndate(positionForm.getIndate());
        positionInfo.setSalary(transferSalary((int)positionDB.getSalaryTop()));
        positionInfo.setOccupation(positionForm.getOccupation());
        positionInfo.setAccommodation(positionForm.getAccommodation()+"");
        positionInfo.setDegree(VeryEastTransferStrategy.VeryEastDegree.moseekerToOther((int)positionDB.getDegree()));
        positionInfo.setExperience(transferExpreience(positionDB.getExperience()));
        positionInfo.setAge(positionForm.getAge());
        positionInfo.setLanguage(positionForm.getLanguage());
        positionInfo.setComputer_level(positionForm.getComputerLevel()+"");
        positionInfo.setDescription(getDescription(positionDB.getAccountabilities(),positionDB.getRequirement()));
        positionInfo.setEmail(getEmail(positionDB));
        positionInfo.setWork_mode(VeryEastTransferStrategy.WorkMode.moseekerToOther((int)positionDB.getEmploymentType()));

        return positionInfo;
    }

    /**
     * 根据redis中的salary规则转换成最佳东方的薪资
     * 仟寻薪资0对应的是最佳东方的0，即面议
     * 仟寻薪资1对应的是最佳东方的1，即1000以下
     * 仟寻薪资100对应的是最佳东方的15，即100000以上
     * 其他的真是验证salary_top是否落在区间中
     * @param salary_top
     * @return
     */
    private int transferSalary(int salary_top){
        if(salary_top==0){
            return 0;
        }
        if(salary_top<1){
            return 1;
        }
        if(salary_top>=100){
            return 15;
        }
        //moseeker的薪资单位是K，所以要乘以1000
        salary_top=salary_top*1000;
        String str=redisClient.get(RefreshConstant.APP_ID, RefreshConstant.VERY_EAST_REDIS_PARAM_KEY,"");
        JSONObject param=JSONObject.parseObject(str);
        JSONArray salarys=param.getJSONArray("salary");
        if(StringUtils.isEmptyList(salarys)){
           return 0;
        }else{
            for(int i=0;i<salarys.size();i++){
                JSONObject salary=salarys.getJSONObject(i);
                String text=salary.getString("text");
                //用正则验证是否是1001~2000这种格式
                if(!StringUtils.isNullOrEmpty(text) && Pattern.matches(salaryPattern,text)){
                    String temp[]=text.split("~");
                    //判断薪资在是否在这个区段内，比如1001~2000,那就是>=1001且<2000
                    if(salary_top>=Integer.valueOf(temp[0]) && salary_top<=Integer.valueOf(temp[1])){
                        return salary.getIntValue("code");
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 根据redis中的experience规则转换成最佳东方的经验要求
     * 例如：
     * 仟寻experience为4，即经验要求4年，
     * 对应到最佳东方就是>3 (3年以上)且 <5 (5年以上)，取3年以上
     * @param expreience
     * @return
     */
    private String transferExpreience(String expreience){
        int experienceInt=0;
        try {
            experienceInt=Integer.parseInt(expreience);
        }catch (NumberFormatException e){
            return "";
        }
        String str=redisClient.get(RefreshConstant.APP_ID, RefreshConstant.VERY_EAST_REDIS_PARAM_KEY,"");
        JSONObject param=JSONObject.parseObject(str);
        JSONArray experiences=param.getJSONArray("experience");
        if(StringUtils.isEmptyList(experiences)){
            return "";
        }else{
            int lastExpreience=0;
            for(int i=0;i<experiences.size();i++){
                JSONObject salary=experiences.getJSONObject(i);
                int code=salary.getIntValue("code");
                if(experienceInt>=lastExpreience && experienceInt<code){
                   return lastExpreience+"";
                }
                lastExpreience=code;
            }
        }
        return "";
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.VERYEAST;
    }

    @Override
    public Class<PositionVeryEastForm> getFormClass() {
        return PositionVeryEastForm.class;
    }

    @Override
    public HrThirdPartyPositionDO toThirdPartyPosition(PositionVeryEastForm position, PositionVeryEastWithAccount pwa) {
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();

        PositionVeryEast p=pwa.getPosition_info();

        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
        data.setSyncTime(syncTime);
        data.setUpdateTime(syncTime);
        data.setPositionId(Integer.parseInt(pwa.getPosition_id()));
        data.setThirdPartyAccountId(Integer.parseInt(pwa.getAccount_id()));
        data.setChannel(getChannel().getValue());
        data.setIsSynchronization((byte) PositionSync.binding.getValue());



        //将最后一个职能的Code存到数据库
        if (!p.getOccupation().isEmpty() && p.getOccupation().size() > 0) {
            data.setOccupation(p.getOccupation().get(p.getOccupation().size() - 1));
        }
        data.setCompanyName(position.getCompanyName());
        data.setCompanyId(position.getCompanyId());

        logger.info("回写到第三方职位对象:{}",data);
        return data;
    }

    @Override
    public ThirdpartyVeryEastPositionDO toExtThirdPartyPosition(PositionVeryEastForm form, PositionVeryEastWithAccount positionVeryEastWithAccount) {
        ThirdpartyVeryEastPositionDO veryEast=new ThirdpartyVeryEastPositionDO();
        veryEast.setAccommodation(form.getAccommodation());
        veryEast.setAge_top(form.getAgeTop());
        veryEast.setAge_bottom(form.getAgeBottom());
        veryEast.setLanguageType1(form.getLanguageType1());
        veryEast.setLanguageLevel1(form.getLanguageLevel1());
        veryEast.setLanguageType2(form.getLanguageType2());
        veryEast.setLanguageLevel2(form.getLanguageLevel2());
        veryEast.setLanguageType3(form.getLanguageType3());
        veryEast.setLanguageLevel3(form.getLanguageLevel3());
        veryEast.setComputerLevel(form.getComputerLevel());
        veryEast.setIndate(form.getIndate());
        veryEast.setStatus((byte) 0);
        veryEast.setCreateTime(sdf.format(new Date()));

        return veryEast;
    }

    @Override
    public ThirdpartyVeryEastPositionDO toExtThirdPartyPosition(Map<String, String> data) {
        ThirdpartyVeryEastPositionDO result= JSON.parseObject(JSON.toJSONString(data),ThirdpartyVeryEastPositionDO.class);
        return result;
    }

    @Override
    public JSONObject toThirdPartyPositionForm(HrThirdPartyPositionDO thirdPartyPosition, ThirdpartyVeryEastPositionDO extPosition) {
        PositionVeryEastForm form=new PositionVeryEastForm();

        form.setAccommodation(extPosition.getAccommodation());
        form.setIndate(extPosition.getIndate());
        form.setComputerLevel(extPosition.getComputerLevel());
        form.setLanguage1(extPosition.getLanguageType1(),extPosition.getLanguageLevel1());
        form.setLanguage2(extPosition.getLanguageType2(),extPosition.getLanguageLevel2());
        form.setLanguage3(extPosition.getLanguageType3(),extPosition.getLanguageLevel3());
        form.setAge(Arrays.asList(extPosition.getAge_bottom(),extPosition.getAge_top()));

        JSONObject result= JSON.parseObject(JSON.toJSONString(form));

        result.putAll(JSON.parseObject(JSON.toJSONString(thirdPartyPosition)));

        return result;
    }

    @Override
    public List<String> toChaosJson(PositionVeryEastWithAccount positionVeryEastWithAccount) {
        if(positionVeryEastWithAccount==null || positionVeryEastWithAccount.getPosition_info()==null){
            return new ArrayList<>();
        }else{
            List<String> results=new ArrayList<>();
            for(List<String> city:positionVeryEastWithAccount.getPosition_info().getRegion()){
                JSONObject result=JSON.parseObject(JSON.toJSONString(positionVeryEastWithAccount));
                result.getJSONObject("position_info").put("region",city);
                results.add(result.toJSONString());
            }
            return results;
        }
    }
}
