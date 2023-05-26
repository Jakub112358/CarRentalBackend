package com.carrentalbackend.util.factories;

import com.carrentalbackend.features.employees.rest.EmployeeRequest;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.model.enumeration.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.carrentalbackend.model.entity.Employee.EmployeeBuilder;
import static com.carrentalbackend.features.employees.rest.EmployeeRequest.EmployeeRequestBuilder;

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

    public static EmployeeRequestBuilder getSimpleEmployeeRequestBuilder(Long officeId) {
        return EmployeeRequest.builder()
                .firstName(simpleEmployeeFirstName)
                .lastName(simpleEmployeeLastName)
                .jobPosition(simpleEmployeeJobPosition)
                .email(simpleEmployeeEmail)
                .password(simpleEmployeePassword)
                .officeId(officeId);
    }
}
