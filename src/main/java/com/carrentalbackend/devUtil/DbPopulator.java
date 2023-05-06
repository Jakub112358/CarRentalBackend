package com.carrentalbackend.devUtil;

import com.carrentalbackend.model.dto.crudDto.*;
import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.model.enumeration.*;
import com.carrentalbackend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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
public class DbPopulator {
    private final CompanyService companyService;
    private final OfficeService officeService;
    private final CarService carService;
    private final EmployeeService employeeService;
    private final ClientService clientService;
    private final ReservationService reservationService;
    private final PricelistService pricelistService;
    private Address[] addresses;
    private int addressCounter = 0;

    @PostConstruct
    public void populateDB() throws IOException {
        createAddressList();
        addCompany();
        addBranchOffices();
        addPricelists()
        addCars();
        addEmployees();
        addClients();
        addReservations();
    }

    private void addPricelists() {
        List<PricelistDto> pricelistDtos = createPricelistDtos();
        pricelistDtos.forEach(pricelistService::save);
    }

    private List<PricelistDto> createPricelistDtos() {
        List<PricelistDto> result = new ArrayList<>();
        result.add(new PricelistDto(0L, 100.0, 90.0, 80.0));
        result.add(new PricelistDto(0L, 150.0, 130.0, 120.0));
        result.add(new PricelistDto(0L, 600.0, 450.0, 400.0));
        return result;
    }

    private void addReservations() {
        List<ReservationDto> reservationDtos = createReservationDtos();
        reservationDtos.forEach(reservationService::save);
    }

    private List<ReservationDto> createReservationDtos() {
        List<ReservationDto> result = new ArrayList<>();
        result.add(new ReservationDto(0L,
                LocalDateTime.now(),
                LocalDate.of(2023,5,10),
                LocalDate.of(2023,5,15),
                BigDecimal.valueOf(300),
                ReservationStatus.PLANNED,
                1L, 1L, 1L, 1L));
        result.add(new ReservationDto(0L,
                LocalDateTime.now(),
                LocalDate.of(2023,5,12),
                LocalDate.of(2023,5,19),
                BigDecimal.valueOf(500),
                ReservationStatus.PLANNED,
                2L, 2L, 1L, 3L));
        result.add(new ReservationDto(0L,
                LocalDateTime.now(),
                LocalDate.of(2022,5,12),
                LocalDate.of(2022,5,19),
                BigDecimal.valueOf(500),
                ReservationStatus.REALIZED,
                2L, 2L, 1L, 3L));


        return result;
    }

    private void addClients() {
        List<ClientDto> clients = createClientList();
        clients.forEach(clientService::save);
    }

    private List<ClientDto> createClientList() {
        List<ClientDto> result = new ArrayList<>();
        result.add(new ClientDto(0L, "Jaś", "Fasola", "jas@fasola.xyz", getAddress()));
        result.add(new ClientDto(0L, "Johnny", "Bravo", "johny@buziaczek.pl", getAddress()));
        result.add(new ClientDto(0L, "Bruce", "Dickinson", "bruce@im.com", getAddress()));
        return result;
    }

    private void addEmployees() {
        List<EmployeeDto> employees = createEmployeeList();
        employees.forEach(employeeService::save);
    }


    private void addCars() {
        List<CarDto> cars = createCarList();
        cars.forEach(carService::save);
    }

    private List<EmployeeDto> createEmployeeList() {
        List<EmployeeDto> employees = new ArrayList<>();
        employees.add(EmployeeDto.builder().id(0L).firstName("John").lastName("Smith").jobPosition(JobPosition.MANAGER).branchOfficeId(1L).build());
        employees.add(EmployeeDto.builder().id(0L).firstName("Bob").lastName("Budowniczy").jobPosition(JobPosition.MANAGER).branchOfficeId(2L).build());
        employees.add(EmployeeDto.builder().id(0L).firstName("Ania").lastName("Z Zielonego Wzgorza").jobPosition(JobPosition.SELLER).branchOfficeId(1L).build());
        return employees;
    }

    private List<CarDto> createCarList() {
        List<CarDto> cars = new ArrayList<>();
        cars.add(new CarDto(0L,"opel","astra",10_000,1,2010,CarBodyType.CITY_CAR,Color.BLUE,CarStatus.AVAILABLE,1L));
        cars.add(new CarDto(0L,"opel","astra",20_000,1,2011,CarBodyType.CITY_CAR,Color.RED,CarStatus.AVAILABLE,1L));
        cars.add(new CarDto(0L,"opel","astra",30_000,1,2020,CarBodyType.CITY_CAR,Color.RED,CarStatus.AVAILABLE,1L));
        cars.add(new CarDto(0L,"kia","sportage",40_000,1,2019,CarBodyType.SUV,Color.ORANGE,CarStatus.AVAILABLE,1L));
        cars.add(new CarDto(0L,"kia","sportage",50_000,1,2018,CarBodyType.SUV,Color.OTHER,CarStatus.AVAILABLE,1L));
        cars.add(new CarDto(0L,"kia","sportage",60_000,1,2017,CarBodyType.SUV,Color.BLACK,CarStatus.UNAVAILABLE,1L));
        cars.add(new CarDto(0L,"ford","focus",70_000,1,2016,CarBodyType.ESTATE,Color.BLUE,CarStatus.UNAVAILABLE,1L));
        cars.add(new CarDto(0L,"ford","focus",80_000,1,2015,CarBodyType.CITY_CAR,Color.BLUE,CarStatus.AVAILABLE,1L));
        cars.add(new CarDto(0L,"ford","focus",90_000,1,2019,CarBodyType.CITY_CAR,Color.BLUE,CarStatus.AVAILABLE,1L));
        cars.add(new CarDto(0L,"ford","focus",100_000,1,2021,CarBodyType.CITY_CAR,Color.BLUE,CarStatus.AVAILABLE,1L));

        return cars;
    }

    private void addBranchOffices() {
        for (int i = 0; i < 3; i++) {
            officeService.save(OfficeDto.builder().address(getAddress()).companyId(1L).build());
        }
    }

    private void addCompany() throws IOException {
        companyService.save(CompanyDto.builder()
                .name("Car Rental Company")
                .domain("www.company.com")
                .logotype(getPicture())
                .address(getAddress())
                .build());
    }

    private byte[] getPicture() throws IOException {
        File file = new File("src/main/java/com/carrentalbackend/devUtil/carIcon.png");
        return Files.readAllBytes(file.toPath());
    }

    private Address getAddress() {
        return this.addresses[addressCounter++];
    }

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
