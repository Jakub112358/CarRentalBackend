package com.carrentalbackend.util.factories;

import com.carrentalbackend.features.employees.rest.EmployeeCreateRequest;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.model.enumeration.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.carrentalbackend.model.entity.Employee.EmployeeBuilder;
import static com.carrentalbackend.features.employees.rest.EmployeeCreateRequest.EmployeeCreateRequestBuilder;

public class EmployeeFactory {
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public final static String simpleEmployeeEmail = "simple@employee.com";
    public final static String simpleEmployeePasswordEncoded = passwordEncoder.encode("123");
    public final static String simpleEmployeePassword = "123";
    public final static String simpleEmployeeFirstName = "name";
    public final static String simpleEmployeeLastName = "lastName";
    public final static JobPosition simpleEmployeeJobPosition = JobPosition.MANAGER;


    public static EmployeeBuilder getSimpleEmployeeBuilder() {
        return Employee.builder()
                .email(simpleEmployeeEmail)
                .password(simpleEmployeePasswordEncoded)
                .role(Role.EMPLOYEE)
                .firstName(simpleEmployeeFirstName)
                .lastName(simpleEmployeeLastName)
                .jobPosition(simpleEmployeeJobPosition)
                .office(OfficeFactory.getSimpleOfficeBuilder().build());
    }

    public static EmployeeCreateRequestBuilder getSimpleEmployeeRequestBuilder(Long officeId) {
        return EmployeeCreateRequest.builder()
                .firstName(simpleEmployeeFirstName)
                .lastName(simpleEmployeeLastName)
                .jobPosition(simpleEmployeeJobPosition)
                .email(simpleEmployeeEmail)
                .password(simpleEmployeePassword)
                .officeId(officeId);
    }
}
