package com.moseeker.profile.dao;

import java.util.HashSet;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileAttachmentRecord;



public interface AttachmentDao extends BaseDao<ProfileAttachmentRecord> {

	int updateProfileUpdateTime(HashSet<Integer> attachmentIds);

}
