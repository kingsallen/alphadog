package com.moseeker.mq.service.impl;

import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.HrWxNoticeMessageDao;
import com.moseeker.common.constants.Constant;
import com.moseeker.mq.exception.MqException;
import com.moseeker.mq.service.TemplateMsgFinder;
import com.moseeker.mq.service.message.FlexibleField;
import com.moseeker.mq.service.message.MessageBody;
import org.apache.commons.lang.StringUtils;
import org.jooq.Record11;
import org.jooq.Record12;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName TemplateMsgFinderImpl
 * @Description TODO
 * @Author jack
 * @Date 2019/3/25 1:02 PM
 * @Version 1.0
 */
@Service
public class TemplateMsgFinderImpl implements TemplateMsgFinder {

   /* flexible_fields :  [
    {
        "key": "begin",
            "name": "开始语",
            "value": "xxx",
            "editable": true
    },
    {
        "key": "end",
            "name": "结束语",
            "value": "xxx",
            "editable": true
    },
            ]





    调用的更新接口：　

            "/wechat/updateNotice",



    职位邀请通知　　＆＆　求职者：

    position_invite = lambda start, end: [
    dict(editable=True, key=u'begin', name=u'开始语', value=start),
    dict(editable=False, key=u'position', name=u'职位名称', value=u'产品经理等'),
    dict(editable=False, key=u'company', name=u'公司名称', value=u'仟寻等'),
    dict(editable=False, key=u'invite_time', name=u'邀请时间', value=u'2018-01-01'),
    dict(editable=True, key=u'end', name=u'结束语', value=end)
]

    简历完善通知：　
    resume_complete = lambda start, end: [
    dict(editable=True, key=u'begin', name=u'开始语', value=start),
    dict(editable=False, key=u'username', name=u'用户名', value=u'大舜'),
    dict(editable=False, key=u'register_time', name=u'注册时间', value=u'2018.01.01'),
    dict(editable=True, key=u'end', name=u'结束语', value=end),
]



    否则：　

            []*/

   private static final String POSITION_INVITE_TITLE = "职位邀请通知";
   private static final String SEEKER = "求职者";
   private static final String PERFECT_PROFILE_NOTICE = "简历完善通知";
   private static final List<FlexibleField> PERFECT_PROFILE_NOTICE_FLEXIBLE_FIELDS = new ArrayList<FlexibleField>(){{
       FlexibleField flexibleField = new FlexibleField();
       flexibleField.setKey("begin");
       flexibleField.setValue("开始语");
       flexibleField.setName("开始语");
   }};

    @Autowired
    public TemplateMsgFinderImpl(HrWxNoticeMessageDao noticeMessageDao,
                                 ConfigSysTemplateMessageLibraryDao messageLibraryDao) {
        this.noticeMessageDao = noticeMessageDao;
        this.messageLibraryDao = messageLibraryDao;
    }

