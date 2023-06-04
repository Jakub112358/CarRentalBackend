package com.carrentalbackend.devUtil;


import com.carrentalbackend.features.cars.CarService;
import com.carrentalbackend.features.cars.rest.CarRequest;
import com.carrentalbackend.features.clients.ClientService;
import com.carrentalbackend.features.clients.rest.ClientCreateRequest;
import com.carrentalbackend.features.companies.rest.CompanyMapper;
import com.carrentalbackend.features.companies.rest.CompanyRequest;
import com.carrentalbackend.features.employees.EmployeeService;
import com.carrentalbackend.features.employees.rest.EmployeeCreateRequest;
import com.carrentalbackend.features.offices.OfficeService;
import com.carrentalbackend.features.offices.rest.OfficeRequest;
import com.carrentalbackend.features.priceLists.PriceListService;
import com.carrentalbackend.features.priceLists.rest.PriceListRequest;
import com.carrentalbackend.features.renting.reservations.ReservationService;
import com.carrentalbackend.features.renting.reservations.rest.ReservationCreateRequest;
import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.model.entity.User;
import com.carrentalbackend.model.enumeration.*;
import com.carrentalbackend.repository.CompanyRepository;
import com.carrentalbackend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
@Profile("dev")
public class DbPopulator {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final OfficeService officeService;
    private final CarService carService;
    private final EmployeeService employeeService;
    private final ClientService clientService;
    private final ReservationService reservationService;
    private final PriceListService pricelistService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private int addressCounter = 0;
    private Address[] addresses;

    @PostConstruct
    public void populateDB() throws IOException {
        createAddressList();
        addCompany();
        addAdmin();
        addClients();
        addOffices();
        addPriceLists();
        addCars();
        addEmployees();
        addReservations();
    }

    private void addCompany() throws IOException {
        var company = companyMapper.toNewEntity(CompanyRequest.builder()
                .name("Car Rental Company")
                .domain("www.company.com")
                .logotype(getPicture())
                .address(getAddress())
                .differentOfficesExtraCharge(50.0)
                .mediumTermRentMinDays(7)
                .longTermRentMinDays(30)
                .lateCancellationRatio(0.5)
                .build());
        companyRepository.save(company);
    }

    private byte[] getPicture() throws IOException {
        File file = new File("src/main/java/com/carrentalbackend/devUtil/carIcon.png");
        return Files.readAllBytes(file.toPath());
    }

    private void addAdmin() {
        var admin = new User(0L, "admin@mail.com", passwordEncoder.encode("123"), Role.ADMIN);
        userRepository.save(admin);
    }

    private void addClients() {
        List<ClientCreateRequest> clients = createClientList();
        clients.forEach(clientService::save);
    }

    private List<ClientCreateRequest> createClientList() {
        List<ClientCreateRequest> result = new ArrayList<>();
        result.add(new ClientCreateRequest("Jaś", "Fasola", "client@mail.com", getAddress(), "123"));
        result.add(new ClientCreateRequest("Johnny", "Bravo", "johny@buziaczek.pl", getAddress(), "123"));
        result.add(new ClientCreateRequest("Bruce", "Dickinson", "bruce@im.com", getAddress(), "123"));
        return result;
    }

    private void addOffices() {
        List<OfficeRequest> offices = createOfficeList();
        offices.forEach(officeService::save);
    }

    private List<OfficeRequest> createOfficeList() {
        List<OfficeRequest> result = new ArrayList<>();
        result.add(new OfficeRequest(getAddress()));
        result.add(new OfficeRequest(getAddress()));
        result.add(new OfficeRequest(getAddress()));
        return result;
    }

    private void addPriceLists() {
        List<PriceListRequest> priceListCreateRequests = createPriceLists();
        priceListCreateRequests.forEach(pricelistService::save);
    }

    private List<PriceListRequest> createPriceLists() {
        List<PriceListRequest> result = new ArrayList<>();
        result.add(new PriceListRequest(100.0, 90.0, 80.0));
        result.add(new PriceListRequest(150.0, 130.0, 120.0));
        result.add(new PriceListRequest(600.0, 450.0, 400.0));
        return result;
    }

    private void addCars() {
        List<CarRequest> cars = createCarList();
        cars.forEach(carService::save);
    }

