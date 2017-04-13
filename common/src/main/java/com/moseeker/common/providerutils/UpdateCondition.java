package com.moseeker.common.providerutils;

import com.moseeker.thrift.gen.common.struct.CommonUpdate;

/**
 * Created by zhangdi on 2017/3/21.
 */
public class UpdateCondition extends CommonCondition<UpdateCondition> {
    CommonUpdate commonUpdate;

    private UpdateCondition(CommonUpdate commonUpdate, CommonCondition condition) {
        init(condition);
        this.commonUpdate = commonUpdate;
        this.commonUpdate.setConditions(this);
    }

    public static UpdateCondition initWithCommonUpdate(CommonUpdate commonUpdate, CommonCondition condition) {
        return new UpdateCondition(commonUpdate, condition);
    }

    public CommonUpdate getCommonUpdate() {
        return commonUpdate;
    }

}
