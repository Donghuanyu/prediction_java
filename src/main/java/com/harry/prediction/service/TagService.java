package com.harry.prediction.service;

import com.harry.prediction.entity.Tag;

import java.util.List;

public interface TagService {

    void insert(Tag tag);

    /**
     * 根据类型，查询出该类型下的所有标签
     * @param type  type
     * @return      List<Tag>
     */
    List<Tag> findByType(String type);

    /**
     * 根据测试结果ID查询出对应标签
     * @param predictionResultId      测试结果ID
     * @return                        Tag
     */
    Tag findByPredictionResult(String predictionResultId);
}
