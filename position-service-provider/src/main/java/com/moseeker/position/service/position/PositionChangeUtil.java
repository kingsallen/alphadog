package com.moseeker.position.service.position;

import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.qianxun.WorkType;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * 职位转换
 *
 * @author wjf
 */
public class PositionChangeUtil {

    Logger logger = LoggerFactory.getLogger(PositionChangeUtil.class);

    @Autowired
    private DictCityMapDao cityMapDao;

    /**
     * 将仟寻职位转成第卅方职位
     *
     * @param form
     * @param positionDB
     * @return
     */
    public ThirdPartyPositionForSynchronization changeToThirdPartyPosition(ThirdPartyPosition form, Position positionDB) {
        LoggerFactory.getLogger(PositionChangeUtil.class).info("---------------------");
        ThirdPartyPositionForSynchronization position = new ThirdPartyPositionForSynchronization();
        position.setAccount_id(form.getThird_party_account_id());
        position.setChannel(form.getChannel());
        position.setTitle(positionDB.getTitle());

        ChannelType channelType = ChannelType.instaceFromInteger(form.getChannel());

        setCategoryMainCode(form.getOccupation_level1(), channelType, position);
        setCategorySubCode(form.getOccupation_level2(), channelType, position);

        setQuantity(form.getCount(), positionDB.getCount(), position);

        setDegree(positionDB.getDegree(), channelType, position);
        Integer experience = null;
        try {
            if (StringUtils.isNotNullOrEmpty(positionDB.getExperience())) {
                experience = Integer.valueOf(positionDB.getExperience().trim());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            LoggerFactory.getLogger(PositionChangeUtil.class).error(e.getMessage(), e);
        } finally {
            //do nothing
        }

        setExperience(experience, channelType, position);

        setSalaryTop(form.getSalary_top(), positionDB.getSalary_top(), position);
        setSalaryBottom(form.getSalary_bottom(), positionDB.getSalary_bottom(), position);
        String description = "";
        if (channelType.getValue() == 1) {
            description = convertDescriptionFor51(positionDB.getAccountabilities(), positionDB.getRequirement());
        } else {
            description = convertDescription(positionDB.getAccountabilities(), positionDB.getRequirement());
        }
        position.setDescription(description);
        position.setPosition_id(positionDB.getId());
        position.setWork_place(form.getAddress());
        positionDB.getEmployment_type();
        setEmployeeType(positionDB.getEmployment_type(), form.getChannel(), position);
        /*if(positionDB.getStop_date() != null) {
            position.setStop_date(positionDB.getStop_date());
		} else {
			DateTime dt = new DateTime();
			DateTime dayAfter60 = dt.plusDays(60);
			position.setStop_date(dayAfter60.toString("yyyy-MM-dd"));
		}*/
        DateTime dt = new DateTime();
        DateTime dayAfter60 = dt.plusDays(60);
        position.setStop_date(dayAfter60.toString("yyyy-MM-dd"));
        //转职位
        if (positionDB.getCities() != null && positionDB.getCities().size() > 0) {
            LoggerFactory.getLogger(PositionChangeUtil.class).info("position have city");
            try {
                int cityCode = 0;
                String cityName = null;
                Map<Integer, String> cities = positionDB.getCities();
                if (cities != null && cities.size() > 0) {
                    for (Map.Entry<Integer, String> entry : cities.entrySet()) {
                        LoggerFactory.getLogger(PositionChangeUtil.class).info("entry.getKey():{}, entry.getValue():{}", entry.getKey(), entry.getValue());
                        cityCode = entry.getKey();
                        cityName = entry.getValue();
                        break;
                    }
                }
                LoggerFactory.getLogger(PositionChangeUtil.class).info("cityCode:{}, cityName:{}", cityCode, cityName);
                String otherCode = changeCity(cityCode, form.getChannel());
                position.setPub_place_code(otherCode);
                position.setPub_place_name(cityName);

            } catch (Exception e) {
                position.setPub_place_code("");
                e.printStackTrace();
                LoggerFactory.getLogger(PositionChangeUtil.class).error(e.getMessage(), e);
            }
        } else {
            position.setPub_place_code("");
        }
        return position;
    }

    private static void setSalaryBottom(int salaryBottom, int salaryBottomDB,
                                        ThirdPartyPositionForSynchronization position) {
        if (salaryBottom > 0) {
            position.setSalary_low(String.valueOf(salaryBottom * 1000));
        } else {
            position.setSalary_low(String.valueOf(salaryBottomDB * 1000));
        }
    }

    private static void setSalaryTop(int salary_top, int salaryTopDB, ThirdPartyPositionForSynchronization position) {
        if (salary_top > 0) {
            position.setSalary_high(String.valueOf(salary_top * 1000));
        } else {
            position.setSalary_high(String.valueOf(salaryTopDB * 1000));
        }
    }

    private static void setQuantity(int count, int countFromDB, ThirdPartyPositionForSynchronization position) {
        if (count > 0) {
            position.setQuantity(String.valueOf(count));
        } else {
            position.setQuantity(String.valueOf(countFromDB));
        }
    }

    private static void setCategorySubCode(String categorySubCode, ChannelType channelType,
                                           ThirdPartyPositionForSynchronization position) {
        System.out.println("--form.getOccupation_level2():" + categorySubCode);
        if (StringUtils.isNotNullOrEmpty(categorySubCode)) {
            switch (channelType) {
                case JOB51:
                    DecimalFormat df = new DecimalFormat("0000");
                    if (StringUtils.isNotNullOrEmpty(categorySubCode)) {
                        position.setCategory_sub_code(df.format(Integer.valueOf(categorySubCode)));
                    }
                    break;
                case ZHILIAN:
                    DecimalFormat df1 = new DecimalFormat("000");
                    if (StringUtils.isNotNullOrEmpty(categorySubCode)) {
                        position.setCategory_sub_code(df1.format(Integer.valueOf(categorySubCode)));
                    }
                    break;
                default:
                    position.setCategory_sub_code(categorySubCode);
            }
        }
    }

    private static void setCategoryMainCode(String categoryMainCode, ChannelType channelType,
                                            ThirdPartyPositionForSynchronization position) {
        System.out.println("--form.getOccupation_level1():" + categoryMainCode);
        if (StringUtils.isNotNullOrEmpty(categoryMainCode)) {
            switch (channelType) {
                case JOB51:
                    DecimalFormat df = new DecimalFormat("0000");
                    if (StringUtils.isNotNullOrEmpty(categoryMainCode)) {
                        position.setCategory_main_code(df.format(Integer.valueOf(categoryMainCode)));
                    }
                    break;
                case ZHILIAN:
                    DecimalFormat df1 = new DecimalFormat("000");
                    if (StringUtils.isNotNullOrEmpty(categoryMainCode)) {
                        position.setCategory_main_code(df1.format(Integer.valueOf(categoryMainCode)));
                    }
                    break;
                default:
                    position.setCategory_main_code(categoryMainCode);
            }
        }
    }

    private static void setEmployeeType(byte employment_type, int channel, ThirdPartyPositionForSynchronization position) {
        WorkType workType = WorkType.instanceFromInt(employment_type);
        ChannelType channelType = ChannelType.instaceFromInteger(channel);
        switch (channelType) {
            case JOB51:
                position.setType_code(String.valueOf(WordTypeChangeUtil.getJob51EmployeeType(workType).getValue()));
                break;
            case ZHILIAN:
                position.setType_code(String.valueOf(WordTypeChangeUtil.getZhilianEmployeeType(workType).getValue()));
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
                break;
            case ZHILIAN:
                position.setDegree_code(DegreeChangeUtil.getZhilianDegree(degree).getValue());
                break;
            default:
                position.setDegree_code("");
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
                break;
            case ZHILIAN:
                position.setExperience_code(ExperienceChangeUtil.getZhilianExperience(experience).getValue());
                break;
            default:
                position.setExperience("");
        }
    }

    private String changeCity(int cityCode, int channel) {
        Query qu = new Query.QueryBuilder().where("code", cityCode).where("channel", channel).buildQuery();
        DictCityMapDO cityMap = cityMapDao.getData(qu);
        String cityCodeStr = "";
        if (cityMap != null) {
            switch (ChannelType.instaceFromInteger(channel)) {
                case JOB51:
                    cityCodeStr = new DecimalFormat("000000").format(Integer.valueOf(cityMap.getCodeOther()));
                    break;
                case ZHILIAN:
                    cityCodeStr = new DecimalFormat("000").format(Integer.valueOf(cityMap.getCodeOther()));
                    break;
                case LIANPIAN:
                    cityCodeStr = cityMap.getCodeOther();
                    break;
            }
        }
        if (cityCodeStr.length() == 0) {
            logger.error("转换仟寻城市到第三方城市时找不到channel:{}:code:{}", channel, cityCode);
        } else {
            logger.info("成功转换仟寻城市到第三方城市channel:{}:code:{}:codeOther:{}", channel, cityCode, cityCodeStr);
        }
        return cityCodeStr;
    }

    private static String convertDescription(String accounTabilities, String requirement) {
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
}
