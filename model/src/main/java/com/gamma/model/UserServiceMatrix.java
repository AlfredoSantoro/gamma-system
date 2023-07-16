package com.gamma.model;

import jakarta.persistence.*;

@Entity
@IdClass(UserServiceMatrixId.class)
public class UserServiceMatrix {

    @Id
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Id
    private String service;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
