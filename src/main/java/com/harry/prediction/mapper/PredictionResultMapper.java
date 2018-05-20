package com.harry.prediction.mapper;


import com.harry.prediction.entity.PredictionResult;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "predictionResultMapper")
public interface PredictionResultMapper {

    /**
     * 插入数据：
     * 1.先获取UUID，然后以UUID作为主键，插入数据
     * @param predictionResult   predictionResult
     */
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO result(id, answer_id, value, type, gender) VALUES (#{id}, #{answerId}, #{value}, #{type}, #{gender})")
    void insert(PredictionResult predictionResult);

    /**
     * 根据类型查询出该类型下的所有测试结果
     * @param type      type
     * @return          List<PredictionResult>
     */
    @Select("SELECT * FROM result WHERE type = #{type}")
    @Results({
            @Result(property = "answerId", column = "answer_id")
    })
    List<PredictionResult> findByType(@Param("type") String type);

    /**
     * 根据题目ID查询出测试结果有几个
     * 1.结果数量大于1，说明与性别有关，需要再根据性别去除对应的结果
     * @param answerId      answerId
     * @return              List<PredictionResult>
     */
    @Select("COUNT (*) FROM result WHERE answer_id = #{answerId}")
    int countByAnswerId(@Param("answerId") String answerId);

    /**
     * 根据题目ID查询出测试结果（数目不确定）
     * @param answerId      answerId
     * @return              List<PredictionResult>
     */
    @Select("SELECT * FROM result WHERE answer_id = #{answerId}")
    @Results({
            @Result(property = "answerId", column = "answer_id")
    })
    List<PredictionResult> findByAnswerId(@Param("answerId")String answerId);

    /**
     * 根据题目ID、性别查询出测试结果
     * 与性别有关
     * @param answerId      answerId
     * @param gender        性别:0\1\2
     * @return              PredictionResult
     */
    @Select("SELECT * FROM result WHERE answer_id = #{answerId} AND gender = #{gender}")
    @Results({
            @Result(property = "answerId", column = "answer_id")
    })
    PredictionResult findByAnswerIdAndGender(@Param("answerId") String answerId, @Param("gender")String gender);

    /**
     * 根据类型、性别查询出测试结果
     * 与性别有关
     * @param type          type
     * @param gender        性别:0\1\2
     * @return              PredictionResult
     */
    @Select("SELECT * FROM result WHERE type = #{type} AND gender != #{gender}")
    @Results({
            @Result(property = "answerId", column = "answer_id")
    })
    List<PredictionResult> findByTypeAndGender(@Param("type") String type, @Param("gender")String gender);


}
