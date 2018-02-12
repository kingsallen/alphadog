package com.moseeker.demo.application;

import com.moseeker.common.exception.CommonException;

import java.util.List;

/**
 * Created by moseeker on 2017/4/11.
 */
public interface DemoApplicaiton {

    /**
     * HR浏览申请记录
     * @param hrId HR编号
     * @param applicationIds 申请编号
     * @return 浏览结果
     * @throws CommonException 业务异常
     */
    String viewApplication(int hrId, List<Integer> applicationIds) throws CommonException;
}
