package com.carrentalbackend.features.notifications;

import com.carrentalbackend.model.enumeration.MsgRecipient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.carrentalbackend.config.ApiConstraints.MESSAGE;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@RequestMapping(MESSAGE)
@CrossOrigin(origins = ORIGIN)
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping(params = "recipient")
    public ResponseEntity<List<Notification>> getAllByRecipient(@RequestParam MsgRecipient recipient) {
        return ResponseEntity.ok(notificationService.getAllByRecipient(recipient));
    }
}
