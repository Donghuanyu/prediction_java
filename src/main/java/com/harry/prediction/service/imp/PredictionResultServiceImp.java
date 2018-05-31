package com.harry.prediction.service.imp;

import com.harry.prediction.entity.PredictionResult;
import com.harry.prediction.mapper.PredictionResultMapper;
import com.harry.prediction.service.PredictionResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionResultServiceImp implements PredictionResultService {

    @Autowired
    private PredictionResultMapper predictionResultMapper;

    @Override
    public void insert(PredictionResult predictionResult) {
        predictionResultMapper.insert(predictionResult);
    }

    @Override
    public PredictionResult findById(String id) {

        if (id == null || "".equals(id))
            return null;

        return predictionResultMapper.findById(id);
    }

    @Override
    public List<PredictionResult> findByType(String type) {

        if (type == null || "".equals(type))
            return new ArrayList<>();

        List<PredictionResult> results = predictionResultMapper.findByType(type);
        if (results == null)
            return new ArrayList<>();

        return results;
    }

    @Override
    public List<PredictionResult> findByTypeAndGender(String type, String gender) {

        if (type == null || "".equals(type))
            return new ArrayList<>();
        List<PredictionResult> results = predictionResultMapper.findByTypeAndGender(type, gender);
        if (results == null)
            return new ArrayList<>();
        return results;
    }

    @Override
    public PredictionResult findByAnswerIdAndGender(String answerId, String gender) {

        if (answerId == null || "".equals(answerId))
            return null;

        List<PredictionResult> results = predictionResultMapper.findByAnswerId(answerId);
        if (results == null || results.isEmpty())
            return null;
        //只有一个结果，说明与性别无关，直接返回
        if (results.size() == 1)
            return results.get(0);
        //多个结果，说明与性别有关，匹配出与性别对应的结果返回
        for (PredictionResult item : results){
            if (gender.equals(item.getGender())){
                return item;
            }
        }

        return null;
    }
}
