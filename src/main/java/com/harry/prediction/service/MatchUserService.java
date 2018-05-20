package com.harry.prediction.service;

import com.harry.prediction.entity.MatchUser;

/**
 * 第三方平台（知乎）用户service
 */
public interface MatchUserService {

    /**
     * 根据昵称查询出用户
     * 如果有同名的，随机抽取
     * @param name  昵称
     * @return      MatchUser
     */
    MatchUser findByName(String name);

    /**
     * 查询点赞过相同问题的用户
     * @param matchUser     用户
     * @return              List<MatchUser>
     */
    MatchUser findBySameAnswerVoteUp(MatchUser matchUser);
}
