package com.moseeker.baseorm.service.Impl;

import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.hrdb.HrTeamDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.baseorm.service.HrDBService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.HistoryOperate;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrOperationRecordDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamStruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HrDBServiceImpl implements HrDBService {

    @Autowired
    private HrOperationRecordDao hrOperationRecordDao;

    @Autowired
    private HrTeamDao hrTeamDao;
    
    Logger logger = LoggerFactory.getLogger(HrDBServiceImpl.class);

    @Autowired
    private HrWxWechatDao hrWxWechatDao;

    @Override
    public Response postHrOperation(HrOperationRecordDO record) {
        // TODO Auto-generated method stub
        try {
            int result = hrOperationRecordDao.addData(record);
            return ResponseUtils.success(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response postHrOperations(List<HrOperationRecordDO> record) {
        // TODO Auto-generated method stub
        try {
            int result = hrOperationRecordDao.addAllData(record).length;
            return ResponseUtils.success(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }

    }

    public Response getHrHistoryOpertation(List<ProcessValidationStruct> records) {
        if (records != null && records.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("(");
            for (ProcessValidationStruct record : records) {
                sb.append("" + record.getId() + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            String param = sb.toString();
            try {
                List<HistoryOperate> list = hrOperationRecordDao.getHistoryOperate(param);
                return ResponseUtils.success(list);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            }
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);

    }

    @Override
    public Response getHrTeam(Query query) {
        // TODO Auto-generated method stub
        try {
            HrTeamRecord data = hrTeamDao.getRecord(query);
            if (data != null) {
                HrTeamStruct result = BeanUtils.DBToStruct(HrTeamStruct.class, data);
                return ResponseUtils.success(result);
            }
            return ResponseUtils.success(null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getHrWxWechat(Query query) {
        try {
            HrWxWechatRecord rs = hrWxWechatDao.getRecord(query);
            if (rs != null) {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("id", rs.getId());
                result.put("company_id", rs.getCompanyId());
                result.put("type", rs.getType());
                result.put("signature", rs.getSignature());
                result.put("name", rs.getName());
                result.put("alias", rs.getAlias());
                result.put("username", rs.getUsername());
                result.put("password", rs.getPassword());
                result.put("token", rs.getToken());
                result.put("appid", rs.getAppid());
                result.put("secret", rs.getSecret());
                result.put("welcome", rs.getWelcome());
                result.put("qrcode", rs.getQrcode());
                result.put("passive_seeker", rs.getPassiveSeeker());
                result.put("third_oauth", rs.getThirdOauth());
                result.put("hr_register", rs.getHrRegister());
                result.put("access_token_create_time", rs.getAccessTokenCreateTime());
                result.put("access_token_expired", rs.getAccessTokenExpired());
                result.put("access_token", rs.getAccessToken());
                result.put("jsapi_ticket", rs.getJsapiTicket());
                result.put("authorized", rs.getAuthorized());
                result.put("unauthorized_time", rs.getUnauthorizedTime());
                result.put("authorizer_refresh_token", rs.getAuthorizerRefreshToken());
                result.put("create_time", rs.getCreateTime());
                result.put("update_time", rs.getUpdateTime());
                result.put("hr_chat", rs.getHrChat());
                result.put("show_qx_qrcode", rs.getShowQxQrcode());
                return ResponseUtils.success(result);
            }
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

}
