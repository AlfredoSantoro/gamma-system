package com.gamma.pecservice.filters;

import com.gamma.model.Message;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class MessageSpecifications {

    public static Specification<Message> filterMessageForDate(LocalDate localDate) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), localDate);
    }

    public static Specification<Message> filterMessageForObject(String object) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("object"), "%"+object+"%");
    }

    public static Specification<Message> filterMessageForSender(String address) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            var pecMessageJoin = root.join("sender");
            return criteriaBuilder.equal(pecMessageJoin.get("address"), address);
        };
    }

    public static Specification<Message> filterMessageForReceivedMessage(String address) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            var pecMessageJoin = root.join("messageReceivers");
            return criteriaBuilder.equal(pecMessageJoin.get("address"), address);
        };
    }
}
