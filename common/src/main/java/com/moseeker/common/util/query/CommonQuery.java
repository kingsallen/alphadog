package com.moseeker.common.util.query;

import com.moseeker.thrift.gen.common.struct.Condition;
import com.moseeker.thrift.gen.common.struct.OrderBy;

import java.util.List;
import java.util.Map;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public class CommonQuery {

    private java.util.List<Select> attributes; // optional
    private Condition conditions; // optional
    private java.util.List<OrderBy> orders; // optional
    private java.util.List<String> groups; // optional
    private int pageSize; // optional
    private int pageNum; // optional
    private java.util.Map<String,String> extras; // optional

    public List<Select> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Select> attributes) {
        this.attributes = attributes;
    }

    public Condition getConditions() {
        return conditions;
    }

    public void setConditions(Condition conditions) {
        this.conditions = conditions;
    }

    public List<OrderBy> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderBy> orders) {
        this.orders = orders;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }
}
