package com.carrentalbackend.features.notifications.providers;

import com.carrentalbackend.features.notifications.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class EmployeeNotificationProvider {
    public List<NotificationDTO> getNotifications() {
        return new ArrayList<>();
    }
}
