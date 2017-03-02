package com.moseeker.candidate.service.entities;

import com.moseeker.candidate.service.Candidate;
import com.moseeker.candidate.service.dao.CandidateDBDao;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.thrift.gen.dao.struct.*;
import org.apache.commons.pool2.PoolUtils;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 候选人实体，提供候选人相关业务
 * //todo 数据库访问操作，如果可以并行处理，则并行处理
 * Created by jack on 10/02/2017.
 */
@Component
public class CandidateEntity implements Candidate {

    Logger logger = LoggerFactory.getLogger(CandidateEntity.class);

    /**
     * C端用户查看职位，判断是否生成候选人数据
     * @param userID 用户编号
     * @param positionID 职位编号
     * @param shareChainID 是否来自员工转发
     */
    @Override
    public void glancePosition(int userID, int positionID, int shareChainID) {
        ValidateUtil vu = new ValidateUtil();
        vu.addRequiredValidate("微信编号", userID, null, null);
        vu.addRequiredValidate("职位编号", positionID, null, null);
        vu.addRequiredValidate("是否来自员工转发", shareChainID, null, null);
        vu.validate();
        if(vu.getVerified().get()) {
            try {
                //检查候选人数据是否存在
                ThreadPool tp = ThreadPool.Instance;
                Future userFuture = tp.startTast(() -> CandidateDBDao.getUserByID(userID));
                Future positionFuture = tp.startTast(() -> CandidateDBDao.getPositionByID(positionID));
                Future shareChainDOFuture = tp.startTast(() -> CandidateDBDao.getCandidateShareChain(shareChainID));
                CandidateShareChainDO shareChainDO = null;
                UserUserDO userUserDO = null;
                JobPositionDO jobPositionDO = null;
                try {
                    shareChainDO = (CandidateShareChainDO) shareChainDOFuture.get();
                    userUserDO = (UserUserDO) userFuture.get();
                    jobPositionDO = (JobPositionDO) positionFuture.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                }

                if(userUserDO != null && jobPositionDO != null) {

                    boolean fromEmployee = false;       //是否是员工转发
                    if(shareChainDO != null) {
                        UserEmployeeDO employeeDO = CandidateDBDao.getEmployee(shareChainDO.getRootRecomUserId());
                        if(employeeDO != null) {
                            fromEmployee = true;
                        }
                    }

                    //暂时不考虑HR的问题
                    List<CandidateRemarkDO> crs = CandidateDBDao.getCandidateRemarks(userID, jobPositionDO.getCompanyId());
                    if(crs != null && crs.size() > 0) {
                        crs.forEach(candidateRemark -> candidateRemark.setStatus((byte)1));
                    }
                    CandidateDBDao.updateCandidateRemarks(crs);

                    String date = new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS");
                    Optional<CandidatePositionDO> cp = CandidateDBDao.getCandidatePosition(positionID, userID);



                    if(cp.isPresent()) {
                        cp.get().setViewNumber(cp.get().getViewNumber()+1);
                        cp.get().setSharedFromEmployee(fromEmployee?(byte)1:0);
                        CandidateDBDao.updateCandidatePosition(cp.get());
                    } else {
                        Optional<CandidateCompanyDO> candidateCompany = CandidateDBDao.getCandidateCompanyByUserIDCompanyID(userID, jobPositionDO.getCompanyId());
                        if(candidateCompany.isPresent()) {
                            candidateCompany.get().setCompanyId(jobPositionDO.getCompanyId());
                            candidateCompany.get().setNickname(userUserDO.getNickname());
                            candidateCompany.get().setHeadimgurl(userUserDO.getHeadimg());
                            candidateCompany.get().setSysUserId(userID);
                            candidateCompany.get().setMobile(String.valueOf(userUserDO.getMobile()));
                            candidateCompany.get().setEmail(userUserDO.getEmail());
                            candidateCompany.get().setUpdateTime(date);
                            CandidateDBDao.updateCandidateCompany(candidateCompany.get());
                        }
                        cp.get().setCandidateCompanyId(candidateCompany.get().getId());
                        cp.get().setViewNumber(1);
                        cp.get().setUpdateTime(date);
                        cp.get().setSharedFromEmployee(fromEmployee?(byte)1:0);
                        CandidateDBDao.saveCandidatePosition(cp.get());
                    }
                }
            } catch (TException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
