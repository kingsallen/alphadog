package com.moseeker.mq.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.HrWxNoticeMessageDao;
import com.moseeker.baseorm.pojo.HrWxNoticeMessagePojo;
import com.moseeker.common.constants.Constant;
import com.moseeker.mq.exception.MqException;
import com.moseeker.mq.service.TemplateMsgFinder;
import com.moseeker.thrift.gen.mq.struct.FlexibleField;
import com.moseeker.thrift.gen.mq.struct.MessageBody;
import com.moseeker.thrift.gen.mq.struct.WxMessageFrequency;
import com.moseeker.thrift.gen.mq.struct.WxMessageFrequencyOption;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

        List<HrWxNoticeMessagePojo> list = noticeMessageDao.listByWechatId(wechatId);
        if (list != null && list.size() > 0) {
            List<MessageBody> bodies = list.stream().map(record -> {
                MessageBody messageBody = new MessageBody();
                BeanUtils.copyProperties(record,messageBody);

                if(StringUtils.isNotBlank(record.getFrequencyOptions())){
                    WxMessageFrequency interval = JSONObject.parseObject(record.getFrequencyOptions(),WxMessageFrequency.class);
                    if(StringUtils.isNotBlank(record.getFrequencyValue()) && interval.getOptions().stream()
                            .filter(item-> Objects.equals(record.getFrequencyValue(),item.getValue())).count() > 0){
                        interval.setValue(record.getFrequencyValue());
                    }else{
                        interval.setValue(interval.getDefaultValue());
                    }
                    Optional<WxMessageFrequencyOption> selectedOption = interval.getOptions().stream().filter(
                            option -> Objects.equals(option.getValue(), interval.getValue())).findFirst();
                    if(selectedOption != null && selectedOption.isPresent()){
                        messageBody.setSendTime(selectedOption.get().getSend_time());
                    }
                    messageBody.setSendFrequency(interval);
                }

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

                } else if(record.getConfigId() != null && Constant.EMPLOYEE_RECOM_POSITION == record.getConfigId()) {

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
