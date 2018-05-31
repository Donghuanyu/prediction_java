package com.harry.prediction.service;

import com.harry.prediction.entity.WeChatForm;

public interface WeChatFormService {

    void insert(String openId, String formId);

    void insert(WeChatForm weChatForm);

    void updateUsed(String openId, String formId, int used);

    WeChatForm getAvailableWeChatForm(String openId);
}
