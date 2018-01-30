package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 招聘进度状态
 * Created by jack on 18/01/2018.
 */
public enum ApplicationStatus {

    Apply(1, "简历提交成功"), CVChecked(6, "简历被HR查看/简历被下载"), CVPassed(10, "简历评审合格"), Offered(12, "面试通过"), Hired(3, "入职");

    private ApplicationStatus(int state, String name) {
        this.state = state;
        this.name = name;
    }

    private int state;
    private String name;

    private static Map<Integer, ApplicationStatus> map = new HashMap<>();
    private static Map<ApplicationStatus, Node> path = new HashMap<>();
    static {
        for (ApplicationStatus applicationStatus:ApplicationStatus.values()) {
            map.put(applicationStatus.getState(), applicationStatus);
        }
    }

    private void init() {
        ApplicationStatus applyState = ApplicationStatus.Apply;
        ApplicationStatus viewedState = ApplicationStatus.CVChecked;
        ApplicationStatus cvPassedState = ApplicationStatus.CVPassed;
        ApplicationStatus offeredStatus = ApplicationStatus.Offered;
        ApplicationStatus hiredState = ApplicationStatus.Hired;

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

    public static ApplicationStatus initFromState(int state) {
        return map.get(state);
    }

    public ApplicationState buildState(ApplicationEntity applicationEntity) {
        ApplicationState applicationState;
        switch (this) {
            case Apply: applicationState = new ApplyState(applicationEntity);break;
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
     * @param applicationStatus 申请进度状态
     * @return 下一个状态
     */
    public synchronized ApplicationStatus getNextNode(ApplicationStatus applicationStatus) {
        if (path.size() == 0) {
            init();
        }
        if (path.get(applicationStatus) != null) {
            if (path.get(applicationStatus).getNextNode() != null) {
                return path.get(applicationStatus).getNextNode().getState();
            }
        }
        return null;
    }

    /**
     * 获取流程定义中的当前状态的上一个状态
     * @param applicationStatus 申请进度状态
     * @return 上一个状态
     */
    public synchronized ApplicationStatus getPreNode(ApplicationStatus applicationStatus) {
        if (path.size() == 0) {
            init();
        }
        if (path.get(applicationStatus) != null) {
            if (path.get(applicationStatus).getPreNode() != null) {
                return path.get(applicationStatus).getPreNode().getState();
            }
        }
        return null;
    }

    public class Node {

        private Node preNode;               //上一个节点
        private ApplicationStatus state;     //当前节点
        private Node nextNode;              //下一个节点

        public ApplicationStatus getState() {
            return state;
        }

        public void setState(ApplicationStatus state) {
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
