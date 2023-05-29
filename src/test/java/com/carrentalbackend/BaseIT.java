package com.carrentalbackend;

import com.carrentalbackend.config.security.JwtService;
import com.carrentalbackend.features.cars.rest.CarMapper;
import com.carrentalbackend.features.renting.RentingUtil;
import com.carrentalbackend.features.renting.carSearch.CarSearchService;
import com.carrentalbackend.repository.*;
import com.carrentalbackend.util.DBOperations;
import com.carrentalbackend.util.RequestTool;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BaseIT {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ClientRepository clientRepository;
    @Autowired
    protected ReservationRepository reservationRepository;
    @Autowired
    protected OfficeRepository officeRepository;
    @Autowired
    protected CarRepository carRepository;
    @Autowired
    protected EmployeeRepository employeeRepository;
    @Autowired
    protected PriceListRepository priceListRepository;
    @Autowired
    protected PickUpRepository pickUpRepository;
    @Autowired
    protected CarReturnRepository carReturnRepository;
    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected JwtService jwtService;
    @Autowired
    protected DBOperations dbOperations;
    @Autowired
    protected RequestTool requestTool;
    @Autowired
    protected RentingUtil rentingUtil;
    @Autowired
    protected CarMapper carMapper;
    @Autowired
    protected CarSearchService carSearchService;

    @SneakyThrows
    protected <T> String toJsonString(T obj) {
        return objectMapper.writeValueAsString(obj);
    }

}
