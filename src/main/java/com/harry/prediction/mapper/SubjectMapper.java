package com.harry.prediction.mapper;

import com.harry.prediction.constant.SubjectConstant;
import com.harry.prediction.entity.Subject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 题目DAO接口
 */
@Component(value = "subjectMapper")
public interface SubjectMapper {

    /**
     * 根据ID获取题目
     * @param id    题目ID
     * @return      Subject
     */
    @Select("SELECT * FROM subject WHERE id = #{id}")
    Subject findById(@Param("id") String id);

    /**
     * 根据类型获取必答题目集合
     * @param type      题目类型
     * @return          List<Subject>
     */
    @Select("SELECT * FROM subject WHERE type = #{type} AND necessary = \"" + SubjectConstant.NECESSARY_TRUE + "\"")
    List<Subject> findNecessaryByType(@Param("type") String type);

    /**
     * 根据类型获取非必答题目集合
     * @param type      题目类型
     * @return          List<Subject>
     */
    @Select("SELECT * FROM subject WHERE type = #{type} AND necessary = \"" + SubjectConstant.NECESSARY_FALSE + "\"")
    List<Subject> findUnNecessaryByType(@Param("type") String type);


    /**
     * 插入数据：
     * 1.先获取UUID，然后以UUID作为主键，插入数据
     * @param subject   subject
     */
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO subject(id, title, necessary, type) VALUES (#{id}, #{title}, #{necessary}, #{type})")
    void insert(Subject subject);

}
