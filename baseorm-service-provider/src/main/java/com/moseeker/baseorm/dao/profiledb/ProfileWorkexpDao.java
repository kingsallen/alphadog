package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.DictIndustry;
import com.moseeker.baseorm.db.dictdb.tables.DictPosition;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.ProfileWorkexp;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileWorkexpDO;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xxx
 *         ProfileWorkexpDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class ProfileWorkexpDao extends JooqCrudImpl<ProfileWorkexpDO, ProfileWorkexpRecord> {

    public ProfileWorkexpDao() {
        super(ProfileWorkexp.PROFILE_WORKEXP, ProfileWorkexpDO.class);
    }

    public ProfileWorkexpDao(TableImpl<ProfileWorkexpRecord> table, Class<ProfileWorkexpDO> profileWorkexpDOClass) {
        super(table, profileWorkexpDOClass);
    }

    /*
        最近工作算法： 有至今的优先，如果有2个至今，选最新添加的。 没有至今的， 选择截止时间最晚的。
     */
    public ProfileWorkexpRecord getLastWorkExp(int profileId) {
        ProfileWorkexpRecord record = null;
        if (profileId > 0) {
            Result<ProfileWorkexpRecord> result = create.selectFrom(ProfileWorkexp.PROFILE_WORKEXP)
                    .where(ProfileWorkexp.PROFILE_WORKEXP.PROFILE_ID.equal((int) (profileId)))
                    .and(ProfileWorkexp.PROFILE_WORKEXP.END_UNTIL_NOW.isTrue())
                    .orderBy(ProfileWorkexp.PROFILE_WORKEXP.ID.desc())
                    .limit(1).fetch();

            if (result == null || result.size() == 0) {
                result = create.selectFrom(ProfileWorkexp.PROFILE_WORKEXP)
                        .where(ProfileWorkexp.PROFILE_WORKEXP.PROFILE_ID.equal((int) (profileId)))
                        .orderBy(ProfileWorkexp.PROFILE_WORKEXP.END.desc())
                        .limit(1).fetch();
            }

            if (result != null && result.size() > 0) {
                record = result.get(0);
            }
        }
        return record;
    }

    public int updateProfileUpdateTime(Set<Integer> workExpIds) {
        int status = 0;

        List<Integer> profileIdList = create.select(ProfileWorkexp.PROFILE_WORKEXP.PROFILE_ID)
                .from(ProfileWorkexp.PROFILE_WORKEXP)
                .where(ProfileWorkexp.PROFILE_WORKEXP.ID.in(workExpIds))
                .stream()
                .map(integerRecord1 -> integerRecord1.value1())
                .collect(Collectors.toList());

        if (profileIdList != null && profileIdList.size() > 0) {
            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            status = create.update(ProfileProfile.PROFILE_PROFILE)
                    .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                    .where(ProfileProfile.PROFILE_PROFILE.ID
                            .in(profileIdList))
                    .execute();
        }

        return status;
    }

    public int delWorkExpsByProfileId(int profileId) {
        int count = 0;
        count = create.delete(ProfileWorkexp.PROFILE_WORKEXP)
                .where(ProfileWorkexp.PROFILE_WORKEXP.PROFILE_ID.equal((int) (profileId)))
                .execute();

        return count;
    }

    public int postWordExps(List<ProfileWorkexpEntity> workexpRecords) {
        int count = 0;
        if (workexpRecords != null && workexpRecords.size() > 0) {

            List<HrCompanyRecord> companies = new ArrayList<>();

//				ProfileCompletenessRecord completenessRecord = create.selectFrom(ProfileCompleteness.PROFILE_COMPLETENESS).where(ProfileCompleteness.PROFILE_COMPLETENESS.PROFILE_ID.equal(workexpRecords.get(0).getProfileId())).fetchOne();
//				if(completenessRecord == null) {
//					completenessRecord = new ProfileCompletenessRecord();
//					completenessRecord.setProfileId(workexpRecords.get(0).getProfileId());
//				}

            Result<DictIndustryRecord> industries = create.selectFrom(DictIndustry.DICT_INDUSTRY).fetch();
            Result<DictCityRecord> cities = create.selectFrom(DictCity.DICT_CITY).fetch();
            Result<DictPositionRecord> positions = create.selectFrom(DictPosition.DICT_POSITION).fetch();

            Timestamp now = new Timestamp(System.currentTimeMillis());
            workexpRecords.forEach(workexp -> {
                workexp.setCreateTime(now);
                if (workexp.getCompany() != null
                        && !StringUtils.isNullOrEmpty(workexp.getCompany().getName())) {
                    HrCompanyRecord hc = create.selectFrom(HrCompany.HR_COMPANY)
                            .where(HrCompany.HR_COMPANY.NAME.equal(workexp.getCompany().getName())).limit(1)
                            .fetchOne();
                    if (hc != null) {
                        workexp.setCompanyId(hc.getId());
                        companies.add(hc);
                    } else {
                        HrCompanyRecord newCompany = workexp.getCompany();
                        create.attach(newCompany);
                        newCompany.insert();
                        workexp.setCompanyId(newCompany.getId());
                        companies.add(newCompany);
                    }
                }
                if (!StringUtils.isNullOrEmpty(workexp.getIndustryName())) {
                    for (DictIndustryRecord industryRecord : industries) {
                        if (workexp.getIndustryName().equals(industryRecord.getName())) {
                            workexp.setIndustryCode(industryRecord.getCode());
                            break;
                        }
                    }
                }
                if (!StringUtils.isNullOrEmpty(workexp.getCityName())) {
                    for (DictCityRecord cityRecord : cities) {
                        if (workexp.getCityName().equals(cityRecord.getName())) {
                            workexp.setCityCode(cityRecord.getCode());
                            break;
                        }
                    }
                }
                if (!StringUtils.isNullOrEmpty(workexp.getPositionName())) {
                    for (DictPositionRecord positionRecord : positions) {
                        if (positionRecord.getName().equals(workexp.getPositionName())) {
                            workexp.setPositionCode(positionRecord.getCode());
                            break;
                        }
                    }
                }

                create.attach(workexp);
                workexp.insert();
            });

//              int workExpCompleteness = completenessCalculator.calculateProfileWorkexps(workexpRecords, companies);
//				completenessRecord.setProfileWorkexp(workExpCompleteness);
//				create.attach(completenessRecord);
//				completenessRecord.update();
        }

        return count;
    }

    public List<ProfileWorkexpRecord> fetchByProfileId(Integer profileId) {
        if (profileId != null && profileId > 0) {
            return create
                    .selectFrom(ProfileWorkexp.PROFILE_WORKEXP)
                    .where(ProfileWorkexp.PROFILE_WORKEXP.PROFILE_ID.eq(profileId))
                    .fetch();
        } else {
            return new ArrayList<>(0);
        }
    }
}
