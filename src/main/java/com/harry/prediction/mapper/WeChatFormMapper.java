package com.harry.prediction.mapper;

import com.harry.prediction.entity.WeChatForm;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "weChatFormMapper")
public interface WeChatFormMapper {

    @Insert("INSERT INTO wechat_form_id(open_id, form_id, add_time, edit_time, used) VALUES " +
            "(#{openId}, #{formId}, #{addTime}, #{editTime}, #{used})")
    void insert(WeChatForm weChatForm);

    @Update("UPDATE wechat_form_id SET used = #{used} WHERE open_id = #{openId} AND form_id = #{formId}")
    void updateUsed(@Param("openId")String openId, @Param("formId")String formId, @Param("used")int used);

    @Select("SELECT open_id, form_id, add_time, edit_time, used FROM wechat_form_id WHERE open_id = #{openId}")
    @Results({
        @Result(column = "open_id", property = "openId"),
        @Result(column = "form_id", property = "formId"),
        @Result(column = "add_time", property = "addTime"),
        @Result(column = "edit_time", property = "editTime")
    })
    List<WeChatForm> findByOpenId(@Param("openId")String openId);

    @Select("SELECT open_id, form_id, add_time, edit_time, used FROM wechat_form_id WHERE open_id = #{openId} AND form_id = #{formId}")
    @Results({
            @Result(column = "open_id", property = "openId"),
            @Result(column = "form_id", property = "formId"),
            @Result(column = "add_time", property = "addTime"),
            @Result(column = "edit_time", property = "editTime")
    })
    WeChatForm findByOpenIdAndFormId(@Param("openId")String openId, @Param("formId")String formId);
}
