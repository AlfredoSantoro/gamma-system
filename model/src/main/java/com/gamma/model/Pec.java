package com.gamma.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Pec {
    @Id
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /*
        we don't expose getters and setters for it.
        This way orphanRemoval logic is applied from Pec to their messages.
        Notice the mappedBy attribute which tells that message entity is responsible
        for the association management, so you continue to associate messages with Pec
        by setting pec in the many-to-one relation defined in the message.
    */
    @OneToMany(mappedBy = "sender", orphanRemoval = true)
    private Collection<Message> sentMessages;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pec pec = (Pec) o;
        return address.equals(pec.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
