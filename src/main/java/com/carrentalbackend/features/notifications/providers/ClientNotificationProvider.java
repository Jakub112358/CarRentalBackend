package com.carrentalbackend.features.notifications.providers;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.notifications.NotificationDTO;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.entity.User;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.repository.ReservationRepository;
import com.carrentalbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientNotificationProvider {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public List<NotificationDTO> getNotifications(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(ResourceNotFoundException::new);
        return checkNotRealizedReservations(user.getId());
    }

    private List<NotificationDTO> checkNotRealizedReservations(long userId) {
        List<Reservation> reservations = reservationRepository.findAllByClient_IdAndStatusNotAndStatusNot(userId, ReservationStatus.REALIZED, ReservationStatus.CANCELLED);
        return reservations.stream()
                .map(r -> new NotificationDTO("reservation Notification", createReservationNotificationContent(r)))
                .collect(Collectors.toList());
    }

    private String createReservationNotificationContent(Reservation r) {
        return ("Your reservation number " + r.getId() + " in dates from: " + r.getDateFrom() + " ,to: " + r.getDateTo() + " has status: " + r.getStatus().name());
    }
}
