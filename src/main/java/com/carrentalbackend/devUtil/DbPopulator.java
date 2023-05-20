package com.carrentalbackend.devUtil;


import com.carrentalbackend.features.clients.ClientService;
import com.carrentalbackend.features.clients.register.ClientCreateRequest;
import com.carrentalbackend.features.companyResources.car.CarCreateRequest;
import com.carrentalbackend.features.companyResources.car.CarService;
import com.carrentalbackend.features.companyResources.car.priceLists.PriceListCreateRequest;
import com.carrentalbackend.features.companyResources.car.priceLists.PriceListService;
import com.carrentalbackend.features.companyResources.company.CompanyCreateRequest;
import com.carrentalbackend.features.companyResources.company.CompanyService;
import com.carrentalbackend.features.companyResources.employee.EmployeeCreateRequest;
import com.carrentalbackend.features.companyResources.employee.EmployeeService;
import com.carrentalbackend.features.companyResources.office.OfficeCreateRequest;
import com.carrentalbackend.features.companyResources.office.OfficeService;
import com.carrentalbackend.features.renting.reservation.ReservationCreateRequest;
import com.carrentalbackend.features.renting.reservation.ReservationService;
import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.model.entity.User;
import com.carrentalbackend.model.enumeration.*;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
@Profile("dev")
public class DbPopulator {
    private final CompanyService companyService;
    private final OfficeService officeService;
    private final CarService carService;
    private final EmployeeService employeeService;
    private final ClientService clientService;
    private final ReservationService reservationService;
    private final PriceListService pricelistService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private Address[] addresses;
    private int addressCounter = 0;

    @PostConstruct
    public void populateDB() throws IOException {
        createAddressList();
        addCompany();
        addOffices();
        addPriceLists();
        addCars();
        addEmployees();
        addClients();
        addAdmin();
        addReservations();
    }

    private void addAdmin() {
        User admin = new User(0L, "admin@mail.com", passwordEncoder.encode("123"), Role.ADMIN);
        userRepository.save(admin);
    }

    private void addPriceLists() {
        List<PriceListCreateRequest> priceListCreateRequests = createPricelistDtos();
        priceListCreateRequests.forEach(pricelistService::save);
    }

    private List<PriceListCreateRequest> createPricelistDtos() {
        List<PriceListCreateRequest> result = new ArrayList<>();
        result.add(new PriceListCreateRequest(100.0, 90.0, 80.0));
        result.add(new PriceListCreateRequest(150.0, 130.0, 120.0));
        result.add(new PriceListCreateRequest(600.0, 450.0, 400.0));
        return result;
    }

    private void addReservations() {
        List<ReservationCreateRequest> reservations = createReservationDtos();
        reservations.forEach(reservationService::save);
    }

