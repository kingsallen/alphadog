package com.moseeker.position.service.position;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrTeamDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.db.dictdb.tables.DictCityMap;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.service.position.base.PositionTransfer;
import com.moseeker.position.service.position.job51.Job51PositionTransfer;
import com.moseeker.position.service.position.liepin.LiepinPositionTransfer;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.qianxun.WorkType;
import com.moseeker.position.service.position.zhilian.ZhilianPositionTransfer;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 职位转换
 *
 * @author wjf
 */
@Service
public class PositionChangeUtil {

    Logger logger = LoggerFactory.getLogger(PositionChangeUtil.class);

    @Autowired
    private Job51PositionTransfer job51PositionTransfer;

    @Autowired
    private ZhilianPositionTransfer zhilianPositionTransfer;

    @Autowired
    private LiepinPositionTransfer liepinPositionTransfer;

    @Autowired
    private DefaultPositionTransfer defaultPositionTransfer;

    /**
     * 将仟寻职位转成第卅方职位
     *
     * @param form
     * @param positionDB
     * @return
     */
    public ThirdPartyPositionForSynchronization changeToThirdPartyPosition(ThirdPartyPosition form, JobPositionDO positionDB) {
        logger.info("changeToThirdPartyPosition---------------------");


        ThirdPartyPositionForSynchronization position = null;

        ChannelType channelType = ChannelType.instaceFromInteger(form.getChannel());

        PositionTransfer transfer=transferSimpleFactory(channelType);

        position=transfer.changeToThirdPartyPosition(form,positionDB);
        logger.info("转换结果:{}"+position);

        return position;
    }

    public PositionTransfer transferSimpleFactory(ChannelType channelType){
        PositionTransfer transfer=null;
        switch (channelType){
            case ZHILIAN:
                logger.info("进入智联转换");
                transfer=zhilianPositionTransfer;
                break;
            case JOB51:
                logger.info("进入51转换");
                transfer=job51PositionTransfer;
                break;
            case LIEPIN:
                logger.info("进入猎聘转换");
                transfer=liepinPositionTransfer;
                break;
            default:
                logger.info("默认职位转换，channelType为"+channelType.getValue());
                transfer=defaultPositionTransfer;
                break;
        }
        return transfer;
    }

    public static String convertDescription(String accounTabilities, String requirement) {
        StringBuffer descript = new StringBuffer();
        if (StringUtils.isNotNullOrEmpty(accounTabilities)) {
            StringBuffer tablities = new StringBuffer();
            if (accounTabilities.contains("\n")) {
                String results[] = accounTabilities.split("\n");
                for (String result : results) {
                    tablities.append("<p>  " + result + "</p>");
                }
            } else {
                tablities.append("<p>  " + accounTabilities + "</p>");
            }
            if (accounTabilities.contains("职位描述")) {
                descript.append(tablities.toString());
            } else {
                descript.append("<p>职位描述：</p>" + tablities.toString());
            }
        }
        if (StringUtils.isNotNullOrEmpty(requirement)) {
            StringBuffer require = new StringBuffer();
            if (requirement.contains("\n")) {
                String results1[] = requirement.split("\n");
                for (String result : results1) {
                    require.append("<p>  " + result + "</p>");
                }
            } else {
                require.append("<p>  " + requirement + "</p>");
            }
            if (requirement.contains("职位要求")) {
                descript.append(require.toString());
            } else {
                descript.append("<p>职位要求：</p>" + require.toString());
            }
        }

        return descript.toString();
    }
}
