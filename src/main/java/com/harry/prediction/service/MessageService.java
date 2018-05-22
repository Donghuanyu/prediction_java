package com.harry.prediction.service;

import com.harry.prediction.entity.Message;

import java.util.List;

public interface MessageService {

    int PAGE_SIZE = 10;

    void insert(Message message);

    List<Message> findByToUserId(String toUserId);

    List<Message> findByFromUserId(String fromUserId);

    int countByToUserId(String toUserId);

    int countByFromUserId(String fromUserId);

    List<Message> findByToUserId(String toUserId, int page, int pageSize);

    List<Message> findByFromUserId(String fromUserId, int page, int pageSize);
}
