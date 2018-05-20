package com.harry.prediction.vo;

import com.harry.prediction.entity.User;

import java.io.Serializable;

public class RequestForUserOpenId implements Serializable {

    private String code;

    private User user;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
