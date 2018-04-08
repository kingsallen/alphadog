package com.moseeker.position.service.position.base.refresh.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.position.utils.PositionParamRefreshUtils;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;

public abstract class DefaultOccupationResultHandler<T> extends AbstractOccupationResultHandler<T>{

    @Override
    protected boolean equals(T oldData, T newData) {
        DefaultOccupationDO oldDataDO = new DefaultOccupationDO();
        DefaultOccupationDO newDataDO = new DefaultOccupationDO();
        BeanUtils.copyProperties(oldData,oldDataDO);
        BeanUtils.copyProperties(newData,newDataDO);

        return oldDataDO.getName().equals(newDataDO.getName())
                && oldDataDO.getCodeOther().equals(newDataDO.getCodeOther())
                && oldDataDO.getLevel() == newDataDO.getLevel();
    }

    @Override
    protected T buildOccupation(List<String> texts, List<String> codes, Map<String, Integer> newCode, JSONObject msg) {
        DefaultOccupationDO temp = new DefaultOccupationDO();

        temp.setCodeOther(codes.get(codes.size() - 1));
        temp.setCode(newCode.get(temp.getCodeOther()));
        temp.setLevel((short) codes.size());
        temp.setName(PositionParamRefreshUtils.lastString(texts));
        temp.setParentId(newCode.get(PositionParamRefreshUtils.parentCode(codes)));
        temp.setStatus((short) 1);

        return JSON.parseObject(JSON.toJSONString(temp), getOccupationClass());
    }

    protected abstract Class<T> getOccupationClass();

    private static class DefaultOccupationDO {
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
