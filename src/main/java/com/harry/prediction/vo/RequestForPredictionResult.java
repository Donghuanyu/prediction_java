package com.harry.prediction.vo;

import com.harry.prediction.entity.User;

import java.io.Serializable;
import java.util.List;

public class RequestForPredictionResult implements Serializable {

    private String type;
    private List<String> answerIds;
    private User user;

    public RequestForPredictionResult() {
    }

    public RequestForPredictionResult(String type, List<String> answerIds, User user) {
        this.type = type;
        this.answerIds = answerIds;
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAnswerIds() {
        return answerIds;
    }

    public void setAnswerIds(List<String> answerIds) {
        this.answerIds = answerIds;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
