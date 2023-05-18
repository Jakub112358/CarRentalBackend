package com.carrentalbackend.controller;

import com.carrentalbackend.model.enumeration.MsgRecipient;
import com.carrentalbackend.model.temporary.Msg;
import com.carrentalbackend.service.MsgService;
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
public class MsgController {
    private final MsgService msgService;

    @GetMapping(params = "recipient")
    public ResponseEntity<List<Msg>> getAllByRecipient (@RequestParam MsgRecipient recipient){
        return ResponseEntity.ok(msgService.getAllByRecipient(recipient));
    }
}
