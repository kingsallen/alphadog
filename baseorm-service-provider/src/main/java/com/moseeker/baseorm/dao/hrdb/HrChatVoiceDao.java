package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatVoice;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatVoiceRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatVoiceDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * 语音dao
 *
 * @author cjm
 * @create 2018-05-07 18:03
 **/
@Service
public class HrChatVoiceDao extends JooqCrudImpl<HrWxHrChatVoiceDO, HrWxHrChatVoiceRecord> {

    public HrChatVoiceDao(TableImpl<HrWxHrChatVoiceRecord> table, Class<HrWxHrChatVoiceDO> hrWxHrChatVoiceDOClass) {
        super(table, hrWxHrChatVoiceDOClass);
    }

    public HrChatVoiceDao() {
        super(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE, HrWxHrChatVoiceDO.class);
    }

    /** 
     * @description  更新数据库语音存储表信息
     * @param   
     * @author  cjm
     * @date  2018/5/13 
     * @return   
     */ 
    public int updateVoiceFileInfo(int chatId, String voiceLocalUrl) {
        return create.update(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE)
                .set(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.LOCAL_URL, voiceLocalUrl)
                .where(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.CHAT_ID.eq(chatId))
                .execute();
    }


}
