package com.moseeker.profile.service.impl.resumesdk;

import com.moseeker.entity.pojo.profile.Language;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.LangObj;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.resumesdk.iface.AbstractMutiResumeParser;
import com.moseeker.entity.pojo.resume.ResumeParseException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 简历-语言能力
 */
@Component
public class LanguageParser extends AbstractMutiResumeParser<LangObj,Language> {
    @Override
    protected Language parseResume(LangObj langObj) throws ResumeParseException {
        Language language = new Language();
        language.setName(langObj.getLanguage_name());
        return language;
    }

    @Override
    protected List<LangObj> get(ResumeObj resumeProfile) {
        return resumeProfile.getResult().getLang_objs();
    }

    @Override
    protected void set(ProfileObj moseekerProfile, List<Language> r) {
        moseekerProfile.setLanguages(r);
    }
}
