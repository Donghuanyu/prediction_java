package com.harry.prediction.service;

import com.harry.prediction.entity.Answer;

import java.util.List;

public interface AnswerService {

    /**
     * 根据题目ID查询该题目的所有答案
     * @param subjectId     题目ID
     * @return              List<Answer>
     */
    List<Answer> findBySubjectId(String subjectId);

    /**
     * 插入数据
     * @param answer answer
     */
    void insert(Answer answer);
}
