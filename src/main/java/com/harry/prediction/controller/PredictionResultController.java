package com.harry.prediction.controller;

import com.harry.prediction.entity.PredictionResult;
import com.harry.prediction.entity.Tag;
import com.harry.prediction.entity.User;
import com.harry.prediction.service.AnswerService;
import com.harry.prediction.service.PredictionResultService;
import com.harry.prediction.service.TagService;
import com.harry.prediction.service.UserService;
import com.harry.prediction.util.CommonUtil;
import com.harry.prediction.vo.RequestForPredictionResult;
import com.harry.prediction.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/prediction/predictionResult")
@RestController
public class PredictionResultController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PredictionResultService predictionResultService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getPredictionResult", method = RequestMethod.POST)
    public Response<PredictionResult> getPredictionResult(@RequestBody RequestForPredictionResult requestForPredictionResult) {

        if (requestForPredictionResult == null)
            return Response.buildSuccessResponse(null);

        if (requestForPredictionResult.getType() == null || "".equals(requestForPredictionResult.getType()))
            return Response.buildSuccessResponse(null);

        if (requestForPredictionResult.getUser() == null)
            return Response.buildSuccessResponse(null);

        if (requestForPredictionResult.getAnswerIds() == null)
            return Response.buildSuccessResponse(null);

        //根据必答题的ID取出对应的测试结果
        List<PredictionResult> results = new ArrayList<>();
        PredictionResult result = null;
        for (String answerId : requestForPredictionResult.getAnswerIds()) {
            result = predictionResultService.findByAnswerIdAndGender(answerId,
                    requestForPredictionResult.getUser().getGender());
            if (result != null){
                results.add(result);
            }
        }
        //所有题目都没有选中结果
        if (results.isEmpty()) {
            //根据类型获取所有结果
            results.addAll(predictionResultService.findByTypeAndGender(requestForPredictionResult.getType(),
                    requestForPredictionResult.getUser().getGender()));
        }
        //选中的结果有多个的时候，随机抽取一个即可
        if (results.size() > 1) {
            int[] randomIndex = CommonUtil.randomCommon(0, results.size(), 1);
            if (randomIndex != null){
                result = results.get(randomIndex[0]);
            }else {
                result = results.get(0);
            }
        }
        //只有一个结果
        if (results.size() == 1){
            result = results.get(0);
        }
        //根据类型查询出所有标签，并计算出百分比
        List<Tag> tags = tagService.findByType(requestForPredictionResult.getType());
        if (tags == null || tags.isEmpty())
            return Response.buildSuccessResponse(result);

        //需要去重，有的TAG标签因为性别的原因会有重复
        Map<String, Tag> map = new HashMap<>();
        for (Tag item : tags) {
            map.put(item.getTitle(), item);
        }
        tags.clear();
        for (Map.Entry<String, Tag> set: map.entrySet()){
            tags.add(set.getValue());
        }
        int sum = tags.size();
        //计算出每一份的百分比
        int per = Math.round( (float) (100 / sum));
        int difference = 10;
        int differencePer = Math.round( (float) (difference / sum));
        for (Tag item : tags){
            if (item.getPredictionResultId().equals(result.getId())) {
                item.setValue(per + difference + "%");
            }else {
                item.setValue(per - differencePer + "%");
            }
        }
        result.setTags(tags);
        //匹配用户
        List<User> users = userService.getUsersMatchPredictionResult(result.getId(), requestForPredictionResult.getUser().getGender());
        //插入或更新用户
        requestForPredictionResult.getUser().setPredictionResultId(result.getId());
        userService.insertOrUpdate(requestForPredictionResult.getUser());
        if (users != null) {
            result.setUsers(users);
        }
        return Response.buildSuccessResponse(result);
    }

}