    private List<ReservationCreateRequest> createReservationDtos() {
        List<ReservationCreateRequest> result = new ArrayList<>();
        result.add(new ReservationCreateRequest(
                LocalDateTime.now(),
                LocalDate.of(2023, 5, 10),
                LocalDate.of(2023, 5, 15),
                BigDecimal.valueOf(300),
                ReservationStatus.PLANNED,
                4L, 1L, 1L, 1L));
        result.add(new ReservationCreateRequest(
                LocalDateTime.now(),
                LocalDate.of(2023, 5, 12),
                LocalDate.of(2023, 5, 19),
                BigDecimal.valueOf(500),
                ReservationStatus.PLANNED,
                5L, 2L, 1L, 3L));
        result.add(new ReservationCreateRequest(
                LocalDateTime.now(),
                LocalDate.of(2022, 5, 12),
                LocalDate.of(2022, 5, 19),
                BigDecimal.valueOf(500),
                ReservationStatus.PLANNED,
                5L, 2L, 1L, 3L));
        result.add(new ReservationCreateRequest(
                LocalDateTime.now(),
                LocalDate.of(2023, 5, 12),
                LocalDate.of(2023, 5, 19),
                BigDecimal.valueOf(500),
                ReservationStatus.PLANNED,
                4L, 2L, 1L, 3L));


        return result;
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

    private void addEmployees() {
        List<EmployeeCreateRequest> employees = createEmployeeList();
        employees.forEach(employeeService::save);
    }


    private void addCars() {
        List<CarCreateRequest> cars = createCarList();
        cars.forEach(carService::save);
    }

    private List<EmployeeCreateRequest> createEmployeeList() {
        List<EmployeeCreateRequest> employees = new ArrayList<>();
        employees.add(EmployeeCreateRequest.builder().firstName("John").lastName("Smith").jobPosition(JobPosition.MANAGER).branchOfficeId(1L).email("employee@mail.com").password("123").build());
        employees.add(EmployeeCreateRequest.builder().firstName("Bob").lastName("Budowniczy").jobPosition(JobPosition.MANAGER).branchOfficeId(2L).email("test2@mail.com").password("123").build());
        employees.add(EmployeeCreateRequest.builder().firstName("Ania").lastName("Z Zielonego Wzgorza").jobPosition(JobPosition.SELLER).branchOfficeId(1L).email("test3@mail.com").password("123").build());
        return employees;
    }

    private List<CarCreateRequest> createCarList() {
        List<CarCreateRequest> cars = new ArrayList<>();
        cars.add(new CarCreateRequest("opel", "astra", 10_000, 1, 2010, CarBodyType.CITY_CAR, Color.BLUE, CarStatus.AVAILABLE, 1L, 1L));
        cars.add(new CarCreateRequest("opel", "astra", 20_000, 1, 2011, CarBodyType.CITY_CAR, Color.RED, CarStatus.AVAILABLE, 1L, 1L));
        cars.add(new CarCreateRequest("opel", "astra", 30_000, 1, 2020, CarBodyType.CITY_CAR, Color.RED, CarStatus.AVAILABLE, 2L, 1L));
        cars.add(new CarCreateRequest("kia", "sportage", 40_000, 1, 2019, CarBodyType.SUV, Color.ORANGE, CarStatus.AVAILABLE, 1L, 1L));
        cars.add(new CarCreateRequest("kia", "sportage", 50_000, 1, 2018, CarBodyType.SUV, Color.OTHER, CarStatus.AVAILABLE, 3L, 1L));
        cars.add(new CarCreateRequest("kia", "sportage", 60_000, 1, 2017, CarBodyType.SUV, Color.BLACK, CarStatus.UNAVAILABLE, 3L, 1L));
        cars.add(new CarCreateRequest("ford", "focus", 70_000, 1, 2016, CarBodyType.ESTATE, Color.BLUE, CarStatus.UNAVAILABLE, 1L, 1L));
        cars.add(new CarCreateRequest("ford", "focus", 80_000, 1, 2015, CarBodyType.CITY_CAR, Color.BLUE, CarStatus.AVAILABLE, 2L, 1L));
        cars.add(new CarCreateRequest("ford", "focus", 90_000, 1, 2019, CarBodyType.CITY_CAR, Color.BLUE, CarStatus.AVAILABLE, 2L, 1L));
        cars.add(new CarCreateRequest("ford", "focus", 100_000, 1, 2021, CarBodyType.CITY_CAR, Color.BLUE, CarStatus.AVAILABLE, 1L, 1L));

        return cars;
    }

    private void addOffices() {
        for (int i = 0; i < 3; i++) {
            officeService.save(OfficeCreateRequest.builder().address(getAddress()).companyId(1L).build());
        }
    }

    private void addCompany() throws IOException {
        companyService.save(CompanyCreateRequest.builder()
                .name("Car Rental Company")
                .domain("www.company.com")
                .logotype(getPicture())
                .address(getAddress())
                .differentOfficesExtraCharge(50.0)
                .build());
    }

    //
    private byte[] getPicture() throws IOException {
        File file = new File("src/main/java/com/carrentalbackend/devUtil/carIcon.png");
        return Files.readAllBytes(file.toPath());
    }

    private Address getAddress() {
        return this.addresses[addressCounter++];
    }

    //
    private void createAddressList() {
        this.addresses = new Address[]{
                new Address(0L, "11-111", "Poznań", "ul. Roosevelta", "1"),
                new Address(0L, "11-111", "Poznań", "ul. Kolejowa", "2"),
                new Address(0L, "11-111", "Poznań", "ul. Piątkowska", "3"),
                new Address(0L, "22-222", "Warszawa", "ul. Marszałkowska", "1"),
                new Address(0L, "22-222", "Warszawa", "ul. Główna", "2"),
                new Address(0L, "22-222", "Warszawa", "ul. Dębowa", "3"),
                new Address(0L, "33-333", "Wrocław", "ul. Kolejowa", "4"),
                new Address(0L, "44-444", "Kraków", "ul. Niepodległości", "100A"),
                new Address(0L, "55-555", "Gdańsk", "ul. Zwycięstwa", "12C/3"),
                new Address(0L, "55-556", "Gdańsk", "ul. Niepodległości", "3"),
                new Address(0L, "55-557", "Gdańsk", "ul. Kolejowa", "12"),
                new Address(0L, "55-558", "Gdańsk", "ul. Uliczna", "8C/15"),
                new Address(0L, "55-559", "Gdańsk", "ul. Zwycięstwa", "12"),

        };
    }


}
