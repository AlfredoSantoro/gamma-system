package com.gamma.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private UUID id;
    private String text;
    private String object;
    @CreationTimestamp
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    private Pec sender;
    @ManyToMany
    @JoinTable(
            name = "pec_message",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "pec_id")
    )
    private Set<Pec> messageReceivers = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Pec getSender() {
        return sender;
    }

    public void setSender(Pec pec) {
        this.sender = pec;
    }

    public Set<Pec> getMessageReceivers() {
        return messageReceivers;
    }

    public void setMessageReceivers(Set<Pec> messageReceivers) {
        this.messageReceivers = messageReceivers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
