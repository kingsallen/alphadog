package com.moseeker.candidate.service.entities;

import com.moseeker.candidate.service.Candidate;
import com.moseeker.candidate.service.pos.*;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.validation.ValidateUtil;
import java.util.Date;
import java.util.List;

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
            Position position = new Position(positionID);
            /*Future userFuture = ThreadPool.Instance.startTast(() -> user.dbExist());
            Future positionFuture = ThreadPool.Instance.startTast(() -> position.dbExist());*/

            if(user.dbExist() && position.dbExist()) {

                //暂时不考虑HR的问题
                QueryUtil queryUtil = new QueryUtil();
                queryUtil.addEqualFilter("user_id", String.valueOf(userID));
                List<CandidateRemark> crs = CandidateRemark.getCandidateRemarks();
                if(crs != null && crs.size() > 0) {
                    crs.forEach(candidateRemark -> candidateRemark.setStatus(1));
                }
                CandidateRemark.updateToDB(crs);

                Date date = new Date();
                CandidatePosition cp = new CandidatePosition(positionID, userID);
                if(cp.dbExist()) {
                    cp.addViewNumber();
                    cp.setSharedFromEmployee(fromEmployee);
                    cp.updateToDB();
                } else {
                    CandidateCompany candidateCompany = new CandidateCompany(position.getCompanyId(), userID);
                    if(candidateCompany.dbExist()) {
                        candidateCompany.setCompanyId(position.getCompanyId());
                        candidateCompany.setNickname(user.getNickname());
                        candidateCompany.setHeadimgurl(user.getHeadimg());
                        candidateCompany.setSysUserId(userID);
                        candidateCompany.setMobile(String.valueOf(user.getMobile()));
                        candidateCompany.setEmail(user.getEmail());
                        candidateCompany.setUpdateTime(date);
                        candidateCompany.upsertToDB();
                    }
                    cp.setCandidateCompanyId(candidateCompany.getId());
                    cp.setViewNumber(1);
                    cp.setUpdateTime(date);
                    cp.setSharedFromEmployee(fromEmployee);
                    cp.upsertToDB();
                }
            }
        }
    }
}
