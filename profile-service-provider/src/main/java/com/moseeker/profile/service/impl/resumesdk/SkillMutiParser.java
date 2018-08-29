package com.moseeker.profile.service.impl.resumesdk;

import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.profile.Skill;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.entity.pojo.resume.SkillsObjs;
import com.moseeker.profile.service.impl.resumesdk.iface.AbstractMutiResumeParser;
import com.moseeker.entity.pojo.resume.ResumeParseException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 简历-技能
 */
@Component
public class SkillMutiParser extends AbstractMutiResumeParser<SkillsObjs,Skill> {
    @Override
    protected Skill parseResume(SkillsObjs skillsObjs) throws ResumeParseException {
        Skill skill = new Skill();
        skill.setName(skillsObjs.getSkills_name());
        return skill;
    }

    @Override
    protected List<SkillsObjs> get(ResumeObj resumeProfile) {
        return resumeProfile.getResult().getSkills_objs();
    }

    @Override
    protected void set(ProfileObj moseekerProfile, List<Skill> r) {
        moseekerProfile.setSkills(r);
    }
}
