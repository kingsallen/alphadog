package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.crud.Crud;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/*
* 这个类JOOQBaseDaoImpl改造过来的
* */
public class BaseProfileService<S, R> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Response getResources(Crud<?, R> dao, CommonQuery query,Class clazz) throws TException {
        try {
            List<S> structs = dao.getDatas(QueryConvert.commonQueryConvertToQuery(query),clazz);

            if (!structs.isEmpty()) {
                return ResponseUtils.success(structs);
            }

        } catch (Exception e) {
            logger.error("getResources error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    public Response postResources(Crud<?, R> dao, List<S> structs) throws TException {
        try {
            List<R> rs = new ArrayList<>();
            if(structs!=null){
                structs.forEach(struct->{
                    rs.add(dao.dataToRecord(struct));
                });
            }
            List<R> data = dao.addAllRecord(rs);
            if (data.size() > 0) {
                return ResponseUtils.success(String.valueOf(data.size()));
            }

        } catch (Exception e) {
            logger.error("postResources error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

        } finally {
            //do nothing
        }

        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
    }

    public Response putResources(Crud<?, R> dao, List<S> structs) throws TException {
        try {
            List<R> rs = new ArrayList<>();
            if(structs!=null){
                structs.forEach(struct->{
                    rs.add(dao.dataToRecord(struct));
                });
            }
            int[] updateStatus = dao.updateRecords(rs);
            if (updateStatus.length > 0 && updateStatus[0] > 0) {
                return ResponseUtils.success(String.valueOf(updateStatus[0]));
            }
        } catch (Exception e) {
            logger.error("putResources error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    public Response delResources(Crud<?, R> dao, List<S> structs) throws TException {
        try {
            List<R> rs = new ArrayList<>();
            if(structs!=null){
                structs.forEach(struct->{
                    rs.add(dao.dataToRecord(struct));
                });
            }
            int[] deleteStatus = dao.deleteRecords(rs);
            if (deleteStatus.length > 0 && deleteStatus[0] > 0) {
                return ResponseUtils.success(String.valueOf(deleteStatus[0]));
            }
        } catch (Exception e) {
            logger.error("delResources error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }

    public Response getResource(Crud<?, R> dao, CommonQuery query,Class<S> sClass) throws TException {
        try {
            S s = dao.getData(QueryConvert.commonQueryConvertToQuery(query),sClass);
            if (s != null) {
                return ResponseUtils.success(s);
            }

        } catch (Exception e) {
            logger.error("getResource error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    public Response postResource(Crud<?, R> dao, S struct) throws TException {
        try {
            R r = dao.addRecord(dao.dataToRecord(struct));

            if (r != null) {
                return ResponseUtils.success(String.valueOf(1));
            }

        } catch (Exception e) {
            logger.error("postResource error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            //do nothing
        }

        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
    }

    public Response putResource(Crud<?, R> dao, S struct) throws TException {
        try {
            int i = dao.updateRecord(dao.dataToRecord(struct));
            if (i > 0) {
                return ResponseUtils.success(String.valueOf(i));
            }
        } catch (Exception e) {
            logger.error("putResource error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    public Response delResource(Crud<?, R> dao, S struct) throws TException {
        try {
            int i = dao.deleteRecord(dao.dataToRecord(struct));
            if (i > 0) {
                return ResponseUtils.success(String.valueOf(i));
            }
        } catch (Exception e) {
            logger.error("delResource error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }

    protected Response getTotalRow(Crud<?, R> dao, CommonQuery query) throws TException {
        int totalRow = 0;
        try {
            totalRow = dao.getCount(QueryConvert.commonQueryConvertToQuery(query));
            return ResponseUtils.success(String.valueOf(totalRow));
        } catch (Exception e) {
            logger.error("getTotalRow error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

        } finally {
            //do nothing
        }
    }

    public Response getPagination(Crud<?, R> dao, CommonQuery query) throws TException {
        try {
            Pagination<R> pagination = new Pagination<>();
            int totalRow = dao.getCount(QueryConvert.commonQueryConvertToQuery(query));
            int pageNo = 1;
            int pageSize = 10;
            if(query.getPage() > 1) {
                pageNo = query.getPage();
            }
            if(query.getPer_page() > 0) {
                pageSize = query.getPer_page();
            }
            int totalPage = (int) (totalRow / pageSize);
            if (totalRow % pageSize != 0) {
                totalPage++;
            }
            List<R> records = dao.getRecords(QueryConvert.commonQueryConvertToQuery(query));

            pagination.setPageNo(pageNo);
            pagination.setPageSize(pageSize);
            pagination.setTotalPage(totalPage);
            pagination.setTotalRow(totalRow);
            pagination.setResults(records);
            return ResponseUtils.success(String.valueOf(pagination));


        } catch (Exception e) {
            logger.error("getPagination error", e);
            return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

        } finally {
            //do nothing
        }
    }
}