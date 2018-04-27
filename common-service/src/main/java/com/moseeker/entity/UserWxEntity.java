package com.moseeker.entity;

import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service
public class UserWxEntity {

    private static final Logger log = LoggerFactory.getLogger(UserWxEntity.class);

    @Autowired
    private UserWxUserDao wxUserDao;

    @Autowired
    private HrWxWechatDao hrWxWechatDao;

    @Autowired
    private UserUserDao userUserDao;

    @Autowired
    private UserWxUserDao userWxUserDao;

    @Autowired
    private UserEmployeeDao userEmployeeDao;
    /**
     *  获取用户wxUserId
     */
    public int getWxuserId(int userId, int companyId) {
        int wxUserId = 0;
        try {
            Query.QueryBuilder query = new Query.QueryBuilder();
            query.where("company_id", String.valueOf(companyId));
            query.and("type", String.valueOf(1));
            HrWxWechatDO wxWechatDO = hrWxWechatDao.getData(query.buildQuery());
            if (wxWechatDO == null || wxWechatDO.getId() <= 0){
                log.info("not found hrwxwechat by company_id={}, type", companyId, 0);
                return wxUserId;
            } else {
                query.clear();
                query.where("sysuser_id", String.valueOf(userId));
                query.and("wechat_id", String.valueOf(wxWechatDO.getId()));
                UserWxUserDO userWxUserDO = wxUserDao.getData(query.buildQuery());
                if (userWxUserDO != null && userWxUserDO.getId() > 0) {
                    wxUserId = (int)userWxUserDO.getId();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return wxUserId;
    }

    public List<UserEmployeeDO> handlerData(List<UserEmployeeDO> dataList ){
        if(!StringUtils.isEmptyList(dataList)){
            List<Integer> userIdList=this.getUserIdList(dataList);
            if(!StringUtils.isEmptyList(userIdList)){
                List<UserWxUserDO> list=this.getUserWXUser(userIdList);
                if(!StringUtils.isEmptyList(list)){
                    for(UserWxUserDO userWxUserDO:list){
                        int sysUserId=userWxUserDO.getSysuserId();
                        String name=userWxUserDO.getNickname();
                        if(StringUtils.isNotNullOrEmpty(name)){
                            for(UserEmployeeDO DO:dataList){
                                if(DO.getSysuserId()==sysUserId){
                                    DO.setCfname(name);
                                }
                            }
                        }

                    }
                }
            }

        }
        return dataList;
    }




    private List<UserWxUserDO> getUserWXUser(List<Integer> userIdList){
        Query query=new Query.QueryBuilder().where(new Condition("id",userIdList.toArray(),ValueOp.IN)).buildQuery();
        List<UserWxUserDO>  list=userWxUserDao.getDatas(query);
        return list;
    }

    private List<Integer> getUserIdList(List<UserEmployeeDO> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<Integer> result=new ArrayList<>();
        for(UserEmployeeDO DO:list){
            if(StringUtils.isNullOrEmpty(DO.getCname())){
                result.add(DO.getSysuserId());
            }
        }
        return result;
    }

    public <T> T getFieldById(int wxId, String fieldName, Class<T> clazz) {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.select(fieldName);
        query.where("id", wxId);
        HrWxWechatRecord wxWechatRecord = hrWxWechatDao.getRecord(query.buildQuery());
        return wxWechatRecord.get(fieldName, clazz);
    }
}
