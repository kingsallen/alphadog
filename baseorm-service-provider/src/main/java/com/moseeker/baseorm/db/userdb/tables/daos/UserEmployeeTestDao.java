/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.daos;


import com.moseeker.baseorm.db.userdb.tables.UserEmployeeTest;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeTestRecord;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserEmployeeTestDao extends DAOImpl<UserEmployeeTestRecord, com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest, Integer> {

    /**
     * Create a new UserEmployeeTestDao without any configuration
     */
    public UserEmployeeTestDao() {
        super(UserEmployeeTest.USER_EMPLOYEE_TEST, com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest.class);
    }

    /**
     * Create a new UserEmployeeTestDao with an attached configuration
     */
    public UserEmployeeTestDao(Configuration configuration) {
        super(UserEmployeeTest.USER_EMPLOYEE_TEST, com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchById(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest fetchOneById(Integer value) {
        return fetchOne(UserEmployeeTest.USER_EMPLOYEE_TEST.ID, value);
    }

    /**
     * Fetch records that have <code>employeeid IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByEmployeeid(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.EMPLOYEEID, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByCompanyId(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>role_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByRoleId(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.ROLE_ID, values);
    }

    /**
     * Fetch records that have <code>wxuser_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByWxuserId(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.WXUSER_ID, values);
    }

    /**
     * Fetch records that have <code>sex IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchBySex(Byte... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.SEX, values);
    }

    /**
     * Fetch records that have <code>ename IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByEname(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.ENAME, values);
    }

    /**
     * Fetch records that have <code>efname IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByEfname(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.EFNAME, values);
    }

    /**
     * Fetch records that have <code>cname IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByCname(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.CNAME, values);
    }

    /**
     * Fetch records that have <code>cfname IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByCfname(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.CFNAME, values);
    }

    /**
     * Fetch records that have <code>password IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByPassword(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.PASSWORD, values);
    }

    /**
     * Fetch records that have <code>is_admin IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByIsAdmin(Byte... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.IS_ADMIN, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByStatus(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.STATUS, values);
    }

    /**
     * Fetch records that have <code>companybody IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByCompanybody(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.COMPANYBODY, values);
    }

    /**
     * Fetch records that have <code>departmentname IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByDepartmentname(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.DEPARTMENTNAME, values);
    }

    /**
     * Fetch records that have <code>groupname IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByGroupname(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.GROUPNAME, values);
    }

    /**
     * Fetch records that have <code>position IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByPosition(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.POSITION, values);
    }

    /**
     * Fetch records that have <code>employdate IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByEmploydate(Date... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.EMPLOYDATE, values);
    }

    /**
     * Fetch records that have <code>managername IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByManagername(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.MANAGERNAME, values);
    }

    /**
     * Fetch records that have <code>city IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByCity(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.CITY, values);
    }

    /**
     * Fetch records that have <code>birthday IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByBirthday(Date... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.BIRTHDAY, values);
    }

    /**
     * Fetch records that have <code>retiredate IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByRetiredate(Date... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.RETIREDATE, values);
    }

    /**
     * Fetch records that have <code>education IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByEducation(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.EDUCATION, values);
    }

    /**
     * Fetch records that have <code>address IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByAddress(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.ADDRESS, values);
    }

    /**
     * Fetch records that have <code>idcard IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByIdcard(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.IDCARD, values);
    }

    /**
     * Fetch records that have <code>mobile IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByMobile(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.MOBILE, values);
    }

    /**
     * Fetch records that have <code>award IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByAward(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.AWARD, values);
    }

    /**
     * Fetch records that have <code>binding_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByBindingTime(Timestamp... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.BINDING_TIME, values);
    }

    /**
     * Fetch records that have <code>email IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByEmail(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.EMAIL, values);
    }

    /**
     * Fetch records that have <code>activation IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByActivation(Byte... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.ACTIVATION, values);
    }

    /**
     * Fetch records that have <code>activation_code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByActivationCode(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.ACTIVATION_CODE, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByDisable(Byte... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.DISABLE, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByCreateTime(Timestamp... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByUpdateTime(Timestamp... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>auth_level IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByAuthLevel(Byte... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.AUTH_LEVEL, values);
    }

    /**
     * Fetch records that have <code>register_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByRegisterTime(Timestamp... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.REGISTER_TIME, values);
    }

    /**
     * Fetch records that have <code>register_ip IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByRegisterIp(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.REGISTER_IP, values);
    }

    /**
     * Fetch records that have <code>last_login_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByLastLoginTime(Timestamp... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.LAST_LOGIN_TIME, values);
    }

    /**
     * Fetch records that have <code>last_login_ip IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByLastLoginIp(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.LAST_LOGIN_IP, values);
    }

    /**
     * Fetch records that have <code>login_count IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByLoginCount(Long... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.LOGIN_COUNT, values);
    }

    /**
     * Fetch records that have <code>source IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchBySource(Byte... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.SOURCE, values);
    }

    /**
     * Fetch records that have <code>download_token IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByDownloadToken(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.DOWNLOAD_TOKEN, values);
    }

    /**
     * Fetch records that have <code>hr_wxuser_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByHrWxuserId(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.HR_WXUSER_ID, values);
    }

    /**
     * Fetch records that have <code>custom_field IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByCustomField(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.CUSTOM_FIELD, values);
    }

    /**
     * Fetch records that have <code>is_rp_sent IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByIsRpSent(Byte... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.IS_RP_SENT, values);
    }

    /**
     * Fetch records that have <code>sysuser_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchBySysuserId(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.SYSUSER_ID, values);
    }

    /**
     * Fetch records that have <code>position_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByPositionId(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.POSITION_ID, values);
    }

    /**
     * Fetch records that have <code>section_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchBySectionId(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.SECTION_ID, values);
    }

    /**
     * Fetch records that have <code>email_isvalid IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByEmailIsvalid(Byte... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.EMAIL_ISVALID, values);
    }

    /**
     * Fetch records that have <code>auth_method IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByAuthMethod(Byte... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.AUTH_METHOD, values);
    }

    /**
     * Fetch records that have <code>custom_field_values IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByCustomFieldValues(String... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.CUSTOM_FIELD_VALUES, values);
    }

    /**
     * Fetch records that have <code>team_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByTeamId(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.TEAM_ID, values);
    }

    /**
     * Fetch records that have <code>job_grade IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByJobGrade(Byte... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.JOB_GRADE, values);
    }

    /**
     * Fetch records that have <code>city_code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByCityCode(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.CITY_CODE, values);
    }

    /**
     * Fetch records that have <code>degree IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByDegree(Byte... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.DEGREE, values);
    }

    /**
     * Fetch records that have <code>bonus IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeTest> fetchByBonus(Integer... values) {
        return fetch(UserEmployeeTest.USER_EMPLOYEE_TEST.BONUS, values);
    }
}
