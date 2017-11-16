package com.moseeker.position.service.position.base;

/**
 * 第三方职位字段刷新接口，
 * 实现该接口后，
 * 会在{com.moseeker.position.service.schedule.ThirdPartyPositionRefresh}类中自动注册
 * 并且在服务启动时和{@Scheduled}设定的计时器触发时调用refresh
 *
 * @author pyb
 */
public interface ParamRefresh {
    public void refresh();
}
