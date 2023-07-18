package com.gamma.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    /*
        we don't expose getters and setters for it.
        This way orphanRemoval logic is applied from user to their pecs.
        Notice the mappedBy attribute which tells that Pec entity is responsible
        for the association management, so you continue to associate pec with users
        by setting user in the many-to-one relation defined in the Pec.
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Collection<Pec> pecCollection;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && userType == user.userType && username.equals(user.username) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
