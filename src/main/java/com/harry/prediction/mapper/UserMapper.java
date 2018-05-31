package com.harry.prediction.mapper;

import com.harry.prediction.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "userMapper")
public interface UserMapper {

    /**
     * 插入user
     * @param user  user
     */
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO prediction_user(id, open_id, session_key, result_id, match_id, nick_name, avatar_url, gender, city) VALUES " +
            "(#{id}, #{openId}, #{sessionKey}, #{predictionResultId}, #{matchId}, #{nickName}, #{avatarUrl}, #{gender}, #{city})")
    void insert(User user);

    @Update("UPDATE prediction_user SET session_key = #{sessionKey}, result_id = #{predictionResultId}, nick_name = #{nickName}, avatar_url= #{avatarUrl}, gender= #{gender}, city= #{city} WHERE open_id = #{openId}")
    void update(User user);

    @Update("UPDATE prediction_user SET result_id = #{predictionResultId}, nick_name = #{nickName}, avatar_url= #{avatarUrl}, gender= #{gender}, city= #{city} WHERE open_id = #{openId}")
    void updateExceptSessionKey(User user);

    @Update("UPDATE prediction_user SET match_id = #{matchId} WHERE id = #{id}")
    void updateMatchId(@Param("id") String id, @Param("matchId") String matchId);

    @Select("SELECT id, open_id, result_id, match_id, nick_name, avatar_url, gender, city FROM prediction_user WHERE id = #{id}")
    @Results({
            @Result(column = "open_id", property = "openId"),
            @Result(column = "result_id", property = "predictionResultId"),
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "nick_name", property = "nickName"),
            @Result(column = "avatar_url", property = "avatarUrl"),
    })
    User findById(@Param("id")String id);

    @Select("SELECT id, open_id, result_id, match_id, nick_name, avatar_url, gender, city FROM prediction_user WHERE open_id = #{openId}")
    @Results({
            @Result(column = "open_id", property = "openId"),
            @Result(column = "result_id", property = "predictionResultId"),
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "nick_name", property = "nickName"),
            @Result(column = "avatar_url", property = "avatarUrl"),
    })
    User findByOpenId(@Param("openId")String openId);

    @Select("SELECT id, open_id, result_id, match_id, nick_name, avatar_url, gender, city FROM prediction_user WHERE match_id = #{matchId}")
    @Results({
            @Result(column = "open_id", property = "openId"),
            @Result(column = "result_id", property = "predictionResultId"),
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "nick_name", property = "nickName"),
            @Result(column = "avatar_url", property = "avatarUrl"),
    })
    User findByMatchId(@Param("matchId")String matchId);

    /**
     * 根据答题结果和性别查询用户集合
     * @param predictionResultId    答题结果ID
     * @param gender                性别
     * @return                      用户集合
     */
    @Select("SELECT id, open_id, result_id, nick_name, avatar_url, gender, city FROM prediction_user WHERE result_id = #{predictionResultId} AND gender != #{gender} AND open_id is not null")
    @Results({
            @Result(column = "open_id", property = "openId"),
            @Result(column = "result_id", property = "predictionResultId"),
            @Result(column = "nick_name", property = "nickName"),
            @Result(column = "avatar_url", property = "avatarUrl"),
    })
    List<User> findByPredictionAndGender(@Param("predictionResultId")String predictionResultId, @Param("gender")String gender);
}
