package com.carrentalbackend.features.notifications.providers;

import com.carrentalbackend.features.notifications.NotificationDTO;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminNotificationProvider {
    private final OfficeRepository officeRepository;
    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;

    public List<NotificationDTO> getNotifications() {
        List<NotificationDTO> result = checkNumberOfManagers();
        result.addAll(checkCarsWithNullBranchOffice());
        result.addAll(checkEmployeesWithNullBranchOffice());

        return result;
    }

    private List<NotificationDTO> checkEmployeesWithNullBranchOffice() {
        List<Employee> employees = employeeRepository.findAllByOfficeIsNull();
        return employees.stream()
                .map(employee -> createMsg_EmployeeWithNullOffice(employee.getId()))
                .toList();
    }

    private NotificationDTO createMsg_EmployeeWithNullOffice(Long id) {
        String content = createContent_EmployeeWithNullOffice(id);
        return new NotificationDTO("Employee with empty branch office field", content);
    }

    private String createContent_EmployeeWithNullOffice(Long id) {
        return "Employee with id: " + id + " has empty branch office field";
    }

    private List<NotificationDTO> checkCarsWithNullBranchOffice() {
        List<Car> cars = carRepository.findAllByCurrentOfficeIsNull();
        return cars.stream()
                .map(car -> createMsg_CarWithNullOffice(car.getId()))
                .toList();
    }

    private NotificationDTO createMsg_CarWithNullOffice(Long id) {
        String content = createContent_CarWithNullOffice(id);
        return new NotificationDTO("Car with empty branch office field", content);
    }

    private String createContent_CarWithNullOffice(Long id) {
        return "Car with id: " + id + " has empty current branch office field";
    }

    private List<NotificationDTO> checkNumberOfManagers() {
        List<NotificationDTO> result = new ArrayList<>();
        List<Long> officeIds = officeRepository.getAllOfficeIds();
        for (Long officeId : officeIds) {
            int managers = getNumberOfManagersInOffice(officeId);
            if (managers != 1) {
                String content = createIncorrectNumberOfManagersContent(officeId, managers);
                result.add(new NotificationDTO("Incorrect manager number", content));
            }
        }
        return result;
    }

    private String createIncorrectNumberOfManagersContent(Long officeId, int managers) {
        return ("The number of managers in office with id = " + officeId + " equals " + managers);
    }

    private Integer getNumberOfManagersInOffice(Long officeId) {
        return employeeRepository.countAllByOffice_IdAndJobPosition(officeId, JobPosition.MANAGER);
    }
}
