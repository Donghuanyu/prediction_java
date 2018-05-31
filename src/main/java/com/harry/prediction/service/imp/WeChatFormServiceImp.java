package com.harry.prediction.service.imp;

import com.harry.prediction.constant.CommonConstant;
import com.harry.prediction.entity.WeChatForm;
import com.harry.prediction.mapper.WeChatFormMapper;
import com.harry.prediction.service.WeChatFormService;
import com.harry.prediction.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WeChatFormServiceImp implements WeChatFormService {

    @Autowired
    private WeChatFormMapper weChatFormMapper;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * 新增
     */
    @Override
    public void insert(String openId, String formId) {
        if (openId == null || "".equals(openId)) {
            LOG.info("插入formId时， openId为空");
            return;
        }
        if (formId == null || "".equals(formId)) {
            LOG.info("插入formId时， formId为空");
            return;
        }
        //去除开发工具的formId
        if ("the formId is a mock one".equals(formId)) {
            LOG.info("插入formId时， formId不可用");
            return;
        }
        //去除无效的openId
        if ("undefined".equals(openId)) {
            LOG.info("插入openId时， openId不可用");
            return;
        }
        WeChatForm weChatForm = new WeChatForm();
        weChatForm.setOpenId(openId);
        weChatForm.setFormId(formId);
        Date time = new Date();
        weChatForm.setCreateTime(time);
        weChatForm.setEditTime(time);
        weChatForm.setUsed(CommonConstant.FORM_UNUSED);
        insert(weChatForm);
    }
    /**
     * 新增
     * @param weChatForm    weChatForm
     */
    @Override
    public void insert(WeChatForm weChatForm) {
        if (weChatForm == null)
            return;
        if (weChatForm.getOpenId() == null || "".equals(weChatForm.getOpenId()))
            return;
        if (weChatForm.getFormId() == null || "".equals(weChatForm.getFormId()))
            return;
        WeChatForm exist = weChatFormMapper.findByOpenIdAndFormId(
                weChatForm.getOpenId(), weChatForm.getFormId());
        if (exist != null) {
            LOG.info("插入formId：该form已经存在：{}", JsonUtil.entity2Json(exist));
            return;
        }
        weChatFormMapper.insert(weChatForm);
    }

    @Override
    public void updateUsed(String openId, String formId, int used) {
        if (used < 0)
            return;
        if (openId == null || "".equals(openId))
            return;
        if (formId == null || "".equals(formId))
            return;
        weChatFormMapper.updateUsed(openId, formId, used);
    }

    @Override
    public WeChatForm getAvailableWeChatForm(String openId) {

        if (openId == null || "".equals(openId)){
            LOG.error("获取可用formId时错误：openId为空");
            return null;
        }
        //获取该用户下的所有formId
        List<WeChatForm> allForm = weChatFormMapper.findByOpenId(openId);
        if (allForm == null || allForm.isEmpty()){
            LOG.error("获取可用formId时错误：openID为 {} 的用户，formId集合为空", openId);
            return null;
        }
        WeChatForm availableForm = null;
        int expired;
        Date now = new Date();
        for (WeChatForm weChatForm : allForm) {
            if (CommonConstant.FORM_UNUSED == weChatForm.getUsed()) {
                //未使用
                availableForm = weChatForm;
                break;
            } else {
                //如果是已使用，就计算是否过期（更新时间向后7天内都不能使用）
                expired = (int)
                        ((now.getTime() - weChatForm.getEditTime().getTime())  / (1000 * 3600 * 24));
                if (expired > 7) {
                    //超过7天可以使用
                    availableForm = weChatForm;
                    break;
                }
            }
        }
        if (availableForm != null) {
            return availableForm;
        }
        LOG.error("openID为 {} 的用户，已经没有可用的formId", openId);
        return null;
    }

}
