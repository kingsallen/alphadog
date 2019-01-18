package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.pojo.CustomEmployeeInsertResult;
import com.moseeker.baseorm.pojo.ExecuteResult;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.jooq.impl.DSL.*;

@Repository
public class UserEmployeeDao extends JooqCrudImpl<UserEmployeeDO, UserEmployeeRecord> {

    public UserEmployeeDao() {
        super(UserEmployee.USER_EMPLOYEE, UserEmployeeDO.class);
    }

    public UserEmployeeDao(TableImpl<UserEmployeeRecord> table, Class<UserEmployeeDO> userEmployeeDOClass) {
        super(table, userEmployeeDOClass);
    }

    public UserEmployeeDO getEmployee(com.moseeker.common.util.query.Query query) {
        UserEmployeeDO employee = new UserEmployeeDO();

        try {
            UserEmployeeRecord record = this.getRecord(query);
            if (record != null) {
                employee = BeanUtils.DBToStruct(UserEmployeeDO.class, record);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

        return employee;
    }

    public List<UserEmployeeRecord> getEmployeeByWeChat(Integer companyId, List<Integer> weChatIds) throws Exception {
        List<UserEmployeeRecord> list = new ArrayList<UserEmployeeRecord>();
        SelectJoinStep<Record> table = create.select().from(UserEmployee.USER_EMPLOYEE);
        table.where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0));
        Result<Record> result = table.fetch();
        if (result != null && result.size() > 0) {
            for (Record r : result) {
                list.add((UserEmployeeRecord) r);
            }
        }
        return list;
    }

    public int delResource(com.moseeker.common.util.query.Query query) throws Exception {
        if (query != null && query.getConditions() != null) {
            List<UserEmployeeRecord> records = getRecords(query);
            if (records.size() > 0) {
                return deleteRecords(records).length;
            }
        }
        return 0;
    }

