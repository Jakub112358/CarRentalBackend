package com.carrentalbackend.features.notifications;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private String title;
    private String content;
}
