package com.gamma.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String text;

    private String object;

    @CreatedDate
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pec sender;

    /*
    we don't expose getters and setters for it.
    This way orphanRemoval logic is applied from message to their receivers.
    Notice the mappedBy attribute which tells that Retrieve entity is responsible
    for the association management, so you continue to associate receivers with message
    by setting message in the many-to-one relation defined in the receivers.
 */
    @OneToMany(mappedBy = "message", orphanRemoval = true)
    private Collection<Receivers> receivers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Pec getsender() {
        return sender;
    }

    public void setSender(Pec pec) {
        this.sender = pec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id.equals(message.id) && Objects.equals(text, message.text) && Objects.equals(object, message.object) && date.equals(message.date) && sender.equals(message.sender);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
