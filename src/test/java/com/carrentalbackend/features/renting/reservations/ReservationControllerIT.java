package com.carrentalbackend.features.renting.reservations;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.features.renting.reservations.rest.ReservationResponse;
import com.carrentalbackend.model.entity.Income;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.util.factories.ClientFactory;
import com.carrentalbackend.util.factories.ReservationFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Stream;

import static com.carrentalbackend.config.ApiConstraints.RESERVATION;
import static com.carrentalbackend.features.renting.reservations.rest.ReservationCreateRequest.ReservationCreateRequestBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationControllerIT extends BaseIT {

    @BeforeEach
    void setUp() {
        dbOperations.cleanReservationTable();
        dbOperations.cleanPriceListTable();
        dbOperations.cleanOfficeTable();

        if (companyRepository.findFirstByIdIsNotNull().isEmpty())
            dbOperations.addSimpleCompanyToDB();

        if (financesRepository.findFirstByIdIsNotNull().isEmpty())
            dbOperations.addSimpleFinancesToDB(companyRepository.findFirstByIdIsNotNull().orElseThrow());
    }

    @Test
    @WithMockUser
    public void whenSave_thenResponseCreated_andCreatedPickUp_andCreatedCarReturn_andIncomeAdded() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //given
        var request = ReservationFactory.getSimpleReservationCreateRequestBuilder(client.getId(), car.getId(), office.getId(), office.getId()).build();

        //when
        var result = requestTool.sendPostRequest(RESERVATION, toJsonString(request));

        //then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(Matchers.greaterThan(0)));

        //and
        expectSimpleReservation(result, client.getId(), car.getId(), office.getId(), office.getId());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        ReservationResponse reservation = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        expectCreatedPickUp(reservation);
        expectCreatedCarReturn(reservation);

        //and
        expectCreatedIncome(reservation);
    }

    @ParameterizedTest
    @MethodSource("reservationCreateRequestBuilderIncorrectParameters")
    @WithMockUser
    public void whenSave_thenBasicValidationFailed(ReservationCreateRequestBuilder requestBuilder) throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //and
        var request = requestBuilder
                .clientId(client.getId())
                .pickUpOfficeId(office.getId())
                .returnOfficeId(office.getId())
                .carId(car.getId())
                .build();

        //and
        var jsonRequest = toJsonString(request);

        //when
        var result = requestTool.sendPostRequest(RESERVATION, jsonRequest);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void whenSave_thenClientIdValidationFailed() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //given
        var request = ReservationFactory.getSimpleReservationCreateRequestBuilder(client.getId(), car.getId(), office.getId(), office.getId()).build();

        //and
        request.setClientId(Long.MAX_VALUE);

        //when
        var result = requestTool.sendPostRequest(RESERVATION, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void whenSave_thenCarIdValidationFailed() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //given
        var request = ReservationFactory.getSimpleReservationCreateRequestBuilder(client.getId(), car.getId(), office.getId(), office.getId()).build();

        //and
        request.setCarId(Long.MAX_VALUE);

        //when
        var result = requestTool.sendPostRequest(RESERVATION, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void whenSave_thenOfficeIdValidationFailed() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //given
        var request = ReservationFactory.getSimpleReservationCreateRequestBuilder(client.getId(), car.getId(), office.getId(), office.getId()).build();

        //and
        request.setPickUpOfficeId(Long.MAX_VALUE);

        //when
        var result = requestTool.sendPostRequest(RESERVATION, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }


    @Test
    public void whenSave_thenForbidden() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //given
        var request = ReservationFactory.getSimpleReservationCreateRequestBuilder(client.getId(), car.getId(), office.getId(), office.getId()).build();

        //when
        var result = requestTool.sendPostRequest(RESERVATION, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void whenSave_thenBadRequest() throws Exception {
        //given
        var request = new Object();

        //when
        var result = requestTool.sendPostRequest(RESERVATION, request.toString());

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void whenSave_withIncorrectDateOrder_thenBadRequest() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //given
        var request = ReservationFactory.getSimpleReservationCreateRequestBuilder(client.getId(), car.getId(), office.getId(), office.getId()).build();

        //and
        request.setDateTo(LocalDate.now());
        request.setDateFrom(LocalDate.now().plusDays(2));

        //when
        var result = requestTool.sendPostRequest(RESERVATION, toJsonString(request));

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void whenSave_withIncorrectPrice_thenBadRequest() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //given
        var request = ReservationFactory.getSimpleReservationCreateRequestBuilder(client.getId(), car.getId(), office.getId(), office.getId()).build();

        //and
        request.setPrice(BigDecimal.valueOf(Long.MAX_VALUE));

        //when
        var result = requestTool.sendPostRequest(RESERVATION, toJsonString(request));

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindById_thenCorrectAnswer() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //and
        var id =dbOperations.addSimpleReservationToDB(client, car, office, office).getId();
        var path = RESERVATION + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        //and
        expectSimpleReservation(result, client.getId(), car.getId(), office.getId(), office.getId());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindById_thenNotFound() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);
        dbOperations.addSimpleReservationToDB(client, car, office, office);

        //and
        var id = Long.MAX_VALUE;
        var path = RESERVATION + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "CLIENT", username = ClientFactory.simpleEmail)
    public void whenFindById_ByClient_thenCorrectAnswer() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //and
        var id =dbOperations.addSimpleReservationToDB(client, car, office, office).getId();
        var path = RESERVATION + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CLIENT", username = "wrong@email.com")
    public void whenFindById_ByClient_thenForbidden() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //and
        var id =dbOperations.addSimpleReservationToDB(client, car, office, office).getId();
        var path = RESERVATION + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void whenFindAll_thenCorrectAnswer() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);
        dbOperations.addSimpleReservationToDB(client, car, office, office);

        //when
        var result = requestTool.sendGetRequest(RESERVATION);

        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<ReservationResponse> reservations = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(1, reservations.size());
    }
    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenFindAll_thenForbidden() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);
        dbOperations.addSimpleReservationToDB(client, car, office, office);

        //when
        var result = requestTool.sendGetRequest(RESERVATION);

        //then
        result.andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "CLIENT", username = "client1@user.name")
    public void whenFindByClientId_thenCorrectAnswer() throws Exception {
        //given
        var client1 = dbOperations.addSimpleClientToDB();
        client1.setEmail("client1@user.name");
        var client2 = dbOperations.addSimpleClientToDB();
        client2.setEmail("client2@user.name");
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //and
        dbOperations.addSimpleReservationToDB(client1, car, office, office);
        dbOperations.addSimpleReservationToDB(client2, car, office, office);
        dbOperations.addSimpleReservationToDB(client1, car, office, office);
        dbOperations.addSimpleReservationToDB(client2, car, office, office);

        //and
        var clientId = client1.getId();

        //when
        var result = mockMvc.perform(get(RESERVATION).param("clientId", String.valueOf(clientId)));

        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<ReservationResponse> reservations = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(2, reservations.size());
    }


    @Test
    @WithMockUser(roles = "CLIENT", username = "incorrect@user.name")
    public void whenFindByClientId_thenForbidden() throws Exception {
        //given
        var client1 = dbOperations.addSimpleClientToDB();
        client1.setEmail("client1@user.name");
        var client2 = dbOperations.addSimpleClientToDB();
        client2.setEmail("client2@user.name");
        var office = dbOperations.addSimpleOfficeToDB();
        var pricelist = dbOperations.addSimplePriceListToDb();
        var car = dbOperations.addSimpleCarToDb(office, pricelist);

        //and
        dbOperations.addSimpleReservationToDB(client1, car, office, office);
        dbOperations.addSimpleReservationToDB(client2, car, office, office);
        dbOperations.addSimpleReservationToDB(client1, car, office, office);
        dbOperations.addSimpleReservationToDB(client2, car, office, office);

        //and
        var clientId = client1.getId();

        //when
        var result = mockMvc.perform(get(RESERVATION).param("clientId", String.valueOf(clientId)));

        //then
        result.andExpect(status().isForbidden());
    }

