package com.harry.prediction.service.imp;

import com.harry.prediction.entity.MatchUser;
import com.harry.prediction.mapper.MatchUserMapper;
import com.harry.prediction.service.MatchUserService;
import com.harry.prediction.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        String[] answers = matchUser.getAnswerVoteUp().split("\\|");
        String answer;
        if (answers.length == 1){
            //只点赞了一个问题，该问题能匹配到几个用户就返回几个用户
            answer = answers[0];
        }else {
            //有多个答案，就随机抽取一个题目去匹配
            int[] index = CommonUtil.randomCommon(0, answers.length, 1);
            if (index == null) {
                answer = answers[0];
            }else {
                answer = answers[index[0]];
            }
        }

        return matchUserByAnswer(answer, matchUser.getGender());
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
        //没有匹配的用户，随机返回一个用户
        if (count == 0){
            count = matchUserMapper.countAll();
        }
        int start = 1;
        int[] index = CommonUtil.randomCommon(1, count, 1);
        if (index != null) {
            start = index[0];
        }
        return matchUserRandAndGender(gender, start);
    }

    private MatchUser matchUserRandAndGender(String gender, int start) {

        if (start <= 0){
            start = 1;
        }
        if (gender == null || "".equals(gender)) {
            gender = "未知";
        }
        if ("未知".equals(gender)) {
            return matchUserMapper.findByRand(start);
        } else if ("男".equals(gender)) {
            return matchUserMapper.findByRandAndGender("女", start);
        }else {
            return matchUserMapper.findByRandAndGender("男", start);
        }

    }
}
