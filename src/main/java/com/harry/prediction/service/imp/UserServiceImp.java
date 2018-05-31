package com.harry.prediction.service.imp;

import com.harry.prediction.constant.CommonConstant;
import com.harry.prediction.entity.User;
import com.harry.prediction.mapper.UserMapper;
import com.harry.prediction.service.UserService;
import com.harry.prediction.service.WeChatService;
import com.harry.prediction.util.CommonUtil;
import com.harry.prediction.util.JsonUtil;
import com.harry.prediction.vo.WeChatLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatService weChatService;

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void insertOrUpdate(User user) {
        User current = findByOpenId(user.getOpenId());
        if (current == null){
            insert(user);
            return;
        }
        if (current.getSessionKey() != null && !"".equals(current.getSessionKey())){
            userMapper.updateExceptSessionKey(current);
            return;
        }
        update(user);
    }

    @Override
    public User findByOpenId(String openId) {
        return userMapper.findByOpenId(openId);
    }

    /**
     * 根据用户的答题测试结果和性别，随机匹配同一类型的异性
     * @param predictionResultId    测试结果ID
     * @param gender                性别
     * @return                      匹配的用户集合
     */
    @Override
    public List<User> getUsersMatchPredictionResult(String predictionResultId, String gender) {

        if (predictionResultId == null || "".equals(predictionResultId))
            return null;

        if (!CommonConstant.GENDER_UNKNOW.equals(gender) &&
                !CommonConstant.GENDER_MALE.equals(gender) &&
                !CommonConstant.GENDER_FEMALE.equals(gender))
            return null;

        List<User> users = userMapper.findByPredictionAndGender(predictionResultId, gender);
        if (users == null || users.isEmpty())
            return new ArrayList<>();

        if (users.size() <= 3)
            return users;

        //如果匹配到的结果 > 3，就筛选出系统的默认用户，尽量用正式用户数据匹配，如果真实数据不满3条，再用默认数据补充
        List<User> realUsers = new ArrayList<>();
        for (User user: users) {
            if (!user.getOpenId().startsWith("default_")) {
                realUsers.add(user);
            }
        }

        if (!realUsers.isEmpty()) {
            //数据总集合中移除真实用户
            for (User user: realUsers) {
                users.remove(user);
            }
        }

        //如果真实用户 = 3，直接返回
        if (realUsers.size() == 3) {
            return realUsers;
        }

        int[] index;
        //如果真实用户 < 3，就从剩下的users中补齐
        if (realUsers.size() < 3) {
            index = CommonUtil.randomCommon(0, users.size(), 3 - realUsers.size());
            if (index == null){
                index = new int[3 - realUsers.size()];
                for (int i = 0; i < index.length; i++) {
                    index[i] = i;
                }
            }
            for (int anIndex : index) {
                realUsers.add(users.get(anIndex));
            }
            return realUsers;
        }

        //如果真实用户 > 3，随机抽取3个
        List<User> result = new ArrayList<>();
        index = CommonUtil.randomCommon(0, realUsers.size(), 3);
        if (index == null){
            index = new int[3];
            for (int i = 0; i < index.length; i++) {
                index[i] = i;
            }
        }
        for (int anIndex : index) {
            result.add(realUsers.get(anIndex));
        }
        return result;
    }

    @Override
    public WeChatLogin getWeChatUser(String code) {

        if (code == null || "".equals(code))
            return null;

        String result = weChatService.WeChatLogin(code);
        if (result == null || "".equals(result))
            return null;

        return JsonUtil.json2Entity(result, WeChatLogin.class);
    }

    @Override
    public void updateMatchId(String id, String matchId) {
        if (null == id || "".equals(id))
            return;
        if (null == matchId || "".equals(matchId))
            return;
        userMapper.updateMatchId(id, matchId);
    }

    @Override
    public User findByMatchId(String matchId) {
        if (null == matchId || "".equals(matchId))
            return null;
        return userMapper.findByMatchId(matchId);
    }

    @Override
    public User findById(String id) {
        if (null == id || "".equals(id))
            return null;
        return userMapper.findById(id);
    }
}
