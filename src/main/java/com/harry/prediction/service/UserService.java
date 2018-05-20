package com.harry.prediction.service;

import com.harry.prediction.entity.User;
import com.harry.prediction.vo.WeChatLogin;

import java.util.List;

public interface UserService {

    void insert(User user);

    User findById(String id);

    /**
     * 根据微信的用户唯一标识查询出用户
     * @param openId    微信用户唯一标识
     */
    User findByOpenId(String openId);

    /**
     * 根据知乎用户唯一标识查询出用户
     * @param matchId    知乎用户唯一标识
     */
    User findByMatchId(String matchId);

    /**
     * 更新用户
     * @param user user
     */
    void update(User user);

    /**
     * 如果user不存在就插入，如果存在就更新
     * @param user  user
     */
    void insertOrUpdate(User user);

    /**
     * 根据测试结果和性别，匹配到异性
     * @param predictionResultId    测试结果ID
     * @param gender                性别
     * @return                      List<User>
     */
    List<User> getUsersMatchPredictionResult(String predictionResultId, String gender);

    /**
     * 通过code获取微信用户信息
     * 微信API强制每一个code只能使用一次，再次获取微信接口API会报错
     * @param code  客户端传递过来的用户登录码
     * @return  WeChatLogin
     */
    WeChatLogin getWeChatUser(String code);

    /**
     * 更新小程序用户表中对应用户的知乎数据ID
     * @param id            小程序用户表ID
     * @param matchId       知乎用户ID
     */
    void updateMatchId(String id, String matchId);
}
