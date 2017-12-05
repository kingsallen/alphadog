package com.moseeker.position.service.position.base;

import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.util.*;

public abstract class PositionTransfer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobPositionCityDao jobPositionCityDao;

    @Autowired
    private DictCityMapDao cityMapDao;

    /**
     * 将仟寻职位转成第卅方职位
     *
     * @param form
     * @param positionDB
     * @return
     */
    public ThirdPartyPositionForSynchronization changeToThirdPartyPosition(ThirdPartyPosition form, JobPositionDO positionDB) {
        logger.info("changeToThirdPartyPosition---------------------");

        //设置一些不需要转换的字段，并且初始化对象
        ThirdPartyPositionForSynchronization position = init(form, positionDB);

        ChannelType channelType = ChannelType.instaceFromInteger(form.getChannel());

        //设置职位职能
        setOccupation(form,  position);
        //招聘人数
        setQuantity(form.getCount(), (int) positionDB.getCount(), position);
        //学历要求
        setDegree((int) positionDB.getDegree(),  position);
        //福利特色
        setFeature(positionDB.getFeature(), position);
        //工作经验要求
        Integer experience = null;
        try {
            if (StringUtils.isNotNullOrEmpty(positionDB.getExperience())) {
                experience = Integer.valueOf(positionDB.getExperience().trim());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        setExperience(experience,  position);
        //薪资要求
            setSalaryBottom(form.getSalaryBottom(), (int) positionDB.getSalaryBottom(), position);
        setSalaryTop(form.getSalaryTop(), (int) positionDB.getSalaryTop(), position);
        //招聘职位类型
        setEmployeeType((byte) positionDB.getEmploymentType(),  position);

        setDepartment(form, positionDB, position);
        //有效时间
        DateTime dt = new DateTime();
        DateTime dayAfter60 = dt.plusDays(60);
        position.setStop_date(dayAfter60.toString("yyyy-MM-dd"));

        setCities(positionDB, position);

        setMore(position,form,positionDB);
        return position;
    }


    /**========================抽象方法，让每个渠道去实现自己的逻辑========================*/
    //设置部门
    protected abstract void setDepartment(ThirdPartyPosition form, JobPositionDO positionDO, ThirdPartyPositionForSynchronization position);
    //设置职位
    protected abstract void setOccupation(ThirdPartyPosition positionForm, ThirdPartyPositionForSynchronization position);
    //转换雇佣类型（全职，兼职。。。）
    protected abstract void setEmployeeType(byte employment_type, ThirdPartyPositionForSynchronization position);
    // 转学位
    protected abstract void setDegree(int degreeInt, ThirdPartyPositionForSynchronization position);
    //转工作经验
    protected abstract void setExperience(Integer experience, ThirdPartyPositionForSynchronization position);
    //获取渠道类型
    public abstract ChannelType getChannel();
    //做一些额外操作,可以继承以后覆盖
    public void setMore(ThirdPartyPositionForSynchronization position,ThirdPartyPosition form, JobPositionDO positionDB){
        //do nothing
    }

    /**========================每个渠道共用的逻辑，当然也可以覆盖实现自己的逻辑========================*/
    private static void setSalaryBottom(int salaryBottom, int salaryBottomDB,
                                        ThirdPartyPositionForSynchronization position) {
        if (salaryBottom > 0) {
            position.setSalary_bottom(salaryBottom * 1000);
        }
    }

    private static void setSalaryTop(int salary_top, int salaryTopDB, ThirdPartyPositionForSynchronization position) {
        if (salary_top > 0) {
            position.setSalary_top(salary_top * 1000);
        }
    }

    private static void setQuantity(int count, int countFromDB, ThirdPartyPositionForSynchronization position) {
        if (count > 0) {
            position.setQuantity(count);
        } else {
            position.setQuantity(countFromDB);
        }
    }

    /**
     * 设置职位福利特色
     *
     * @param feature  福利特色
     * @param position 职位信息
     */
    public static void setFeature(String feature, ThirdPartyPositionForSynchronization position) {
        if (StringUtils.isNotNullOrEmpty(feature)) {
            String[] featureArray = feature.trim().split(",");
            List<String> featureList = new ArrayList<>();
            for (String featureElement : featureArray) {
                if (!featureElement.trim().equals("")) {
                    featureList.add(featureElement);
                }
            }
            if (featureList.size() > 0) {
                position.setWelfare(featureList);
            }
        }
    }


    private static String setDescription(String accounTabilities, String requirement) {
        StringBuffer descript = new StringBuffer();
        if (StringUtils.isNotNullOrEmpty(accounTabilities)) {
            descript.append(accounTabilities);
        }
        if (StringUtils.isNotNullOrEmpty(requirement)) {
            if (!requirement.contains("职位要求")) {
                descript.append("\n职位要求：\n" + requirement);
            } else {
                descript.append(requirement);
            }
        }
        return descript.toString();
    }

    public void setCities(JobPositionDO positionDB, ThirdPartyPositionForSynchronization syncPosition) {
        //获取职位对应的moseeker城市code
        Query query = new Query.QueryBuilder().where("pid", positionDB.getId()).buildQuery();
        List<JobPositionCityDO> positionCitys = jobPositionCityDao.getDatas(query);

        Set<Integer> positionCityCodes = new HashSet<>();

        if (positionCitys == null || positionCitys.size() == 0) {
            //设置为全国
            positionCityCodes.add(111111);
        } else {
            for (JobPositionCityDO cityDO : positionCitys) {
                positionCityCodes.add(cityDO.getCode());
            }
        }

        List<List<String>> otherCityCodes = cityMapDao.getOtherCityByLastCodes(getChannel(), new ArrayList<>(positionCityCodes));
        syncPosition.setCities(otherCityCodes);
        logger.info("setCities:otherCityCodes:{}", otherCityCodes);
    }

    //把直接赋值，不需要做处理的字段集合到一个方法中
    private ThirdPartyPositionForSynchronization init(ThirdPartyPosition form, JobPositionDO positionDB) {
        ThirdPartyPositionForSynchronization position = new ThirdPartyPositionForSynchronization();

        position.setCompany_name(form.getCompanyName());
        //仟寻职位ID，用于回传区分
        position.setPosition_id(positionDB.getId());
        //使用的第三方帐号的
        position.setAccount_id(form.getThirdPartyAccountId());
        //渠道
        position.setChannel(form.getChannel());
        //职位名称
        position.setTitle(positionDB.getTitle());
        //是否薪资面谈
        position.setSalary_discuss(form.isSalaryDiscuss());
        //薪水发放月数
        position.setSalary_month(form.getSalaryMonth());
        //职位详情
        String description = setDescription(positionDB.getAccountabilities(), positionDB.getRequirement());
        position.setDescription(description);
        //设置工作地点
        position.setWork_place(form.getAddressName());
        //反馈时间
        position.setFeedback_period(form.getFeedbackPeriod());
        //实习薪资
        position.setPractice_salary(form.getPracticeSalary());
        //每周实习天数
        position.setPractice_per_week(form.getPracticePerWeek());
        //区分校招还是社招
        position.setRecruit_type(String.valueOf(Double.valueOf(positionDB.getCandidateSource()).intValue()));

        position.setPractice_salary_unit(form.getPracticeSalaryUnit());

        return position;
    }

    public List<List<String>> formateList(List<List<String>> list,String pattern){
        if(list==null || list.isEmpty()){
            return list;
        }
        List<List<String>> result=new ArrayList<>();
        for(List<String> o:list){
            result.add(formateStr(o,pattern));
        }
        return result;
    }

    public List<String> formateStr(List<String> list,String pattern){
        if(list==null || list.isEmpty()){
            return list;
        }
        DecimalFormat df = new DecimalFormat(pattern);
        List<String> result=new ArrayList<>();
        for(String s:list){
            try{
                result.add(df.format(Integer.valueOf(s)));
            }catch (NumberFormatException e){
                continue;
            }
        }
        return result;
    }
}