package com.gamma.model;

import java.io.Serializable;
import java.util.Objects;

public class UserServiceMatrixId implements Serializable {

    private UserType userType;

    private String service;

    public UserServiceMatrixId() {}

    public UserServiceMatrixId(UserType userType, String service) {
        this.userType = userType;
        this.service = service;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserServiceMatrixId that = (UserServiceMatrixId) o;
        return userType == that.userType && service.equals(that.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userType, service);
    }
}
