package com.moseeker.profile.service.impl.resumesdk;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojo.profile.Company;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.profile.Workexps;
import com.moseeker.entity.pojo.resume.JobExpObj;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.resumesdk.iface.AbstractMutiResumeParser;
import com.moseeker.entity.pojo.resume.ResumeParseException;
import com.moseeker.profile.utils.DictCode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 简历-工作经历
 */
@Component
public class WorkexpsParser extends AbstractMutiResumeParser<JobExpObj,Workexps> {
    @Override
    protected Workexps parseResume(JobExpObj jobExpObj) throws ResumeParseException {
        Workexps workexps = new Workexps();
        Company company = new Company();
        company.setCompanyIndustry(jobExpObj.getJob_industry());
        if (StringUtils.isNotNullOrEmpty(jobExpObj.getJob_cpy())) {
            company.setCompanyName(jobExpObj.getJob_cpy());
        } else {
            throw new ResumeParseException()
                    .errorLog("公司名称为空")
                    .fieldValue("job_exp_obj: " + JSONObject.toJSONString(jobExpObj));
        }
        int companyScaleMaxValue = 0;
        try {
            companyScaleMaxValue = Arrays.stream(org.apache.commons.lang.StringUtils.defaultIfBlank(jobExpObj.getJob_cpy_size() == null ? "0-0" :
                    jobExpObj.getJob_cpy_size().replaceAll("[\\u4E00-\\u9FA5]", ""), "0-0").split("-", 2)).max(String::compareTo).map(m -> Integer.valueOf(m)).get();
        } catch (Exception e) {
            throw new ResumeParseException()
                    .errorLog("公司规模转换异常: " + e.getMessage())
                    .fieldValue("companyScale: " + jobExpObj.getJob_cpy_size());
        }
        company.setCompanyScale(String.valueOf(DictCode.companyScale(companyScaleMaxValue)));
        workexps.setCompany(company);

        StringBuilder description = new StringBuilder();
        if(StringUtils.isNotNullOrEmpty(jobExpObj.getJob_cpy_desc())){
            description.append("公司描述："+jobExpObj.getJob_cpy_desc()+"\n");
        }
        if(StringUtils.isNotNullOrEmpty(jobExpObj.getJob_content())){
            description.append(jobExpObj.getJob_content()+"\n");
        }

        workexps.setDescription(description.toString());
        try {
            workexps.setStartDate(DateUtils.dateRepair(jobExpObj.getStart_date(), "\\."));
            if (jobExpObj.getEnd_date() != null && jobExpObj.getEnd_date().equals("至今")) {
                workexps.setEndUntilNow(1);
            } else {
                workexps.setEndDate(DateUtils.dateRepair(jobExpObj.getEnd_date(), "\\."));
            }
        } catch (Exception e) {
            throw new ResumeParseException()
                    .errorLog("日期转换异常: " + e.getMessage())
                    .fieldValue("workexp: {\"startDate\": " + jobExpObj.getStart_date() + "\", \"endDate\":" + jobExpObj.getEnd_date()+"}");
        }
        if (StringUtils.isNullOrEmpty(jobExpObj.getJob_position())) {
            throw new ResumeParseException()
                    .errorLog("工作职位为空: ");
        } else {
            workexps.setJob(jobExpObj.getJob_position());
        }
        if (StringUtils.isNotNullOrEmpty(jobExpObj.getJob_nature())) {
            workexps.setType(DictCode.workType(jobExpObj.getJob_nature()));
        }
        workexps.setDepartmentName(jobExpObj.getJob_cpy_dept());
        return workexps;
    }

    @Override
    protected List<JobExpObj> get(ResumeObj resumeProfile) {
        return resumeProfile.getResult().getJob_exp_objs();
    }

    @Override
    protected void set(ProfileObj moseekerProfile, List<Workexps> r) {
        moseekerProfile.setWorkexps(r);
    }
}
