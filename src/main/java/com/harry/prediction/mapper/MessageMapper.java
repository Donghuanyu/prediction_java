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
    @Insert("INSERT INTO message(id, to_user_id, from_user_id, content) VALUES " +
            "(#{id}, #{toUserId}, #{fromUserId}, #{content})")
    void insert(Message message);


    @Select("SELECT * From message where to_user_id = #{toUserId}")
    @Results({
            @Result(column = "to_user_id", property = "toUserId"),
            @Result(column = "from_user_id", property = "fromUserId")
    })
    List<Message> findByToUserId(@Param("toUserId") String toUserId);
}
