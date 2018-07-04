package com.moseeker.profile.service.impl.resumesdk;

import com.moseeker.common.util.DateUtils;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.profile.Projectexps;
import com.moseeker.entity.pojo.resume.ProjectexpObj;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.resumesdk.iface.AbstractMutiResumeParser;
import com.moseeker.profile.service.impl.resumesdk.iface.ResumeParserHelper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

/**
 * 简历-项目经验
 */
@Component
public class ProjectexpsParser extends AbstractMutiResumeParser<ProjectexpObj, Projectexps> {


    @Override
    protected Projectexps parseResume(ProjectexpObj projectexpObj) throws ResumeParserHelper.ResumeParseException {
        Projectexps project = new Projectexps();
        try {
            if (projectexpObj.getEnd_date() != null && projectexpObj.getEnd_date().equals("至今")) {
                project.setEndUntilNow(1);
            } else {
                project.setEndDate(DateUtils.dateRepair(projectexpObj.getEnd_date(), "\\."));
            }
            project.setStartDate(DateUtils.dateRepair(projectexpObj.getStart_date(), "\\."));
        } catch (ParseException e) {
            throw new ResumeParserHelper.ResumeParseException()
                    .errorLog("日期转换异常: " + e.getMessage())
                    .fieldValue("projectexp: {\"startDate\": " + projectexpObj.getStart_date() + "\", \"endDate\":" + projectexpObj.getEnd_date() + "}");
        }
        // 职责
        project.setResponsibility(projectexpObj.getProj_resp());
        project.setDescription(projectexpObj.getProj_content());
        project.setName(projectexpObj.getProj_name());
        return project;
    }

    @Override
    protected List<ProjectexpObj> get(ResumeObj resumeProfile) {
        return resumeProfile.getResult().getProj_exp_objs();
    }

    @Override
    protected void set(ProfileObj moseekerProfile, List<Projectexps> r) {
        moseekerProfile.setProjectexps(r);
    }
}
