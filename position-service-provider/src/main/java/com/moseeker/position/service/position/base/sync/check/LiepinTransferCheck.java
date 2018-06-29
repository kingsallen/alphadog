package com.moseeker.position.service.position.base.sync.check;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjm
 * @date 2018-06-22 11:21
 **/
public class LiepinTransferCheck extends AbstractTransferCheck<ThirdPartyPosition> {
    private static String OCCUPATION_NOT_EMPTY = "职能不为空!";
    private static String DEPARTMENT_NOT_EMPTY = "部门不为空!";
    private static String SALARY_TOP_NOT_EMPTY = "薪资上限不为空!";
    private static String SALARY_BOTTOM_NOT_EMPTY = "薪资下限不为空!";
    private static String SALARY_ERROR = "薪资上限需要大于薪资下限!";
    private static String SALARYMONTH_NOT_EMPTY = "薪资发放月数不为空!";
    private static String FEATURE_NOT_EMPTY = "福利特色不为空!";

    @Override
    public Class<ThirdPartyPosition> getFormClass() {
        return ThirdPartyPosition.class;
    }

    @Override
    public List<String> getError(ThirdPartyPosition thirdPartyPosition, JobPositionDO moseekerPosition) {
        if(moseekerPosition.getCandidateSource() == 0){
            List<String> errorMsg=new ArrayList<>();

            // 必须设置职能
            List<String> occuptationList = thirdPartyPosition.getOccupation();
            if(StringUtils.isEmptyList(occuptationList)){
                errorMsg.add(OCCUPATION_NOT_EMPTY);
            }

            // 必须设置部门
            if(StringUtils.isNullOrEmpty(thirdPartyPosition.getDepartmentName())){
                errorMsg.add(DEPARTMENT_NOT_EMPTY);
            }

            // 必须设置薪资上限
            if(thirdPartyPosition.getSalaryTop() == 0){
                errorMsg.add(SALARY_TOP_NOT_EMPTY);
            }

            // 必须设置薪资下限
            if(thirdPartyPosition.getSalaryBottom() == 0){
                errorMsg.add(SALARY_BOTTOM_NOT_EMPTY);
            }

            if(thirdPartyPosition.getSalaryTop() <= thirdPartyPosition.getSalaryBottom()){
                errorMsg.add(SALARY_ERROR);
            }

            // 必须设置发放月数
            if(thirdPartyPosition.getSalaryMonth() < 12){
                errorMsg.add(SALARYMONTH_NOT_EMPTY);
            }

            // 必须设置福利特色
            if(thirdPartyPosition.getFeature() == null || thirdPartyPosition.getFeature().size() < 1){
                errorMsg.add(FEATURE_NOT_EMPTY);
            }else if(thirdPartyPosition.getFeature() != null && thirdPartyPosition.getFeature().size() > 1){
                int index = 0;
                for(String singleFeature : thirdPartyPosition.getFeature()){
                    if(StringUtils.isNullOrEmpty(singleFeature)){
                        index ++;
                    }
                }
                if(thirdPartyPosition.getFeature().size() == index){
                    errorMsg.add(FEATURE_NOT_EMPTY);
                }
            }

            return errorMsg;
        }
        return null;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.LIEPIN;
    }
}
