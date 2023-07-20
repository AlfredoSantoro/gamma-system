package com.gamma.pecservice.model;

import com.gamma.model.Message;
import java.util.ArrayList;
import java.util.List;

public class MessageResponse {
    private List<MessageDto> messageDtoList;

    private int page;

    private int size;

    private int totalPages;

    public MessageResponse() {
        this.messageDtoList = new ArrayList<>();
    }

    public List<MessageDto> getMessageDtoList() {
        return messageDtoList;
    }

    public void addMessages(List<Message> messages, String sender) {
        messages.forEach((message) -> {
            this.messageDtoList.add(this.toMessageDto(message, sender));
        });
    }

    private MessageDto toMessageDto(Message message, String sender) {
        MessageDto messageDto = new MessageDto();
        messageDto.setDate(message.getDate());
        messageDto.setId(message.getId());
        messageDto.setObject(message.getObject());
        messageDto.setText(message.getText());
        messageDto.setSender(sender);
        return messageDto;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
