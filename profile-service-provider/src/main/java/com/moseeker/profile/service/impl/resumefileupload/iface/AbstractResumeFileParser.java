package com.moseeker.profile.service.impl.resumefileupload.iface;

import com.moseeker.profile.service.impl.resumefileupload.resumeFileParser;
import com.moseeker.profile.service.impl.vo.ProfileDocParseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public abstract class AbstractResumeFileParser implements resumeFileParser {
    Logger logger = LoggerFactory.getLogger(AbstractResumeFileParser.class);

    @Override
    public  ProfileDocParseResult parseResume(Integer id, String fileName, ByteBuffer fileData) {
        return null;
    }

}
