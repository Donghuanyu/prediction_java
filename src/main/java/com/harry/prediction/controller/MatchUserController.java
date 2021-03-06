package com.harry.prediction.controller;

import com.harry.prediction.entity.MatchUser;
import com.harry.prediction.entity.User;
import com.harry.prediction.service.MatchUserService;
import com.harry.prediction.service.UserService;
import com.harry.prediction.vo.Response;
import com.harry.prediction.vo.ResultForMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/prediction/match")
@RestController
public class MatchUserController {

    @Autowired
    private MatchUserService matchUserService;

    @Autowired
    private UserService userService;

    /**
     * 匹配过程中，需要将小程序用户ID与知乎用户数据绑定，这样后续才能实现留言功能
     */
    @RequestMapping(value = "/matchUser", method = RequestMethod.POST)
    public Response<ResultForMatch> matchUser(@RequestParam("name") String name, @RequestParam("id") String id) {

        if (name == null || "".equals(name))
            return Response.buildFailedResponse("昵称为空");

        MatchUser matchUser = matchUserService.findByName(name);
        if (matchUser == null)
            return Response.buildFailedResponse("该昵称暂未收录，已为您自动收录，下次更新版本时生效");
        ResultForMatch resultForMatch = new ResultForMatch();
        //如果小程序用户已经与该知乎用户有对应关系就不关联
        User user = userService.findByMatchId(matchUser.getId());
        if (user == null){
            //将对应的知乎用户ID更新到当前使用小程序的用户表中
            userService.updateMatchId(id, matchUser.getId());
        }
        //没有点赞过任何问题
        if (null == matchUser.getAnswerVoteUp() || "".equals(matchUser.getAnswerVoteUp()))
            return Response.buildFailedResponse("抱歉，您还没有点赞过任何问题，无法为您匹配");
        //匹配点赞过相同问题的用户
        MatchUser result = matchUserService.findBySameAnswerVoteUp(matchUser);
        if (result == null)
            return Response.buildFailedResponse("没有点赞过相同问题的用户");
        resultForMatch.setMatchUser(result);
        user = userService.findByMatchId(result.getId());
        resultForMatch.setUser(user);
        return Response.buildSuccessResponse(resultForMatch);
    }
}
