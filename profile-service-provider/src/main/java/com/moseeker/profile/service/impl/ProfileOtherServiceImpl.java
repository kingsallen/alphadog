package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileOtherDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.common.exception.CommonException;
import com.moseeker.entity.biz.ProfileValidation;
import com.moseeker.entity.biz.ValidationMessage;
import com.moseeker.profile.service.ProfileOtherService;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jack on 02/01/2018.
 */
@Service
public class

ProfileOtherServiceImpl implements ProfileOtherService {

    @Autowired
    private ProfileOtherDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;

    @Transactional
    @Override
    public List<ProfileOtherDO> addOthers(List<ProfileOtherDO> others) {
        if (others != null) {
            validateOthers(others);
            if (others.size() > 0) {
                List<ProfileOtherDO> otherDOS = dao.addAllData(others);
                Set<Integer> ProfileIds = otherDOS.stream().map(m -> m.getProfileId()).collect(Collectors.toSet());
                profileDao.updateUpdateTime(ProfileIds);
                ProfileIds.forEach( profileId -> {
                    profileCompanyTagService.handlerCompanyTag(profileId);
                });
                return otherDOS;
            }

        }
        return new ArrayList<>();
    }

    @Override
    public ProfileOtherDO addOther(ProfileOtherDO other) throws CommonException {
        ValidationMessage<ProfileOtherDO> validationMessage =  ProfileValidation.verifyOther(other);
        if (validationMessage.isPass()) {
            ProfileOtherDO otherDO = dao.addData(other);
            Set<Integer> set = new HashSet<>();
            set.add(other.getProfileId());
            profileDao.updateUpdateTime(set);
            profileCompanyTagService.handlerCompanyTag(other.getProfileId());
            return otherDO;
        }
        return new ProfileOtherDO();
    }

    @Override
    public int[] putOthers(List<ProfileOtherDO> others) {
        if (others != null) {
            validateOthers(others);
            if (others.size() > 0) {
                int[] ids =  dao.updateDatas(others);
                Set<Integer> ProfileIds = others.stream().map(m -> m.getProfileId()).collect(Collectors.toSet());
                profileDao.updateUpdateTime(ProfileIds);
                ProfileIds.forEach( profileId -> {
                    profileCompanyTagService.handlerCompanyTag(profileId);
                });
                return ids;
            }
        }
        return new int[0];
    }

    @Override
    public int putOther(ProfileOtherDO other) throws CommonException {
        ValidationMessage<ProfileOtherDO> validationMessage =  ProfileValidation.verifyOther(other);
        if (validationMessage.isPass()) {
            int id = dao.updateData(other);
            Set<Integer> set = new HashSet<>();
            set.add(other.getProfileId());
            profileDao.updateUpdateTime(set);
            profileCompanyTagService.handlerCompanyTag(other.getProfileId());
            return id;
        }
        return 0;
    }

    private List<ProfileOtherDO> validateOthers(List<ProfileOtherDO> others) {
        if (others != null) {
            Iterator<ProfileOtherDO> iterator = others.iterator();
            while (iterator.hasNext()) {
                ProfileOtherDO otherDO = iterator.next();
                ValidationMessage<ProfileOtherDO> validationMessage =  ProfileValidation.verifyOther(otherDO);
                if (!validationMessage.isPass()) {
                    iterator.remove();
                }
            }
        }
        return others;
    }
}
