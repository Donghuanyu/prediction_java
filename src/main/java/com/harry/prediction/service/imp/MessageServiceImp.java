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

    @Override
    public void insert(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public List<Message> findByToUserId(String toUserId) {
        if (toUserId == null || "".equals(toUserId))
            return new ArrayList<>();

        return messageMapper.findByToUserId(toUserId);
    }
}
