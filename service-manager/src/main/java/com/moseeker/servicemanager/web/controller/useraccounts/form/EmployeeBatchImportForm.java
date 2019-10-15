package com.moseeker.servicemanager.web.controller.useraccounts.form;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class EmployeeBatchImportForm {

    private List<EmployeeDO> employees;
    private Integer companyId;

    public List<UserEmployeeDO> getEmployees() {
        if( employees == null || employees.isEmpty()) return Collections.emptyList() ;

        List<UserEmployeeDO> list = new ArrayList<>();
        employees.forEach(e->{
            UserEmployeeDO ue = new UserEmployeeDO();
            ue.setCname(e.cname);
            ue.setCustomField(e.customField);
            ue.setEmail(e.getEmail());
            if(e.getCustomFieldValues() != null){
                ue.setCustomFieldValues(JSONObject.toJSONString(e.getCustomFieldValues()));
            }
            list.add(ue);
        });
        return list ;
    }

    public void setEmployees(List<EmployeeDO> employees) {
        this.employees = employees;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public static class EmployeeDO {
        String customField;
        String cname;
        String email;
        Map<String,String> customFieldValues;

        public String getCustomField() {
            return customField;
        }

        public void setCustomField(String customField) {
            this.customField = customField;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Map<String, String> getCustomFieldValues() {
            return customFieldValues;
        }

        public void setCustomFieldValues(Map<String, String> customFieldValues) {
            this.customFieldValues = customFieldValues;
        }
    }
}
