package com.harry.prediction.controller;

import com.harry.prediction.entity.Answer;
import com.harry.prediction.entity.Subject;
import com.harry.prediction.service.AnswerService;
import com.harry.prediction.service.PredictionResultService;
import com.harry.prediction.service.SubjectService;
import com.harry.prediction.service.TagService;
import com.harry.prediction.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/prediction/subject")
@RestController
public class SubjectController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PredictionResultService predictionResultService;

    @RequestMapping(value = "/getSubjects", method = RequestMethod.POST)
    public Response<List<Subject>> getSubjectsByType(@RequestParam("type") String type){

        //获取对应类型下的题目
        List<Subject> subjects = subjectService.findByType(type);

        if (subjects == null)
            return Response.buildSuccessResponse(new ArrayList<>());

        //根据题目找出所有答案，并组装数据
        List<Answer> answers;
        for (int i = 0; i < subjects.size(); i++) {
            answers = answerService.findBySubjectId(subjects.get(i).getId());
            subjects.get(i).setTitle((i + 1) + "." + subjects.get(i).getTitle());
            subjects.get(i).setAnswers(answers);
        }
        return Response.buildSuccessResponse(subjects);
    }

}
