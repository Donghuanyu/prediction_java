package com.harry.prediction.mapper;

import com.harry.prediction.entity.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "messageMapper")
public interface MessageMapper {

    @SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO message(id, to_user_id, from_user_id, content, time) VALUES " +
            "(#{id}, #{toUserId}, #{fromUserId}, #{content}, #{time})")
    void insert(Message message);


    @Select("SELECT * From message where to_user_id = #{toUserId} order by time DESC")
    @Results({
            @Result(column = "to_user_id", property = "toUserId"),
            @Result(column = "from_user_id", property = "fromUserId")
    })
    List<Message> findByToUserId(@Param("toUserId")String toUserId);

    @Select("SELECT * From message where from_user_id = #{fromUserId} order by time DESC")
    @Results({
            @Result(column = "to_user_id", property = "toUserId"),
            @Result(column = "from_user_id", property = "fromUserId")
    })
    List<Message> findByFromUserId(@Param("fromUserId")String fromUserId);

    @Select("SELECT COUNT(id) From message where to_user_id = #{toUserId}")
    int countByToUserId(@Param("toUserId")String toUserId);

    @Select("SELECT COUNT(id) From message where from_user_id = #{fromUserId}")
    int countByFromUserId(@Param("fromUserId")String fromUserId);

    @Select("SELECT * From message where to_user_id = #{toUserId} order by time DESC LIMIT #{start}, #{end}")
    @Results({
            @Result(column = "to_user_id", property = "toUserId"),
            @Result(column = "from_user_id", property = "fromUserId")
    })
    List<Message> findForPageByToUserId(@Param("toUserId")String toUserId, @Param("start")int start, @Param("end")int end);

    @Select("SELECT * From message where from_user_id = #{fromUserId} order by time DESC LIMIT #{start}, #{end}")
    @Results({
            @Result(column = "to_user_id", property = "toUserId"),
            @Result(column = "from_user_id", property = "fromUserId")
    })
    List<Message> findForPageByFromUserId(@Param("fromUserId")String fromUserId, @Param("start")int start, @Param("end")int end);
}
