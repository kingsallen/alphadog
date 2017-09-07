package com.moseeker.candidate.service.checkout;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.thrift.gen.candidate.struct.RecommmendParam;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jack on 07/09/2017.
 */
public class ParamCheckToolTest {

    @Test
    public void checkRecommend() throws Exception {
        RecommmendParam recommmendParam = new RecommmendParam();
        recommmendParam.setClickTime("2017-07-08");
        recommmendParam.setCompanyId(1);
        recommmendParam.setPostUserId(1);
        recommmendParam.setId(1);
        ValidateUtil validateUtil = ParamCheckTool.checkRecommend(recommmendParam);

        assertEquals(true, StringUtils.isNotNullOrEmpty(validateUtil.validate()));
    }

}