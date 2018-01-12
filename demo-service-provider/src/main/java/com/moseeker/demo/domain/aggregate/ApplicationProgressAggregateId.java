package com.moseeker.demo.domain.aggregate;

import java.io.Serializable;
import java.util.List;

/**
 * 招聘进度业务聚合根编号
 * Created by jack on 11/01/2018.
 */
public class ApplicationProgressAggregateId implements Serializable {

    private static final long serialVersionUID = -6816253909441434710L;

    private int hrId;
    private List<Integer> applicationIdList;

    public ApplicationProgressAggregateId(int hrId, List<Integer> applicationIdList) {
        this.hrId = hrId;
        this.applicationIdList = applicationIdList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationProgressAggregateId that = (ApplicationProgressAggregateId) o;

        if (hrId != that.hrId) return false;

        if (applicationIdList != null && that.applicationIdList != null
                && applicationIdList.size() == that.applicationIdList.size()) {
            boolean flag = true;
            for (int i=0; i<applicationIdList.size(); i++) {
                if (applicationIdList.get(i) != null) {
                    if (applicationIdList.get(i).intValue() != that.applicationIdList.get(i)) {
                        flag = false;
                        break;
                    }
                } else {
                    if (that.applicationIdList.get(i) != null) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = hrId;
        result = 31 * result + applicationIdList.hashCode();
        return result;
    }
}
