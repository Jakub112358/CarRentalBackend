package com.carrentalbackend.features.cars;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.features.cars.rest.CarResponse;
import com.carrentalbackend.model.entity.CarReturn;
import com.carrentalbackend.model.entity.PickUp;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.util.factories.CarFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static com.carrentalbackend.config.ApiConstraints.CAR;
import static com.carrentalbackend.features.cars.rest.CarRequest.CarRequestBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarControllerIT extends BaseIT {
    @BeforeEach
    void cleanTables() {
        dbOperations.cleanCarsTable();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenResponseCreated() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var request = CarFactory.getSimpleCarRequestBuilder(pricelist.getId(), office.getId()).build();

        //when
        var result = requestTool.sendPostRequest(CAR, toJsonString(request));

        //then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(Matchers.greaterThan(0)));

        expectSimpleCar(result, pricelist.getId(), office.getId());
    }

    @ParameterizedTest
    @MethodSource("carRequestBuilderIncorrectParameters")
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenValidationFailed(CarRequestBuilder requestBuilder) throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        //setting valid priceList and office ids
        var request = requestBuilder
                .priceListId(pricelist.getId())
                .currentOfficeId(office.getId())
                .build();

        //when
        var result = requestTool.sendPostRequest(CAR, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenExistingOfficeIdValidationFailed() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var request = CarFactory.getSimpleCarRequestBuilder(pricelist.getId(), Long.MAX_VALUE).build();

        //when
        var result = requestTool.sendPostRequest(CAR, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenExistingPriceListIdValidationFailed() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var request = CarFactory.getSimpleCarRequestBuilder(Long.MAX_VALUE, office.getId()).build();

        //when
        var result = requestTool.sendPostRequest(CAR, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenSave_thenForbidden() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var request = CarFactory.getSimpleCarRequestBuilder(pricelist.getId(), office.getId()).build();

        //when
        var result = requestTool.sendPostRequest(CAR, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenBadRequest() throws Exception {
        //given
        var request = new Object();

        //when
        var result = requestTool.sendPostRequest(CAR, request.toString());

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindById_thenCorrectAnswer() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car = dbOperations.addSimpleCarToDb(office, pricelist);
        var path = CAR + "/" + car.getId();

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(car.getId()));

        //and
        expectSimpleCar(result, pricelist.getId(), office.getId());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindById_thenNotFound() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        dbOperations.addSimpleCarToDb(office, pricelist);
        var path = CAR + "/" + Long.MAX_VALUE;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenFindById_thenForbidden() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car = dbOperations.addSimpleCarToDb(office, pricelist);
        var path = CAR + "/" + car.getId();

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindAll_thenCorrectAnswer() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();
        dbOperations.addSimpleCarToDb(office, pricelist);

        //when
        var result = requestTool.sendGetRequest(CAR);

        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<CarResponse> cars = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(1, cars.size());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenFindAll_thenForbidden() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();
        dbOperations.addSimpleCarToDb(office, pricelist);

        //when
        var result = requestTool.sendGetRequest(CAR);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenCorrectAnswer() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car = dbOperations.addSimpleCarToDb(office, pricelist);
        var path = CAR + "/" + car.getId();

        //and
        var changedMake = "Lamborghini";
        var updateRequest = CarFactory.getSimpleCarRequestBuilder(pricelist.getId(), office.getId())
                .make(changedMake)
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.make").value(changedMake));
    }

    @Test
    public void whenUpdate_thenForbidden() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car = dbOperations.addSimpleCarToDb(office, pricelist);
        var path = CAR + "/" + car.getId();

        //and
        var changedMake = "Lamborghini";
        var updateRequest = CarFactory.getSimpleCarRequestBuilder(pricelist.getId(), office.getId())
                .make(changedMake)
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource("carRequestBuilderIncorrectParameters")
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenValidationFailed(CarRequestBuilder requestBuilder) throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car = dbOperations.addSimpleCarToDb(office, pricelist);
        var path = CAR + "/" + car.getId();

        //and
        var request = requestBuilder
                .priceListId(pricelist.getId())
                .currentOfficeId(office.getId())
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenBadRequest() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car = dbOperations.addSimpleCarToDb(office, pricelist);
        var path = CAR + "/" + car.getId();

        //and
        var request = new Object();

        //when
        var result = requestTool.sendPatchRequest(path, request.toString());

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenDelete_thenCorrectAnswer() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //and
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var reservation = dbOperations.addSimpleReservationToDB(car, office, office);
        var pickUp = dbOperations.addSimplePickUpToDB(employee, reservation, car, office);
        var carReturn = dbOperations.addSimpleCarReturnToDB(employee, reservation, car, office);

        //and
        var path = CAR + "/" + car.getId();

        //and
        assertTrue(carRepository.existsById(car.getId()));
        assertCarFieldsNotNull(pickUp, carReturn, reservation);

        //when
        var result = requestTool.sendDeleteRequest(path);

        //then
        result.andExpect(status().isNoContent());

        //and
        assertFalse(employeeRepository.existsById(office.getId()));
        assertCarFieldsNull(pickUp, carReturn, reservation);
    }

    @Test
    public void whenDelete_thenForbidden() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //and
        var path = CAR + "/" + car.getId();

        //when
        var result = requestTool.sendDeleteRequest(path);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenDelete_thenNotFound() throws Exception {
        //given
        var pricelist = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();
        dbOperations.addSimpleCarToDb(office, pricelist);

        //and
        var path = CAR + "/" + Long.MAX_VALUE;

        //when
        var result = requestTool.sendDeleteRequest(path);

        //then
        result.andExpect(status().isNotFound());
    }

    private static Stream<Arguments> carRequestBuilderIncorrectParameters() {
        return Stream.of(
                Arguments.of(CarFactory.getSimpleCarRequestBuilder(null, null).make(null)),
                Arguments.of(CarFactory.getSimpleCarRequestBuilder(null, null).model("")),
                Arguments.of(CarFactory.getSimpleCarRequestBuilder(null, null).mileage(0)),
                Arguments.of(CarFactory.getSimpleCarRequestBuilder(null, null).mileage(null)),
                Arguments.of(CarFactory.getSimpleCarRequestBuilder(null, null).minRentalTime(0)),
                Arguments.of(CarFactory.getSimpleCarRequestBuilder(null, null).minRentalTime(null)),
                Arguments.of(CarFactory.getSimpleCarRequestBuilder(null, null).yearOfManufacture(null)),
                Arguments.of(CarFactory.getSimpleCarRequestBuilder(null, null).yearOfManufacture(1899)),
                Arguments.of(CarFactory.getSimpleCarRequestBuilder(null, null).yearOfManufacture(LocalDate.now().getYear() + 2)),
                Arguments.of(CarFactory.getSimpleCarRequestBuilder(null, null).bodyType(null)),
                Arguments.of(CarFactory.getSimpleCarRequestBuilder(null, null).color(null)),
                Arguments.of(CarFactory.getSimpleCarRequestBuilder(null, null).status(null))
        );
    }

    private void expectSimpleCar(ResultActions result, Long priceListId, Long officeId) throws Exception {
        result.andExpect(jsonPath("$.make").value(CarFactory.simpleCarMake))
                .andExpect(jsonPath("$.model").value(CarFactory.simpleCarModel))
                .andExpect(jsonPath("$.mileage").value(CarFactory.simpleCarMileage))
                .andExpect(jsonPath("$.minRentalTime").value(CarFactory.simpleCarMinRentalTime))
                .andExpect(jsonPath("$.yearOfManufacture").value(CarFactory.simpleCarYearOfManufacture))
                .andExpect(jsonPath("$.bodyType").value(CarFactory.simpleCarBodyType.name()))
                .andExpect(jsonPath("$.color").value(CarFactory.simpleCarColor.name()))
                .andExpect(jsonPath("$.status").value(CarFactory.simpleCarStatus.name()))
                .andExpect(jsonPath("$.priceListId").value(priceListId))
                .andExpect(jsonPath("$.currentBranchOfficeId").value(officeId));
    }

    private void assertCarFieldsNull(PickUp pickUp, CarReturn carReturn, Reservation reservation) {
        assertNull(pickUpRepository.findById(pickUp.getId()).orElseThrow().getCar());
        assertNull(carReturnRepository.findById(carReturn.getId()).orElseThrow().getCar());
        assertNull(reservationRepository.findById(reservation.getId()).orElseThrow().getCar());

    }

    private void assertCarFieldsNotNull(PickUp pickUp, CarReturn carReturn, Reservation reservation) {
        assertNotNull(pickUpRepository.findById(pickUp.getId()).orElseThrow().getCar());
        assertNotNull(carReturnRepository.findById(carReturn.getId()).orElseThrow().getCar());
        assertNotNull(reservationRepository.findById(reservation.getId()).orElseThrow().getCar());
    }
}
