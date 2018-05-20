package com.harry.prediction.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 题目
 */
public class Subject implements Serializable {

    private String id;

    private String title;

    private String type;

    private String necessary;

    private List<Answer> answers;

    public Subject(String id, String title, String type, String necessary) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.necessary = necessary;
    }

    public Subject(String id, String title, String type, String necessary, List<Answer> answers) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.necessary = necessary;
        this.answers = answers;
    }

    public Subject(String title, String type, String necessary) {
        this.title = title;
        this.type = type;
        this.necessary = necessary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNecessary() {
        return necessary;
    }

    public void setNecessary(String necessary) {
        this.necessary = necessary;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
