package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileOtherDao;
import com.moseeker.common.exception.CommonException;
import com.moseeker.entity.biz.ProfileValidation;
import com.moseeker.entity.biz.ValidationMessage;
import com.moseeker.profile.service.ProfileOtherService;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jack on 02/01/2018.
 */
@Service
public class ProfileOtherServiceImpl implements ProfileOtherService {

    @Autowired
    private ProfileOtherDao dao;

    @Transactional
    @Override
    public List<ProfileOtherDO> addOthers(List<ProfileOtherDO> others) {
        if (others != null) {
            validateOthers(others);
            if (others.size() > 0) {
                return dao.addAllData(others);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public ProfileOtherDO addOther(ProfileOtherDO other) throws CommonException {
        ValidationMessage<ProfileOtherDO> validationMessage =  ProfileValidation.verifyOther(other);
        if (validationMessage.isPass()) {
            return dao.addData(other);
        }
        return new ProfileOtherDO();
    }

    @Override
    public int[] putOthers(List<ProfileOtherDO> others) {
        if (others != null) {
            validateOthers(others);
            if (others.size() > 0) {
                return dao.updateDatas(others);
            }
        }
        return new int[0];
    }

    @Override
    public int putOther(ProfileOtherDO other) throws CommonException {
        ValidationMessage<ProfileOtherDO> validationMessage =  ProfileValidation.verifyOther(other);
        if (validationMessage.isPass()) {
            return dao.updateData(other);
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
