package com.harry.prediction.service.imp;

import com.harry.prediction.entity.Message;
import com.harry.prediction.mapper.MessageMapper;
import com.harry.prediction.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImp implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    /**
     * 插入留言
     * @param message   留言信息
     */
    @Override
    public void insert(Message message) {
        messageMapper.insert(message);
    }

    /**
     * 获取发给toUserId的所有留言信息
     * @param toUserId  用户ID
     * @return          留言信息
     */
    @Override
    public List<Message> findByToUserId(String toUserId) {
        if (toUserId == null || "".equals(toUserId))
            return new ArrayList<>();

        return messageMapper.findByToUserId(toUserId);
    }

    /**
     * 获取fromUserId发送的所有留言信息
     * @param fromUserId    用户ID
     * @return              留言信息
     */
    @Override
    public List<Message> findByFromUserId(String fromUserId) {
        if (fromUserId == null || "".equals(fromUserId))
            return new ArrayList<>();
        return messageMapper.findByFromUserId(fromUserId);
    }

    /**
     * 获取发给toUserId的留言信息总数
     * @param toUserId  用户ID
     * @return          留言信息总数
     */
    @Override
    public int countByToUserId(String toUserId) {
        if (toUserId == null || "".equals(toUserId))
            return 0;
        return messageMapper.countByToUserId(toUserId);
    }

    /**
     * 获取fromUserId发送的所有留言信息总数
     * @param fromUserId    用户ID
     * @return              留言信息总数
     */
    @Override
    public int countByFromUserId(String fromUserId) {
        if (fromUserId == null || "".equals(fromUserId))
            return 0;
        return messageMapper.countByFromUserId(fromUserId);
    }

    /**
     * 获取发给toUserId的分页留言信息
     * @param toUserId  用户ID
     * @param page      页码
     * @param pageSize  每页数量
     * @return          留言信息
     */
    @Override
    public List<Message> findByToUserId(String toUserId, int page, int pageSize) {
        if (toUserId == null || "".equals(toUserId))
            return new ArrayList<>();
        if (page < 0 || pageSize <= 0)
            return new ArrayList<>();
        int start = (page - 1) * pageSize;
        return messageMapper.findForPageByToUserId(toUserId, start, pageSize);
    }

    /**
     * 获取fromUserId发送的分页留言信息
     * @param fromUserId    用户ID
     * @param page          页码
     * @param pageSize      每页数量
     * @return              留言信息
     */
    @Override
    public List<Message> findByFromUserId(String fromUserId, int page, int pageSize) {
        if (fromUserId == null || "".equals(fromUserId))
            return new ArrayList<>();
        if (page < 0 || pageSize <= 0)
            return new ArrayList<>();
        int start = (page - 1) * pageSize;
        return messageMapper.findForPageByFromUserId(fromUserId, start, pageSize);
    }
}
