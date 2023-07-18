package com.gamma.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Pec {
    @Id
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /*
        we don't expose getters and setters for it.
        This way orphanRemoval logic is applied from Pec to their receivers.
        Notice the mappedBy attribute which tells that Retrieve entity is responsible
        for the association management, so you continue to associate receivers with Pec
        by setting pec in the many-to-one relation defined in the receivers.
    */
    @OneToMany(mappedBy = "receiver", orphanRemoval = true)
    private Collection<Receivers> receivers;

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
        return address.equals(pec.address) && user.equals(pec.user);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
