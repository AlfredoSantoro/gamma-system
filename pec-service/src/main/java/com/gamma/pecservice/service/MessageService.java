package com.gamma.pecservice.service;

import com.gamma.model.Message;
import com.gamma.pecservice.filters.MessageSpecifications;
import com.gamma.pecservice.model.FilterEnum;
import com.gamma.pecservice.model.MessageResponse;
import com.gamma.pecservice.model.MessageResponseBuilder;
import com.gamma.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class MessageService {

    private final Logger logger = LoggerFactory.getLogger(MessageService.class);

    private final Integer defaultPage = 0;

    private final Integer defaultSize = 10;

    @Autowired
    private MessageRepository messageRepository;

    public MessageResponse search(String pecAddress, Integer page, Integer size, Map<String, String> searchParameters) {
        int realPage = page == null ? this.defaultPage : page;
        int realSize = size == null ? this.defaultSize : size;
        List<Specification<Message>> list = new ArrayList<>();
        Map<FilterEnum, String> filterEnumStringMap = new HashMap<>();
        Arrays.stream(FilterEnum.values()).toList().forEach((filter) -> {
            final var value = searchParameters.get(filter.name().toLowerCase());
            if (value != null) {
                filterEnumStringMap.put(filter, value);
            }
        });

        if (filterEnumStringMap.isEmpty()) {
            var result = this.messageRepository.findAll(MessageSpecifications.filterMessageForSender(pecAddress), PageRequest.of(realPage, realSize));
            return MessageResponseBuilder.create(result.getNumber(), result.getSize(), result.getContent(), result.getTotalPages(), pecAddress);
        } else {
            list.add(MessageSpecifications.filterMessageForSender(pecAddress));
            filterEnumStringMap.keySet().forEach(filter -> {
                switch (filter) {
                    case DATE -> list.add(MessageSpecifications.filterMessageForDate(LocalDate.now()));
                    case OBJECT -> list.add(MessageSpecifications.filterMessageForObject(filterEnumStringMap.get(filter)));
                }
            });
        }
        var specificationOptional = list.stream().reduce(Specification::and);
        var result = specificationOptional.map(messageSpecification -> this.messageRepository.findAll(
                Specification.where(messageSpecification), PageRequest.of(realPage, realSize))).orElseGet(
                        () -> this.messageRepository.findAll(PageRequest.of(realPage, realSize)));
        return MessageResponseBuilder.create(result.getNumber(), result.getSize(),
                result.getContent(), result.getTotalPages(), pecAddress);
    }

    public List<Message> findAllMessagesSentBySenderOnASpecificDate(LocalDate localDate, String address) {
        this.logger.info("filtering messages sent by #{}" , address);
        return this.messageRepository.findAll(Specification
                .where(MessageSpecifications.filterMessageForDate(localDate)
                        .and(MessageSpecifications.filterMessageForSender(address))
                ));
    }
    public List<Message> findAllMessagesSentBySender(String address) {
        this.logger.info("filtering messages sent by #{}" , address);
        return this.messageRepository.findAll(MessageSpecifications.filterMessageForSender(address));
    }
    public List<Message> findAllMessagesReceived(String address) {
        this.logger.info("find all messages received for #{}" , address);
        return this.messageRepository.findAll(MessageSpecifications.filterMessageForReceivedMessage(address));
    }
    public List<Message> findAllMessagesReceivedOnASpecificDate(LocalDate localDate, String address) {
        this.logger.info("find all messages received for #{} on a specific date" , address);
        return this.messageRepository.findAll(Specification
                .where(MessageSpecifications.filterMessageForReceivedMessage(address))
                .and(MessageSpecifications.filterMessageForDate(localDate)));
    }
    public List<Message> findAllMessagesReceivedFromSpecificSender(String sender, String receiver) {
        this.logger.info("find all messages received sent to {} from {}" , sender, receiver);
        return this.messageRepository.findAll(Specification
                .where(MessageSpecifications.filterMessageForSender(sender))
                .and(MessageSpecifications.filterMessageForReceivedMessage(receiver)));
    }
    public List<Message> findAllMessagesReceivedFromSpecificSenderOnASpecificDate(String sender, String receiver, LocalDate localDate) {
        this.logger.info("find all messages received sent to {} from {}" , sender, receiver);
        return this.messageRepository.findAll(Specification
                .where(MessageSpecifications.filterMessageForSender(sender))
                .and(MessageSpecifications.filterMessageForReceivedMessage(receiver))
                .and(MessageSpecifications.filterMessageForDate(localDate)));
    }
}
