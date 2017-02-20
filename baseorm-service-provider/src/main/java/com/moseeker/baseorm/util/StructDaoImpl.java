package com.moseeker.baseorm.util;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateRemarkDO;
import org.apache.thrift.TBase;
import org.jooq.impl.TableImpl;
import org.jooq.impl.UpdatableRecordImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jack on 15/02/2017.
 */
public class StructDaoImpl<S extends  TBase, R extends UpdatableRecordImpl<R>, T extends TableImpl<R>> extends BaseDaoImpl<R, T> {
    @Override
    protected void initJOOQEntity() {

    }

    private Class<S> sClass;
    private Class<R> rClass;

    public StructDaoImpl() {
        Type t = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) t).getActualTypeArguments();
        sClass = (Class<S>) params[0];
        rClass = (Class<R>) params[1];
    }

    public S updateResource(S s) throws CURDException {
        R record = BeanUtils.structToDB(s, rClass);
        try {
            this.putResource(record);
            BeanUtils.DBToStruct(s, record, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildPutException();
        }
        return s;
    }

    public S saveResource(S s) throws CURDException {
        R record = BeanUtils.structToDB(s, rClass);
        try {
            this.postResource(record);
            BeanUtils.DBToStruct(s, record, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildPutException();
        }
        return s;
    }

    public S findResource(CommonQuery query) throws CURDException {
        try {
            R record = this.getResource(query);
            if(record != null) {
                return BeanUtils.DBToStruct(sClass, record);
            } else {
                throw CURDExceptionUtils.buildGetNothingException();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildGetNothingException();
        }
    }

    public List<S> listResources(CommonQuery query) throws CURDException {
        List<S> resources = null;
        try {
            List<R> records = this.getResources(query);
            if(records != null && records.size() > 0) {
                resources = BeanUtils.DBToStruct(sClass, records);
                return resources;
            } else {
                throw CURDExceptionUtils.buildGetNothingException();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildGetNothingException();
        }
    }

    public void deleteResource(S s) throws CURDException {
        R r = BeanUtils.structToDB(s, rClass);
        try {
            this.delResource(r);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildDelException();
        }
    }

    public List<S> updateResources(List<S> resources) throws CURDException {
        List<R> records = BeanUtils.structToDB(resources, rClass);
        try {
            this.putResources(records);
            resources = BeanUtils.DBToStruct(sClass, records);
        } catch (Exception e) {
        logger.error(e.getMessage(), e);
        throw CURDExceptionUtils.buildPutException();
    }
        return resources;
    }
}
