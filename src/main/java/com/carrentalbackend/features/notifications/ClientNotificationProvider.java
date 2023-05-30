package com.carrentalbackend.features.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientNotificationProvider {
    public List<NotificationDTO> getNotifications() {
        List<NotificationDTO> clientNotificationList = new ArrayList<>();
        NotificationDTO notification = new NotificationDTO("example client title", "example client content");
        clientNotificationList.add(notification);
        return clientNotificationList;
    }
}
