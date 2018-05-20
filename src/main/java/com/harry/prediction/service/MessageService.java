package com.harry.prediction.service;

import com.harry.prediction.entity.Message;

import java.util.List;

public interface MessageService {

    void insert(Message message);

    List<Message> findByToUserId(String toUserId);
}
