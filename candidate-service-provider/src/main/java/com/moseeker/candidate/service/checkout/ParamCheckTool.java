package com.moseeker.candidate.service.checkout;

import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.common.validation.rules.DateType;
import com.moseeker.thrift.gen.candidate.struct.CandidateListParam;

/**
 * 校验工具
 * Created by jack on 06/04/2017.
 */
public class ParamCheckTool {

    public static ValidateUtil checkCandidateList(CandidateListParam param) {

        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("推荐人信息", param.getPostUserId(), null, "缺少必要的参数“推荐人信息”", 0, Integer.MAX_VALUE);
        vu.addRequiredStringValidate("点击时间", param.getClickTime(), null, "缺少必要的参数“点击时间”");
        vu.addIntTypeValidate("部门信息", param.getCompanyId(), null, "缺少必要的参数“部门信息”", 0, Integer.MAX_VALUE);
        vu.addDateValidate("点击时间", param.getClickTime(), DateType.shortDate, null, "“点击时间”格式不正确");
        return vu;
    }
}
