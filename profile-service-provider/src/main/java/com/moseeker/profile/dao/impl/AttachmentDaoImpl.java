package com.moseeker.profile.dao.impl;

import java.text.ParseException;
import java.util.Date;

import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.DateUtils;
import com.moseeker.db.profiledb.tables.ProfileAttachment;
import com.moseeker.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.profile.dao.AttachmentDao;
import com.moseeker.thrift.gen.profile.struct.Attachment;

@Repository
public class AttachmentDaoImpl extends
		BaseDaoImpl<ProfileAttachmentRecord, ProfileAttachment> implements
		AttachmentDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileAttachment.PROFILE_ATTACHMENT;
	}

	protected Attachment DBToStruct(ProfileAttachmentRecord r) {
		Attachment attachment = null;
		if (r != null) {
			attachment = new Attachment();
			if (r.getCreateTime() != null) {
				attachment.setCreate_time(DateUtils.dateToNormalDate(new Date(r
						.getCreateTime().getTime())));
			}
			if (r.getUpdateTime() != null) {
				attachment.setUpdate_time(DateUtils.dateToNormalDate(new Date(r
						.getUpdateTime().getTime())));
			}
			attachment.setDescription(r.getDescription());
			attachment.setId(r.getId().intValue());
			attachment.setName(r.getName());
			attachment.setPath(r.getPath());
			attachment.setProfile_id(r.getProfileId().intValue());
		}
		return attachment;
	}

	protected ProfileAttachmentRecord structToDB(Attachment attachment)
			throws ParseException {
		ProfileAttachmentRecord record = new ProfileAttachmentRecord();
		if(attachment.isSetId()) {
			record.setId(UInteger.valueOf(attachment.getId()));
		}
		if(attachment.isSetName()) {
			record.setName(attachment.getName());
		}
		if(attachment.isSetPath()) {
			record.setPath(attachment.getPath());
		}
		if(attachment.isSetProfile_id()) {
			record.setProfileId(UInteger.valueOf(attachment.getProfile_id()));
		}
		if(attachment.isSetDescription()) {
			record.setDescription(attachment.getDescription());
		}
		return record;
	}
}
