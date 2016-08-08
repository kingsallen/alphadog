package com.moseeker.dict.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.dict.pojo.DictConstantPojo;

import java.net.Inet4Address;
import java.util.List;
import java.util.Map;

/**
 * 获取常量字典
 * <p>
 *
 * Created by zzh on 16/5/25.
 */
public interface DictConstantDao extends BaseDao<DictConstantRecord> {

    public Map<Integer, List<DictConstantPojo>> getDictConstantJsonByParentCode(List<Integer> parentCodeList) throws Exception;

    public DictConstantPojo getDictConstantJson(Integer parentCode, Integer code) throws Exception;

}
