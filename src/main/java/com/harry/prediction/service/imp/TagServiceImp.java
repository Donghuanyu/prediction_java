package com.harry.prediction.service.imp;

import com.harry.prediction.entity.Tag;
import com.harry.prediction.mapper.TagMapper;
import com.harry.prediction.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImp implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public void insert(Tag tag) {
        tagMapper.insert(tag);
    }

    @Override
    public List<Tag> findByType(String type) {

        if (type == null || "".equals(type))
            return new ArrayList<>();

        List<Tag> tags = tagMapper.findByType(type);
        if (tags == null)
            return new ArrayList<>();

        return tags;
    }

    @Override
    public Tag findByPredictionResult(String predictionResultId) {

        if (predictionResultId == null || "".equals(predictionResultId))
            return null;
        return tagMapper.findByPredictionResult(predictionResultId);
    }
}
