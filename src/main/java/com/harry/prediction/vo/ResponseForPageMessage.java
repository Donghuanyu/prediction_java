package com.harry.prediction.vo;

import java.util.List;

public class ResponseForPageMessage {

    /**
     * 总页数
     */
    private int sumPage;

    /**
     * 此页数量
     */
    private int currentSize;

    /**
     * 消息列表
     */
    private List<ResponseForMessage> responseForMessages;

    public int getSumPage() {
        return sumPage;
    }

    public void setSumPage(int sumPage) {
        this.sumPage = sumPage;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public List<ResponseForMessage> getResponseForMessages() {
        return responseForMessages;
    }

    public void setResponseForMessages(List<ResponseForMessage> responseForMessages) {
        this.responseForMessages = responseForMessages;
    }
}
