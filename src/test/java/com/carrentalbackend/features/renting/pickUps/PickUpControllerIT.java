package com.carrentalbackend.features.renting.pickUps;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.features.renting.pickUps.rest.PickUpResponse;
import com.carrentalbackend.features.renting.pickUps.rest.PickUpUpdateRequest;
import com.carrentalbackend.model.entity.PickUp;
import com.carrentalbackend.util.factories.PickUpFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Set;
import java.util.stream.Stream;

import static com.carrentalbackend.config.ApiConstraints.PICK_UP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PickUpControllerIT extends BaseIT {


    @BeforeEach
    void setUp() {
        dbOperations.cleanPickUpTable();
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void whenFindById_thenCorrectAnswer() throws Exception {
        //given
        var pickUp = addBasePickUp();

        var path = PICK_UP + "/" + pickUp.getId();

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pickUp.getId()));

    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindById_thenNotFound() throws Exception {
        //given
        addBasePickUp();

        var path = PICK_UP + "/" + Long.MAX_VALUE;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenFindById_thenForbidden() throws Exception {
        //given
        var pickUp = addBasePickUp();

        var path = PICK_UP + "/" + pickUp.getId();

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindAll_thenCorrectAnswer() throws Exception {
        //given
        addBasePickUp();

        //when
        var result = requestTool.sendGetRequest(PICK_UP);

        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<PickUpResponse> pickUps = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(1, pickUps.size());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindAllByOfficeId_thenCorrectAnswer() throws Exception {
        //given
        var pickUp1 = addBasePickUp();
        var pickUp2 = addBasePickUp();
        var pickUp3 = addBasePickUp();

        //and
        var office1 = dbOperations.addSimpleOfficeToDB();
        var office2 = dbOperations.addSimpleOfficeToDB();
        pickUp1.setOffice(office1);
        pickUp2.setOffice(office2);
        pickUp3.setOffice(office1);

        //when
        var result = mockMvc.perform(get(PICK_UP).param("officeId", String.valueOf(office1.getId())));

        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<PickUpResponse> pickUps = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(2, pickUps.size());
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
        var pickUp = reservation.getPickUp();
        pickUp.setEmployee(employee);
        pickUp.setReservation(reservation);
        pickUp.setOffice(office);
        pickUp.setCar(car);

        //and
        var changedCar = dbOperations.addSimpleCarToDb(office, priceList);

        //and
        var updateRequest = PickUpFactory.getSimplePickUpRequestBuilder(employee.getId(), car.getId(), office.getId())
                .carId(changedCar.getId())
                .build();

        //and
        var path = PICK_UP + "/" + pickUp.getId();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pickUp.getId()))
                .andExpect(jsonPath("$.carId").value(changedCar.getId()));
    }


    @ParameterizedTest
    @MethodSource("pickUpRequestIncorrectParameters")
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenValidationFailed(PickUpUpdateRequest request) throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var priceList = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, priceList);
        var client = dbOperations.addSimpleClientToDB();
        var reservation = dbOperations.addSimpleReservationToDB(client, car, office, office);

        //and
        var pickUp = reservation.getPickUp();
        pickUp.setEmployee(employee);
        pickUp.setReservation(reservation);
        pickUp.setOffice(office);
        pickUp.setCar(car);

        //and
        var id = pickUp.getId();
        var path = PICK_UP + "/" + id;

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
        var pickUp = reservation.getPickUp();
        pickUp.setEmployee(employee);
        pickUp.setReservation(reservation);
        pickUp.setOffice(office);
        pickUp.setCar(car);


        //and
        var updateRequest = PickUpFactory.getSimplePickUpRequestBuilder(employee.getId(), car.getId(), office.getId())
                .employeeId(Long.MAX_VALUE)
                .build();

        //and
        var path = PICK_UP + "/" + pickUp.getId();

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
        var pickUp = reservation.getPickUp();
        pickUp.setEmployee(employee);
        pickUp.setReservation(reservation);
        pickUp.setOffice(office);
        pickUp.setCar(car);


        //and
        var updateRequest = PickUpFactory.getSimplePickUpRequestBuilder(employee.getId(), car.getId(), office.getId())
                .carId(Long.MAX_VALUE)
                .build();

        //and
        var path = PICK_UP + "/" + pickUp.getId();

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
        var pickUp = reservation.getPickUp();
        pickUp.setEmployee(employee);
        pickUp.setReservation(reservation);
        pickUp.setOffice(office);
        pickUp.setCar(car);


        //and
        var updateRequest = PickUpFactory.getSimplePickUpRequestBuilder(employee.getId(), car.getId(), office.getId())
                .officeId(Long.MAX_VALUE)
                .build();

        //and
        var path = PICK_UP + "/" + pickUp.getId();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isForbidden());
    }


    private PickUp addBasePickUp() {
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var priceList = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, priceList);
        var client = dbOperations.addSimpleClientToDB();
        var reservation = dbOperations.addSimpleReservationToDB(client, car, office, office);
        var pickUp = reservation.getPickUp();

        pickUp.setEmployee(employee);
        pickUp.setReservation(reservation);
        pickUp.setOffice(office);
        pickUp.setCar(car);
        return pickUp;
    }

    private static Stream<Arguments> pickUpRequestIncorrectParameters() {
        return Stream.of(
                Arguments.of(PickUpFactory.getSimplePickUpRequestBuilder(null, null, null).pickUpDate(null).build()),
                Arguments.of(PickUpFactory.getSimplePickUpRequestBuilder(null, null, null).plannedPickUpDate(null).build()),
                Arguments.of(PickUpFactory.getSimplePickUpRequestBuilder(null, null, null).status(null).build())
        );

    }


}
