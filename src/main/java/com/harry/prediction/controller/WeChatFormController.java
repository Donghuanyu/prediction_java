package com.harry.prediction.controller;

import com.harry.prediction.service.WeChatFormService;
import com.harry.prediction.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/prediction/weChatForm")
public class WeChatFormController {

    @Autowired
    private WeChatFormService weChatFormService;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/buildWeChatForm", method = RequestMethod.POST)
    public Response<String> buildWeChatForm(@RequestParam("openId")String openId,
                                            @RequestParam("formId")String formId) {
        if (openId == null || "".equals(openId)) {
            LOG.error("插入formId时出错，openId为空");
            return Response.buildFailedResponse("openId为空");
        }
        if (formId == null || "".equals(formId)) {
            LOG.error("插入formId时出错，formId为空");
            return Response.buildFailedResponse("formId为空");
        }
        weChatFormService.insert(openId, formId);
        return Response.buildSuccessResponse("OK");
    }
}
