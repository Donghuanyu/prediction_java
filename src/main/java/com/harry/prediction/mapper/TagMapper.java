package com.harry.prediction.mapper;

import com.harry.prediction.entity.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "tagMapper")
public interface TagMapper {

    /**
     * 插入数据：
     * 1.先获取UUID，然后以UUID作为主键，插入数据
     * @param tag   tag
     */
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO tag(id, prediction_result_id, title, type) VALUES (#{id}, #{predictionResultId}, #{title}, #{type})")
    void insert(Tag tag);

    /**
     * 根据类型查询出该类型下的所有标签
     * @param type      type
     * @return          List<Tag>
     */
    @Select("SELECT * FROM tag WHERE type = #{type}")
    @Results({
            @Result(property = "predictionResultId", column = "prediction_result_id")
    })
    List<Tag> findByType(@Param("type") String type);

    /**
     * 根据测试结果ID查询出对应标签
     * @param predictionResultId      测试结果ID
     * @return                        Tag
     */
    @Select("SELECT * FROM tag WHERE prediction_result_id = #{predictionResultId}")
    @Results({
            @Result(property = "predictionResultId", column = "prediction_result_id")
    })
    Tag findByPredictionResult(@Param("predictionResultId")String predictionResultId);
}
