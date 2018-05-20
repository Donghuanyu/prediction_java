package com.harry.prediction.service.imp;

import com.harry.prediction.constant.SubjectConstant;
import com.harry.prediction.entity.Subject;
import com.harry.prediction.mapper.AnswerMapper;
import com.harry.prediction.mapper.SubjectMapper;
import com.harry.prediction.service.SubjectService;
import com.harry.prediction.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImp implements SubjectService {


    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public List<Subject> findNecessaryByType(String type) {

        if (type == null || "".equals(type))
            return new ArrayList<>();

        return subjectMapper.findNecessaryByType(type);
    }

    @Override
    public List<Subject> findUnNecessaryByType(String type) {

        if (type == null || "".equals(type))
            return new ArrayList<>();

        return subjectMapper.findUnNecessaryByType(type);
    }

    @Override
    public List<Subject> findByType(String type) {

        if (type == null || "".equals(type))
            return new ArrayList<>();

        //拿到必答题
        List<Subject> subjects = subjectMapper.findNecessaryByType(type);

        if (subjects == null)
            return new ArrayList<>();

        //计算去除必答题后需要随机抽取的题目
        int letSubjectNum = SubjectConstant.SUBJECT_SUM - subjects.size();
        //获取所有非必答题，随机抽取剩余数量的题目
        List<Subject> lestSubjects = subjectMapper.findUnNecessaryByType(type);
        if (lestSubjects == null || lestSubjects.isEmpty())
            return subjects;
        if (letSubjectNum >= lestSubjects.size()){
            subjects.addAll(lestSubjects);
            return subjects;
        }
        int[] leftCodes = CommonUtil.randomCommon(0, lestSubjects.size(), letSubjectNum);
        if (leftCodes == null)
            return subjects;
        for (int leftCode : leftCodes) {
            subjects.add(lestSubjects.get(leftCode));
        }
        return subjects;
    }

    /**
     * 插入数据
     * @param subject   subject
     */
    @Override
    public void insert(Subject subject) {
        subjectMapper.insert(subject);
    }
}
