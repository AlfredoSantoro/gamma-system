package com.gamma.pecservice.rest;
import com.gamma.auth.UserDetail;
import com.gamma.model.Pec;
import com.gamma.pecservice.model.MessageResponse;
import com.gamma.pecservice.service.MessageService;
import com.gamma.repository.PecRepository;
import com.gamma.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageRestController {

    private final Logger logger = LoggerFactory.getLogger(MessageRestController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private PecRepository pecRepository;

    @GetMapping("/sender/{sender}/{page}/{size}")
    public ResponseEntity<MessageResponse> searchForSentMessages(@PathVariable String sender,
                                                                 @PathVariable Integer page,
                                                                 @PathVariable Integer size,
                                                                 @RequestParam(required = false) Map<String, String> searchParameters) {
        var username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Pec> optionalPec = this.pecRepository.findById(sender);
        if (optionalPec.isPresent()) {
            if (!optionalPec.get().getUser().getUsername().equals(username)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                MessageResponse messageResponse = this.messageService.search(sender, page, size, searchParameters);
                return new ResponseEntity<>(messageResponse, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/receiver")
    public ResponseEntity<String> searchForReceivedMessages() {
        return new ResponseEntity<>("Hello, you're accessing a protected service", HttpStatus.OK);
    }
}
