package com.moseeker.demo.application;

import com.moseeker.common.exception.CommonException;

import java.util.List;

/**
 * Created by moseeker on 2017/4/11.
 */
public interface DemoApplicaiton {

    String viewApplication(int hrId, List<Integer> applicationIds) throws CommonException;

}
