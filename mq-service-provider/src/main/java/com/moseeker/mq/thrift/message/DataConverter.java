package com.moseeker.mq.thrift.message;

import com.moseeker.mq.service.message.MessageBody;
import com.moseeker.thrift.gen.mq.struct.FlexibleField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DataConverter
 * @Description TODO
 * @Author jack
 * @Date 2019/3/25 12:48 PM
 * @Version 1.0
 */
public class DataConverter {

    /**
     * pojo convert to struct
     * //todo
     * @param messageBodys
     * @return
     */
    public static List<com.moseeker.thrift.gen.mq.struct.MessageBody> convertMessageBody(List<MessageBody> messageBodys) {

        if (messageBodys != null && messageBodys.size() > 0) {
            return messageBodys
                    .stream()
                    .map(messageBody -> {
                        com.moseeker.thrift.gen.mq.struct.MessageBody mb = new com.moseeker.thrift.gen.mq.struct.MessageBody();

                        mb.setId(messageBody.getId());
                        mb.setCustomFirst(messageBody.getCustomFirst());
                        mb.setCustomRemark(messageBody.getCustomRemark());
                        mb.setFirst(messageBody.getFirst());
                        mb.setPriority(messageBody.getPriority());
                        mb.setRemark(messageBody.getRemark());
                        mb.setSample(messageBody.getSample());
                        mb.setSendCondition(messageBody.getSendCondition());
                        mb.setSendTime(messageBody.getSendTime());
                        mb.setSendTo(messageBody.getSendTo());
                        mb.setStatus(messageBody.getStatus());
                        mb.setTitle(messageBody.getTitle());

                        if (messageBody.getFlexibleFields() != null && messageBody.getFlexibleFields().size() > 0) {
                            mb.setFlexibleFields(messageBody.getFlexibleFields()
                            .stream()
                            .map(flexibleField -> {
                                FlexibleField flexibleFieldStruct = new FlexibleField();
                                flexibleFieldStruct.setEditable(flexibleField.isEditable());
                                flexibleFieldStruct.setKey(flexibleField.getKey());
                                flexibleFieldStruct.setName(flexibleField.getName());
                                flexibleFieldStruct.setValue(flexibleField.getValue());

                                return flexibleFieldStruct;
                            })
                            .collect(Collectors.toList()));

                        }

                        return mb;
                    })
                    .collect(Collectors.toList());

        } else {
            return new ArrayList<>(0);
        }
    }
}
