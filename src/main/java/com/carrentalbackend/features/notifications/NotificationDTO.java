package com.carrentalbackend.features.notifications;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NotificationDTO {
    @NonNull
    private String title;
    @NonNull
    private String content;
}
