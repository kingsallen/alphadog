package com.moseeker.dict.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictConstantRecord;

import java.util.List;

/**
 * 获取常量字典
 * <p>
 *
 * Created by zzh on 16/5/25.
 */
public interface DictConstantDao extends BaseDao<DictConstantRecord> {

    public String getDictConstantJsonByParentCode(List<Integer> parentCodeList) throws Exception;

}
