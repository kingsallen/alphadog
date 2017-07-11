package com.moseeker.profile.service.impl.retriveprofile;

import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 简历回收流程
 * Created by jack on 10/07/2017.
 */
public abstract class RetrievalFlow {

    @Autowired
    UserUserDao userUserDao;

    @Autowired
    JobPositionDao positionDao;

    private Executor excutor;

    /**
     * 简历回收
     * @param parameter 参数
     * @return 执行结果
     * @throws BIZException 业务异常
     */
    public boolean retrieveProfile(RetrieveParam parameter) throws CommonException {
        if (excutor == null) {
            this.excutor = customExcutor();
            if (excutor == null) {
                throw ExceptionFactory.buildException(Category.VALIDATION_RETRIEVAL_EXCUTOR_NOT_CUSTOMED);
            }
        }
        Executor tmp = excutor;
        while (tmp != null) {
            tmp.execute(parameter);
            tmp = tmp.getNextExcutor();
        }
        return true;
    }

    /**
     * 定制流程
     * @return 执行者队列
     * @throws BIZException 业务异常
     */
    protected abstract Executor customExcutor() throws CommonException;

}
