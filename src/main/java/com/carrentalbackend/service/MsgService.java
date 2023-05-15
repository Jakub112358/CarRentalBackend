package com.carrentalbackend.service;

import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.model.enumeration.MsgRecipient;
import com.carrentalbackend.model.temporary.Msg;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MsgService {
    private final OfficeRepository officeRepository;
    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;

    public List<Msg> getAllByRecipient(MsgRecipient recipient) {
        return switch (recipient) {
            case ADMIN -> getMsgsForAdmin();
            default -> new ArrayList<>();
        };
    }

    private List<Msg> getMsgsForAdmin() {
        List<Msg> result = new ArrayList<>(checkNumberOfManagers());
        result.addAll(checkCarsWithNullBranchOffice());
        result.addAll(checkEmployeesWithNullBranchOffice());

        return result;
    }

    private List<Msg> checkEmployeesWithNullBranchOffice() {
        List<Employee> employees = employeeRepository.findAllByOfficeIsNull();
        return employees.stream()
                .map(employee -> createMsg_EmployeeWithNullOffice(employee.getId()))
                .toList();
    }

    private Msg createMsg_EmployeeWithNullOffice(Long id) {
        String content = createContent_EmployeeWithNullOffice(id);
        return new Msg("Employee with empty branch office field", content, MsgRecipient.ADMIN);
    }

    private String createContent_EmployeeWithNullOffice(Long id) {
        return "Employee with id: " + id + " has empty branch office field";
    }

    private List<Msg> checkCarsWithNullBranchOffice() {
        List<Car> cars = carRepository.findAllByCurrentOfficeIsNull();
        return cars.stream()
                .map(car -> createMsg_CarWithNullOffice(car.getId()))
                .toList();
    }

    private Msg createMsg_CarWithNullOffice(Long id) {
        String content = createContent_CarWithNullOffice(id);
        return new Msg("Car with empty branch office field", content, MsgRecipient.ADMIN);
    }

    private String createContent_CarWithNullOffice(Long id) {
        return "Car with id: " + id + " has empty current branch office field";
    }

    private List<Msg> checkNumberOfManagers() {
        List<Msg> result = new ArrayList<>();
        List<Long> officeIds = officeRepository.getAllOfficeIds();
        for (Long officeId : officeIds) {
            int managers = getNumberOfManagersInOffice(officeId);
            if (managers != 1) {
                String content = createIncorrectNumberOfManagersContent(officeId, managers);
                result.add(new Msg("Incorrect manager number", content, MsgRecipient.ADMIN));
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