//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void whenUpdate_thenCorrectAnswer() throws Exception {
//        //given
//        var priceList = dbOperations.addSimplePriceListToDb();
//        var path = PRICE_LIST + "/" + priceList.getId();
//
//        //and
//        var changedShortTermPrice = 10_000.0;
//        var updateRequest = PriceListFactory.getSimplePriceListRequestBuilder()
//                .shortTermPrice(changedShortTermPrice)
//                .build();
//
//        //when
//        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));
//
//        //then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(priceList.getId()))
//                .andExpect(jsonPath("$.shortTermPrice").value(changedShortTermPrice));
//    }
//
//    @Test
//    public void whenUpdate_thenForbidden() throws Exception {
//        //given
//        var priceList = dbOperations.addSimplePriceListToDb();
//        var path = PRICE_LIST + "/" + priceList.getId();
//
//        //and
//        var changedPricePerDay = 10_000.0;
//        var updateRequest = PriceListFactory.getSimplePriceListRequestBuilder()
//                .shortTermPrice(changedPricePerDay)
//                .build();
//
//        //when
//        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));
//
//        //then
//        result.andExpect(status().isForbidden());
//    }
//
//    @ParameterizedTest
//    @MethodSource("priceListRequestIncorrectParameters")
//    @WithMockUser(roles = "ADMIN")
//    public void whenUpdate_thenValidationFailed(PriceListRequest request) throws Exception {
//        //given
//        var priceList = dbOperations.addSimplePriceListToDb();
//        var path = PRICE_LIST + "/" + priceList.getId();
//
//        //when
//        var result = requestTool.sendPatchRequest(path, toJsonString(request));
//
//        //then
//        result.andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void whenUpdate_thenBadRequest() throws Exception {
//        //given
//        var priceList = dbOperations.addSimplePriceListToDb();
//        var path = PRICE_LIST + "/" + priceList.getId();
//
//        //and
//        var request = " ";
//
//        //when
//        var result = requestTool.sendPatchRequest(path, request);
//
//        //then
//        result.andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void whenDelete_thenCorrectAnswer() throws Exception {
//        //given
//        var priceList = dbOperations.addSimplePriceListToDb();
//        var office = dbOperations.addSimpleOfficeToDB();
//        var car = dbOperations.addSimpleCarToDb(office, priceList);
//
//        //and
//        var path = PRICE_LIST + "/" + priceList.getId();
//
//        //and
//        assertTrue(priceListRepository.existsById(priceList.getId()));
//        assertPriceListFieldsNotNull(car);
//
//        //when
//        var result = requestTool.sendDeleteRequest(path);
//
//        //then
//        result.andExpect(status().isNoContent());
//
//        //and
//        assertFalse(priceListRepository.existsById(office.getId()));
//        assertPriceListFieldsNull(car);
//    }
//
//    @Test
//    public void whenDelete_thenForbidden() throws Exception {
//        //given
//        var priceList = dbOperations.addSimplePriceListToDb();
//        var office = dbOperations.addSimpleOfficeToDB();
//        dbOperations.addSimpleCarToDb(office, priceList);
//
//        //and
//        var path = PRICE_LIST + "/" + priceList.getId();
//
//        //when
//        var result = requestTool.sendDeleteRequest(path);
//
//        //then
//        result.andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void whenDelete_thenNotFound() throws Exception {
//        //given
//        var priceList = dbOperations.addSimplePriceListToDb();
//        var office = dbOperations.addSimpleOfficeToDB();
//        dbOperations.addSimpleCarToDb(office, priceList);
//
//        //and
//        var path = PRICE_LIST + "/" + Long.MAX_VALUE;
//
//        //when
//        var result = requestTool.sendDeleteRequest(path);
//
//        //then
//        result.andExpect(status().isNotFound());
//    }

    private static Stream<Arguments> reservationCreateRequestBuilderIncorrectParameters() {
        return Stream.of(
                Arguments.of(getBasicRequestBuilder().dateFrom(LocalDate.now().minusDays(1))),
                Arguments.of(getBasicRequestBuilder().dateFrom(null)),
                Arguments.of(getBasicRequestBuilder().dateTo(LocalDate.now().minusDays(1))),
                Arguments.of(getBasicRequestBuilder().dateTo(null))
        );
    }

    private static ReservationCreateRequestBuilder getBasicRequestBuilder() {
        return ReservationFactory.getSimpleReservationCreateRequestBuilder(null, null, null, null);
    }

    private void expectSimpleReservation(ResultActions result, Long clientId, Long carId, Long pickUpOfficeId, Long returnOfficeId) throws Exception {
        result
                .andExpect(jsonPath("$.reservationDate").isNotEmpty())
                .andExpect(jsonPath("$.dateFrom").value(ReservationFactory.simpleReservationDateFrom.format(DateTimeFormatter.ISO_DATE)))
                .andExpect(jsonPath("$.dateTo").value(ReservationFactory.simpleReservationDateTo.format(DateTimeFormatter.ISO_DATE)))
                .andExpect(jsonPath("$.price").value(ReservationFactory.simpleReservationPrice))
                .andExpect(jsonPath("$.status").value(ReservationStatus.PLANNED.name()))
                .andExpect(jsonPath("$.clientId").value(clientId))
                .andExpect(jsonPath("$.car.id").value(carId))
                .andExpect(jsonPath("$.pickUpOffice.id").value(pickUpOfficeId))
                .andExpect(jsonPath("$.returnOffice.id").value(returnOfficeId));
    }

    private void expectCreatedIncome(ReservationResponse reservation) {
        var incomes = incomeRepository.findAllByReservation_Id(reservation.getId());
        var reservationIncomeSum = incomes.stream()
                .map(Income::getIncomeValue)
                .reduce(BigDecimal::add)
                .orElseThrow();
        assertEquals(reservationIncomeSum, reservation.getPrice());
    }

    private void expectCreatedCarReturn(ReservationResponse reservation) {
        var carReturn = carReturnRepository.findAllByReservation_Id(reservation.getId()).stream().findFirst().orElseThrow();
        assertEquals(reservation.getCar().getId(), carReturn.getCar().getId());
        assertEquals(reservation.getReturnOffice().getId(), carReturn.getOffice().getId());
    }

    private void expectCreatedPickUp(ReservationResponse reservation) {
        var pickUp = pickUpRepository.findAllByReservation_Id(reservation.getId()).stream().findFirst().orElseThrow();
        assertEquals(reservation.getCar().getId(), pickUp.getCar().getId());
        assertEquals(reservation.getPickUpOffice().getId(), pickUp.getOffice().getId());
    }

//
//    private void assertPriceListFieldsNull(Car car) {
//        assertNull(carRepository.findById(car.getId()).orElseThrow().getPriceList());
//    }
//
//    private void assertPriceListFieldsNotNull(Car car) {
//        assertNotNull(carRepository.findById(car.getId()).orElseThrow().getPriceList());
//    }

}
