package com.gamma.model;

import javax.persistence.*;

@Entity
public class Receivers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Message message;

    @ManyToOne
    private Pec receiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Pec getReceiver() {
        return receiver;
    }

    public void setReceiver(Pec receiver) {
        this.receiver = receiver;
    }
}
