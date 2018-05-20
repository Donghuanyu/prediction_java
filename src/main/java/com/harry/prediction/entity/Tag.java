package com.harry.prediction.entity;

import java.io.Serializable;

/**
 * 结果标签
 */
public class Tag implements Serializable {

    private String id;

    private String title;

    private String predictionResultId;

    private String value;

    private String type;

    public Tag(String id, String title, String predictionResultId, String value, String type) {
        this.id = id;
        this.title = title;
        this.predictionResultId = predictionResultId;
        this.value = value;
        this.type = type;
    }

    public Tag(String id, String title, String predictionResultId, String type) {
        this.id = id;
        this.title = title;
        this.predictionResultId = predictionResultId;
        this.type = type;
    }

    public Tag(String title, String predictionResultId, String type) {
        this.title = title;
        this.predictionResultId = predictionResultId;
        this.type = type;
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

    public String getPredictionResultId() {
        return predictionResultId;
    }

    public void setPredictionResultId(String predictionResultId) {
        this.predictionResultId = predictionResultId;
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
}
