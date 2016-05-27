package com.moseeker.dict.pojo;

import java.sql.Timestamp;

/**
 * DictConstantPojo 字典常量Pojo类
 * <p>
 *
 * Created by zzh on 16/5/26.
 */
public class DictConstantPojo {

   public int id;
   public int code;
   public String name;
   public int priority;
   public int parent_code;
   public Timestamp create_time;
   public Timestamp update_time;

    public int getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public int getParent_code() {
        return parent_code;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setParent_code(int parent_code) {
        this.parent_code = parent_code;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", parent_code=" + parent_code +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
