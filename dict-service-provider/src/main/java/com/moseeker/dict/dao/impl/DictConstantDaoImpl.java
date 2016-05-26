package com.moseeker.dict.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.dictdb.tables.DictConstant;
import com.moseeker.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.dict.dao.DictConstantDao;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
public class DictConstantDaoImpl extends BaseDaoImpl<DictConstantRecord, DictConstant> implements DictConstantDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = DictConstant.DICT_CONSTANT;
    }

    /**
     * 获取常量字典
     * @param parentCodeList 类型code集合
     * @return
     * @throws Exception
     */
    public String getDictConstantJsonByParentCode(List<Integer> parentCodeList) throws Exception{
        String jsonDictConstant = "";
        Connection conn = null;
        try {
            initJOOQEntity();
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            Condition condition = null;

            // parentCodeList 为空时, 返回所有字典常量
            if(parentCodeList == null){
                condition = DictConstant.DICT_CONSTANT.PARENT_CODE.equal(0);
            }else if(parentCodeList.size() == 1){
                condition = DictConstant.DICT_CONSTANT.PARENT_CODE.equal(parentCodeList.get(0));
            }else{
                for (Integer parentCode : parentCodeList) {

                }
                condition = DictConstant.DICT_CONSTANT.PARENT_CODE.in(parentCodeList);
            }
            jsonDictConstant = create.select().from(tableLike).where(condition).fetch().formatJSON();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return jsonDictConstant;
    }

}