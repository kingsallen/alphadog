package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.profile.dao.AttachmentDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.AttachmentServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Attachment;

@Service
public class ProfileAttachmentServicesImpl extends JOOQBaseServiceImpl<Attachment, ProfileAttachmentRecord> implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileAttachmentServicesImpl.class);
	
	@Override
	public Response postResources(List<Attachment> structs) throws TException {
		return super.postResources(structs);
	}

	@Override
	public Response putResources(List<Attachment> structs) throws TException {
		return super.putResources(structs);
	}

	@Override
	public Response delResources(List<Attachment> structs) throws TException {
		return super.delResources(structs);
	}

	@Override
	public Response postResource(Attachment struct) throws TException {
		return super.postResource(struct);
	}

	@Override
	public Response putResource(Attachment struct) throws TException {
		return super.putResource(struct);
	}

	@Override
	public Response delResource(Attachment struct) throws TException {
		return super.delResource(struct);
	}

	@Autowired
	private AttachmentDao dao;
	
	public AttachmentDao getDao() {
		return dao;
	}

	public void setDao(AttachmentDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Attachment DBToStruct(ProfileAttachmentRecord r) {
		return (Attachment)BeanUtils.DBToStruct(Attachment.class, r);
	}

	@Override
	protected ProfileAttachmentRecord structToDB(Attachment attachment) throws ParseException {
		return (ProfileAttachmentRecord)BeanUtils.structToDB(attachment, ProfileAttachmentRecord.class);
	}
}
