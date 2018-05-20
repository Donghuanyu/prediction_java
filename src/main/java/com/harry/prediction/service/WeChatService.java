package com.harry.prediction.service;

import org.springframework.boot.configurationprocessor.json.JSONObject;

/**
 * 提供微信相关服务
 */
public interface WeChatService {

    /**
     * 微信登录，获取登录微信用户的opnId、sessionKey
     * @param code      小程序微信用户登录后获取的授权码，有时间限制，并且每个授权码只能使用一次
     * @return
     * //正常返回的JSON数据包
     * {
     *       "openid": "OPENID",
     *       "session_key": "SESSIONKEY",
     * }
     *
     * //满足UnionID返回条件时，返回的JSON数据包
     * {
     *     "openid": "OPENID",
     *     "session_key": "SESSIONKEY",
     *     "unionid": "UNIONID"
     * }
     * //错误时返回JSON数据包(示例为Code无效)
     * {
     *     "errcode": 40029,
     *     "errmsg": "invalid code"
     * }
     */
    String WeChatLogin(String code);

    /**
     * 获取获取access_token，用于发送模板消息，access_token有时效性
     * @return      成功：{"access_token": "ACCESS_TOKEN", "expires_in": 7200}
     *              失败：{"errcode": 40013, "errmsg": "invalid appid"}
     */
    String getAccessToken();

    /**
     * 发送模板消息
     * @param toUser    目标用户的openId
     * @param formId    提交留言时的表单ID
     * @param data      留言内容
     * @return          成功：{"errcode": 0, "errmsg": "ok"}
     */
    String sendMessage(String toUser, String formId, JSONObject data);
}
