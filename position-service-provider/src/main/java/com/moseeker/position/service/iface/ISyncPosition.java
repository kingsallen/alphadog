package com.moseeker.position.service.iface;

/**
 * 通过api同步职位（发布、下架、恢复、编辑）
 * 目前api对接的有猎聘、58同城
 * @author cjm
 * @date 2018-10-26 14:11
 **/
public interface ISyncPosition<T> {

    void createPosition(T t);

    void endPosition(T t);

    void republishPosition(T t);

    void editPosition(T t);

}
