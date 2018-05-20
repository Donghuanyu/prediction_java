package com.harry.prediction.vo;

import com.harry.prediction.entity.Message;

import java.io.Serializable;

public class RequestForMessage implements Serializable {

    /**
     * 微信小程序需要发送模板消息的form表单中提交事件的formId
     */
    private String formId;

    private Message message;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
