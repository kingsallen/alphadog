package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.pojos.ThirdPartyInfoData;
import org.apache.commons.lang.time.FastDateFormat;
import org.jooq.UpdatableRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class AbstractInfoHandler<T> {

    private Logger logger = LoggerFactory.getLogger(AbstractInfoHandler.class);

    /**
     * 所有传过来的数据初始化成对应的第三方信息
     * @return
     */
    public abstract List<T> buildNewData(ThirdPartyInfoData data);

    /**
     * 判断两个data是否相同，用来判断是否删除或者新增
     * @param data1
     * @param data2
     * @return
     */
    public abstract boolean equals(T data1,T data2);

    /**
     * 返回对应的dao用来删除和新增,可以返回协变类型
     * @return
     */
    protected abstract <R extends UpdatableRecord<R>> JooqCrudImpl<T,R> getDao();

    /**
     * 找出新增的数据和删除的数据，然后做持久化操作
     * @param data
     */
    public void handler(ThirdPartyInfoData data){
        if(data == null){
            return;
        }

        List<T> newDatas = buildNewData(data);

        Query query = new Query.QueryBuilder().where("account_id",data.getAccountId()).buildQuery();
        List<T> dbList = getDao().getDatas(query);

        // 找出新增的数据
        List<T> toBeAddData = new ArrayList<>();
        if(!StringUtils.isEmptyList(newDatas)) {
            for (T newData : newDatas) {
                if(newData==null){
                    continue;
                }
                if (newDataNotInOldData(newData, dbList)) {
                    toBeAddData.add(newData);
                }
            }
        }

        // 找出删除的数据
        List<T> toBeDelData = new ArrayList<>();
        if(!StringUtils.isEmptyList(dbList)) {
            for (T oldData : dbList) {
                if(oldData == null){
                    continue;
                }
                if (oldDataNotInNewData(oldData, newDatas)) {
                    toBeDelData.add(oldData);
                }
            }
        }

        if(!StringUtils.isEmptyList(toBeAddData)) {
            getDao().addAllData(toBeAddData);
            logger.info("third party info handler {} add data :{}",getDao().toString(),toBeAddData.size());
        }

        if(!StringUtils.isEmptyList(toBeDelData)) {
            getDao().deleteDatas(toBeDelData);
            logger.info("third party info handler {} del data :{}",getDao().toString(),toBeDelData.size());
        }
    }

    protected String getCurrentTime(){
        return FastDateFormat.getDateInstance(FastDateFormat.LONG, Locale.CHINA).format(new Date());
    }

    private boolean oldDataNotInNewData(T oldData,List<T> newDatas){
        return dataNotInDatas(oldData,newDatas);
    }

    private boolean newDataNotInOldData(T newData,List<T> oldDatas){
        return dataNotInDatas(newData,oldDatas);
    }

    private boolean dataNotInDatas(T data, List<T> datas){
        for(T t:datas){
            if(equals(t,data)){
                return false;
            }
        }
        return true;
    }

}
