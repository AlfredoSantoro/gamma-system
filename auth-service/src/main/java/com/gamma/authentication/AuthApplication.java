package com.gamma.authentication;

import com.gamma.auth.Sha256PasswordEncoder;
import com.gamma.model.User;
import com.gamma.model.UserServiceMatrix;
import com.gamma.model.UserType;
import com.gamma.repository.UserMatrixRepository;
import com.gamma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
@EntityScan("com.gamma.model")
@EnableJpaRepositories({"com.gamma.repository"})
@ComponentScan({"com.gamma.auth", "com.gamma.authentication"})
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
        loadUserServiceMatrix();
        Sha256PasswordEncoder passwordEncoder = new Sha256PasswordEncoder();
        User guest = new User();
        guest.setUsername("guest");
        guest.setPassword(passwordEncoder.encode("guest"));
        guest.setUserType(UserType.GUEST);
        this.userRepository.save(guest);
    }

    private void loadUserServiceMatrix() {
        UserServiceMatrix adminPecService = new UserServiceMatrix();
        adminPecService.setService("pec");
        adminPecService.setUserType(UserType.ADMIN);
        UserServiceMatrix adminSignService = new UserServiceMatrix();
        adminSignService.setService("sign");
        adminSignService.setUserType(UserType.ADMIN);
        UserServiceMatrix adminStorageService = new UserServiceMatrix();
        adminStorageService.setService("storage");
        adminStorageService.setUserType(UserType.ADMIN);
        UserServiceMatrix userSignService = new UserServiceMatrix();
        userSignService.setService("pec");
        userSignService.setUserType(UserType.USER);
        this.userMatrixRepository.saveAll(List.of(adminPecService, adminSignService, adminStorageService, userSignService));
    }
}