package com.moseeker.apps.utils;

import java.util.List;

import com.moseeker.apps.bean.RecruitmentResult;
import com.moseeker.thrift.gen.config.HrAwardConfigTemplate;

public class BusinessUtil {
	public static RecruitmentResult excuteRecruitRewardOperation(int preRecruitOrder, int recruitOrder, List<HrAwardConfigTemplate> recruitProcesses) {
		RecruitmentResult result = new RecruitmentResult();
		int recruitOrderCVApply = 0;
		int recruitOrderCVChecked = 0;
		int recruitOrderCVPassedReward = 0;
		int recruitOrderOfferedReward = 0;
		int recruitOrderHiredReward = 0;
		for(HrAwardConfigTemplate recruitProcess : recruitProcesses) {
            if (recruitProcess.getRecruit_order() == recruitOrder) {
                result.setPointsConfId(recruitProcess.getPoints_conf_id());
            }
			if((Integer)recruitProcess.getRecruit_order() == 3 && recruitProcess.getReward()!=0l) {
				recruitOrderCVApply = (int) recruitProcess.getReward();
			}
			if((Integer)recruitProcess.getRecruit_order() == 4 && 
					recruitProcess.getReward()!=0l) {
				recruitOrderCVChecked = (int) recruitProcess.getReward();
			}
			if((Integer)recruitProcess.getRecruit_order() == 7 && 
					recruitProcess.getReward() != 0l) {
				recruitOrderCVPassedReward = (int) recruitProcess.getReward();
			}
			if((Integer)recruitProcess.getRecruit_order() == 10 && 
					recruitProcess.getReward()!= 0l) {
				recruitOrderOfferedReward = (int) recruitProcess.getReward();
			}
			if((Integer)recruitProcess.getRecruit_order() == 12 && 
					recruitProcess.getReward() != 0l) {
				recruitOrderHiredReward =(int) recruitProcess.getReward();
			}
		}
		if(preRecruitOrder == 0 && recruitOrder == 3) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_APPLY);
			result.setReward(recruitOrderCVApply);
			result.setStatus(0);
		}
		if(preRecruitOrder == 3 && recruitOrder == 4) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_CVCHECKED);
			result.setReward(recruitOrderCVChecked);
			
			result.setStatus(0);
		}
		if(preRecruitOrder == 4 && recruitOrder == 7) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_CVPASSED);
			result.setReward(recruitOrderCVPassedReward);
			
			result.setStatus(0);
		}
		if(preRecruitOrder == 7 && recruitOrder == 10) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_OFFERED);
			result.setReward(recruitOrderOfferedReward);
			
			result.setStatus(0);
		}
		if(preRecruitOrder == 10 && recruitOrder == 12) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_HIRED);
			result.setReward(recruitOrderHiredReward);
			result.setStatus(0);
		}
		
		if(preRecruitOrder == 12 && recruitOrder == 10) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_REOFFERED);
			result.setReward(0);
			result.setStatus(0);
		}
		if(preRecruitOrder == 10 && recruitOrder == 7) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_RECVPASSED);
			result.setReward(0);
			result.setStatus(0);
		}
		if(preRecruitOrder == 7 && recruitOrder == 4) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_RECVCHECKED);
			result.setReward(0);
			result.setStatus(0);
		}
		if(recruitOrder == 13) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_BATCHREJECT);
			result.setStatus(0);
		}
		if(preRecruitOrder == 3 && recruitOrder == 13) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_APPLYREJECT);
			result.setStatus(0);
		}
		if(preRecruitOrder == 4 && recruitOrder == 13) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_CVCHECKEDREJECT);
			result.setStatus(0);
		}
		if(preRecruitOrder == 7 && recruitOrder == 13) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_CVPASSEDREJECT);
			result.setStatus(0);
		}
		if(preRecruitOrder == 10 && recruitOrder == 13) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_OFFEREDREJECT);
			
			result.setStatus(0);
		}
		if(preRecruitOrder == 12 && recruitOrder == 13) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_OFFEREDREJECT);
			result.setStatus(0);
		}
		if(preRecruitOrder == 13 && recruitOrder == 4) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_CANCELILLEGAL);
			result.setReward(0);
			result.setStatus(0);
		}
		if(preRecruitOrder == 13 && recruitOrder == 3) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_CANCELILLEGAL);
			result.setReward(0);
			result.setStatus(0);
		}
		if(preRecruitOrder == 13 && recruitOrder == 4) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_CANCELILLEGAL);
			result.setReward(0);
			result.setStatus(0);
		}
		if(preRecruitOrder == 13 && recruitOrder == 7) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_CANCELILLEGAL);
			result.setReward(0);
			result.setStatus(0);
		}
		if(preRecruitOrder == 13 && recruitOrder == 10) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_CANCELILLEGAL);
			result.setReward(0);
			result.setStatus(0);
		}
		if(preRecruitOrder == 13 && recruitOrder == 12) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_CANCELILLEGAL);
			result.setReward(0);
			result.setStatus(0);
		}
		if(preRecruitOrder == 13 && recruitOrder == 99) {
			result.setReason(ProcessUtils.LETTERS_RECRUITMENT_CANCELILLEGAL);
			result.setReward(0);
			result.setStatus(0);
		}
		return result;
	}
}
