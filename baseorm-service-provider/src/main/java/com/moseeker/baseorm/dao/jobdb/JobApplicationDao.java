package com.moseeker.baseorm.dao.jobdb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;

/**
 * 封装申请表基本操作
 *
 * @author jack
 * date : Jan 22, 2017
 * email: wengjianfei@moseeker.com
 */
@Service
public class JobApplicationDao extends BaseDaoImpl<JobApplicationRecord, JobApplication> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = JobApplication.JOB_APPLICATION;
	}

	/**
	 * 查询申请数据
	 * @param query
	 * @return
	 */
	public List<com.moseeker.thrift.gen.application.struct.JobApplication> getApplications(CommonQuery query) {
		List<com.moseeker.thrift.gen.application.struct.JobApplication> applications = new ArrayList<>();
		try {
			List<JobApplicationRecord> records = getResources(query);
			if(records != null && records.size() > 0) {
				applications = BeanUtils.DBToStruct(com.moseeker.thrift.gen.application.struct.JobApplication.class, records);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//do nothing
		}
		return applications;
	}

}
