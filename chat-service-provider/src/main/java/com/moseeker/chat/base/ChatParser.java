package com.moseeker.chat.base;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;

public interface ChatParser {
    //前端chat信息解析成对应数据库字段
    HrWxHrChatDO parseToDO(HrWxHrChatDO chatDO, JSONObject vo);

    //数据库字段解析成视图chat信息
    JSONObject parseToVO(JSONObject vo,HrWxHrChatDO chatDO);
}
