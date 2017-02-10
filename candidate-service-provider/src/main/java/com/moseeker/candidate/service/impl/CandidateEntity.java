package com.moseeker.candidate.service.impl;

import com.moseeker.candidate.service.Candidate;
import com.moseeker.common.validation.ValidateUtil;

/**
 * Created by jack on 10/02/2017.
 */
public class CandidateEntity implements Candidate {
    @Override
    public void glancePosition(int userID, int positionID, boolean fromEmployee) {
        ValidateUtil vu = new ValidateUtil();
        vu.addRequiredValidate("微信编号", userID, null, null);
        vu.addRequiredValidate("职位编号", positionID, null, null);
        vu.addRequiredValidate("是否来自员工转发", fromEmployee, null, null);
        vu.validate();
        if(vu.getVerified().get()) {
            //检查数据是否存在

            //存在
                //添加

        }
    }
}
