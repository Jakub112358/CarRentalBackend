package com.carrentalbackend.features.notifications;

import com.carrentalbackend.model.enumeration.MsgRecipient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Notification {
    private String title;
    private String content;
    private MsgRecipient recipient;
}
