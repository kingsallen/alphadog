package com.moseeker.profile.service.impl.resumesdk;

import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojo.profile.City;
import com.moseeker.entity.pojo.profile.Intentions;
import com.moseeker.entity.pojo.profile.Positions;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.Result;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.resumesdk.iface.AbstractMutiResumeParser;
import com.moseeker.entity.pojo.resume.ResumeParseException;
import com.moseeker.profile.utils.DictCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 简历-期望
 */
@Component
public class IntentionsParser extends AbstractMutiResumeParser<Result, List<Intentions>> {
    Logger logger = LoggerFactory.getLogger(IntentionsParser.class);


    protected List<ResumeParseException> exceptions = new ArrayList<>();

    @Override
    protected List<Intentions> parseResume(Result result) throws ResumeParseException {
        // 期望
        Intentions intentions = new Intentions();
        if (result.getExpect_jnature() != null) {
            intentions.setWorktype(DictCode.workType(result.getExpect_jnature()));
        }

        if (StringUtils.isNotNullOrEmpty(result.getExpect_salary())) {
            try{
                int topSalary = Arrays.stream(result.getExpect_salary().replaceAll("[\\u4E00-\\u9FA5|/]", "")
                        .split("-|~", 2)).max(String::compareTo).map(m -> Integer.valueOf(m)).get();
                intentions.setSalaryCode(DictCode.salary(topSalary));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                ResumeParseException resumeParseException = new ResumeParseException();
                resumeParseException.setErrorLog("期望薪资转换异常: " + e.getMessage());
                resumeParseException.setFieldValue("expectSalary: " + result.getExpect_salary());
                exceptions.add(resumeParseException);
            }
        }

        // 期望城市
        if (StringUtils.isNotNullOrEmpty(result.getExpect_jlocation())) {
            City city = new City();
            city.setCityName(result.getExpect_jlocation());
            intentions.setCities(new ArrayList<City>(){{add(city);}});
        }



        // 期望职能
        if (StringUtils.isNotNullOrEmpty(result.getExpect_job())) {
            Positions positions = new Positions();
            positions.setPositionName(result.getExpect_job());
            intentions.setPositions(new ArrayList<Positions>(){{add(positions);}});
        }

        List<Intentions> intentionsList = new ArrayList<>();
        intentionsList.add(intentions);

        return intentionsList;
    }

    @Override
    protected List<Result> get(ResumeObj resumeProfile) {
        return new ArrayList<Result>(){{add(resumeProfile.getResult());}};
    }

    @Override
    protected void set(ProfileObj moseekerProfile, List<List<Intentions>> r) {
        if (r != null && r.size() > 0) {
            moseekerProfile.setIntentions(r.get(0));
        }
    }
}
