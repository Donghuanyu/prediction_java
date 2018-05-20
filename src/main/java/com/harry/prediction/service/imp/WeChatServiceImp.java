package com.harry.prediction.service.imp;

import com.harry.prediction.config.WeChatConfig;
import com.harry.prediction.service.WeChatService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeChatServiceImp implements WeChatService {

    @Autowired
    private OkHttpClient okHttps;

    @Autowired
    private WeChatConfig weChatConfig;

    @Override
    public String WeChatLogin(String code) {
        if (code == null || "".equals(code))
            return null;
        return requestWeChatApi(
                weChatConfig.getRequestOpenIdUrl(code), null);
    }

    @Override
    public String getAccessToken() {
        return requestWeChatApi(
                weChatConfig.getRequestAccessTokenUrl(), null);
    }

    @Override
    public String sendMessage(String toUser, String formId, JSONObject data) {

        String accessTokenResult = getAccessToken();
        if (accessTokenResult == null || "".equals(accessTokenResult))
            return null;
        JSONObject accessTokenJson = null;
        try {
            accessTokenJson = new JSONObject(accessTokenResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (accessTokenJson == null || accessTokenJson.has("errcode")){
            return null;
        }
        JSONObject requestParam = new JSONObject();
        String accessToken = null;
        try {
            accessToken = accessTokenJson.getString("access_token");
            requestParam.put("touser", toUser);
            requestParam.put("template_id", weChatConfig.getMessageId());
            requestParam.put("form_id", formId);
            requestParam.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (accessToken == null || "".equals(accessToken))
            return null;

        return requestWeChatApi(
                weChatConfig.getRequestMessageUrl(accessToken), requestParam);
    }

    private String requestWeChatApi(String url, JSONObject requestParam) {
        if (url == null || "".equals(url))
            return null;

        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (requestParam != null) {
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"), requestParam.toString());
            requestBuilder.post(body);
        }
        Request request = requestBuilder.build();
        String responseStr;
        try {
            Response response = okHttps.newCall(request).execute();
            if (response == null || response.body() == null){
                return null;
            }
            responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return responseStr;
    }
}
