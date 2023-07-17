package com.gamma;

import com.gamma.auth.Sha256PasswordEncoder;
import com.gamma.model.User;
import com.gamma.model.UserServiceMatrix;
import com.gamma.model.UserType;
import com.gamma.repository.UserMatrixRepository;
import com.gamma.repository.UserRepository;
import com.gamma.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@Import(SecurityConfig.class)
@EntityScan("com.gamma.model")
@ComponentScan({"com.gamma.auth"})
public class AuthApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMatrixRepository userMatrixRepository;

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        System.out.println("Hello world auth-service!");
    }

    @Override
    public void run(String... args) throws Exception {
        UserServiceMatrix userServiceMatrix = new UserServiceMatrix();
        userServiceMatrix.setService("pec");
        userServiceMatrix.setUserType(UserType.ADMIN);
        UserServiceMatrix userServiceMatrix2 = new UserServiceMatrix();
        userServiceMatrix2.setService("sign");
        userServiceMatrix2.setUserType(UserType.ADMIN);
        UserServiceMatrix userServiceMatrix3 = new UserServiceMatrix();
        userServiceMatrix3.setService("storage");
        userServiceMatrix3.setUserType(UserType.ADMIN);
        this.userMatrixRepository.saveAll(List.of(userServiceMatrix, userServiceMatrix2, userServiceMatrix3));
        Sha256PasswordEncoder passwordEncoder = new Sha256PasswordEncoder();
        User user = new User();
        user.setUsername("mario");
        user.setPassword(passwordEncoder.encode("mariorossi"));
        user.setUserType(UserType.ADMIN);
        var userSaved = this.userRepository.save(user);
        System.out.println("user " + userSaved.getUsername() + " saved successfully");
    }
}