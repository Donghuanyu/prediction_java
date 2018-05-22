package com.harry.prediction.mapper;

import com.harry.prediction.entity.MatchUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 第三方平台（知乎）用户DAO
 */
@Component(value = "matchUserMapper")
public interface MatchUserMapper {

    /**
     * 根据ID查询出对应的用户信息
     * @param id        ID
     * @return          List<MatchUser>
     */
    @Select("SELECT id, name, avatar_url, gender, business, employments, answer_vote_up" +
            " FROM user WHERE id = #{id}")
    @Results({
            @Result(column = "avatar_url", property = "avatarUrl"),
            @Result(column = "answer_vote_up", property = "answerVoteUp")
    })
    MatchUser findById(@Param("id")String id);

    /**
     * 查询用户个数
     * @return      用户信息个数
     */
    @Select("SELECT COUNT(id) FROM user")
    int countAll();

    /**
     * 根据昵称查询用户信息个数
     * @param name  昵称
     * @return      用户信息个数
     */
    @Select("SELECT COUNT(name) FROM user WHERE name = #{name}")
    int countByName(@Param("name")String name);

    /**
     * 根据答案查询点赞过相同问题的异性用户信息个数
     * @param answer    答案
     * @return          用户信息个数
     */
    @Select("SELECT COUNT(id) FROM user WHERE MATCH (answer_vote_up) AGAINST " +
            "(\"${answer}\" IN NATURAL LANGUAGE MODE) AND gender = #{gender}")
    int countByAnswerAndGender(@Param("answer")String answer, @Param("gender")String gender);

    /**
     * 根据答案查询点赞过相同问题的用户信息个数
     * @param answer    答案
     * @return          用户信息个数
     */
    @Select("SELECT COUNT(id) FROM user WHERE MATCH (answer_vote_up) AGAINST " +
            "(\"${answer}\" IN NATURAL LANGUAGE MODE)")
    int countByAnswer(@Param("answer")String answer);

    /**
     * 根据昵称查询用户（昵称不重复）
     * @return
     */
    @Select("SELECT * FROM user WHERE name = #{name}")
    @Results({
            @Result(column = "url_token", property = "token"),
            @Result(column = "avatar_url", property = "avatarUrl"),
            @Result(column = "answer_create", property = "answerCreate"),
            @Result(column = "question_follow", property = "questionFollow"),
            @Result(column = "answer_vote_up", property = "answerVoteUp"),
            @Result(column = "answer_vote_up_answer", property = "answerVoteUpAnswer"),
            @Result(column = "following_count", property = "followingCount", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "follower_count", property = "followerCount", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "answer_count", property = "answerCount", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "articles_count", property = "articleCount", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
    })
    MatchUser findByName(@Param("name") String name);

    /**
     * 根据昵称查询用户（昵称重复，取第N条）
     * @return  MatchUser
     */
    @Select("SELECT * FROM user WHERE name = #{name} LIMIT #{start}, 1")
    @Results({
            @Result(column = "url_token", property = "token"),
            @Result(column = "avatar_url", property = "avatarUrl"),
            @Result(column = "answer_create", property = "answerCreate"),
            @Result(column = "question_follow", property = "questionFollow"),
            @Result(column = "answer_vote_up", property = "answerVoteUp"),
            @Result(column = "answer_vote_up_answer", property = "answerVoteUpAnswer"),
            @Result(column = "following_count", property = "followingCount", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "follower_count", property = "followerCount", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "answer_count", property = "answerCount", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "articles_count", property = "articleCount", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
    })
    MatchUser findByNameRand(@Param("name") String name, @Param("start")int start);

    /**
     * 随机获取用户
     * @return  MatchUser
     */
    @Select("SELECT id, name, avatar_url, gender, business, employments FROM user LIMIT #{start}, 1")
    @Results({
            @Result(column = "avatar_url", property = "avatarUrl")
    })
    MatchUser findByRand(@Param("start")int start);

    /**
     * 随机获取异性用户
     * @return  MatchUser
     */
    @Select("SELECT id, name, avatar_url, gender, business, employments FROM user WHERE gender = #{gender} LIMIT #{start}, 1")
    @Results({
            @Result(column = "avatar_url", property = "avatarUrl")
    })
    MatchUser findByRandAndGender(@Param("gender")String gender, @Param("start")int start);

    /**
     * 匹配点赞过相同问题的异性
     * @param answer    问题
     * @return          List<MatchUser>
     */
    @Select("SELECT id, name, avatar_url, gender, business, employments " +
            "FROM user WHERE MATCH (answer_vote_up) " +
            "AGAINST (\"${answer}\" IN NATURAL LANGUAGE MODE) LIMIT #{start}, 1")
    @Results({
            @Result(column = "avatar_url", property = "avatarUrl")
    })
    MatchUser findBySameAnswerVoteUp(@Param("answer") String answer, @Param("start") int start);

    /**
     * 匹配点赞过相同问题的异性
     * @param answer    问题
     * @return          List<MatchUser>
     */
    @Select("SELECT id, name, avatar_url, gender, business, employments " +
            "FROM user WHERE MATCH (answer_vote_up) " +
            "AGAINST (\"${answer}\" IN NATURAL LANGUAGE MODE) AND gender = #{gender} LIMIT #{start}, 1")
    @Results({
            @Result(column = "avatar_url", property = "avatarUrl")
    })
    MatchUser findBySameAnswerVoteUpAndGender(@Param("answer") String answer, @Param("gender")String gender, @Param("start") int start);
}
