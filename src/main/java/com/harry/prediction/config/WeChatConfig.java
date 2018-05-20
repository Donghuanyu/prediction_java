package com.harry.prediction.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wechat")
public class WeChatConfig {

    private String appid;

    private String secret;

    private String baseUrl;

    private String openIdUrl;

    private String messageUrl;

    private String accessTokenUrl;

    private String grantType;

    private String messageId;

    private String page;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getOpenIdUrl() {
        return openIdUrl;
    }

    public void setOpenIdUrl(String openIdUrl) {
        this.openIdUrl = openIdUrl;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRequestOpenIdUrl(String code) {
        return baseUrl + openIdUrl +
                "?appid=" +
                appid +
                "&secret=" +
                secret +
                "&js_code=" +
                code +
                "&grant_type=" +
                grantType;
    }

    public String getRequestAccessTokenUrl() {
        return baseUrl + accessTokenUrl +
                "?grant_type=client_credential&appid=" +
                appid +
                "&secret=" +
                secret;
    }

    public String getRequestMessageUrl(String accessToken) {
        return baseUrl + messageUrl + "?access_token=" + accessToken;
    }
}