    public UserEmployeeDO getUserEmployeeForUpdate(int id) {
        UserEmployeeRecord record = create.selectFrom(table).where(UserEmployee.USER_EMPLOYEE.ID.eq(id)).and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0)).
                and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0)).forUpdate().fetchOne();
        return BeanUtils.DBToStruct(UserEmployeeDO.class, record);
    }

    public int addAward(Integer employeeId, int award, int oldAward) {
        return create.update(table).set(UserEmployee.USER_EMPLOYEE.AWARD, award).where(UserEmployee.USER_EMPLOYEE.ID.eq(employeeId)).and(UserEmployee.USER_EMPLOYEE.AWARD.eq(oldAward)).execute();
    }

    /*
    获取有邮箱认证的雇员信息
     */
    public List<Map<String, Object>> getUserEmployeeLike(int companyId, String email, int pageNum, int pageSize) {
        List<Map<String, Object>> list = create.select(UserEmployee.USER_EMPLOYEE.ID, UserEmployee.USER_EMPLOYEE.CNAME, UserEmployee.USER_EMPLOYEE.SYSUSER_ID, UserEmployee.USER_EMPLOYEE.EMAIL.as("email"))
                .from(UserEmployee.USER_EMPLOYEE).where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId)).and(UserEmployee.USER_EMPLOYEE.EMAIL.like("%" + email + "%"))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0)).and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0))
                .orderBy(UserEmployee.USER_EMPLOYEE.UPDATE_TIME.desc())
                .union(
                        create.select(UserEmployee.USER_EMPLOYEE.ID, UserEmployee.USER_EMPLOYEE.CNAME, UserEmployee.USER_EMPLOYEE.SYSUSER_ID, UserUser.USER_USER.EMAIL.as("email"))
                                .from(UserEmployee.USER_EMPLOYEE).join(UserUser.USER_USER).on(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(UserUser.USER_USER.ID))
                                .and(UserUser.USER_USER.EMAIL_VERIFIED.eq((byte) 1)).and(UserUser.USER_USER.EMAIL.like("%" + email + "%"))
                                .where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
                                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0)).and(UserEmployee.USER_EMPLOYEE.EMAIL.eq(""))
                ).limit((pageNum - 1) * pageSize, pageSize).fetchMaps();
        return list;
    }

    /*
   获取有邮箱雇员数量
     */
    public int getUserEmployeeLikeCount(int companyId, String email) {
        int count = create.selectCount()
                .from(UserEmployee.USER_EMPLOYEE).where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId)).and(UserEmployee.USER_EMPLOYEE.EMAIL.like("%" + email + "%"))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0)).and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0))
                .orderBy(UserEmployee.USER_EMPLOYEE.UPDATE_TIME.desc()).fetchOne().value1();
        int count1 = create.selectCount()
                .from(UserEmployee.USER_EMPLOYEE).join(UserUser.USER_USER).on(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(UserUser.USER_USER.ID))
                .and(UserUser.USER_USER.EMAIL_VERIFIED.eq((byte) 1)).and(UserUser.USER_USER.EMAIL.like("%" + email + "%"))
                .where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0)).and(UserEmployee.USER_EMPLOYEE.EMAIL.eq("")).fetchOne().value1();
        return count + count1;
    }

    /*
    根据id获取有邮箱的雇员信息
     */
    public List<Map<String, Object>> getUserEmployeeInfoById(List<Integer> idList) {
        List<Map<String, Object>> list = create.select(UserEmployee.USER_EMPLOYEE.ID, UserEmployee.USER_EMPLOYEE.CNAME, UserEmployee.USER_EMPLOYEE.SYSUSER_ID, UserEmployee.USER_EMPLOYEE.EMAIL.as("email"))
                .from(UserEmployee.USER_EMPLOYEE).where(UserEmployee.USER_EMPLOYEE.ID.in(idList))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0)).and(UserEmployee.USER_EMPLOYEE.AUTH_METHOD.eq((byte) 0))
                .orderBy(UserEmployee.USER_EMPLOYEE.UPDATE_TIME.desc())
                .union(
                        create.select(UserEmployee.USER_EMPLOYEE.ID,UserEmployee.USER_EMPLOYEE.CNAME,UserEmployee.USER_EMPLOYEE.SYSUSER_ID,UserUser.USER_USER.EMAIL.as("email")).from(UserEmployee.USER_EMPLOYEE).join(UserUser.USER_USER).on(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(UserUser.USER_USER.ID))
                                .and(UserUser.USER_USER.EMAIL_VERIFIED.eq((byte)1)).where(UserEmployee.USER_EMPLOYEE.ID.in(idList))
                                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte)0)).and(UserEmployee.USER_EMPLOYEE.AUTH_METHOD.ne((byte)0))
                ).fetchMaps();
        return list;
    }


    public List<UserEmployeeDO> getUserEmployeeForidList(Set<Integer> idList) {
        if (idList != null && idList.size() > 0) {
            List<UserEmployeeRecord> record = create.selectFrom(table).where(UserEmployee.USER_EMPLOYEE.ID.in(idList)).and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0)).
                    and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0)).fetch();
            return BeanUtils.DBToStruct(UserEmployeeDO.class, record);
        }
        return new ArrayList<>();
    }


    public Map<Integer, Integer> getEmployeeNum(List<Integer> idList) {
        if (!StringUtils.isEmptyList(idList)) {
            Result<Record2<Integer, Integer>> result = create.select(UserEmployee.USER_EMPLOYEE.COMPANY_ID, DSL.count(UserEmployee.USER_EMPLOYEE.ID))
                    .from(UserEmployee.USER_EMPLOYEE).where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.in(idList)).and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0)).
                            and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0))
                    .groupBy(UserEmployee.USER_EMPLOYEE.COMPANY_ID)
                    .fetch();
            if (!result.isEmpty()) {
                Map<Integer, Integer> params = new HashMap<>();
                for (Record2<Integer, Integer> record2 : result) {
                    params.put(record2.value1(), record2.value2());
                }
                return params;
            }
        }
        return new HashMap<>();
    }

    /**
     * 查找用户的员工信息
     *
     * @param userId 用户编号
     * @return 员工信息
     */
    public UserEmployeeRecord getActiveEmployeeByUserId(int userId) {

        return create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(userId))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq(EmployeeActiveState.Actived.getState()))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .limit(1)
                .fetchOne();
    }

    public void unFollowWechat(int id) {
        create.update(UserEmployee.USER_EMPLOYEE)
                .set(UserEmployee.USER_EMPLOYEE.ACTIVATION, EmployeeActiveState.UnFollow.getState())
                .where(UserEmployee.USER_EMPLOYEE.ID.eq(id))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq(EmployeeActiveState.Actived.getState()))
                .execute();
    }

    public UserEmployeeDO getUnFollowEmployeeByUserId(int userId) {
        return create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(userId))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq(EmployeeActiveState.UnFollow.getState()))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .limit(1)
                .fetchOneInto(UserEmployeeDO.class);
    }

    /**
     * 叫取消关注状态的员工转回认证成功的状态。
     * 但是必须保证一个sysuser_id 只能对应一个认证成功的员工
     *
     * @param id        员工编号
     * @param sysuserId 用户编号
     */
    public void followWechat(int id, int sysuserId, String time) {

        create.execute("update " +
                " userdb.user_employee u " +
                "left join (" +
                "  select uu.id, uu.sysuser_id as user_id " +
                "  from userdb.user_employee uu " +
                "  where uu.sysuser_id = " + sysuserId + " and uu.activation = 0 and uu.disable = 0) ut " +
                " on u.sysuser_id = ut.user_id " +
                " set u.activation = " + EmployeeActiveState.Actived.getState() +
                " , set u.binding_time = " + time +
                " where u.activation = " + EmployeeActiveState.UnFollow.getState() + " " +
                " and u.id = " + id + " and ut.id is null");
    }

    @Transactional
    public ExecuteResult registerEmployee(UserEmployeeDO useremployee) {

        ExecuteResult executeResult = new ExecuteResult();

        Param<Integer> companyIdParam = param(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), useremployee.getCompanyId());
        Param<String> employeeIdParam = param(UserEmployee.USER_EMPLOYEE.EMPLOYEEID.getName(),
                org.apache.commons.lang.StringUtils.defaultIfBlank(useremployee.getEmployeeid(), ""));
        Param<Integer> userIdParam = param(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName(), useremployee.getSysuserId());
        Param<String> cnameParam = param(UserEmployee.USER_EMPLOYEE.CNAME.getName(), useremployee.getCname());
        Param<String> mobileParam = param(UserEmployee.USER_EMPLOYEE.MOBILE.getName(),
                org.apache.commons.lang.StringUtils.defaultIfBlank(useremployee.getMobile(), ""));
        Param<String> emailParam = param(UserEmployee.USER_EMPLOYEE.EMAIL.getName(),
                org.apache.commons.lang.StringUtils.defaultIfBlank(useremployee.getEmail(), ""));
        Param<Integer> wxUserIdParam = param(UserEmployee.USER_EMPLOYEE.WXUSER_ID.getName(), useremployee.getWxuserId());
        Param<Byte> authMethodParam = param(UserEmployee.USER_EMPLOYEE.AUTH_METHOD.getName(), useremployee.getAuthMethod());
        Param<Byte> activationParam = param(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), (byte) useremployee.getActivation());
        Param<String> customFieldValueParam;
        if (StringUtils.isNotNullOrEmpty(useremployee.getCustomFieldValues())) {
            customFieldValueParam = param(UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD_VALUES.getName(), useremployee.getCustomFieldValues());
        } else {
            customFieldValueParam = param(UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD_VALUES.getName(), Constant.EMPLOYEE_DEFAULT_CUSTOM_FIELD_VALUE);
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Param<Timestamp> createTimeParam;
        if (org.apache.commons.lang.StringUtils.isNotBlank(useremployee.getCreateTime())) {
            createTimeParam = param(UserEmployee.USER_EMPLOYEE.CREATE_TIME.getName(),
                    new Timestamp(LocalDateTime.parse(useremployee.getCreateTime(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            .atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() * 1000));
        } else {
            createTimeParam = param(UserEmployee.USER_EMPLOYEE.CREATE_TIME.getName(), now);
        }
        Param<Timestamp> BindingTimeParam;
        if (org.apache.commons.lang.StringUtils.isNotBlank(useremployee.getBindingTime())) {
            BindingTimeParam = param(UserEmployee.USER_EMPLOYEE.BINDING_TIME.getName(),
                    new Timestamp(LocalDateTime.parse(useremployee.getBindingTime(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            .atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() * 1000));
        } else {
            BindingTimeParam = param(UserEmployee.USER_EMPLOYEE.BINDING_TIME.getName(), now);
        }

        int execute = create.insertInto(UserEmployee.USER_EMPLOYEE,
                UserEmployee.USER_EMPLOYEE.COMPANY_ID,
                UserEmployee.USER_EMPLOYEE.SYSUSER_ID,
                UserEmployee.USER_EMPLOYEE.EMPLOYEEID,
                UserEmployee.USER_EMPLOYEE.CNAME,
                UserEmployee.USER_EMPLOYEE.MOBILE,
                UserEmployee.USER_EMPLOYEE.EMAIL,
                UserEmployee.USER_EMPLOYEE.WXUSER_ID,
                UserEmployee.USER_EMPLOYEE.AUTH_METHOD,
                UserEmployee.USER_EMPLOYEE.ACTIVATION,
                UserEmployee.USER_EMPLOYEE.CREATE_TIME,
                UserEmployee.USER_EMPLOYEE.BINDING_TIME,
                UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD_VALUES)

                .select(
                        select(
                                companyIdParam,
                                userIdParam,
                                employeeIdParam,
                                cnameParam,
                                mobileParam,
                                emailParam,
                                wxUserIdParam,
                                authMethodParam,
                                activationParam,
                                createTimeParam,
                                BindingTimeParam,
                                customFieldValueParam
                        )
                                .whereNotExists(
                                        selectOne()
                                                .from(UserEmployee.USER_EMPLOYEE)
                                                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(useremployee.getSysuserId()))
                                                .and(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(useremployee.getCompanyId()))
                                )
                )
                .execute();
        executeResult.setExecute(execute);
        if (execute == 0) {
            Record2<Integer, Byte> result = create.select(UserEmployee.USER_EMPLOYEE.ID, UserEmployee.USER_EMPLOYEE.ACTIVATION)
                    .from(UserEmployee.USER_EMPLOYEE)
                    .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(useremployee.getSysuserId()))
                    .and(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(useremployee.getCompanyId()))
                    .orderBy(UserEmployee.USER_EMPLOYEE.ID.desc())
                    .limit(1)
                    .fetchOne();
            if (result != null && result.value2() != EmployeeActiveState.Actived.getState()) {
                int execute1 = create.update(UserEmployee.USER_EMPLOYEE)
                        .set(UserEmployee.USER_EMPLOYEE.ACTIVATION, EmployeeActiveState.Actived.getState())
                        .where(UserEmployee.USER_EMPLOYEE.ID.eq(result.value1()))
                        .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq(result.value2()))
                        .execute();
                if (execute1 != 0) {
                    logger.error("员工信息认证失败！");
                }
            }
        }

        UserEmployeeRecord record = create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(useremployee.getSysuserId()))
                .and(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(useremployee.getCompanyId()))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq(EmployeeActiveState.Actived.getState()))
                .fetchOne();
        if (record != null) {
            executeResult.setId(record.getId());
        } else {
            executeResult.setId(0);
        }
        return executeResult;
    }

    public UserEmployeeRecord getUnActiveEmployee(int sysuserId, int companyId) {

        return create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(sysuserId))
                .and(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
                .orderBy(UserEmployee.USER_EMPLOYEE.BINDING_TIME.desc())
                .limit(1)
                .fetchOne();
    }

    public UserEmployeeRecord getActiveEmployee(int sysuserId, int companyId) {

        return create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(sysuserId))
                .and(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq(EmployeeActiveState.Actived.getState()))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .fetchOne();
    }

    public List<UserEmployeeDO> getActiveEmployee(List<Integer> sysuserIds, int companyId) {
        if(StringUtils.isEmptyList(sysuserIds)){
            return new ArrayList<>();
        }
        return create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.in(sysuserIds))
                .and(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq(EmployeeActiveState.Actived.getState()))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .fetchInto(UserEmployeeDO.class);
    }

    public UserEmployeeRecord getEmployeeByUserId(int userId) {


        return create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(userId))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .limit(1)
                .fetchOne();
    }


    public UserEmployeeRecord insertCustomEmployeeIfNotExist(UserEmployeeRecord record) {

        Param<Integer> companyIdParam = param(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), record.getCompanyId());
        Param<Byte> activationParam = param(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(),
                record.getActivation() == null ? EmployeeActiveState.Init.getState() : record.getActivation());
        Param<String> cnameParam = param(UserEmployee.USER_EMPLOYEE.CNAME.getName(), record.getCname());
        Param<String> customFieldParam = param(UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD.getName(), record.getCustomField());
        Param<Byte> authMethodParam = param(UserEmployee.USER_EMPLOYEE.AUTH_METHOD.getName(), record.getAuthMethod());

        UserEmployeeRecord record1 = create.insertInto(UserEmployee.USER_EMPLOYEE)
                .columns(UserEmployee.USER_EMPLOYEE.COMPANY_ID,
                        UserEmployee.USER_EMPLOYEE.ACTIVATION,
                        UserEmployee.USER_EMPLOYEE.CNAME,
                        UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD,
                        UserEmployee.USER_EMPLOYEE.AUTH_METHOD)
                .select(
                        select(
                                companyIdParam, activationParam, cnameParam, customFieldParam, authMethodParam
                        )
                                .whereNotExists(
                                        selectOne()
                                                .from(UserEmployee.USER_EMPLOYEE)
                                                .where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(record.getCompanyId()))
                                                .and(UserEmployee.USER_EMPLOYEE.CNAME.eq(record.getCname()))
                                                .and(UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD.eq(record.getCustomField()))
                                )
                )
                .returning()
                .fetchOne();
        return record1;
    }

    public UserEmployeeDO getEmployeeById(int employeeId) {
        return create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.ID.eq(employeeId))
                .fetchOneInto(UserEmployeeDO.class);
    }

    public List<UserEmployeeDO> getEmployeeByIds(List<Integer> employeeIds) {
        return create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.ID.in(employeeIds))
                .fetchInto(UserEmployeeDO.class);
    }


    public List<UserEmployeeDO> getEmployeeBycompanyIds(List<Integer> companyIds) {

        return create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.in(companyIds))
                .fetchInto(UserEmployeeDO.class);
    }


    public List<UserEmployeeDO> getEmployeeBycompanyId(Integer companyId) {

        return create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0))
                .fetchInto(UserEmployeeDO.class);
    }

    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee> getEmployeeList(List<Integer> userIdList,int companyId) {
        return create.selectFrom(UserEmployee.USER_EMPLOYEE).where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.in(userIdList)).and(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte)0)).and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte)0))
                .fetchInto(com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee.class);
    }

    public com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee getSingleEmployeeByUserId(int userId) {
        return create.selectFrom(UserEmployee.USER_EMPLOYEE).where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(userId))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte)0)).and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte)0))
                .fetchOneInto(com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee.class);
    }

    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee> getDataListByCidListAndUserIdList(List<Integer> userIdList,List<Integer> companyIdList) {
        return create.selectFrom(UserEmployee.USER_EMPLOYEE).where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.in(userIdList))
                .and(UserEmployee.USER_EMPLOYEE.COMPANY_ID.in(companyIdList)).orderBy(UserEmployee.USER_EMPLOYEE.ACTIVATION.asc(),UserEmployee.USER_EMPLOYEE.DISABLE.asc())
                .fetchInto(com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee.class);
    }

}