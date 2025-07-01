package com.example.Billing.System.controller;

//public class MailSubscriptionController {
//
//}
//package com.example.Billing.System.controller;

import com.example.Billing.System.entity.Subscriber;
//import com.example.Billing.System.model.Subscriber;
import com.example.Billing.System.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
//@RequestMapping("/api")
@RequiredArgsConstructor

public class MailSubscriptionController {

    private final SubscriberRepository subscriberRepository;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email is required"));
        }

        if (subscriberRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("message", "Already subscribed"));
        }

        Subscriber subscriber = new Subscriber();
        subscriber.setEmail(email);
        subscriberRepository.save(subscriber);

        return ResponseEntity.ok(Map.of("message", "Subscribed successfully"));
    }

    @PostMapping("/unsubscribe")
    @Transactional
    public ResponseEntity<?> unsubscribe(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message","Email is required"));
        }

        subscriberRepository.deleteByEmail(email);
        return ResponseEntity.ok(Map.of("message","Unsubscribed successfully"));
    }
}
