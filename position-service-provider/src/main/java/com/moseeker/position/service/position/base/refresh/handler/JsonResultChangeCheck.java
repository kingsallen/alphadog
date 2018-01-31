package com.moseeker.position.service.position.base.refresh.handler;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface JsonResultChangeCheck<T> {
    void changeCheck(List<T> oldDatas,List<T> newDatas);
}
