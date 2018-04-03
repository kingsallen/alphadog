package com.moseeker.consistencysuport.db;

/**
 *
 * 参数转换
 *
 * 将业务调用方使用到的参数转成可以持久化到数据库的字符串；并且提供数据库中字符串的参数还原成原始的参数
 *
 * Created by jack on 03/04/2018.
 */
public interface ParamConvertTool {

    /**
     * 参数转成字符串，用于持久化
     * @param params 消息发送的接口
     * @return
     */
    String convertParamToStorage(Object... params);

    /**
     * 将数据库的字符串参数解析成发送消息所需的参数
     *
     * @return
     */
    Object[] convertStorageToParam();
}
