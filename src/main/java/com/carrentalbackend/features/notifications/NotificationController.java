package com.carrentalbackend.features.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.carrentalbackend.config.ApiConstraints.NOTIFICATION;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@RequestMapping(NOTIFICATION)
@CrossOrigin(origins = ORIGIN)
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllByRecipient(Authentication auth) {

        return ResponseEntity.ok(notificationService.getAllByRecipient(auth));
    }
}
