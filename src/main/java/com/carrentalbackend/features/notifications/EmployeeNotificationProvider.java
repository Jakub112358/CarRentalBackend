package com.carrentalbackend.features.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class EmployeeNotificationProvider {
    public List<NotificationDTO> getNotifications() {
        List<NotificationDTO> clientNotificationList = new ArrayList<>();
        NotificationDTO notification = new NotificationDTO("example employee title", "example employee content");
        clientNotificationList.add(notification);
        return clientNotificationList;
    }
}
