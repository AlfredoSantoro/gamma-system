package com.gamma.pecservice;

import com.gamma.auth.Sha256PasswordEncoder;
import com.gamma.model.Message;
import com.gamma.model.Pec;
import com.gamma.model.User;
import com.gamma.model.UserType;
import com.gamma.pecservice.filters.MessageSpecifications;
import com.gamma.pecservice.service.MessageService;
import com.gamma.repository.MessageRepository;
import com.gamma.repository.PecRepository;
import com.gamma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.util.Set;

@SpringBootApplication
@EntityScan("com.gamma.model")
@EnableJpaRepositories({"com.gamma.repository"})
@ComponentScan({"com.gamma.auth", "com.gamma.pecservice"})
public class PecServiceApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PecRepository pecRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    public static void main(String[] args) {
        SpringApplication.run(PecServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // creating users
        Sha256PasswordEncoder passwordEncoder = new Sha256PasswordEncoder();
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setUserType(UserType.ADMIN);
        User user_1 = new User();
        user_1.setUsername("marcofino");
        user_1.setPassword(passwordEncoder.encode("marcofino"));
        User user_2 = new User();
        user_2.setUsername("tizio");
        user_2.setPassword(passwordEncoder.encode("tiziorossi"));
        User user_3 = new User();
        user_3.setUsername("alfredosantoro");
        user_3.setPassword(passwordEncoder.encode("fredgiordy"));

        // users saved
        var adminSaved = this.userRepository.save(admin);
        var user1Saved = this.userRepository.save(user_1);
        var user2Saved = this.userRepository.save(user_2);
        var user3Saved = this.userRepository.save(user_3);

        // creating pec
        Pec pec_1 = new Pec();
        pec_1.setAddress("email1@gmail.com");
        Pec pec_2 = new Pec();
        pec_2.setAddress("email2@gmail.com");
        Pec pec_3 = new Pec();
        pec_3.setAddress("email3@gmail.com");
        Pec pec_4 = new Pec();
        pec_4.setAddress("admin@gmail.com");

        pec_1.setUser(user1Saved);
        pec_2.setUser(user2Saved);
        pec_3.setUser(user3Saved);
        pec_4.setUser(adminSaved);

        // pecs saved
        var pec1Saved = this.pecRepository.save(pec_1);
        var pec2Saved = this.pecRepository.save(pec_2);
        var pec3Saved = this.pecRepository.save(pec_3);
        var adminPecSaved = this.pecRepository.save(pec_4);


        // saved messages
        var message = new Message();
        message.setText("email body 1");
        message.setObject("object 1");
        message.setSender(adminPecSaved);
        message.setMessageReceivers(Set.of(pec2Saved, pec3Saved));
        this.messageRepository.save(message);

        var message2 = new Message();
        message2.setText("email body 2");
        message2.setObject("object 2");
        message2.setSender(adminPecSaved);
        message2.setMessageReceivers(Set.of(pec2Saved, pec3Saved));
        this.messageRepository.save(message2);

        var message3 = new Message();
        message3.setText("email body 3");
        message3.setObject("object 3");
        message3.setSender(pec2Saved);
        message3.setMessageReceivers(Set.of(pec1Saved, adminPecSaved, pec3Saved));
        this.messageRepository.save(message3);

        // filters
        var messages = this.messageRepository.findAll(Specification.where(MessageSpecifications.filterMessageForSender(adminPecSaved.getAddress())
                .and(MessageSpecifications.filterMessageForObject("object"))));

        messages.forEach(message1 -> {
            System.out.println("message received from " + message1.getSender().getAddress());
            System.out.println("message id #" + message1.getId());
        } );
    }
}