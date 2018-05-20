package com.harry.prediction.controller;

import com.harry.prediction.entity.User;
import com.harry.prediction.service.UserService;
import com.harry.prediction.vo.RequestForUserOpenId;
import com.harry.prediction.vo.Response;
import com.harry.prediction.vo.WeChatLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/prediction/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 创建用户
     * @param requestForUserOpenId  requestForUserOpenId
     * @return                      User
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public Response<User> createUser(@RequestBody RequestForUserOpenId requestForUserOpenId) {

        if (requestForUserOpenId == null ||
                "".equals(requestForUserOpenId.getCode()) ||
                requestForUserOpenId.getUser() == null)
            return Response.buildFailedResponse("参数错误");

        WeChatLogin weChatLogin = userService.getWeChatUser(requestForUserOpenId.getCode());

        if (weChatLogin == null)
            return Response.buildFailedResponse("调用微信接口失败");

        User current = userService.findByOpenId(weChatLogin.getOpenid());
        if (current == null) {
            //该用户是新用户
            current = requestForUserOpenId.getUser();
            current.setOpenId(weChatLogin.getOpenid());
            current.setSessionKey(weChatLogin.getSessionKey());
            userService.insert(current);
            //去除sessionKey
            current.setSessionKey("");
        }

        return Response.buildSuccessResponse(current);
    }
}
