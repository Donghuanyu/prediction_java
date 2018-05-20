package com.harry.prediction.service;

import com.harry.prediction.entity.Subject;

import java.util.List;

public interface SubjectService {

    /**
     * 根据类型获取必答题目集合
     * @param type      题目类型
     * @return          List<Subject>
     */
    List<Subject> findNecessaryByType(String type);

    /**
     * 根据类型获取非必答题目集合
     * @param type      题目类型
     * @return          List<Subject>
     */
    List<Subject> findUnNecessaryByType(String type);

    /**
     * 根据类型获取答题目集合
     * @param type      题目类型
     * @return          List<Subject>
     */
    List<Subject> findByType(String type);

    /**
     * 插入数据
     * @param subject   subject
     */
    void insert(Subject subject);
}
