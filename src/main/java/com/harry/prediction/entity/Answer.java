package com.harry.prediction.entity;

import java.io.Serializable;

/**
 * 答案
 */
public class Answer implements Serializable {

    private String id;

    private String subjectId;

    private String title;

    private int sn;

    public Answer(String id, String subjectId, String title, int sn) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.sn = sn;
    }

    public Answer(String subjectId, String title, int sn) {
        this.subjectId = subjectId;
        this.title = title;
        this.sn = sn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }
}
