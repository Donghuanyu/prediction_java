package com.harry.prediction.controller;

import com.harry.prediction.entity.Message;
import com.harry.prediction.entity.User;
import com.harry.prediction.service.MessageService;
import com.harry.prediction.service.UserService;
import com.harry.prediction.service.WeChatService;
import com.harry.prediction.vo.RequestForMessage;
import com.harry.prediction.vo.Response;
import com.harry.prediction.vo.ResponseForMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/prediction/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private WeChatService weChatService;

    @RequestMapping(value = "/leaveMessage", method = RequestMethod.POST)
    public Response<String> leaveMessage(@RequestBody RequestForMessage requestForMessage) {

        if (requestForMessage == null ||
                requestForMessage.getMessage() == null ||
                requestForMessage.getFormId() == null)
            return Response.buildFailedResponse("参数错误");
        if (requestForMessage.getMessage().getToUserId() == null ||
                "".equals(requestForMessage.getMessage().getToUserId()))
            return Response.buildFailedResponse("没有留言对象");
        if (requestForMessage.getMessage().getFromUserId() == null ||
                "".equals(requestForMessage.getMessage().getFromUserId()))
            return Response.buildFailedResponse("没有留言者");
        if (requestForMessage.getMessage().getContent() == null ||
                "".equals(requestForMessage.getMessage().getContent()))
            return Response.buildFailedResponse("没有留言内容");
        //组装留言消息内容并发送留言消息
        User fromUser = userService.findById(requestForMessage.getMessage().getFromUserId());
        User toUser = userService.findById(requestForMessage.getMessage().getToUserId());

        if (toUser == null || fromUser == null)
            return Response.buildFailedResponse("发送消息失败");

        //跳过系统内置假用户
        if (toUser.getOpenId() == null ||
                "".equals(toUser.getOpenId()) || toUser.getOpenId().startsWith("default_")){
            messageService.insert(requestForMessage.getMessage());
            return Response.buildSuccessResponse("OK");
        }

        JSONObject data = new JSONObject();
        JSONObject nickName = new JSONObject();
        JSONObject time = new JSONObject();
        JSONObject name = new JSONObject();
        JSONObject note = new JSONObject();
        JSONObject project = new JSONObject();
        JSONObject msg = new JSONObject();
        try {
            nickName.put("value", fromUser.getNickName());
            time.put("value",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new Date()));
            name.put("value", fromUser.getNickName());
            note.put("value", "备注测试");
            project.put("value", "项目测试");
            msg.put("value", requestForMessage.getMessage().getContent());
            data.put("keyword1", nickName);
            data.put("keyword2", time);
            data.put("keyword3", name);
//            data.put("keyword4", note);
//            data.put("keyword5", project);
            data.put("keyword6", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String resultString = weChatService.sendMessage(
                toUser.getOpenId(), requestForMessage.getFormId(), data);
        JSONObject result = null;
        try {
            result = new JSONObject(resultString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result == null || !result.has("errcode"))
            return Response.buildFailedResponse("发送消息失败");
        int code;
        try {
            code = result.getInt("errcode");
        } catch (JSONException e) {
            e.printStackTrace();
            code = -1;
        }
        if (code != 0)
            return Response.buildFailedResponse("发送消息失败");

        messageService.insert(requestForMessage.getMessage());
        return Response.buildSuccessResponse("OK");
    }

    @RequestMapping(value = "/getMessageList", method = RequestMethod.POST)
    public Response<List<ResponseForMessage>> getMessageList(@RequestParam("userId") String userId) {
        if (userId == null || "".equals(userId))
            return Response.buildFailedResponse("参数错误");
        List<Message> messages = messageService.findByToUserId(userId);
        if (messages == null)
            return Response.buildSuccessResponse(null);
        List<ResponseForMessage> result = new ArrayList<>();
        User user;
        ResponseForMessage responseForMessage;
        for (Message message: messages) {
            responseForMessage = new ResponseForMessage();
            user = userService.findById(message.getFromUserId());
            responseForMessage.setUserId(user.getId());
            responseForMessage.setAvatarUrl(user.getAvatarUrl());
            responseForMessage.setOpenId(user.getOpenId());
            responseForMessage.setGender(user.getGender());
            responseForMessage.setNickName(user.getNickName());
            responseForMessage.setContent(message.getContent());
            result.add(responseForMessage);
        }
        return Response.buildSuccessResponse(result);
    }
}
