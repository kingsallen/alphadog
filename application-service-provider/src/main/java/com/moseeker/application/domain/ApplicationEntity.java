package com.moseeker.application.domain;

import com.moseeker.application.domain.component.state.ApplicationState;
import com.moseeker.application.domain.component.state.ApplicationStateRoute;
import com.moseeker.application.exception.ApplicationException;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord;
import com.moseeker.common.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * 申请实体
 *
 * 非线程安全
 *
 * Created by jack on 17/01/2018.
 */
public class ApplicationEntity {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int id;                           //申请编号
    private ApplicationState state;                 //状态
    private final ApplicationState initState;       //申请信息初始化时，申请的状态
    private List<Integer> hrIdList;                 //有权限修改招聘进度、查看申请的HR集合
    private int viewNumber;                         //查看次数
    private final int initViewNumber;               //申请初始化时的浏览次数
    private boolean refuse;                         //true 表示申请被拒绝

    public ApplicationEntity(int id, int state, boolean refuse, List<Integer> hrIdList, int viewNumber) {
        this.id = id;
        logger.info("ApplicationEntity param state:{}, ", state);
        this.state = ApplicationStateRoute.initFromState(state).buildState(this);
        logger.info("ApplicationEntity state:{}, ", this.state);
        this.initState = ApplicationStateRoute.initFromState(state).buildState(this);
        this.hrIdList = hrIdList;
        this.viewNumber = viewNumber;
        this.initViewNumber = viewNumber;
        this.refuse = refuse;
        logger.info("ApplicationEntity refuse:{}, ", this.refuse);
    }

    /**
     * 简历被查阅
     * @param hrEntity
     * @return
     * @throws CommonException
     */
    public HrOperationRecord view(HREntity hrEntity) throws CommonException {
        if (!validateAuthority(hrEntity)) {
            throw ApplicationException.APPLICATION_HAVE_NO_PERMISSION;
        }
        HrOperationRecord hrOperationRecord = null;
        try {
            addViewNumber();
            logger.info("ApplicationEntity view state:{}", state.getStatus());
            logger.info("ApplicationEntity view state:{}", state.getStatus().getName());
            logger.info("ApplicationEntity view state:{}", state.getStatus().getState());
            if (!refuse) {
                state.pass();
            }
            hrOperationRecord = new HrOperationRecord();
            hrOperationRecord.setAdminId((long) hrEntity.getId());
            hrOperationRecord.setCompanyId((long) hrEntity.getCompanyId());
            hrOperationRecord.setAppId((long) id);
            logger.info("ApplicationEntity view state:{}", state.getStatus().getName());
            logger.info("ApplicationEntity view state:{}", state.getStatus().getState());
            hrOperationRecord.setOperateTplId(this.state.getStatus().getState());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return hrOperationRecord;
    }

    /**
     * 判断HR 是否有权限操作申请记录
     * @param hrEntity HR
     * @return true 有权限；false 没有权限
     */
    private boolean validateAuthority(HREntity hrEntity) {
        Optional<Integer> optional = this.hrIdList
                .stream()
                .filter(id -> id.intValue() == hrEntity.getId())
                .findAny();
        return optional.isPresent();
    }

    public int getId() {
        return id;
    }

    public ApplicationState getState() {
        return state;
    }

    public List<Integer> getHrIdList() {
        return hrIdList;
    }

    private void addViewNumber() {
        this.viewNumber ++;
    }

    public void setState(ApplicationState state) {
        this.state = state;
    }

    public ApplicationState getInitState() {
        return initState;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }

    public int getInitViewNumber() {
        return initViewNumber;
    }

    public boolean isRefuse() {
        return refuse;
    }
}
