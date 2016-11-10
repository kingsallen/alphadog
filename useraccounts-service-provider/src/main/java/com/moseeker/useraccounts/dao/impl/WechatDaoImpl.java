package com.moseeker.useraccounts.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.hrdb.tables.HrWxWechat;
import com.moseeker.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.useraccounts.dao.WechatDao;

/**
 * 
 * 公众号数据服务 
 * <p>Company: MoSeeker</P>  
 * <p>date: Oct 9, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Repository
public class WechatDaoImpl extends BaseDaoImpl<HrWxWechatRecord, HrWxWechat> implements WechatDao {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrWxWechat.HR_WX_WECHAT;
    }
}
