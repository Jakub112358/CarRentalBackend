package com.carrentalbackend.features.notifications;

import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.model.enumeration.MsgRecipient;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final OfficeRepository officeRepository;
    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;

    public List<Notification> getAllByRecipient(MsgRecipient recipient) {
        return switch (recipient) {
            case ADMIN -> getMsgsForAdmin();
            default -> new ArrayList<>();
        };
    }

    private List<Notification> getMsgsForAdmin() {
        List<Notification> result = new ArrayList<>(checkNumberOfManagers());
        result.addAll(checkCarsWithNullBranchOffice());
        result.addAll(checkEmployeesWithNullBranchOffice());

        return result;
    }

    private List<Notification> checkEmployeesWithNullBranchOffice() {
        List<Employee> employees = employeeRepository.findAllByOfficeIsNull();
        return employees.stream()
                .map(employee -> createMsg_EmployeeWithNullOffice(employee.getId()))
                .toList();
    }

    private Notification createMsg_EmployeeWithNullOffice(Long id) {
        String content = createContent_EmployeeWithNullOffice(id);
        return new Notification("Employee with empty branch office field", content, MsgRecipient.ADMIN);
    }

    private String createContent_EmployeeWithNullOffice(Long id) {
        return "Employee with id: " + id + " has empty branch office field";
    }

    private List<Notification> checkCarsWithNullBranchOffice() {
        List<Car> cars = carRepository.findAllByCurrentOfficeIsNull();
        return cars.stream()
                .map(car -> createMsg_CarWithNullOffice(car.getId()))
                .toList();
    }

    private Notification createMsg_CarWithNullOffice(Long id) {
        String content = createContent_CarWithNullOffice(id);
        return new Notification("Car with empty branch office field", content, MsgRecipient.ADMIN);
    }

    private String createContent_CarWithNullOffice(Long id) {
        return "Car with id: " + id + " has empty current branch office field";
    }

    private List<Notification> checkNumberOfManagers() {
        List<Notification> result = new ArrayList<>();
        List<Long> officeIds = officeRepository.getAllOfficeIds();
        for (Long officeId : officeIds) {
            int managers = getNumberOfManagersInOffice(officeId);
            if (managers != 1) {
                String content = createIncorrectNumberOfManagersContent(officeId, managers);
                result.add(new Notification("Incorrect manager number", content, MsgRecipient.ADMIN));
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
