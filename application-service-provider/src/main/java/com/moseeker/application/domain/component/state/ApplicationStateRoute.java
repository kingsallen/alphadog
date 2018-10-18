package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 招聘进度状态路线图
 * Created by jack on 18/01/2018.
 */
public enum ApplicationStateRoute {

    Apply(1, "简历提交成功"), CVChecked(6, "简历被HR查看/简历被下载"), CVPassed(10, "简历评审合格"), Offered(12, "面试通过"),
    Hired(3, "入职"), EmployeeReferral(15, "员工上传人才简历");

    private ApplicationStateRoute(int state, String name) {
        this.state = state;
        this.name = name;
    }

    private int state;
    private String name;

    private static Map<Integer, ApplicationStateRoute> map = new HashMap<>();
    private static Map<ApplicationStateRoute, Node> path = new HashMap<>();
    static {
        for (ApplicationStateRoute applicationStateRoute : ApplicationStateRoute.values()) {
            map.put(applicationStateRoute.getState(), applicationStateRoute);
        }
    }

    private void init() {
        ApplicationStateRoute applyState = ApplicationStateRoute.Apply;
        ApplicationStateRoute referral = ApplicationStateRoute.EmployeeReferral;
        ApplicationStateRoute viewedState = ApplicationStateRoute.CVChecked;
        ApplicationStateRoute cvPassedState = ApplicationStateRoute.CVPassed;
        ApplicationStateRoute offeredStatus = ApplicationStateRoute.Offered;
        ApplicationStateRoute hiredState = ApplicationStateRoute.Hired;

        Node firstNode = new Node();
        firstNode.setState(applyState);

        Node secondNode = new Node();
        firstNode.setNextNode(secondNode);
        secondNode.setState(viewedState);

        Node thirdNode = new Node();
        secondNode.setNextNode(thirdNode);
        thirdNode.setState(cvPassedState);

        Node fourthNode = new Node();
        thirdNode.setNextNode(fourthNode);
        fourthNode.setState(offeredStatus);

        Node fifthNode = new Node();
        thirdNode.setNextNode(fifthNode);
        fifthNode.setState(hiredState);

        Node firstNode1 = new Node();
        firstNode1.setState(referral);
        firstNode1.setNextNode(secondNode);

        path.put(referral, firstNode1);
        path.put(applyState, firstNode);
        path.put(viewedState, secondNode);
        path.put(cvPassedState, thirdNode);
        path.put(offeredStatus, fourthNode);
        path.put(hiredState, fifthNode);
    }

    public int getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public static ApplicationStateRoute initFromState(int state) {
        return map.get(state);
    }

    public ApplicationState buildState(ApplicationEntity applicationEntity) {
        ApplicationState applicationState;
        switch (this) {
            case Apply: applicationState = new ApplyState(applicationEntity);break;
            case EmployeeReferral: applicationState = new ReferralState(applicationEntity); break;
            case CVChecked: applicationState = new CVCheckedState(applicationEntity); break;
            case CVPassed: applicationState = new CVPassedState(applicationEntity); break;
            case Hired:applicationState = new HiredState(applicationEntity); break;
            case Offered: applicationState = new OfferedStatus(applicationEntity); break;
            default: applicationState = null; break;
        }
        return applicationState;
    }

    /**
     * 获取流程定义中，当前状态的下一个状态
     * @param applicationStateRoute 申请进度状态
     * @return 下一个状态
     */
    public synchronized ApplicationStateRoute getNextNode(ApplicationStateRoute applicationStateRoute) {
        if (path.size() == 0) {
            init();
        }
        if (path.get(applicationStateRoute) != null) {
            if (path.get(applicationStateRoute).getNextNode() != null) {
                return path.get(applicationStateRoute).getNextNode().getState();
            }
        }
        return null;
    }

    /**
     * 获取流程定义中的当前状态的上一个状态
     * @param applicationStateRoute 申请进度状态
     * @return 上一个状态
     */
    public synchronized ApplicationStateRoute getPreNode(ApplicationStateRoute applicationStateRoute) {
        if (path.size() == 0) {
            init();
        }
        if (path.get(applicationStateRoute) != null) {
            if (path.get(applicationStateRoute).getPreNode() != null) {
                return path.get(applicationStateRoute).getPreNode().getState();
            }
        }
        return null;
    }

    public class Node {

        private Node preNode;               //上一个节点
        private ApplicationStateRoute state;     //当前节点
        private Node nextNode;              //下一个节点

        public ApplicationStateRoute getState() {
            return state;
        }

        public void setState(ApplicationStateRoute state) {
            this.state = state;
        }

        public Node getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }

        public Node getPreNode() {
            return preNode;
        }

        public void setPreNode(Node preNode) {
            this.preNode = preNode;
        }
    }
}
