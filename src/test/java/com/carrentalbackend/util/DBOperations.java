package com.carrentalbackend.util;


import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.repository.*;
import com.carrentalbackend.util.factories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private PriceListRepository priceListRepository;
    @Autowired
    private FinancesRepository financesRepository;


    public User addSimpleUserToDB() {
        var user = UserFactory.getSimpleUser();
        userRepository.save(user);
        return user;
    }

    public Client addSimpleClientToDB() {
        var client = ClientFactory.getSimpleClient();
        clientRepository.save(client);
        return client;
    }

    public void addSimpleCompanyToDB() {
        var company = CompanyFactory.getSimpleCompanyBuilder().build();
        companyRepository.save(company);
    }

    public Office addSimpleOfficeToDB() {
        var office = OfficeFactory.getSimpleOfficeBuilder().build();
        officeRepository.save(office);
        return office;
    }

    public Employee addSimpleEmployeeToDB(Office office) {
        var employee = EmployeeFactory.getSimpleEmployeeBuilder()
                .office(office)
                .build();
        employeeRepository.save(employee);
        return employee;
    }

    public Car addSimpleCarToDb(Office currentOffice, PriceList priceList) {
        var car = CarFactory.getSimpleCarBuilder()
                .priceList(priceList)
                .currentOffice(currentOffice)
                .build();
        carRepository.save(car);
        return car;
    }

    public PriceList addSimplePriceListToDb() {
        var priceList = PriceListFactory.getSimplePriceListBuilder().build();
        priceListRepository.save(priceList);
        return priceList;
    }

    public Reservation addSimpleReservationToDB(Client client, Car car, Office pickUpOffice, Office returnOffice) {

        var reservation = ReservationFactory.getSimpleReservationBuilder()
                .client(client)
                .car(car)
                .pickUpOffice(pickUpOffice)
                .returnOffice(returnOffice)
                .pickUp(new PickUp())
                .carReturn(new CarReturn())
                .build();
        reservationRepository.save(reservation);
        return reservation;
    }

    public PickUp addSimplePickUpToDB(Employee employee, Reservation reservation, Car car, Office office) {
        var pickUp = PickUpFactory.getSimplePickUpBuilder()
                .employee(employee)
                .reservation(reservation)
                .car(car)
                .office(office)
                .build();
        pickUpRepository.save(pickUp);
        return pickUp;
    }

    public CarReturn addSimpleCarReturnToDB(Employee employee, Reservation reservation, Car car, Office office) {
        var carReturn = CarReturnFactory.getSimpleCarReturnBuilder()
                .employee(employee)
                .reservation(reservation)
                .car(car)
                .office(office)
                .build();
        carReturnRepository.save(carReturn);
        return carReturn;
    }

    public void addSimpleFinancesToDB(Company company) {
        var finances = Finances.builder()
                .company(company)
                .build();
        financesRepository.save(finances);
    }

    public void cleanClientTable() {
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

    public void cleanCompanyTable() {
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


    public void cleanPriceListTable() {
        cleanCarTable();
        priceListRepository.deleteAll();
    }

    public void cleanEmployeeTable() {
        cleanCarReturnTable();
        cleanPickUpTable();
        employeeRepository.deleteAll();
    }


    public void cleanCarsTable() {
        cleanCarReturnTable();
        cleanPickUpTable();
        carRepository.deleteAll();
    }


    public void cleanIncomesTable() {
        incomeRepository.deleteAll();
    }
}
