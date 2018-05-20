package com.harry.prediction.mapper;

import com.harry.prediction.entity.Answer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 答案DAO接口
 */
@Component(value = "answerMapper")
public interface AnswerMapper {

    /**
     * 根据ID查询答案
     * @param id    id
     * @return      Answer
     */
    @Select("SELECT * FROM answers WHERE id = #{id}")
    @Results({
            @Result(property = "subjectId", column = "subject_id")
    })
    Answer findById(@Param("id")String id);

    /**
     * 根据题目ID查询该题目的所有答案
     * @param subjectId     题目ID
     * @return              List<Answer>
     */
    @Select("SELECT * FROM answers WHERE subject_id = #{subjectId} ORDER BY sn")
    @Results({
            @Result(property = "subjectId", column = "subject_id")
    })
    List<Answer> findBySubjectId(@Param("subjectId")String subjectId);

    /**
     * 插入数据：
     * 1.先获取UUID，然后以UUID作为主键，插入数据
     * @param answer   answer
     */
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO answers(id, subject_id, title, sn) VALUES (#{id}, #{subjectId}, #{title}, #{sn})")
    void insert(Answer answer);
}