    @Override
    public List<MessageBody> listTemplateMsg(int wechatId) throws MqException {

        List<Record12<Integer, String, String, String, String, String, String, Integer, String, String, String, Integer>> list =
                noticeMessageDao.listByWechatId(wechatId);
        if (list != null && list.size() > 0) {
            List<MessageBody> bodies = list.stream().map(record -> {
                MessageBody messageBody = new MessageBody();

                messageBody.setId(record.value1());
                messageBody.setTitle(record.value2());
                messageBody.setSendCondition(record.value3());
                messageBody.setSendTime(record.value4());
                messageBody.setSendTo(record.value5());
                messageBody.setSample(record.value6());
                messageBody.setFirst(record.value7());
                messageBody.setPriority(record.value8()!=null?record.value8().toString():"");
                messageBody.setRemark(record.value9());
                messageBody.setCustomFirst(record.value10());
                messageBody.setCustomRemark(record.value11());

                if (PERFECT_PROFILE_NOTICE.equals(messageBody.getTitle())) {

                    List<FlexibleField> flexibleFields = new ArrayList<>(4);
                    FlexibleField flexibleField = new FlexibleField();
                    flexibleField.setKey("begin");
                    flexibleField.setValue(StringUtils.isNotBlank(messageBody.getCustomFirst())?messageBody.getCustomFirst():messageBody.getFirst());
                    flexibleField.setName("开始语");
                    flexibleField.setEditable(true);
                    flexibleFields.add(flexibleField);

                    FlexibleField flexibleField1 = new FlexibleField();
                    flexibleField1.setKey("position");
                    flexibleField1.setValue("产品经理等");
                    flexibleField1.setName("职位名称");
                    flexibleField1.setEditable(false);
                    flexibleFields.add(flexibleField1);

                    FlexibleField flexibleField2 = new FlexibleField();
                    flexibleField2.setKey("company");
                    flexibleField2.setValue("仟寻等");
                    flexibleField2.setName("公司名称");
                    flexibleField2.setEditable(false);
                    flexibleFields.add(flexibleField2);

                    FlexibleField flexibleField3 = new FlexibleField();
                    flexibleField3.setKey("end");
                    flexibleField3.setValue(StringUtils.isNotBlank(messageBody.getCustomRemark())?messageBody.getCustomRemark():messageBody.getRemark());
                    flexibleField3.setName("结束语");
                    flexibleField3.setEditable(true);
                    flexibleFields.add(flexibleField3);
                    messageBody.setFlexibleFields(flexibleFields);

                } else if(POSITION_INVITE_TITLE.equals(messageBody.getTitle()) && SEEKER.equals(messageBody.getSendTo())) {

                    List<FlexibleField> flexibleFields = new ArrayList<>(4);
                    FlexibleField flexibleField = new FlexibleField();
                    flexibleField.setKey("begin");
                    flexibleField.setValue(StringUtils.isNotBlank(messageBody.getCustomFirst())?messageBody.getCustomFirst():messageBody.getFirst());
                    flexibleField.setName("开始语");
                    flexibleField.setEditable(true);
                    flexibleFields.add(flexibleField);

                    FlexibleField flexibleField1 = new FlexibleField();
                    flexibleField1.setKey("username");
                    flexibleField1.setValue("大舜");
                    flexibleField1.setName("用户名");
                    flexibleField1.setEditable(false);
                    flexibleFields.add(flexibleField1);

                    FlexibleField flexibleField2 = new FlexibleField();
                    flexibleField2.setKey("register_time");
                    flexibleField2.setValue("仟寻等");
                    flexibleField2.setName("2018.01.01");
                    flexibleField2.setEditable(false);
                    flexibleFields.add(flexibleField2);

                    FlexibleField flexibleField3 = new FlexibleField();
                    flexibleField3.setKey("end");
                    flexibleField3.setValue(StringUtils.isNotBlank(messageBody.getCustomRemark())?messageBody.getCustomRemark():messageBody.getRemark());
                    flexibleField3.setName("结束语");
                    flexibleField3.setEditable(true);
                    flexibleFields.add(flexibleField3);
                    messageBody.setFlexibleFields(flexibleFields);

                } else if(record.value12() != null && Constant.EMPLOYEE_RECOM_POSITION == record.value12()) {

                    List<FlexibleField> flexibleFields = new ArrayList<>(4);
                    FlexibleField flexibleField = new FlexibleField();
                    flexibleField.setKey("begin");
                    flexibleField.setValue(StringUtils.isNotBlank(messageBody.getCustomFirst())?messageBody.getCustomFirst():messageBody.getFirst());
                    flexibleField.setName("开始语");
                    flexibleField.setEditable(true);
                    flexibleFields.add(flexibleField);

                    FlexibleField flexibleField1 = new FlexibleField();
                    flexibleField1.setKey("title");
                    flexibleField1.setValue("产品经理");
                    flexibleField1.setName("职位名称");
                    flexibleField1.setEditable(false);
                    flexibleFields.add(flexibleField1);

                    FlexibleField flexibleField2 = new FlexibleField();
                    flexibleField2.setKey("company");
                    flexibleField2.setValue("仟寻等");
                    flexibleField2.setName("公司名称");
                    flexibleField2.setEditable(false);
                    flexibleFields.add(flexibleField2);

                    FlexibleField flexibleField4 = new FlexibleField();
                    flexibleField4.setKey("invite_time");
                    flexibleField4.setValue("仟寻等");
                    flexibleField4.setName("2018.01.01");
                    flexibleField4.setEditable(false);
                    flexibleFields.add(flexibleField4);

                    FlexibleField flexibleField3 = new FlexibleField();
                    flexibleField3.setKey("end");
                    flexibleField3.setValue(StringUtils.isNotBlank(messageBody.getCustomRemark())?messageBody.getCustomRemark():messageBody.getRemark());
                    flexibleField3.setName("结束语");
                    flexibleField3.setEditable(true);
                    flexibleFields.add(flexibleField3);
                    messageBody.setFlexibleFields(flexibleFields);

                } else  {

                    messageBody.setFlexibleFields(new ArrayList<>(0));
                }

                return messageBody;
            }).collect(Collectors.toList());
            return bodies;
        } else {
            return new ArrayList<>();
        }
    }

    private HrWxNoticeMessageDao noticeMessageDao;
    private ConfigSysTemplateMessageLibraryDao messageLibraryDao;
}
