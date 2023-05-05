package com.carrentalbackend.devUtil;

import com.carrentalbackend.model.dto.*;
import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.model.enumeration.CarBodyType;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.Color;
import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    private Address[] addresses;
    private int addressCounter = 0;

    @PostConstruct
    public void populateDB() throws IOException {
        createAddressList();
        addCompany();
        addBranchOffices();
        addCars();
        addEmployees();
        addClients();
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
        cars.add(CarDto.builder()
                .id(0L)
                .make("opel")
                .model("astra")
                .mileage(80_000)
                .minRentalTime(1)
                .yearOfManufacture(2010)
                .bodyType(CarBodyType.CITY_CAR)
                .color(Color.BLUE)
                .status(CarStatus.AVAILABLE)
                .currentBranchOfficeId(1L)
                .build());
        cars.add(CarDto.builder()
                .id(0L)
                .make("opel")
                .model("astra")
                .mileage(120_000)
                .minRentalTime(1)
                .yearOfManufacture(2013)
                .bodyType(CarBodyType.CITY_CAR)
                .color(Color.RED)
                .status(CarStatus.UNAVAILABLE)
                .currentBranchOfficeId(2L)
                .build());
        cars.add(CarDto.builder()
                .id(0L)
                .make("kia")
                .model("sportage")
                .mileage(30_000)
                .minRentalTime(7)
                .yearOfManufacture(2021)
                .bodyType(CarBodyType.SUV)
                .color(Color.WHITE)
                .status(CarStatus.RENTED)
                .currentBranchOfficeId(1L)
                .build());

        return cars;
    }

    private void addBranchOffices() {
        for (int i = 0; i < 3; i++) {
            officeService.save(OfficeDto.builder().address(getAddress()).companyId(1L).build());
        }
    }

    private void addCompany() throws IOException {
        companyService.save(CompanyDto.builder()
                .name("Company1")
                .domain("www.company1.com")
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
                new Address(0L, "11-111", "town1", "streetA", "1"),
                new Address(0L, "22-111", "town1", "streetB", "2"),
                new Address(0L, "33-111", "town1", "streetC", "3"),
                new Address(0L, "44-111", "town2", "streetA", "1"),
                new Address(0L, "55-111", "town2", "streetD", "2"),
                new Address(0L, "66-111", "town2", "streetE", "3"),
                new Address(0L, "77-111", "town3", "streetF", "4"),
                new Address(0L, "88-111", "town4", "streetA", "100A"),
                new Address(0L, "99-111", "town5", "streetB", "12C/3"),

        };
    }


}
