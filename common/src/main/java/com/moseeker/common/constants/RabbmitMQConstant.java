package com.moseeker.common.constants;

public enum RabbmitMQConstant {
    APPLICATION_QUEUE_UPDATE_PROCESS("application_queue_update_process"),
    APPLICATION_QUEUE_UPDATE_PROCESS_EXCHANGE("application_queue_update_process_exchange"),
    APPLICATION_QUEUE_UPDATE_PROCESS_ROTINGKEY("application_queue_update_process_rotingkey");

    private String value;
    private RabbmitMQConstant(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
