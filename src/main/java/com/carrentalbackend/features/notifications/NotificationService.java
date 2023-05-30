package com.carrentalbackend.features.notifications;

import com.carrentalbackend.exception.MissingTokenClaimException;
import com.carrentalbackend.features.notifications.providers.AdminNotificationProvider;
import com.carrentalbackend.features.notifications.providers.ClientNotificationProvider;
import com.carrentalbackend.features.notifications.providers.EmployeeNotificationProvider;
import com.carrentalbackend.model.enumeration.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final AdminNotificationProvider adminNotificationProvider;
    private final EmployeeNotificationProvider employeeNotificationProvider;
    private final ClientNotificationProvider clientNotificationProvider;

    public List<NotificationDTO> getAllByRecipient(Authentication auth) {
        var authorities = auth.getAuthorities();
        var recipient = extractRole(authorities);
        var username = auth.getName();

        return switch (recipient) {
            case ADMIN -> adminNotificationProvider.getNotifications();
            case EMPLOYEE -> employeeNotificationProvider.getNotifications();
            case CLIENT -> clientNotificationProvider.getNotifications(username);
        };
    }

    private Role extractRole(Collection<? extends GrantedAuthority> authorities) {
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            return Role.ADMIN;
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE")))
            return Role.EMPLOYEE;
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_CLIENT")))
            return Role.CLIENT;
        throw new MissingTokenClaimException("username with assigned role");
    }


}
