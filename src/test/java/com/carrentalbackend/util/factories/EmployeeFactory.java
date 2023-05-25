package com.carrentalbackend.util.factories;

import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.model.enumeration.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.carrentalbackend.model.entity.Employee.EmployeeBuilder;

public class EmployeeFactory {
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public final static String simpleEmployeeEmail = "simple@employee.com";
    public final static String simpleEmployeePassword = "123";
    public final static String simpleEmployeeFirstName = "name";
    public final static String simpleEmployeeLasName = "lastName";


    public static EmployeeBuilder getSimpleEmployeeBuilder() {
        return Employee.builder()
                .email(simpleEmployeeEmail)
                .password(passwordEncoder.encode(simpleEmployeePassword))
                .role(Role.EMPLOYEE)
                .firstName(simpleEmployeeFirstName)
                .lastName(simpleEmployeeLasName)
                .jobPosition(JobPosition.MANAGER);
    }
}
