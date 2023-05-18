package com.carrentalbackend.features.messages;

import com.carrentalbackend.model.enumeration.MsgRecipient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Msg {
    private String title;
    private String content;
    private MsgRecipient recipient;
}
