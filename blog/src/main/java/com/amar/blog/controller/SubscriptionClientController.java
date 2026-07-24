package com.amar.blog.controller;

import com.amar.blog.dto.SubscriptionDTO;
import com.amar.blog.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client/subscribe")
public class SubscriptionClientController {

    private SubscriptionService subscriptionService;
    public SubscriptionClientController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<?> subscribeUser(@Valid @RequestBody SubscriptionDTO subscriptionRequest) {
        String message = subscriptionService.subscribe(subscriptionRequest);
        Map map = new HashMap();
        map.put("message", message);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
