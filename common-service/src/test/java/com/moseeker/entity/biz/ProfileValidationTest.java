package com.moseeker.entity.biz;

import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.thrift.gen.profile.struct.Education;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;
import com.moseeker.thrift.gen.profile.struct.WorkExp;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

/**
 * Created by jack on 07/11/2017.
 */
public class ProfileValidationTest {
    @Test
    public void verifyEducation() throws Exception {
        Education education = new Education();
        education.setStart_date("2027-09-01");
        education.setEnd_date("2021-07-01");

        ValidationMessage<Education> vm = ProfileValidation.verifyEducation(education);
        assertEquals(StringUtils.isBlank(vm.getResult()), false);

        Education education1 = new Education();
        education1.setStart_date("2007-09-01");
        education1.setEnd_date("2021-07-01");
        education1.setDegree(2);
        education1.setCollege_name("中原工学院");
        ValidationMessage<Education> vm1 = ProfileValidation.verifyEducation(education1);
        assertEquals(StringUtils.isBlank(vm1.getResult()), true);
    }

    @Test
    public void verifyEducation1() throws Exception {
        ProfileEducationRecord record = new ProfileEducationRecord();
        record.setStart(new Date(DateTime.parse("2027-09-01").getMillis()));
        record.setEnd(new Date(DateTime.parse("2021-07-01").getMillis()));

        ValidationMessage<ProfileEducationRecord> vm = ProfileValidation.verifyEducation(record);
        assertEquals(StringUtils.isBlank(vm.getResult()), false);


        ProfileEducationRecord record1 = new ProfileEducationRecord();
        record1.setStart(new Date(DateTime.parse("2017-09-01").getMillis()));
        record1.setEnd(new Date(DateTime.parse("2021-07-01").getMillis()));
        record1.setDegree((byte) 1);
        record1.setCollegeName("中原工学院");

        ValidationMessage<ProfileEducationRecord> vm1 = ProfileValidation.verifyEducation(record1);
        assertEquals(StringUtils.isBlank(vm1.getResult()), true);
    }

    @Test
    public void verifyProjectExp() throws Exception {
        ProjectExp projectExp = new ProjectExp();
        projectExp.setStart_date("2007-09-01");
        projectExp.setEnd_date("2021-07-01");
        projectExp.setName("项目名称");
        ValidationMessage<ProjectExp> vm = ProfileValidation.verifyProjectExp(projectExp);
        assertEquals(StringUtils.isBlank(vm.getResult()), true);

        ProjectExp projectExp1 = new ProjectExp();
        ValidationMessage<ProjectExp> vm1 = ProfileValidation.verifyProjectExp(projectExp1);
        System.out.println(vm1.getResult());
        assertEquals(StringUtils.isBlank(vm1.getResult()), false);

        ProjectExp projectExp2 = new ProjectExp();
        projectExp2.setName("项目名称");
        projectExp2.setStart_date("2027-09-01");
        projectExp2.setEnd_date("2021-07-01");
        ValidationMessage<ProjectExp> vm2 = ProfileValidation.verifyProjectExp(projectExp2);
        System.out.println(vm2.getResult());
        assertEquals(StringUtils.isBlank(vm2.getResult()), false);
    }

    @Test
    public void verifyProjectExp1() throws Exception {
        ProfileProjectexpRecord projectExp = new ProfileProjectexpRecord();
        projectExp.setStart(new Date(DateTime.parse("2017-09-01").getMillis()));
        projectExp.setEnd(new Date(DateTime.parse("2021-07-01").getMillis()));
        projectExp.setName("项目名称");
        ValidationMessage<ProfileProjectexpRecord> vm = ProfileValidation.verifyProjectExp(projectExp);
        assertEquals(StringUtils.isBlank(vm.getResult()), true);

        ProfileProjectexpRecord projectExp1 = new ProfileProjectexpRecord();
        ValidationMessage<ProfileProjectexpRecord> vm1 = ProfileValidation.verifyProjectExp(projectExp1);
        assertEquals(StringUtils.isBlank(vm1.getResult()), false);

        ProfileProjectexpRecord projectExp2 = new ProfileProjectexpRecord();
        projectExp2.setName("项目名称");
        projectExp2.setStart(new Date(DateTime.parse("2027-09-01").getMillis()));
        projectExp2.setEnd(new Date(DateTime.parse("2021-07-01").getMillis()));
        ValidationMessage<ProfileProjectexpRecord> vm2 = ProfileValidation.verifyProjectExp(projectExp2);
        System.out.println(vm2.getResult());
        assertEquals(StringUtils.isBlank(vm2.getResult()), false);
    }

    @Test
    public void verifyWorkExp() throws Exception {
        WorkExp workExp = new WorkExp();
        workExp.setStart_date("2017-09-01");
        workExp.setEnd_date("2021-07-01");
        workExp.setCompany_name("公司名称");
        workExp.setJob("职位");
        ValidationMessage<WorkExp> vm = ProfileValidation.verifyWorkExp(workExp);
        assertEquals(StringUtils.isBlank(vm.getResult()), true);

        WorkExp workExp1 = new WorkExp();
        ValidationMessage<WorkExp> vm1 = ProfileValidation.verifyWorkExp(workExp1);
        System.out.println(vm1.getResult());
        assertEquals(StringUtils.isBlank(vm1.getResult()), false);

        WorkExp workExp2 = new WorkExp();
        workExp2.setStart_date("2027-09-01");
        workExp2.setEnd_date("2021-07-01");
        workExp2.setCompany_name("公司名称");
        workExp2.setJob("职位");
        ValidationMessage<WorkExp> vm2 = ProfileValidation.verifyWorkExp(workExp2);
        System.out.println(vm2.getResult());
        assertEquals(StringUtils.isBlank(vm2.getResult()), false);
    }

    @Test
    public void verifyWorkExp1() throws Exception {
        ProfileWorkexpEntity workExp = new ProfileWorkexpEntity();
        workExp.setStart(new Date(DateTime.parse("2017-09-01").getMillis()));
        workExp.setEnd(new Date(DateTime.parse("2021-07-01").getMillis()));
        HrCompanyRecord companyRecord = new HrCompanyRecord();
        companyRecord.setName("公司名称");
        workExp.setCompany(companyRecord);
        workExp.setJob("职位");
        workExp.setDescription("描述");
        ValidationMessage<ProfileWorkexpEntity> vm = ProfileValidation.verifyWorkExp(workExp);
        System.out.println(vm.getResult());
        assertEquals(StringUtils.isBlank(vm.getResult()), true);

        ProfileWorkexpEntity workExp1 = new ProfileWorkexpEntity();
        ValidationMessage<ProfileWorkexpEntity> vm1 = ProfileValidation.verifyWorkExp(workExp1);
        System.out.println(vm1.getResult());
        assertEquals(StringUtils.isBlank(vm1.getResult()), false);

        ProfileWorkexpEntity workExp2 = new ProfileWorkexpEntity();
        workExp2.setStart(new Date(DateTime.parse("2027-09-01").getMillis()));
        workExp2.setEnd(new Date(DateTime.parse("2021-07-01").getMillis()));
        workExp2.setCompany(companyRecord);
        workExp2.setJob("职位");
        workExp2.setDescription("描述");
        ValidationMessage<ProfileWorkexpEntity> vm2 = ProfileValidation.verifyWorkExp(workExp2);
        System.out.println(vm2.getResult());
        assertEquals(StringUtils.isBlank(vm2.getResult()), false);
    }

}