package com.moseeker.entity.pojo.resume;

/**
 * Created by YYF
 *
 * Date: 2017/8/30
 *
 * Project_name :alphadog
 */
public class TrainingObj {

    //    开始时间
    private String start_date;
    //    结束时间
    private String end_date;
    //    培训机构
    private String train_org;
    //    培训内容
    private String train_cont;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getTrain_org() {
        return train_org;
    }

    public void setTrain_org(String train_org) {
        this.train_org = train_org;
    }

    public String getTrain_cont() {
        return train_cont;
    }

    public void setTrain_cont(String train_cont) {
        this.train_cont = train_cont;
    }
}
