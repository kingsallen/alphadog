package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.useraccounts.service.EmployeeBinder;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service("auth_method_question")
public class EmployeeBindByQuestion extends EmployeeBinder{

    private static final Logger log = LoggerFactory.getLogger(EmployeeBindByQuestion.class);

    @Override
    protected void paramCheck(BindingParams bindingParams, HrEmployeeCertConfDO certConf) throws Exception {
        // 问题校验
        List<String> answers = JSONObject.parseArray(certConf.getQuestions()).stream().map(m -> JSONObject.parseObject(String.valueOf(m)).getString("a")).collect(Collectors.toList());
        log.info("answers: {}", answers);
        String[] replys = {bindingParams.getAnswer1().trim(), bindingParams.getAnswer2().trim()};
        if (!StringUtils.isEmptyList(answers)) {
            for (int i = 0; i < answers.size(); i++) {
                if (!org.apache.commons.lang.StringUtils.defaultString(answers.get(i), "").equals(replys[i])) {
                    throw new RuntimeException("员工认证信息不正确");
                }
            }
        } else {
            throw new RuntimeException("员工认证信息不正确");
        }
    }
}
