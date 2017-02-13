package com.moseeker.candidate.service.entities;

import com.moseeker.candidate.service.Candidate;
import com.moseeker.candidate.service.values.User;
import com.moseeker.common.validation.ValidateUtil;

/**
 * 候选人实体，提供候选人相关业务
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
            //检查候选人数据是否存在
            User user = new User(userID);
            //存在
                //职位是否查看过
                //查看过
                    //添加浏览次数
                //未查看过
                    //新增职位查看记录
            //不存在
                //排除员工和HR
                //新增候选人记录
                //新增职位浏览记录

        }
    }
}
