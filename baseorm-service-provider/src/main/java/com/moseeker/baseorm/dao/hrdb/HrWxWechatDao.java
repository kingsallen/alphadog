package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import java.util.List;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HrWxWechatDao extends JooqCrudImpl<HrWxWechatDO, HrWxWechatRecord> {

    public HrWxWechatDao() {
        super(HrWxWechat.HR_WX_WECHAT, HrWxWechatDO.class);
    }

	public HrWxWechatDao(TableImpl<HrWxWechatRecord> table, Class<HrWxWechatDO> hrWxWechatDOClass) {
		super(table, hrWxWechatDOClass);
	}

	public HrWxWechatDO getHrWxWechatByCompanyId(int company_id ){
        List<HrWxWechatDO> result = create.selectFrom(HrWxWechat.HR_WX_WECHAT)
                .where(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.eq(company_id))
                .and(HrWxWechat.HR_WX_WECHAT.AUTHORIZED.eq((byte)AbleFlag.ENABLE.getValue()))
                .fetchInto(HrWxWechatDO.class);
        if(result != null && result.size()>0){
            return result.get(0);
        }
        return null;
    }
    /**
     * 获取公众号accessToken
     * @param
     * @author  cjm
     * @date  2018/5/14
     * @return
     */
    public Result getAccessTokenAndAppId(int companyId) {
        return create.select(HrWxWechat.HR_WX_WECHAT.ACCESS_TOKEN, HrWxWechat.HR_WX_WECHAT.APPID)
                .from(HrWxWechat.HR_WX_WECHAT)
                .where(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.eq(companyId))
                .fetch();
    }

    public List<HrWxWechatDO> getHrWxWechatByCompanyIds(List<Integer> companyIdList){
        List<HrWxWechatDO> result = create.selectFrom(HrWxWechat.HR_WX_WECHAT)
                .where(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.in(companyIdList))
                .fetchInto(HrWxWechatDO.class);
        return result;
    }

    public List<HrWxWechatDO> getHrWxWechatByIds(List<Integer> idList){
        List<HrWxWechatDO> result = create.selectFrom(HrWxWechat.HR_WX_WECHAT)
                .where(HrWxWechat.HR_WX_WECHAT.ID.in(idList))
                .fetchInto(HrWxWechatDO.class);
        return result;
    }

    /**
     * 查找公司公众号信息
     * @param wechatId 公众号编号
     * @return
     */
    public HrWxWechatDO fetchWechat(int wechatId) {
        return create.selectFrom(HrWxWechat.HR_WX_WECHAT)
                .where(HrWxWechat.HR_WX_WECHAT.ID.eq(wechatId))
                .fetchOneInto(HrWxWechatDO.class);

    }

    public HrWxWechatRecord getById(int id) {
        return create.selectFrom(HrWxWechat.HR_WX_WECHAT)
                .where(HrWxWechat.HR_WX_WECHAT.ID.eq(id))
                .fetchOne();
    }
}
