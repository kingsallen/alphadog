package com.moseeker.profile.service.impl.resumesdk;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojo.profile.Credential;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.CertObj;
import com.moseeker.entity.pojo.resume.Result;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.resumesdk.iface.AbstractResumeParser;
import com.moseeker.profile.service.impl.resumesdk.iface.ResumeParserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 简历-证书
 */
@Component
public class CredentialParser extends AbstractResumeParser<Result,List<Credential>> {
    Logger logger = LoggerFactory.getLogger(CredentialParser.class);

    @Override
    protected List<Credential> parseResume(Result result) throws ResumeParserHelper.ResumeParseException {
        List<CertObj> certObjs = result.getCert_objs();
        List<Credential> credentialList = new ArrayList<>();
        if (!StringUtils.isEmptyList(certObjs)) {
            for (CertObj certObj : certObjs) {
                Credential credential = new Credential();
                credential.setName(certObj.getLangcert_name());
                credentialList.add(credential);
            }
        }
        logger.info("profileParser getCredentials:{}", JSON.toJSONString(credentialList));

        if (credentialList.isEmpty() && StringUtils.isNotNullOrEmpty(result.getCont_certificate())) {
            throw new ResumeParserHelper.ResumeParseException()
                    .errorLog("证书为空，证书内容却不为空 ")
                    .fieldValue("contCertificate: " + result.getCont_certificate());
        }
        return credentialList;
    }

    @Override
    protected Result get(ResumeObj resumeProfile) {
        return resumeProfile.getResult();
    }

    @Override
    protected void set(ProfileObj moseekerProfile, List<Credential> credentials) {
        moseekerProfile.setCredentials(credentials);
    }
}
