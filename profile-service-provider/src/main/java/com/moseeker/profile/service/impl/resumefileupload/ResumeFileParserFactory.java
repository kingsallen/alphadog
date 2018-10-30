package com.moseeker.profile.service.impl.resumefileupload;

import com.moseeker.entity.EmployeeEntity;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.resumefileupload.iface.AbstractResumeFileParser;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author cjm
 * @date 2018-10-29 17:53
 **/
@Component
public class ResumeFileParserFactory {

    @Autowired
    List<AbstractResumeFileParser> fileParsers;

    @Autowired
    EmployeeEntity employeeEntity;

    public AbstractResumeFileParser getResumeFileParserByEmployeeId(int id){
        AbstractResumeFileParser fileParser = fileParsers.get(0);
        UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(id);
        if (employeeDO != null && employeeDO.getId() > 0) {
            for(AbstractResumeFileParser resumeFileParser : fileParsers){
                if(resumeFileParser instanceof ReferralProfileParser){
                    fileParser = resumeFileParser;
                    break;
                }
            }
        }else {
            for(AbstractResumeFileParser resumeFileParser : fileParsers){
                if(resumeFileParser instanceof UserProfileParser){
                    fileParser = resumeFileParser;
                    break;
                }
            }
        }
        return fileParser;
    }
}
