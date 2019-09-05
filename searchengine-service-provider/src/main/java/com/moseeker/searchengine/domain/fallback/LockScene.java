package com.moseeker.searchengine.domain.fallback;

/**
 * Redis全局锁的场景
 *
 * @Author jack
 * @Date 2019/9/5 12:18 PM
 * @Version 1.0
 */
public enum LockScene {
    /**
     * PC端职位列表页面
     */
    PC_POSITION_LIST,
    /**
     * PC端公司列表
     */
    PC_COMPANY_LIST;
}
