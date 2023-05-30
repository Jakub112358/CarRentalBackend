package com.carrentalbackend.features.renting.carReturns;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.features.renting.carReturns.rest.CarReturnResponse;
import com.carrentalbackend.features.renting.carReturns.rest.CarReturnUpdateRequest;
import com.carrentalbackend.model.entity.CarReturn;
import com.carrentalbackend.model.entity.Income;
import com.carrentalbackend.util.factories.CarReturnFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Stream;

import static com.carrentalbackend.config.ApiConstraints.CAR_RETURN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarReturnControllerIT extends BaseIT {


    @BeforeEach
    void setUp() {
        dbOperations.cleanCarReturnTable();

        if (companyRepository.findFirstByIdIsNotNull().isEmpty())
            dbOperations.addSimpleCompanyToDB();

        if (financesRepository.findFirstByIdIsNotNull().isEmpty())
            dbOperations.addSimpleFinancesToDB(companyRepository.findFirstByIdIsNotNull().orElseThrow());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void whenFindById_thenCorrectAnswer() throws Exception {
        //given
        var carReturn = addBaseCarReturn();

        var path = CAR_RETURN + "/" + carReturn.getId();

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carReturn.getId()));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindById_thenNotFound() throws Exception {
        //given
        addBaseCarReturn();

        var path = CAR_RETURN + "/" + Long.MAX_VALUE;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenFindById_thenForbidden() throws Exception {
        //given
        var carReturn = addBaseCarReturn();

        var path = CAR_RETURN + "/" + carReturn.getId();

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindAll_thenCorrectAnswer() throws Exception {
        //given
        addBaseCarReturn();

        //when
        var result = requestTool.sendGetRequest(CAR_RETURN);

        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<CarReturnResponse> carReturns = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(1, carReturns.size());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindAllByOfficeId_thenCorrectAnswer() throws Exception {
        //given
        var carReturn1 = addBaseCarReturn();
        var carReturn2 = addBaseCarReturn();
        var carReturn3 = addBaseCarReturn();

        //and
        var office1 = dbOperations.addSimpleOfficeToDB();
        var office2 = dbOperations.addSimpleOfficeToDB();
        carReturn1.setOffice(office1);
        carReturn2.setOffice(office2);
        carReturn3.setOffice(office1);

        //when
        var result = mockMvc.perform(get(CAR_RETURN).param("officeId", String.valueOf(office1.getId())));

        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<CarReturnResponse> carReturns = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(2, carReturns.size());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenCorrectAnswer() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var priceList = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, priceList);
        var client = dbOperations.addSimpleClientToDB();
        var reservation = dbOperations.addSimpleReservationToDB(client, car, office, office);

        //and
        var carReturn = reservation.getCarReturn();
        carReturn.setEmployee(employee);
        carReturn.setReservation(reservation);
        carReturn.setOffice(office);
        carReturn.setCar(car);

        //and
        var changedCar = dbOperations.addSimpleCarToDb(office, priceList);

        //and
        var updateRequest = CarReturnFactory.getSimpleCarReturnRequestBuilder(employee.getId(), car.getId(), office.getId())
                .carId(changedCar.getId())
                .build();

        //and
        var path = CAR_RETURN + "/" + carReturn.getId();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carReturn.getId()))
                .andExpect(jsonPath("$.carId").value(changedCar.getId()));

        //and
        expectSumOfIncomesEqualPrice(reservation.getId(), updateRequest.getExtraCharge());
    }


    @ParameterizedTest
    @MethodSource("carReturnRequestIncorrectParameters")
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenValidationFailed(CarReturnUpdateRequest request) throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var priceList = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, priceList);
        var client = dbOperations.addSimpleClientToDB();
        var reservation = dbOperations.addSimpleReservationToDB(client, car, office, office);

        //and
        var carReturn = reservation.getCarReturn();
        carReturn.setEmployee(employee);
        carReturn.setReservation(reservation);
        carReturn.setOffice(office);
        carReturn.setCar(car);

        //and
        var id = carReturn.getId();
        var path = CAR_RETURN + "/" + id;

        //and
        request.setEmployeeId(employee.getId());
        request.setCarId(car.getId());
        request.setOfficeId(office.getId());

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenEmployeeIdValidationFailed() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var priceList = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, priceList);
        var client = dbOperations.addSimpleClientToDB();
        var reservation = dbOperations.addSimpleReservationToDB(client, car, office, office);

        //and
        var carReturn = reservation.getCarReturn();
        carReturn.setEmployee(employee);
        carReturn.setReservation(reservation);
        carReturn.setOffice(office);
        carReturn.setCar(car);


        //and
        var updateRequest = CarReturnFactory.getSimpleCarReturnRequestBuilder(employee.getId(), car.getId(), office.getId())
                .employeeId(null)
                .build();

        //and
        var path = CAR_RETURN + "/" + carReturn.getId();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenCarIdValidationFailed() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var priceList = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, priceList);
        var client = dbOperations.addSimpleClientToDB();
        var reservation = dbOperations.addSimpleReservationToDB(client, car, office, office);

        //and
        var carReturn = reservation.getCarReturn();
        carReturn.setEmployee(employee);
        carReturn.setReservation(reservation);
        carReturn.setOffice(office);
        carReturn.setCar(car);


        //and
        var updateRequest = CarReturnFactory.getSimpleCarReturnRequestBuilder(employee.getId(), car.getId(), office.getId())
                .carId(Long.MAX_VALUE)
                .build();

        //and
        var path = CAR_RETURN + "/" + carReturn.getId();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenOfficeIdValidationFailed() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var priceList = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, priceList);
        var client = dbOperations.addSimpleClientToDB();
        var reservation = dbOperations.addSimpleReservationToDB(client, car, office, office);

        //and
        var carReturn = reservation.getCarReturn();
        carReturn.setEmployee(employee);
        carReturn.setReservation(reservation);
        carReturn.setOffice(office);
        carReturn.setCar(car);


        //and
        var updateRequest = CarReturnFactory.getSimpleCarReturnRequestBuilder(employee.getId(), car.getId(), office.getId())
                .officeId(Long.MAX_VALUE)
                .build();

        //and
        var path = CAR_RETURN + "/" + carReturn.getId();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isForbidden());
    }


    private CarReturn addBaseCarReturn() {
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var priceList = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, priceList);
        var client = dbOperations.addSimpleClientToDB();
        var reservation = dbOperations.addSimpleReservationToDB(client, car, office, office);
        var carReturn = reservation.getCarReturn();


        carReturn.setEmployee(employee);
        carReturn.setReservation(reservation);
        carReturn.setOffice(office);
        carReturn.setCar(car);
        return carReturn;
    }

    private static Stream<Arguments> carReturnRequestIncorrectParameters() {
        return Stream.of(
                Arguments.of(CarReturnFactory.getSimpleCarReturnRequestBuilder(null, null, null).returnDate(null).build()),
                Arguments.of(CarReturnFactory.getSimpleCarReturnRequestBuilder(null, null, null).plannedReturnDate(null).build()),
                Arguments.of(CarReturnFactory.getSimpleCarReturnRequestBuilder(null, null, null).status(null).build())
        );

    }

    private void expectSumOfIncomesEqualPrice(Long reservationId, BigDecimal price) {
        var incomes = incomeRepository.findAllByReservation_Id(reservationId);
        var reservationIncomeSum = incomes.stream()
                .map(Income::getIncomeValue)
                .reduce(BigDecimal::add)
                .orElseThrow();
        assertEquals(reservationIncomeSum, price);
    }

}
