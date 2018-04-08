package com.moseeker.dict.service.impl.occupation;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DefaultOccupationHandler<T> extends AbstractOccupationHandler<T> {

    @Override
    public JSONObject toJsonObject(T t) {
        DefaultOccupationDO occupation = new DefaultOccupationDO();
        BeanUtils.copyProperties(t,occupation);

        JSONObject obj=new JSONObject();

        obj.put("code", occupation.getCode());
        obj.put("parent_id", occupation.getParentId());
        obj.put("name", occupation.getName());
        obj.put("code_other", occupation.getCodeOther());
        obj.put("level", occupation.getLevel());
        obj.put("status", occupation.getStatus());

        return obj;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.NONE;
    }

    static class DefaultOccupationDO{
        private int code; 
        private int parentId; 
        private String name; 
        private String codeOther; 
        private short level; 
        private short status; 
        private String createtime;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCodeOther() {
            return codeOther;
        }

        public void setCodeOther(String codeOther) {
            this.codeOther = codeOther;
        }

        public short getLevel() {
            return level;
        }

        public void setLevel(short level) {
            this.level = level;
        }

        public short getStatus() {
            return status;
        }

        public void setStatus(short status) {
            this.status = status;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }

}
