package com.carrentalbackend.features.notifications;

import com.carrentalbackend.exception.MissingTokenClaimException;
import com.carrentalbackend.model.enumeration.Role;
import lombok.RequiredArgsConstructor;
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

    public List<NotificationDTO> getAllByRecipient(Collection<? extends GrantedAuthority> authorities) {
        Role recipient = extractRole(authorities);

        return switch (recipient) {
            case ADMIN -> adminNotificationProvider.getNotifications();
            case EMPLOYEE -> employeeNotificationProvider.getNotifications();
            case CLIENT -> clientNotificationProvider.getNotifications();
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
