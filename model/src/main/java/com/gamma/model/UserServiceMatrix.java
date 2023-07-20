package com.gamma.model;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserServiceMatrix that = (UserServiceMatrix) o;
        return userType == that.userType && service.equals(that.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userType, service);
    }
}
