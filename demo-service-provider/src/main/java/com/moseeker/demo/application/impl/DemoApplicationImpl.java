package com.moseeker.demo.application.impl;

import com.moseeker.common.exception.CommonException;
import com.moseeker.demo.application.DemoApplicaiton;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jack on 11/01/2018.
 */
@Service
public class DemoApplicationImpl implements DemoApplicaiton {

    @Override
    public String viewApplication(int hrId, List<Integer> applicationIds) throws CommonException {
        return null;
    }
}
