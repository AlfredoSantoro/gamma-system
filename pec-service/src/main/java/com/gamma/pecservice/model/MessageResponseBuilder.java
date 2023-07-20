package com.gamma.pecservice.model;

import com.gamma.model.Message;

import java.util.List;

public class MessageResponseBuilder {

    public static MessageResponse create(int page, int size, List<Message> messages, int totalPages, String pecSender) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setPage(page);
        messageResponse.setSize(size);
        messageResponse.setTotalPages(totalPages);
        messageResponse.addMessages(messages, pecSender);
        return messageResponse;
    }
}
