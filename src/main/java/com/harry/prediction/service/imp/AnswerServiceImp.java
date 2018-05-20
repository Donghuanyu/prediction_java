package com.harry.prediction.service.imp;

import com.harry.prediction.entity.Answer;
import com.harry.prediction.mapper.AnswerMapper;
import com.harry.prediction.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImp implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public List<Answer> findBySubjectId(String subjectId) {

        if (subjectId == null || "".equals(subjectId))
            return new ArrayList<>();

        List<Answer> answers = answerMapper.findBySubjectId(subjectId);
        if (answers == null)
            return new ArrayList<>();

        return answers;
    }

    @Override
    public void insert(Answer answer) {
        answerMapper.insert(answer);
    }
}
