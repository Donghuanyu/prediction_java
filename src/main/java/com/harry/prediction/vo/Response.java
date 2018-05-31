package com.harry.prediction.vo;

import java.io.Serializable;

/**
 * 统一返回数据格式
 */
public class Response<T> implements Serializable {

    private static final String CODE_SUCCESS = "200";
    private static final String CODE_FAILED_COMMON = "555";
    public static final String CODE_FAILED_MSG_NO_AVAILABLE_FORM = "567";

    private String code;

    private String msg;

    private T data;

    private Response() {
    }

    private Response(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T>Response buildSuccessResponse(String msg, T data){
        return new Response(CODE_SUCCESS, msg, data);
    }

    public static <T>Response<T> buildSuccessResponse(T data){
        return buildSuccessResponse("success", data);
    }

    public static <T>Response<T> buildFailedResponse(String code, String msg, T data){
        return new Response(code, msg, data);
    }

    public static <T>Response<T> buildFailedResponse(String msg, T data){
        return buildFailedResponse(CODE_FAILED_COMMON, msg, data);
    }

    public static <T>Response<T> buildFailedResponse(String msg){
        return buildFailedResponse(msg, null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
