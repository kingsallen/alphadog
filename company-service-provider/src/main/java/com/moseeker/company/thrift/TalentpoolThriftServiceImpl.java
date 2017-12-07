package com.moseeker.company.thrift;

import com.moseeker.common.exception.Category;
import com.moseeker.company.exception.ExceptionFactory;
import com.moseeker.company.service.impl.TalentPoolService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.TalentpoolServices;
import org.apache.thrift.TException;
import org.elasticsearch.common.recycler.Recycler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zztaiwll on 17/12/7.
 */
@Service
public class TalentpoolThriftServiceImpl implements TalentpoolServices.Iface {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TalentPoolService talentPoolService;
    @Override
    public Response getTalentAllComment(int hr_id, int company_id, int user_id) throws BIZException, TException {
        try{
            return talentPoolService.getAllTalentComment(hr_id,company_id,user_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getHrTag(int hr_id, int company_id, int page_num, int page_size) throws BIZException, TException {
        try{
            return talentPoolService.getAllHrTag(hr_id,company_id,page_num,page_size);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response batchAddTalent(int hr_id, List<Integer> user_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.batchAddTalent(hr_id,ConvertListToSet(user_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }    }

    @Override
    public Response batchCancelTalent(int hr_id, List<Integer> user_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.batchCancelTalent(hr_id,ConvertListToSet(user_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }    }

    @Override
    public Response hrAddTag(int hr_id, int company_id, String name) throws BIZException, TException {
        try{
            return talentPoolService.addHrTag(hr_id,company_id,name);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }    }

    @Override
    public Response hrDelTag(int hr_id, int company_id, int tag_id) throws BIZException, TException {
        try{
            return talentPoolService.deleteHrTag(hr_id,company_id,tag_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response hrUpdateTag(int hr_id, int company_id, int tag_id, String name) throws BIZException, TException {
        try{
            return talentPoolService.updateHrTag(hr_id,company_id,tag_id,name);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response batchCancleTalentTag(int hr_id, List<Integer> user_ids, List<Integer> tag_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.batchCancelTalentTag(hr_id,ConvertListToSet(user_ids),ConvertListToSet(tag_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response batchAddTalentTag(int hr_id, List<Integer> user_ids, List<Integer> tag_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.addBatchTalentTag(hr_id,ConvertListToSet(user_ids),ConvertListToSet(tag_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response hrAddComment(int hr_id, int company_id, int user_id, String content) throws BIZException, TException {
        try{
            return talentPoolService.addTalentComment(hr_id,company_id,user_id,content);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }    }

    @Override
    public Response hrDelComment(int hr_id, int company_id, int user_id, int comment_id) throws BIZException, TException {
        try{
            return talentPoolService.delTalentComment(hr_id,company_id,user_id,comment_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response batchAddPublicTalent(int hr_id, int company_id, List<Integer> user_ids) throws BIZException, TException {
        try{
            return talentPoolService.AddbatchPublicTalent(hr_id,company_id,ConvertListToSet(user_ids));
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }    }

    @Override
    public Response batchCancelPublicTalent(int hr_id, int company_id, List<Integer> user_ids) throws BIZException, TException {
        try{
            return talentPoolService.cancelBatchPublicTalent(hr_id,company_id,ConvertListToSet(user_ids));
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }    }

    @Override
    public Response getCompanyPulicTalent(int hr_id, int company_id, int page_num, int page_size) throws BIZException, TException {
        try{
            return talentPoolService.getCompanyPublic(hr_id,company_id,page_num,page_size);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response batchNewAddTalentTag(int hr_id, List<Integer> user_ids, List<Integer> tag_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.addNewBatchTalentTag(hr_id,ConvertListToSet(user_ids),ConvertListToSet(tag_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    private Set<Integer> ConvertListToSet(List<Integer> list){
        Set<Integer> param=new HashSet<>();
        for(Integer id :list){
            param.add(id);
        }
        return param;
    }
}
