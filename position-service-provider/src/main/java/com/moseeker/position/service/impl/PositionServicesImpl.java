package com.moseeker.position.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.position.dao.DictConstantDao;
import com.moseeker.position.dao.JobPositionDao;
import com.moseeker.position.dao.PositionDao;
import com.moseeker.position.dao.UserDao;
import com.moseeker.position.pojo.DictConstantPojo;
import com.moseeker.position.pojo.JobPositionPojo;
import com.moseeker.position.pojo.RecommendedPositonPojo;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices.Iface;
import com.moseeker.thrift.gen.position.struct.Position;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServicesImpl extends JOOQBaseServiceImpl<Position, JobPositionRecord> implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PositionDao dao;
    
    @Autowired
	private UserDao userDao;
    
    @Autowired
	private JobPositionDao jobPositionDao;

	@Autowired
	private DictConstantDao dictConstantDao;

    @Override
    protected void initDao() {
        super.dao = this.dao;
    }

    @Override
    protected Position DBToStruct(JobPositionRecord r) {
        return (Position) BeanUtils.DBToStruct(Position.class, r);
    }

    @Override
    protected JobPositionRecord structToDB(Position p) {
        return (JobPositionRecord) BeanUtils.structToDB(p, JobPositionRecord.class);
    }

	/**
	 * 获取推荐职位
	 * <p></p>
	 *
	 * @param pid
	 * @return
     */
	@Override
    public Response getRecommendedPositions(int pid) {

        List<RecommendedPositonPojo> recommendPositons = this.dao.getRecommendedPositions(pid);

        return ResponseUtils.successWithoutStringify(JSON.toJSONString(recommendPositons, new ValueFilter() {
            @Override
            public Object process(Object object, String name, Object value) {
                if (value == null) {
                    return "";
                }
                return value;
            }
        }));

    }

	@Override
	public Response verifyCustomize(int positionId) throws TException {
		try{
			JobPositionRecord positionRecord = jobPositionDao.getPositionById(positionId);

			if(positionRecord == null) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_POSITION_NOTEXIST);
			}
			if(positionRecord.getAppCvConfigId() != null && positionRecord.getAppCvConfigId().intValue() > 0) {
				return ResponseUtils.success(true);
			} else {
				return ResponseUtils.success(false);
			}
		}catch (Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

	/**
	 * 根据职位Id获取当前职位信息
	 *
	 * @param positionId
	 * @return
	 * @throws TException
     */
	@Override
	public Response getPositionById(int positionId) throws TException {

		try{
			// 必填项校验
			if(positionId == 0){
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "position_id"));
			}

			// NullPoint check
			JobPositionRecord jobPositionRecord = jobPositionDao.getPositionById(positionId);
			if (jobPositionRecord == null){
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}

			JobPositionPojo jobPositionPojo = jobPositionDao.getPosition(positionId);

			/** 子公司Id设置 **/
			if(jobPositionPojo.publisher != 0){
				HrCompanyAccountRecord hrCompanyAccountRecord = jobPositionDao.getHrCompanyAccountByPublisher(jobPositionPojo.publisher);
				// 子公司ID>0
				if (hrCompanyAccountRecord != null && hrCompanyAccountRecord.getCompanyId() > 0){
					jobPositionPojo.publisher_company_id = hrCompanyAccountRecord.getCompanyId();
				}
			}

			/** 常量转换 **/
			// 性别
			if(jobPositionPojo.gender < 2){
				jobPositionPojo.gender_name = getDictConstantJson(2102, jobPositionPojo.gender);
			}

			// 学历
			if(jobPositionPojo.degree > 0){
				jobPositionPojo.degree_name = getDictConstantJson(2101, jobPositionPojo.degree);
			}

			// 工作性质
			jobPositionPojo.employment_type_name = getDictConstantJson(2103, jobPositionPojo.employment_type);

			// 招聘类型
			jobPositionPojo.candidate_source_name = getDictConstantJson(2104, jobPositionPojo.candidate_source);

			return ResponseUtils.success(jobPositionPojo);
		}catch (Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

	/**
	 * 获取常量字典一条记录
	 *
	 * @param parentCode
	 * @param code
	 * @return
	 * @throws Exception
     */
	private String getDictConstantJson(Integer parentCode, Integer code) throws Exception{
		DictConstantPojo dictConstantPojo = dictConstantDao.getDictConstantJson(parentCode, code);
		return dictConstantPojo != null ? dictConstantPojo.getName() : "";
	}
}