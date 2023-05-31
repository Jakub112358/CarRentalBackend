package com.carrentalbackend.features.notifications.providers;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.notifications.NotificationDTO;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.entity.PickUp;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.PickUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeNotificationProvider {
    private final EmployeeRepository employeeRepository;
    private final PickUpRepository pickUpRepository;

    public List<NotificationDTO> getNotifications(String username) {
        Employee employee = employeeRepository.findByEmail(username).orElseThrow(ResourceNotFoundException::new);
        long officeId = employee.getOffice().getId();
        return checkIncomingPickUps(officeId);
    }

    private List<NotificationDTO> checkIncomingPickUps(long officeId) {
        List<PickUp> pickUps = pickUpRepository.findAllByOffice_IdAndStatus(officeId, RentalActionStatus.PLANNED);
        return pickUps.stream()
                .map(pu -> new NotificationDTO("Incoming pick-up", createIncomingPickUpContent(pu)))
                .collect(Collectors.toList());
    }

    private String createIncomingPickUpContent(PickUp pu) {
        return "Car pick-up number " + pu.getId() + " is planned for: " + pu.getPlannedPickUpDate();
    }

}
