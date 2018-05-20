package com.harry.prediction.vo;

import com.harry.prediction.entity.MatchUser;
import com.harry.prediction.entity.User;

import java.io.Serializable;

public class ResultForMatch implements Serializable {

    private MatchUser matchUser;

    private User user;

    public MatchUser getMatchUser() {
        return matchUser;
    }

    public void setMatchUser(MatchUser matchUser) {
        this.matchUser = matchUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
