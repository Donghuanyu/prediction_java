package com.harry.prediction.entity;

import java.io.Serializable;

/**
 * 小程序，用户表
 */
public class User implements Serializable {

    private String id;

    private String openId;

    private String sessionKey;

    private String predictionResultId;

    private String matchId;

    private String nickName;

    private String avatarUrl;

    private String gender;

    private String city;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public User(String id, String openId) {
        this.id = id;
        this.openId = openId;
    }

    public User(String id, String openId, String predictionResultId) {
        this.id = id;
        this.openId = openId;
        this.predictionResultId = predictionResultId;
    }

    public User(String id, String openId, String predictionResultId, String nickName) {
        this.id = id;
        this.openId = openId;
        this.predictionResultId = predictionResultId;
        this.nickName = nickName;
    }

    public User(String id, String openId, String predictionResultId, String nickName, String avatarUrl, String gender, String city) {
        this.id = id;
        this.openId = openId;
        this.predictionResultId = predictionResultId;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.city = city;
    }

    public User(String openId, String predictionResultId, String nickName, String avatarUrl, String gender, String city) {
        this.openId = openId;
        this.predictionResultId = predictionResultId;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getPredictionResultId() {
        return predictionResultId;
    }

    public void setPredictionResultId(String predictionResultId) {
        this.predictionResultId = predictionResultId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
