package com.moseeker.useraccounts.service.impl;

import com.moseeker.baseorm.dao.userdb.UserSettingsDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.Usersetting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@CounterIface
public class UsersettingsService {

	@Autowired
	protected UserSettingsDao dao;

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Response postResources(List<Usersetting> structs) throws Exception {

        List<UserSettingsRecord> userSettingsRecords = dao.addAllRecord(BeanUtils.structToDB(structs, UserSettingsRecord.class));
        if(userSettingsRecords != null && !userSettingsRecords.isEmpty()) {
			List<Integer> idArray = userSettingsRecords.stream().map(m -> m.getUserId()).collect(Collectors.toList());
			dao.updateProfileUpdateTimeByUserId(idArray);
            return ResponseUtils.success(userSettingsRecords);
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
    }

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Response putResources(List<Usersetting> structs) throws Exception {
        int[] rownums = dao.updateRecords(BeanUtils.structToDB(structs, UserSettingsRecord.class));
        if(rownums != null && rownums.length > 0) {
			List<Integer> idArray = structs.stream().map(m -> m.getId()).collect(Collectors.toList());
			dao.updateProfileUpdateTime(idArray);
			return ResponseUtils.success(Arrays.stream(rownums).sum());
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Response delResources(List<Usersetting> structs) throws Exception {
        int[] rownums = dao.deleteRecords(BeanUtils.structToDB(structs, UserSettingsRecord.class));
        if(rownums != null && rownums.length > 0) {
            List<Integer> idArray = structs.stream().map(m -> m.getId()).collect(Collectors.toList());
            dao.updateProfileUpdateTime(idArray);
            return ResponseUtils.success(Arrays.stream(rownums).sum());
		}
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Response postResource(Usersetting struct) throws Exception {
        UserSettingsRecord userSettingsRecord = dao.addRecord(BeanUtils.structToDB(struct, UserSettingsRecord.class));
        if(userSettingsRecord != null && userSettingsRecord.getId() > 0) {
			List<Integer> idArray = new ArrayList<Integer>();
			idArray.add(userSettingsRecord.getId());
			dao.updateProfileUpdateTime(idArray);
            return ResponseUtils.success(userSettingsRecord.getId());
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Response putResource(Usersetting struct) throws Exception {
        int row = dao.updateRecord(BeanUtils.structToDB(struct, UserSettingsRecord.class));
        if(row > 0) {
			List<Integer> idArray = new ArrayList<Integer>();
			idArray.add(struct.getId());
			dao.updateProfileUpdateTime(idArray);
			return  ResponseUtils.success(row);
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Response delResource(Usersetting struct) throws Exception {
        int row = dao.deleteRecord(BeanUtils.structToDB(struct, UserSettingsRecord.class));
        if(row > 0) {
			List<Integer> idArray = new ArrayList<Integer>();
			idArray.add(struct.getId());
            return  ResponseUtils.success(row);
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
	}

    public Response getResource(CommonQuery query) {
        return ResponseUtils.success(dao.getData(QueryConvert.commonQueryConvertToQuery(query), Usersetting.class));
    }

}
