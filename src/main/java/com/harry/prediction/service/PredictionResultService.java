package com.harry.prediction.service;

import com.harry.prediction.entity.PredictionResult;

import java.util.List;

public interface PredictionResultService {

    void insert(PredictionResult predictionResult);

    PredictionResult findById(String id);

    /**
     * 根据类型，查询出该类型下的所有结果
     * @param type  type
     * @return      List<PredictionResult>
     */
    List<PredictionResult> findByType(String type);

    /**
     * 根据类型，查询出对应性别该类型下的所有结果
     * @param type  type
     * @return      List<PredictionResult>
     */
    List<PredictionResult> findByTypeAndGender(String type, String gender);

    /**
     * 根据答案ID、性别查询出该类型下的所有结果
     * @param answerId  answerId
     * @param gender    性别:0\1\2
     * @return          PredictionResult
     */
    PredictionResult findByAnswerIdAndGender(String answerId, String gender);
}
