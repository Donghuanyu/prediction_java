package com.harry.prediction.controller;

import com.harry.prediction.entity.Message;
import com.harry.prediction.entity.User;
import com.harry.prediction.entity.WeChatForm;
import com.harry.prediction.service.MessageService;
import com.harry.prediction.service.UserService;
import com.harry.prediction.service.WeChatFormService;
import com.harry.prediction.service.WeChatService;
import com.harry.prediction.util.JsonUtil;
import com.harry.prediction.vo.RequestForMessage;
import com.harry.prediction.vo.Response;
import com.harry.prediction.vo.ResponseForMessage;
import com.harry.prediction.vo.ResponseForPageMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private WeChatFormService weChatFormService;

    private static final String TO_USER = "to_user";
    private static final String FROM_USER = "from_user";
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

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

        LOG.info("留言参数：{}", JsonUtil.entity2Json(requestForMessage));

        //组装留言消息内容并发送留言消息
        User fromUser = userService.findById(requestForMessage.getMessage().getFromUserId());
        User toUser = userService.findById(requestForMessage.getMessage().getToUserId());

        if (toUser == null || fromUser == null)
            return Response.buildFailedResponse("发送消息失败");

        String leaveMsgTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());
        requestForMessage.getMessage().setTime(leaveMsgTime);

        //跳过系统内置假用户
        if (toUser.getOpenId() == null ||
                "".equals(toUser.getOpenId()) || toUser.getOpenId().startsWith("default_")){
            messageService.insert(requestForMessage.getMessage());
            LOG.error("留言目标名称：{}，ID：{}, openId：{}",
                    toUser.getNickName(), toUser.getId(), toUser.getOpenId());
            return Response.buildSuccessResponse("OK");
        }
        //先看此formId是否存在，不存在就插入数据库
        weChatFormService.insert(fromUser.getOpenId(), requestForMessage.getFormId());
        //获取toUser下的可用的formId
        WeChatForm weChatForm = weChatFormService.getAvailableWeChatForm(toUser.getOpenId());
        if (weChatForm == null) {
            LOG.error("发送消息失败，没有可用的formId，用户数据: {}",
                    JsonUtil.entity2Json(fromUser));
            return Response.buildFailedResponse(
                    Response.CODE_FAILED_MSG_NO_AVAILABLE_FORM,"抱歉，对方留言机会已经用完，改天再来试一试吧。", null);
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
            time.put("value", leaveMsgTime);
            name.put("value", fromUser.getNickName());
            note.put("value", "备注测试");
            project.put("value", "邀请你来答题测试");
            msg.put("value", requestForMessage.getMessage().getContent());
            data.put("keyword1", nickName);
            data.put("keyword2", time);
            data.put("keyword3", name);
//            data.put("keyword4", note);
            data.put("keyword5", project);
            data.put("keyword6", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String resultString = weChatService.sendMessage(
                toUser.getOpenId(), weChatForm.getFormId(), data);
        //不管成功与否，都算使用过了formId，需要更新使用次数
        weChatFormService.updateUsed(
                weChatForm.getOpenId(), weChatForm.getFormId(), weChatForm.getUsed() + 1);
        JSONObject result = null;
        try {
            result = new JSONObject(resultString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result == null || !result.has("errcode")){
            String resultMsg;
            if (result == null) {
                resultMsg = "result为空";
            }else {
                resultMsg = result.toString();
            }
            LOG.error("留言目标名称：{}, ID： {}, 留言失败：{}",
                    toUser.getNickName(), toUser.getId(), resultMsg);
            return Response.buildFailedResponse("发送消息失败");
        }

        int code;
        try {
            code = result.getInt("errcode");
        } catch (JSONException e) {
            e.printStackTrace();
            code = -1;
        }
        if (code != 0) {
            LOG.error("留言目标名称：{}, ID： {}, 留言失败：{}",
                    toUser.getNickName(), toUser.getId(), result.toString());
            return Response.buildFailedResponse("发送消息失败");
        }
        LOG.info("留言目标名称：{}, ID： {}, 留言成功：{}",
                toUser.getNickName(), toUser.getId(), result.toString());
        messageService.insert(requestForMessage.getMessage());
        return Response.buildSuccessResponse("OK");
    }

    /**
     * 获取发给userId的所有消息列表
     * @param userId    用户ID
     * @return          消息列表
     */
    @RequestMapping(value = "/getMessageList", method = RequestMethod.POST)
    public Response<List<ResponseForMessage>> getMessageList(@RequestParam("userId") String userId) {
        if (userId == null || "".equals(userId))
            return Response.buildFailedResponse("参数错误");
        List<Message> messages = messageService.findByToUserId(userId);
        if (messages == null)
            return Response.buildSuccessResponse(null);
        return Response.buildSuccessResponse(getResponseForMessageList(TO_USER, messages));
    }

    /**
     * 获取userId发出的所有消息列表
     * @param userId    用户ID
     * @return          消息列表
     */
    @RequestMapping(value = "/getFromMessageList", method = RequestMethod.POST)
    public Response<List<ResponseForMessage>> getFromMessageList(@RequestParam("userId") String userId) {
        if (userId == null || "".equals(userId))
            return Response.buildFailedResponse("参数错误");
        List<Message> messages = messageService.findByToUserId(userId);
        if (messages == null)
            return Response.buildSuccessResponse(null);

        return Response.buildSuccessResponse(getResponseForMessageList(FROM_USER, messages));
    }

    /**
     * 分页获取发给userId的消息列表
     * @param userId    用户ID
     * @return          消息列表
     */
    @RequestMapping(value = "/getMessagePageList", method = RequestMethod.POST)
    public Response<ResponseForPageMessage> getMessageList(
            @RequestParam("userId")String userId, @RequestParam(value = "page", defaultValue = "1")Integer page) {

        return getResponseForPageMessage(TO_USER, userId, page);
    }

    /**
     * 分页获取userId发送的消息列表
     * @param userId    用户ID
     * @return          消息列表
     */
    @RequestMapping(value = "/getFromMessagePageList", method = RequestMethod.POST)
    public Response<ResponseForPageMessage> getFromMessageList(
            @RequestParam("userId")String userId, @RequestParam(value = "page", defaultValue = "1")Integer page) {

        return getResponseForPageMessage(FROM_USER, userId, page);
    }

    /**
     * 分页获取留言列表数据
     * @param tag           标识获取用户发送的，还是获取用户收到的
     * @param userId        用户ID
     * @param page          页码
     * @return              ResponseForPageMessage
     */
    private Response<ResponseForPageMessage> getResponseForPageMessage(String tag, String userId, int page) {

        if (userId == null || "".equals(userId) || page < 1)
            return Response.buildFailedResponse("参数错误");

        int sum = 0;
        if (TO_USER.equals(tag)) {
            sum = messageService.countByToUserId(userId);
        } else if (FROM_USER.equals(tag)) {
            sum = messageService.countByFromUserId(userId);
        }
        if (sum <= 0){
            //没有留言信息
            ResponseForPageMessage result = new ResponseForPageMessage();
            result.setSumPage(0);
            result.setCurrentSize(0);
            result.setResponseForMessages(null);
            return Response.buildSuccessResponse(result);
        }
        //有留言信息
        double pageDouble = ((double) sum / MessageService.PAGE_SIZE);
        int sumPage = (int) Math.ceil(pageDouble);

        //如果当前请求页码大于中页码数
        if (page > sumPage)
            return Response.buildFailedResponse("参数错误");

        List<Message> messages = null;
        if (TO_USER.equals(tag)) {
            messages = messageService.findByToUserId(userId, page, MessageService.PAGE_SIZE);
        } else if (FROM_USER.equals(tag)) {
            messages = messageService.findByFromUserId(userId, page, MessageService.PAGE_SIZE);
        }

        ResponseForPageMessage result = new ResponseForPageMessage();
        result.setSumPage(sumPage);
        List<ResponseForMessage> responseForMessageList = getResponseForMessageList(tag, messages);
        result.setResponseForMessages(responseForMessageList);
        if (responseForMessageList == null || responseForMessageList.isEmpty()) {
            result.setCurrentSize(0);
        } else {
            result.setCurrentSize(responseForMessageList.size());
        }
        return Response.buildSuccessResponse(result);
    }

    private List<ResponseForMessage> getResponseForMessageList(String tag, List<Message> messages) {
        List<ResponseForMessage> result = new ArrayList<>();
        if (messages == null || messages.isEmpty()) {
            return result;
        }
        User user;
        ResponseForMessage responseForMessage;
        for (Message message: messages) {
            responseForMessage = new ResponseForMessage();
            if (TO_USER.equals(tag)) {
                user = userService.findById(message.getFromUserId());
            } else if (FROM_USER.equals(tag)) {
                user = userService.findById(message.getToUserId());
            } else {
                user = userService.findById(message.getToUserId());
            }
            responseForMessage.setUserId(user.getId());
            responseForMessage.setAvatarUrl(user.getAvatarUrl());
            responseForMessage.setOpenId(user.getOpenId());
            responseForMessage.setGender(user.getGender());
            responseForMessage.setNickName(user.getNickName());
            responseForMessage.setContent(message.getContent());
            responseForMessage.setTime(message.getTime());
            result.add(responseForMessage);
        }
        return result;
    }
}
