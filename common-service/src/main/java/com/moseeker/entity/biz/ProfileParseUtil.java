package com.moseeker.entity.biz;

import com.moseeker.baseorm.dao.dictdb.DictCountryDao;
import com.moseeker.baseorm.dao.dictdb.DictIndustryTypeDao;
import com.moseeker.entity.biz.ProfileExtParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileParseUtil {

    @Autowired
    private DictCountryDao countryDao;

    @Autowired
    private DictIndustryTypeDao industryTypeDao;

    /**
     * 初始化解析简历需要的参数
     * @return
     */
    public ProfileExtParam initParseProfileParam(){
        ProfileExtParam extParam = new ProfileExtParam();
        extParam.setCountryDOList(countryDao.getAll());
        extParam.setDictIndustryTypeDOList(industryTypeDao.getAll());
        return extParam;
    }

}
