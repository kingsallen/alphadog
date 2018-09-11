package com.moseeker.useraccounts.constant;

import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;

public enum EmployeeAuthMethod {
    EMAIL_AUTH(0,"使用邮箱认证") {
        @Override
        public String uniqueKey(UserEmployeeStruct employeeStruct) {
            String email;
            if (employeeStruct.getEmail() == null) {
                email = "";
            } else{
                email = employeeStruct.getEmail();
            }
            return employeeStruct.getCompany_id() + "_email_" + email;
        }

        @Override
        public String uniqueKey(UserEmployeeRecord employeeRecord) {
            UserEmployeeStruct employeeStruct = new UserEmployeeStruct();
            employeeStruct.setCompany_id(employeeRecord.getCompanyId());
            employeeStruct.setEmail(employeeRecord.getEmail());
            return uniqueKey(employeeStruct);
        }

        @Override
        public boolean checkDataValid(UserEmployeeStruct struct) {
            return struct!=null && struct.isSetCompany_id() && struct.isSetEmail();
        }
    },
    CUSTOM_AUTH(1,"使用自定义认证") {
        @Override
        public String uniqueKey(UserEmployeeStruct employeeStruct) {
            String customField;
            if (employeeStruct.getCustom_field() == null) {
                customField = "";
            } else{
                customField = employeeStruct.getCustom_field();
            }
            return employeeStruct.getCompany_id() + "_custom_field_" + customField;
        }

        @Override
        public String uniqueKey(UserEmployeeRecord employeeRecord) {
            UserEmployeeStruct employeeStruct = new UserEmployeeStruct();
            employeeStruct.setCompany_id(employeeRecord.getCompanyId());
            employeeStruct.setCustom_field(employeeRecord.getCustomField());
            return uniqueKey(employeeStruct);
        }

        @Override
        public boolean checkDataValid(UserEmployeeStruct struct) {
            return struct!=null && struct.isSetCompany_id() && struct.isSetCustom_field();
        }
    },
    QUESTION_AUTH(2,"使用问答认证") {
        @Override
        public String uniqueKey(UserEmployeeStruct employeeStruct) {
            String cname;
            if (employeeStruct.getCname() == null) {
                cname = "";
            } else{
                cname = employeeStruct.getCname();
            }
            return employeeStruct.getCompany_id() + "_cname_" + cname;
        }

        @Override
        public String uniqueKey(UserEmployeeRecord employeeRecord) {
            UserEmployeeStruct employeeStruct = new UserEmployeeStruct();
            employeeStruct.setCompany_id(employeeRecord.getCompanyId());
            employeeStruct.setCname(employeeRecord.getCname());
            return uniqueKey(employeeStruct);
        }

        @Override
        public boolean checkDataValid(UserEmployeeStruct struct) {
            return struct!=null && struct.isSetCompany_id() && struct.isSetCname();
        }
    };

    EmployeeAuthMethod(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;
    private String name;

    private static final BIZException NO_MATCH_AUTH_METHOD = ExceptionUtils.getBizException(ConstantErrorCodeMessage.NO_MATCH_AUTH_METHOD);

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static EmployeeAuthMethod getAuthMethod(int code) throws BIZException {
        for(EmployeeAuthMethod authMethod:values()){
            if(authMethod.code == code){
                return authMethod;
            }
        }
        throw NO_MATCH_AUTH_METHOD;
    }

    public abstract String uniqueKey(UserEmployeeStruct employeeStruct);
    public abstract String uniqueKey(UserEmployeeRecord employeeRecord);
    public abstract boolean checkDataValid(UserEmployeeStruct struct);
}
