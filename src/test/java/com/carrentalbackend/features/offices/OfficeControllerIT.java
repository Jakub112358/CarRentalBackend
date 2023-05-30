package com.carrentalbackend.features.offices;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.features.offices.rest.OfficeRequest;
import com.carrentalbackend.features.offices.rest.OfficeResponse;
import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.util.factories.AddressFactory;
import com.carrentalbackend.util.factories.OfficeFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;
import java.util.stream.Stream;

import static com.carrentalbackend.config.ApiConstraints.OFFICE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OfficeControllerIT extends BaseIT {

    @BeforeEach
    void setUp() {
        dbOperations.cleanOfficeTable();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenResponseCreated() throws Exception {
        //given
        var officeRequest = OfficeFactory.getSimpleOfficeRequestBuilder().build();

        //when
        var result = requestTool.sendPostRequest(OFFICE, toJsonString(officeRequest));

        //then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(Matchers.greaterThan(0)));

        expectSimpleOffice(result);
    }

    @ParameterizedTest
    @MethodSource("officeRequestIncorrectParameters")
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenValidationFailed(OfficeRequest request) throws Exception {
        //given
        var jsonRequest = toJsonString(request);

        //when
        var result = requestTool.sendPostRequest(OFFICE, jsonRequest);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenSave_thenForbidden() throws Exception {
        //given
        var officeRequest = OfficeFactory.getSimpleOfficeRequestBuilder().build();

        //when
        var result = requestTool.sendPostRequest(OFFICE, toJsonString(officeRequest));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenBadRequest() throws Exception {
        //given
        var request = new Object();

        //when
        var result = requestTool.sendPostRequest(OFFICE, request.toString());

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindById_thenCorrectAnswer() throws Exception {
        //given
        var id = dbOperations.addSimpleOfficeToDB().getId();
        var path = OFFICE + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        //and
        expectSimpleOffice(result);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindById_thenNotFound() throws Exception {
        //given
        dbOperations.addSimpleOfficeToDB();
        var id = Long.MAX_VALUE;
        var path = OFFICE + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenFindById_thenForbidden() throws Exception {
        //given
        var id = dbOperations.addSimpleOfficeToDB().getId();
        var path = OFFICE + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindAll_thenCorrectAnswer() throws Exception {
        //given
        dbOperations.addSimpleOfficeToDB();

        //when
        var result = requestTool.sendGetRequest(OFFICE);

        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<OfficeResponse> offices = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(1, offices.size());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenFindAll_thenForbidden() throws Exception {
        //given
        dbOperations.addSimpleOfficeToDB();

        //when
        var result = requestTool.sendGetRequest(OFFICE);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenCorrectAnswer() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var path = OFFICE + "/" + office.getId();

        //and
        var changedStreet = "changed street";
        var changedAddress = AddressFactory.getSimpleAddressBuilder()
                .street(changedStreet)
                .build();
        var updateRequest = OfficeFactory.getSimpleOfficeRequestBuilder()
                .address(changedAddress)
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(office.getId()))
                .andExpect(jsonPath("$.address.street").value(changedStreet));
    }

    @Test
    public void whenUpdate_thenForbidden() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var path = OFFICE + "/" + office.getId();

        //and
        var changedStreet = "changed street";
        var changedAddress = AddressFactory.getSimpleAddressBuilder()
                .street(changedStreet)
                .build();
        var updateRequest = OfficeFactory.getSimpleOfficeRequestBuilder()
                .address(changedAddress)
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource("officeRequestIncorrectParameters")
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenValidationFailed(OfficeRequest request) throws Exception {
        //given
        var id = dbOperations.addSimpleOfficeToDB().getId();
        var path = OFFICE + "/" + id;

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenBadRequest() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var path = OFFICE + "/" + office.getId();

        //and
        var request = "";

        //when
        var result = requestTool.sendPatchRequest(path, request);

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenDelete_thenCorrectAnswer() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var priceList = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, priceList);
        var client = dbOperations.addSimpleClientToDB();
        var reservation = dbOperations.addSimpleReservationToDB(client, car, office, office);

        //and
        var path = OFFICE + "/" + office.getId();

        //and
        assertTrue(officeRepository.existsById(office.getId()));
        assertOfficeFieldsNotNull(employee, car, reservation);

        //when
        var result = requestTool.sendDeleteRequest(path);

        //then
        result.andExpect(status().isNoContent());

        //and
        assertFalse(officeRepository.existsById(office.getId()));
        assertOfficeFieldsNull(employee, car, reservation);


    }

    @Test
    public void whenDelete_thenForbidden() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        dbOperations.addSimpleEmployeeToDB(office);
        var priceList = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, priceList);
        var client = dbOperations.addSimpleClientToDB();
        dbOperations.addSimpleReservationToDB(client, car, office, office);

        //and
        var path = OFFICE + "/" + office.getId();

        //when
        var result = requestTool.sendDeleteRequest(path);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenDelete_thenNotFound() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        dbOperations.addSimpleEmployeeToDB(office);
        var priceList = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, priceList);
        var client = dbOperations.addSimpleClientToDB();
        dbOperations.addSimpleReservationToDB(client, car, office, office);

        //and
        var path = OFFICE + "/" + Long.MAX_VALUE;

        //when
        var result = requestTool.sendDeleteRequest(path);

        //then
        result.andExpect(status().isNotFound());
    }

    private static Stream<Arguments> officeRequestIncorrectParameters() {
        Address invalidAddress1 = AddressFactory.getSimpleAddressBuilder().zipCode("x1-500").build();
        Address invalidAddress2 = AddressFactory.getSimpleAddressBuilder().town(null).build();
        Address invalidAddress3 = AddressFactory.getSimpleAddressBuilder().street("").build();
        Address invalidAddress4 = AddressFactory.getSimpleAddressBuilder().houseNumber("").build();

        return Stream.of(
                Arguments.of(OfficeFactory.getSimpleOfficeRequestBuilder().address(invalidAddress1).build()),
                Arguments.of(OfficeFactory.getSimpleOfficeRequestBuilder().address(invalidAddress2).build()),
                Arguments.of(OfficeFactory.getSimpleOfficeRequestBuilder().address(invalidAddress3).build()),
                Arguments.of(OfficeFactory.getSimpleOfficeRequestBuilder().address(invalidAddress4).build()),
                Arguments.of(OfficeFactory.getSimpleOfficeRequestBuilder().address(null).build())
        );
    }

    private void expectSimpleOffice(ResultActions result) throws Exception {
        result.andExpect(jsonPath("$.address.town").value(AddressFactory.simpleTown))
                .andExpect(jsonPath("$.address.zipCode").value(AddressFactory.simpleZipCode))
                .andExpect(jsonPath("$.address.street").value(AddressFactory.simpleStreet))
                .andExpect(jsonPath("$.address.houseNumber").value(AddressFactory.simpleHouseNumber))
                .andExpect(jsonPath("$.address.id").value(Matchers.greaterThan(0)));
    }

    private void assertOfficeFieldsNull(Employee employee, Car car, Reservation reservation) {
        assertNull(employeeRepository.findById(employee.getId()).orElseThrow().getOffice());
        assertNull(carRepository.findById(car.getId()).orElseThrow().getCurrentOffice());
        assertNull(reservationRepository.findById(reservation.getId()).orElseThrow().getReturnOffice());
        assertNull(reservationRepository.findById(reservation.getId()).orElseThrow().getPickUpOffice());
    }

    private void assertOfficeFieldsNotNull(Employee employee, Car car, Reservation reservation) {
        assertNotNull(employeeRepository.findById(employee.getId()).orElseThrow().getOffice());
        assertNotNull(carRepository.findById(car.getId()).orElseThrow().getCurrentOffice());
        assertNotNull(reservationRepository.findById(reservation.getId()).orElseThrow().getReturnOffice());
        assertNotNull(reservationRepository.findById(reservation.getId()).orElseThrow().getPickUpOffice());
    }
}
