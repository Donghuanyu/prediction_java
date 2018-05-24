package com.harry.prediction.service.imp;

import com.harry.prediction.entity.MatchUser;
import com.harry.prediction.mapper.MatchUserMapper;
import com.harry.prediction.service.MatchUserService;
import com.harry.prediction.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchUserServiceImp implements MatchUserService {

    @Autowired
    private MatchUserMapper matchUserMapper;

    @Override
    public MatchUser findById(String id) {
        if (null == id || "".equals(id))
            return null;

        return matchUserMapper.findById(id);
    }

    @Override
    public MatchUser findByName(String name) {

        if (null == name || "".equals(name))
            return null;

        int num = matchUserMapper.countByName(name);
        if (num == 0){
            return null;
        }else if (num == 1) {
            return matchUserMapper.findByName(name);
        }
        //如果有昵称重复的，就随机选
        int[] index = CommonUtil.randomCommon(1, num, 1);
        int start;
        if (index == null || index.length == 0){
            start = 0;
        }else {
            start = index[0] - 1;
        }
        return matchUserMapper.findByNameRand(name, start);
    }

    @Override
    public MatchUser findBySameAnswerVoteUp(MatchUser matchUser) {

        if (null == matchUser)
            return null;

        if (null == matchUser.getAnswerVoteUp() || "".equals(matchUser.getAnswerVoteUp()))
            return null;

        //循环所有答案去匹配，如果有匹配到就返回，没有匹配到就返回Null
        MatchUser result;
        String[] answers = matchUser.getAnswerVoteUp().split("\\|");
        for (String answer : answers) {
            result = matchUserByAnswer(answer, matchUser.getGender());
            if (result != null) {
                result.setAnswerVoteUp(answer);
                return result;
            }
        }
        return null;
    }

    /**
     * 根据点赞的问题、性别匹配点赞过相同问题的异性用户
     * 1.性别是未知就忽略性别
     * 2.匹配出点赞过相同问题的用户
     * 3.如果没有，就随机返回一个异性用户
     * 4.匹配用户大于1个，就随机返回一个
     * @param answer    答案
     * @param gender    性别
     * @return          匹配异性用户
     */
    private MatchUser matchUserByAnswer(String answer, String gender) {

        int count;
        if ("未知".equals(gender)) {
            count = matchUserMapper.countByAnswer(answer);
        } else if ("男".equals(gender)) {
            count = matchUserMapper.countByAnswerAndGender(answer, "女");
        }else {
            count = matchUserMapper.countByAnswerAndGender(answer, "男");
        }
        //没有匹配的用户
        if (count == 0){
            return null;
        }
        int start = 1;
        int[] index = CommonUtil.randomCommon(1, count, 1);
        if (index != null) {
            start = index[0];
        }
        return matchUserRandAndGender(answer, gender, start);
    }

    private MatchUser matchUserRandAndGender(String answer, String gender, int start) {

        if (start <= 0){
            start = 1;
        }
        if (gender == null || "".equals(gender)) {
            gender = "未知";
        }
        if ("未知".equals(gender)) {
            return matchUserMapper.findBySameAnswerVoteUp(answer, start);
        } else if ("男".equals(gender)) {
            return matchUserMapper.findBySameAnswerVoteUpAndGender(answer, "女", start);
        }else {
            return matchUserMapper.findBySameAnswerVoteUpAndGender(answer, "男", start);
        }

    }
}
