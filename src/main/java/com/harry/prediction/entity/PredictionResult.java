package com.harry.prediction.entity;

import java.util.List;

/**
 * 答案对应的测试结果
 * 只有必答题才有结果
 */
public class PredictionResult {

    private String id;

    private String answerId;

    private String value;

    private String type;

    private String gender;

    private List<Tag> tags;

    private List<User> users;

    public PredictionResult(String id, String answerId, String value, String type, String gender) {
        this.id = id;
        this.answerId = answerId;
        this.value = value;
        this.type = type;
        this.gender = gender;
    }

    public PredictionResult(String answerId, String value, String type, String gender) {
        this.answerId = answerId;
        this.value = value;
        this.type = type;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
