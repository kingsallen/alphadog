package com.moseeker.position.service.position.jobsdb.refresh.handler;

import java.util.List;

public class WorkLocationPojo{
    private WorkLocation workLocation;
    private List<ChildWorkLocation> childWorkLocation;

    public WorkLocation getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(WorkLocation workLocation) {
        this.workLocation = workLocation;
    }

    public List<ChildWorkLocation> getChildWorkLocation() {
        return childWorkLocation;
    }

    public void setChildWorkLocation(List<ChildWorkLocation> childWorkLocation) {
        this.childWorkLocation = childWorkLocation;
    }

    public static class ChildWorkLocation{
        private String text;
        private String code;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
    public static class WorkLocation{
        private String text;
        private String code;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

}
