package com.moseeker.demo.application.impl;

import com.moseeker.common.exception.CommonException;
import com.moseeker.demo.application.DemoApplication;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jack on 11/01/2018.
 */
@Service
public class DemoApplicationImpl implements DemoApplication {

    @Override
    public String viewApplication(int hrId, List<Integer> applicationIds) throws CommonException {
        return "查看申请";
    }
}
