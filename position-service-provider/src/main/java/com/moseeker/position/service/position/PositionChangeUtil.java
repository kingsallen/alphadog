package com.moseeker.position.service.position;

import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrTeamDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.db.dictdb.tables.DictCityMap;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.qianxun.WorkType;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 职位转换
 *
 * @author wjf
 */
@Service
public class PositionChangeUtil {

    Logger logger = LoggerFactory.getLogger(PositionChangeUtil.class);

    @Autowired
    private DictCityMapDao cityMapDao;

    @Autowired
    private JobPositionCityDao jobPositionCityDao;

    @Autowired
    private HrTeamDao hrTeamDao;

    /**
     * 将仟寻职位转成第卅方职位
     *
     * @param form
     * @param positionDB
     * @return
     */
    public ThirdPartyPositionForSynchronization changeToThirdPartyPosition(ThirdPartyPosition form, JobPositionDO positionDB) {
        logger.info("changeToThirdPartyPosition---------------------");
        ThirdPartyPositionForSynchronization position = new ThirdPartyPositionForSynchronization();

        //仟寻职位ID，用于回传区分
        position.setPosition_id(positionDB.getId());

        //使用的第三方帐号的
        position.setAccount_id(form.getThird_party_account_id());

        //渠道
        position.setChannel(form.getChannel());

        //职位名称
        position.setTitle(positionDB.getTitle());

        ChannelType channelType = ChannelType.instaceFromInteger(form.getChannel());

        //设置职位职能
        setOccupation(form, channelType, position);

        //招聘人数
        setQuantity(form.getCount(), (int) positionDB.getCount(), position);

        //学历要求
        setDegree((int) positionDB.getDegree(), channelType, position);

        //工作经验要求
        Integer experience = null;
        try {
            if (StringUtils.isNotNullOrEmpty(positionDB.getExperience())) {
                experience = Integer.valueOf(positionDB.getExperience().trim());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        setExperience(experience, channelType, position);

        //薪资要求
        setSalaryBottom(form.getSalary_bottom(), (int) positionDB.getSalaryBottom(), position);
        setSalaryTop(form.getSalary_top(), (int) positionDB.getSalaryTop(), position);

        //是否薪资面谈
        position.setSalary_discuss(form.isSalary_discuss());

        //薪水发放月数
        position.setSalary_month(form.getSalary_month());

        //职位详情
        String description = "";
        if (channelType == ChannelType.JOB51 || channelType == ChannelType.LIEPIN) {
            description = convertDescriptionFor51(positionDB.getAccountabilities(), positionDB.getRequirement());
        } else {
            description = convertDescription(positionDB.getAccountabilities(), positionDB.getRequirement());
        }
        position.setDescription(description);

        //设置工作地点
        position.setWork_place(form.getAddress());

        //招聘职位类型
        setEmployeeType((byte) positionDB.getEmploymentType(), form.getChannel(), position);

        //反馈时间
        position.setFeedback_period(form.getFeedback_period());


        setDepartment(form, positionDB, position);

        //有效时间
        DateTime dt = new DateTime();
        DateTime dayAfter60 = dt.plusDays(60);
        position.setStop_date(dayAfter60.toString("yyyy-MM-dd"));

        setCities(positionDB, position, channelType);

        return position;
    }

    private void setDepartment(ThirdPartyPosition form, JobPositionDO positionDO, ThirdPartyPositionForSynchronization position) {
        ChannelType channelType = ChannelType.instaceFromInteger(form.getChannel());
        switch (channelType) {
            case LIEPIN:
                position.setDepartment(form.getDepartment());
                break;
            case JOB51:
                HrTeamDO hrTeam = hrTeamDao.getHrTeam(positionDO.getTeamId());
                if (hrTeam != null) {
                    position.setDepartment(hrTeam.getName());
                }else{
                    position.setDepartment("");
                }
                break;
        }
    }

    private static void setSalaryBottom(int salaryBottom, int salaryBottomDB,
                                        ThirdPartyPositionForSynchronization position) {
        if (salaryBottom > 0) {
            position.setSalary_bottom(salaryBottom * 1000);
        } else {
            position.setSalary_bottom(salaryBottomDB * 1000);
        }
    }

    private static void setSalaryTop(int salary_top, int salaryTopDB, ThirdPartyPositionForSynchronization position) {
        if (salary_top > 0) {
            position.setSalary_top(salary_top * 1000);
        } else {
            position.setSalary_bottom(salaryTopDB * 1000);
        }
    }

    private static void setQuantity(int count, int countFromDB, ThirdPartyPositionForSynchronization position) {
        if (count > 0) {
            position.setQuantity(count);
        } else {
            position.setQuantity(countFromDB);
        }
    }

    private static void setOccupation(ThirdPartyPosition positionForm, ChannelType channelType,
                                      ThirdPartyPositionForSynchronization position) {
        System.out.println("--form.occupation:" + positionForm.getOccupation());
        position.setOccupation(new ArrayList<>());
        if (positionForm.getOccupation() != null) {
            for (String occupation : positionForm.getOccupation()) {
                String finalOccupation = null;
                switch (channelType) {
                    case JOB51:
                        DecimalFormat df = new DecimalFormat("0000");
                        finalOccupation = df.format(Integer.valueOf(occupation));
                        break;
                    case ZHILIAN:
                        DecimalFormat df1 = new DecimalFormat("000");
                        finalOccupation = df1.format(Integer.valueOf(occupation));
                        break;
                    case LIEPIN:
                        finalOccupation = occupation;
                        break;
                }

                if (finalOccupation != null) {
                    position.getOccupation().add(finalOccupation);
                }
            }
        }
    }

    private static void setEmployeeType(byte employment_type, int channel, ThirdPartyPositionForSynchronization position) {
        WorkType workType = WorkType.instanceFromInt(employment_type);
        ChannelType channelType = ChannelType.instaceFromInteger(channel);
        switch (channelType) {
            case JOB51:
                position.setType_code(String.valueOf(WorkTypeChangeUtil.getJob51EmployeeType(workType).getValue()));
                break;
            case ZHILIAN:
                position.setType_code(String.valueOf(WorkTypeChangeUtil.getZhilianEmployeeType(workType).getValue()));
                break;
            default:
                position.setType_code("");
        }
    }

    /**
     * 转学位
     *
     * @param degreeInt
     * @param channelType
     * @param position
     */
    private static void setDegree(int degreeInt, ChannelType channelType, ThirdPartyPositionForSynchronization position) {
        Degree degree = Degree.instanceFromCode(String.valueOf(degreeInt));
        switch (channelType) {
            case JOB51:
                position.setDegree_code(DegreeChangeUtil.getJob51Degree(degree).getValue());
                position.setDegree_code(DegreeChangeUtil.getJob51Degree(degree).getName());
                break;
            case ZHILIAN:
                position.setDegree_code(DegreeChangeUtil.getZhilianDegree(degree).getValue());
                position.setDegree(DegreeChangeUtil.getZhilianDegree(degree).getName());
                break;
            case LIEPIN:
                position.setDegree_code(DegreeChangeUtil.getLiepinDegree(degree).getValue());
                position.setDegree(DegreeChangeUtil.getLiepinDegree(degree).getName());
        }
    }

    /**
     * 转工作经验
     *
     * @param experience
     * @param position
     */
    private static void setExperience(Integer experience, ChannelType channelType, ThirdPartyPositionForSynchronization position) {
        switch (channelType) {
            case JOB51:
                position.setExperience_code(ExperienceChangeUtil.getJob51Experience(experience).getValue());
                position.setExperience(ExperienceChangeUtil.getJob51Experience(experience).getValue());
                break;
            case ZHILIAN:
                position.setExperience_code(ExperienceChangeUtil.getZhilianExperience(experience).getValue());
                position.setExperience(ExperienceChangeUtil.getZhilianExperience(experience).getName());
                break;
            case LIEPIN:
                position.setExperience_code((experience == null || experience == 0) ? "不限" : String.valueOf(experience));
                position.setExperience((experience == null || experience == 0) ? "不限" : String.valueOf(experience));
                break;
        }
    }

    public String changeCity(int cityCode, int channel) {

        Query qu = new Query.QueryBuilder().where(DictCityMap.DICT_CITY_MAP.CODE.getName(), cityCode).where("channel", channel).buildQuery();
        DictCityMapDO cityMap = cityMapDao.getData(qu);
        String cityCodeStr = "";
        try {
            if (cityMap == null) {
                return cityCodeStr;
            }
            switch (ChannelType.instaceFromInteger(channel)) {
                case JOB51:
                    cityCodeStr = new DecimalFormat("000000").format(Integer.valueOf(cityMap.getCodeOther()));
                    break;
                case ZHILIAN:
                    cityCodeStr = new DecimalFormat("000").format(Integer.valueOf(cityMap.getCodeOther()));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        if (cityCodeStr.length() == 0) {
            logger.error("转换仟寻城市到第三方城市时找不到channel:{}:code:{}", channel, cityCode);
        } else {
            logger.info("成功转换仟寻城市到第三方城市channel:{}:code:{}:codeOther:{}", channel, cityCode, cityCodeStr);
        }
        return cityCodeStr;
    }

    public static String convertDescription(String accounTabilities, String requirement) {
        StringBuffer descript = new StringBuffer();
        if (StringUtils.isNotNullOrEmpty(accounTabilities)) {
            StringBuffer tablities = new StringBuffer();
            if (accounTabilities.contains("\n")) {
                String results[] = accounTabilities.split("\n");
                for (String result : results) {
                    tablities.append("<p>  " + result + "</p>");
                }
            } else {
                tablities.append("<p>  " + accounTabilities + "</p>");
            }
            if (accounTabilities.contains("职位描述")) {
                descript.append(tablities.toString());
            } else {
                descript.append("<p>职位描述：</p>" + tablities.toString());
            }
        }
        if (StringUtils.isNotNullOrEmpty(requirement)) {
            StringBuffer require = new StringBuffer();
            if (requirement.contains("\n")) {
                String results1[] = requirement.split("\n");
                for (String result : results1) {
                    require.append("<p>  " + result + "</p>");
                }
            } else {
                require.append("<p>  " + requirement + "</p>");
            }
            if (requirement.contains("职位要求")) {
                descript.append(require.toString());
            } else {
                descript.append("<p>职位要求：</p>" + require.toString());
            }
        }

        return descript.toString();
    }

    private static String convertDescriptionFor51(String accounTabilities, String requirement) {
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


    public void setCities(JobPositionDO positionDB, ThirdPartyPositionForSynchronization syncPosition, ChannelType channelType) {

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

        logger.info("setCities:{}", positionCityCodes);
        //转城市
        if (channelType == ChannelType.LIEPIN) {
            List<List<String>> otherCityCodes = cityMapDao.getOtherCityFunllLevel(ChannelType.LIEPIN, positionCityCodes);
            syncPosition.setCities(otherCityCodes);
            logger.info("setCities:otherCityCodes:{}", otherCityCodes);
        } else {
            if (positionCitys != null && positionCitys.size() > 0) {
                logger.info("position have city");
                int cityCode = positionCitys.get(0).getCode();
                logger.info("cityCode:{}", cityCode);
                String otherCode = changeCity(cityCode, channelType.getValue());
                syncPosition.setPub_place_code(otherCode);
            } else {
                syncPosition.setPub_place_code("");
                syncPosition.setPub_place_name("");
            }
        }
    }
}
