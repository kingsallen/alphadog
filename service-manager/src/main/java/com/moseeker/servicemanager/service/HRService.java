package com.moseeker.servicemanager.service;

import com.moseeker.servicemanager.service.vo.HRInfo;

/**
 * @Author: jack
 * @Date: 2018/8/10
 */
public interface HRService {


    /**
     * 查找HR信息
     * @param id HR编号
     * @return HR信息
     */
    HRInfo getHR(int id) throws Exception;
}
