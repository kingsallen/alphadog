package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileAttachmentDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Attachment;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@CounterIface
public class ProfileAttachmentService extends BaseProfileService<Attachment, ProfileAttachmentRecord> {
	
	Logger logger = LoggerFactory.getLogger(ProfileAttachmentService.class);

	@Autowired
	ProfileAttachmentDao profileAttachmentDao;

	@Autowired
	ProfileProfileDao profileProfileDao;

	public Response getResource(CommonQuery query) throws TException {
		return super.getResource(profileAttachmentDao, query, Attachment.class);
	}

	public Response getResources(CommonQuery query) throws TException {
		return super.getResources(profileAttachmentDao,query,Attachment.class);
	}

	public Response postResources(List<Attachment> structs) throws TException {
		Response response = super.postResources(profileAttachmentDao,structs);
		if(response != null && response.getStatus() == 0) {
			Set<Integer> profileIds = new HashSet<>();
			for(Attachment attachement : structs) {
				if(attachement.getProfile_id() > 0) {
					profileIds.add(attachement.getProfile_id());
				}
			}
            profileProfileDao.updateUpdateTime(profileIds);
		}
		return response;
	}

	public Response putResources(List<Attachment> structs) throws TException {
		Response response = super.putResources(profileAttachmentDao,structs);
		updateUpdateTime(structs, response);
		return response;
	}

	public Response delResources(List<Attachment> structs) throws TException {
		Response response = super.delResources(profileAttachmentDao,structs);
		updateUpdateTime(structs, response);
		return response;
	}
	
	public Response postResource(Attachment struct) throws TException {
		Response response = super.postResource(profileAttachmentDao,struct);
		if(response != null && response.getStatus() == 0) {
			Set<Integer> profileIds = new HashSet<>();
			profileIds.add(struct.getProfile_id());
			profileProfileDao.updateUpdateTime(profileIds);
		}
		return response;
	}

	public Response putResource(Attachment struct) throws TException {
		Response response =  super.putResource(profileAttachmentDao,struct);
		updateUpdateTime(struct, response);
		return response;
	}

	public Response delResource(Attachment struct) throws TException {
		Response response = super.delResource(profileAttachmentDao,struct);
		updateUpdateTime(struct, response);
		return response;
	}

	private void updateUpdateTime(List<Attachment> attachments, Response response) {
		if(response.getStatus() == 0) {
			HashSet<Integer> attachmentIds = new HashSet<>();
			attachments.forEach(attachment -> {
				attachmentIds.add(attachment.getId());
			});
			profileAttachmentDao.updateProfileUpdateTime(attachmentIds);
		}
	}
	
	private void updateUpdateTime(Attachment attachment, Response response) {
		if(response.getStatus() == 0) {
			List<Attachment> attachments = new ArrayList<>();
			attachments.add(attachment);
			updateUpdateTime(attachments, response);
		}
	}

	public Response getPagination(CommonQuery query) throws TException {
		return super.getPagination(profileAttachmentDao, query);
	}
}
