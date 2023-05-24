package com.carrentalbackend.util;


import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.User;
import com.carrentalbackend.repository.*;
import com.carrentalbackend.util.factories.ClientFactory;
import com.carrentalbackend.util.factories.CompanyFactory;
import com.carrentalbackend.util.factories.OfficeFactory;
import com.carrentalbackend.util.factories.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
@Transactional
public class DBOperations {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private PickUpRepository pickUpRepository;
    @Autowired
    private CarReturnRepository carReturnRepository;


    public User addSimpleUserToDB() {
        var user = UserFactory.getSimpleUser();
        userRepository.save(user);
        assertTrue(userRepository.existsByEmail(user.getEmail()));
        return user;
    }

    public Client addSimpleClientToDB() {
        var client = ClientFactory.getSimpleClient();
        clientRepository.save(client);
        assertTrue(clientRepository.existsByEmail(ClientFactory.simpleEmail));
        return client;
    }

    public Company addSimpleCompanyToDB() {
        var company = CompanyFactory.getSimpleCompanyBuilder().build();
        companyRepository.save(company);
        return company;
    }

    public Office addSimpleOfficeToDB() {
        var office = OfficeFactory.getSimpleOfficeBuilder().build();
        officeRepository.save(office);
        return office;
    }

    public void cleanClientTable(){
        cleanReservationTable();
        clientRepository.deleteAll();
    }

    public void cleanReservationTable() {
        cleanIncomeTable();
        reservationRepository.deleteAll();
    }

    private void cleanIncomeTable() {
        incomeRepository.deleteAll();
    }

    public void cleanCompanyTable(){
        companyRepository.deleteAll();
    }


    public void cleanOfficeTable() {
        cleanEmployeesTable();
        cleanCarTable();
        officeRepository.deleteAll();
    }

    private void cleanCarTable() {
        cleanCarReturnTable();
        cleanPickUpTable();
        carRepository.deleteAll();
    }

    private void cleanPickUpTable() {
        cleanReservationTable();
        pickUpRepository.deleteAll();
    }

    private void cleanCarReturnTable() {
        cleanReservationTable();
        carReturnRepository.deleteAll();
    }


    public void cleanUserTable() {
        cleanClientTable();
        cleanEmployeesTable();
        userRepository.deleteAll();
    }

    private void cleanEmployeesTable() {
        cleanCarReturnTable();
        cleanPickUpTable();
        employeeRepository.deleteAll();
    }


}
