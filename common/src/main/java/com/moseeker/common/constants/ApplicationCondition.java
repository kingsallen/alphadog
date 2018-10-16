package com.moseeker.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 申请状态
 * Created by jack on 18/01/2018.
 */
public enum ApplicationCondition {

    RecomClick(7, "转发职位被点击"), ConsummateCandate(13, "完善被推荐人信息"), Apply(1, "被推荐人投递简历"),
    CVPassed(10, "简历评审合格"), Offered(12, "面试通过"), Hired(3, "入职");

    private ApplicationCondition(int state, String name) {
        this.state = state;
        this.name = name;
    }

    private static Map<Integer, ApplicationCondition> map = new HashMap<>();
    static {
        for (ApplicationCondition awardCondition : values()) {
            map.put(awardCondition.getState(), awardCondition);
        }
    }

    private int state;
    private String name;

    public int getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public static ApplicationCondition initFromSate(int state) {
        return map.get(state);
    }
}
