package com.moseeker.entity;

import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeVO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeVOPageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private UserWxUserDao userWxUserDao;

    @Autowired
    private HrCompanyDao hrCompanyDao;

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
    /*
     获取雇员的名称,id,email
     */
    @CounterIface
    public UserEmployeeVOPageVO getFordEmployeeData(int companyId, String email, int page, int pageSize){
       UserEmployeeVOPageVO VO=new UserEmployeeVOPageVO();
       int total=userEmployeeDao.getUserEmployeeLikeCount(companyId,email);
       VO.setPageNumber(page);
       VO.setPageSize(pageSize);
       VO.setTotalRow(total);
       List<Map<String,Object>> dataList=userEmployeeDao.getUserEmployeeLike(companyId,email,page,pageSize);
       if(StringUtils.isEmptyList(dataList)){
           return VO;
       }
       List<UserWxUserRecord> userWXList=this.getEmployeeName(dataList);
       this.handlerEmployeeData(userWXList,dataList);
       List<UserEmployeeVO> list=ConvertToUserEmployeeVO(dataList);
       VO.setData(list);
       return VO;

                                }
    /*
     根据雇员id获取雇员的名称，id,email
     */
    public List<UserEmployeeDO> getForWordEmployeeInfo(List<Integer> idList){
        if(StringUtils.isEmptyList(idList)){
            return null;
                            }
        List<Map<String,Object>> dataList=userEmployeeDao.getUserEmployeeInfoById(idList);
        if(StringUtils.isEmptyList(dataList)){
            return null;
                        }
        List<UserWxUserRecord> userWXList=this.getEmployeeName(dataList);
        this.handlerEmployeeData(userWXList,dataList);
        List<UserEmployeeDO> result=this.ConvertToUserEmployeeDO(dataList);
        return result;
                    }
    /*
     将map转换成UserEmployeeVO
     */
    private List<UserEmployeeDO> ConvertToUserEmployeeDO( List<Map<String,Object>> dataList){
        List<UserEmployeeDO> list=new ArrayList<>();
        for(Map<String,Object> map:dataList){
            UserEmployeeDO DO=new UserEmployeeDO();
            int id=(int)map.get("id");
            String cname=(String)map.get("cname");
            String email=(String)map.get("email");
            DO.setEmail(email);
            DO.setId(id);
            DO.setCname(cname);
            list.add(DO);

        }
        return list;
    }
    /*
     将map转换成UserEmployeeVO
     */
    private List<UserEmployeeVO> ConvertToUserEmployeeVO( List<Map<String,Object>> dataList){
        List<UserEmployeeVO> list=new ArrayList<>();
        for(Map<String,Object> map:dataList){
            UserEmployeeVO VO=new UserEmployeeVO();
            int id=(int)map.get("id");
            String cname=(String)map.get("cname");
            String email=(String)map.get("email");
            VO.setEmail(email);
            VO.setId(id);
            VO.setNickName(cname);
            VO.setUsername(cname);
            list.add(VO);

        }
        return list;
    }
    /*
     将微信的昵称填入到雇员信息里面
     */
    private void handlerEmployeeData(List<UserWxUserRecord> userWXList,List<Map<String,Object>> dataList){
        if(!StringUtils.isEmptyList(userWXList)){
            for(Map<String,Object> map:dataList){
                int userId=(int)map.get("sysuser_id");
                for(UserWxUserRecord record:userWXList){
                    if(record.getSysuserId()==userId){
                        map.put("cname",record.getNickname());
                        break;
                    }
                }
            }
        }
    }
    /*
     获取没有名字的雇员的微信信息
     */
    private List<UserWxUserRecord> getEmployeeName(List<Map<String,Object>> dataList){
        List<Integer> userIdList=this.getNoNameUserIdList(dataList);
        if(StringUtils.isEmptyList(userIdList)){
            return null;
        }
        List<UserWxUserRecord> list=this.getUserWxUserData(userIdList);
        return list;

            }
    /*
     获取没有名字的userId
     */
    private List<Integer> getNoNameUserIdList(List<Map<String,Object>> dataList){
        if(StringUtils.isEmptyList(dataList)){
            return null;
        }
        List<Integer> userIdList=new ArrayList<>();
        for(Map<String,Object> map:dataList){
            String cname=(String)map.get("cname");
            if(StringUtils.isNullOrEmpty(cname)){
                int userId=(int)map.get("sysuser_id");
                userIdList.add(userId);
            }

        }
        return userIdList;
    }
    /*
     获取user_wx_user
     */
    private List<UserWxUserRecord> getUserWxUserData(List<Integer> userIdList){
        Query query=new Query.QueryBuilder().where(new Condition("sysuser_id",userIdList.toArray(),ValueOp.IN)).buildQuery();
        List<UserWxUserRecord> list=userWxUserDao.getRecords(query);
        return list;
    }
}
