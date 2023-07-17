package com.gamma.repository;

import com.gamma.model.UserServiceMatrix;
import com.gamma.model.UserServiceMatrixId;
import com.gamma.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMatrixRepository extends JpaRepository<UserServiceMatrix, UserServiceMatrixId> {

    List<UserServiceMatrix> findByUserType(UserType userType);
}
