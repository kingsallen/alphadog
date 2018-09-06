package com.moseeker.profile.service.impl.resumesdk;

import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojo.profile.Education;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.EducationObj;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.resumesdk.iface.AbstractMutiResumeParser;
import com.moseeker.entity.pojo.resume.ResumeParseException;
import com.moseeker.profile.utils.DegreeSource;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

/**
 * 简历-教育经历
 */
@Component
public class EducationMutiParser extends AbstractMutiResumeParser<EducationObj, Education> {
    @Override
    protected Education parseResume(EducationObj educationObj) throws ResumeParseException {
        Education education = new Education();
        if (educationObj.getEdu_degree() != null) {
            int degree = DegreeSource.get(educationObj.getEdu_degree());
            education.setDegree(degree);
        }
        try {
            education.setStartDate(DateUtils.dateRepair(educationObj.getStart_date(), "\\."));
            if (educationObj.getEnd_date() != null && educationObj.getEnd_date().equals("至今")) {
                education.setEndUntilNow(1);
            } else {
                education.setEndDate(DateUtils.dateRepair(educationObj.getEnd_date(), "\\."));
            }
        } catch (ParseException e) {
            throw new ResumeParseException()
                    .errorLog("日期转换异常: " + e.getMessage())
                    .fieldValue("education: {\"startDate\": " + educationObj.getStart_date() + "\", \"endDate\":" + educationObj.getEnd_date() + "}");
        }
        // 学校名称
        education.setCollegeName(educationObj.getEdu_college());
        // 专业名称
        education.setMajorName(educationObj.getEdu_major());
        if (StringUtils.isNotNullOrEmpty(educationObj.getEdu_recruit())) {
            education.setIsUnified(educationObj.getEdu_recruit().equals("统招") ? 1 : 2);
        }

        education.setDescription("无");
        return education;
    }

    @Override
    protected List<EducationObj> get(ResumeObj resumeProfile) {
        return resumeProfile.getResult().getEducation_objs();
    }

    @Override
    protected void set(ProfileObj moseekerProfile, List<Education> r) {
        moseekerProfile.setEducations(r);
    }
}