    private List<CarRequest> createCarList() {
        List<CarRequest> cars = new ArrayList<>();
        cars.add(new CarRequest("opel", "astra", 10_000, 1, 2010, CarBodyType.CITY_CAR, Color.BLUE, CarStatus.AVAILABLE, 1L, 1L));
        cars.add(new CarRequest("opel", "astra", 20_000, 1, 2011, CarBodyType.CITY_CAR, Color.RED, CarStatus.AVAILABLE, 1L, 1L));
        cars.add(new CarRequest("opel", "astra", 30_000, 1, 2020, CarBodyType.CITY_CAR, Color.RED, CarStatus.AVAILABLE, 2L, 1L));
        cars.add(new CarRequest("kia", "sportage", 40_000, 1, 2019, CarBodyType.SUV, Color.ORANGE, CarStatus.AVAILABLE, 1L, 1L));
        cars.add(new CarRequest("kia", "sportage", 50_000, 1, 2018, CarBodyType.SUV, Color.OTHER, CarStatus.AVAILABLE, 3L, 1L));
        cars.add(new CarRequest("kia", "sportage", 60_000, 1, 2017, CarBodyType.SUV, Color.BLACK, CarStatus.UNAVAILABLE, 3L, 1L));
        cars.add(new CarRequest("ford", "focus", 70_000, 1, 2016, CarBodyType.ESTATE, Color.BLUE, CarStatus.UNAVAILABLE, 1L, 2L));
        cars.add(new CarRequest("ford", "focus", 80_000, 1, 2015, CarBodyType.CITY_CAR, Color.BLUE, CarStatus.AVAILABLE, 2L, 2L));
        cars.add(new CarRequest("ford", "focus", 90_000, 1, 2019, CarBodyType.CITY_CAR, Color.BLUE, CarStatus.AVAILABLE, 2L, 2L));
        cars.add(new CarRequest("ford", "focus", 100_000, 1, 2021, CarBodyType.CITY_CAR, Color.BLUE, CarStatus.AVAILABLE, 1L, 3L));
        return cars;
    }

    private void addEmployees() {
        List<EmployeeCreateRequest> employees = createEmployeeList();
        employees.forEach(employeeService::save);
    }

    private List<EmployeeCreateRequest> createEmployeeList() {
        List<EmployeeCreateRequest> employees = new ArrayList<>();
        employees.add(new EmployeeCreateRequest("John", "Smith", JobPosition.MANAGER, 1L, "employee@mail.com", "123"));
        employees.add(new EmployeeCreateRequest("Bob", "Budowniczy", JobPosition.MANAGER, 2L, "employee2@mail.com", "123"));
        employees.add(new EmployeeCreateRequest("Ania", "Z Zielonego Wzgorza", JobPosition.SELLER, 1L, "employee3@mail.com", "123"));
        return employees;
    }

    private void addReservations() {
        List<ReservationCreateRequest> reservations = createReservations();
        reservations.forEach(reservationService::save);
    }

    private List<ReservationCreateRequest> createReservations() {
        List<ReservationCreateRequest> result = new ArrayList<>();
        result.add(new ReservationCreateRequest(
                LocalDate.of(2023, 7, 10),
                LocalDate.of(2023, 7, 15),
                BigDecimal.valueOf(650.0),
                3L, 1L, 1L, 2L));
        result.add(new ReservationCreateRequest(
                LocalDate.of(2023, 7, 12),
                LocalDate.of(2023, 7, 19),
                BigDecimal.valueOf(770.0),
                2L, 2L, 1L, 3L));
        result.add(new ReservationCreateRequest(
                LocalDate.of(2023, 7, 12),
                LocalDate.of(2023, 7, 19),
                BigDecimal.valueOf(1090.0),
                4L, 3L, 1L, 3L));
        result.add(new ReservationCreateRequest(
                LocalDate.of(2023, 7, 18),
                LocalDate.of(2023, 7, 19),
                BigDecimal.valueOf(250.0),
                4L, 1L, 2L, 3L));
        return result;
    }

    private Address getAddress() {
        return this.addresses[addressCounter++ % this.addresses.length];
    }

    private void createAddressList() {
        this.addresses = new Address[]{
                new Address(null, "11-111", "Poznań", "ul. Roosevelta", "1"),
                new Address(null, "11-111", "Poznań", "ul. Kolejowa", "2"),
                new Address(null, "11-111", "Poznań", "ul. Piątkowska", "3"),
                new Address(null, "22-222", "Warszawa", "ul. Marszałkowska", "1"),
                new Address(null, "22-222", "Warszawa", "ul. Główna", "2"),
                new Address(null, "22-222", "Warszawa", "ul. Dębowa", "3"),
                new Address(null, "33-333", "Wrocław", "ul. Kolejowa", "4"),
                new Address(null, "44-444", "Kraków", "ul. Niepodległości", "100A"),
                new Address(null, "55-555", "Gdańsk", "ul. Zwycięstwa", "12C/3"),
                new Address(null, "55-556", "Gdańsk", "ul. Niepodległości", "3"),
                new Address(null, "55-557", "Gdańsk", "ul. Kolejowa", "12"),
                new Address(null, "55-558", "Gdańsk", "ul. Uliczna", "8C/15"),
                new Address(null, "55-559", "Gdańsk", "ul. Zwycięstwa", "12"),

        };
    }
}
