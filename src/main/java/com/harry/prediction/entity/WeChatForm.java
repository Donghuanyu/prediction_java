package com.harry.prediction.entity;

import java.util.Date;

/**
 * 微信表单
 * 用于存储小程序formId，便于多次发送模板消息
 */
public class WeChatForm {

    /**
     * 微信用户标识
     */
    private String openId;

    /**
     * 小程序表单ID
     */
    private String formId;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 使用次数：
     * 0-----未使用
     */
    private int used;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Date getCreateTime() {
        return addTime;
    }

    public void setCreateTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
