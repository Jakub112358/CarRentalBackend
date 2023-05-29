package com.carrentalbackend.features.priceLists;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.features.priceLists.rest.PriceListRequest;
import com.carrentalbackend.features.priceLists.rest.PriceListResponse;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.util.factories.PriceListFactory;
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

import static com.carrentalbackend.config.ApiConstraints.PRICE_LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PriceListControllerIT extends BaseIT {
    @BeforeEach
    void setUp() {
        dbOperations.cleanPriceListTable();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenResponseCreated() throws Exception {
        //given
        var request = PriceListFactory.getSimplePriceListRequestBuilder().build();

        //when
        var result = requestTool.sendPostRequest(PRICE_LIST, toJsonString(request));

        //then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(Matchers.greaterThan(0)));

        expectSimplePriceList(result);
    }

    @ParameterizedTest
    @MethodSource("priceListRequestIncorrectParameters")
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenValidationFailed(PriceListRequest request) throws Exception {
        //given
        var jsonRequest = toJsonString(request);

        //when
        var result = requestTool.sendPostRequest(PRICE_LIST, jsonRequest);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenSave_thenForbidden() throws Exception {
        //given
        var request = PriceListFactory.getSimplePriceListRequestBuilder().build();

        //when
        var result = requestTool.sendPostRequest(PRICE_LIST, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenBadRequest() throws Exception {
        //given
        var request = new Object();

        //when
        var result = requestTool.sendPostRequest(PRICE_LIST, request.toString());

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindById_thenCorrectAnswer() throws Exception {
        //given
        var id = dbOperations.addSimplePriceListToDb().getId();
        var path = PRICE_LIST + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        //and
        expectSimplePriceList(result);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindById_thenNotFound() throws Exception {
        //given
        dbOperations.addSimplePriceListToDb();
        var id = Long.MAX_VALUE;
        var path = PRICE_LIST + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenFindById_thenForbidden() throws Exception {
        //given
        var id = dbOperations.addSimplePriceListToDb().getId();
        var path = PRICE_LIST + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindAll_thenCorrectAnswer() throws Exception {
        //given
        dbOperations.addSimplePriceListToDb();

        //when
        var result = requestTool.sendGetRequest(PRICE_LIST);

        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<PriceListResponse> priceLists = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(1, priceLists.size());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenFindAll_thenForbidden() throws Exception {
        //given
        dbOperations.addSimplePriceListToDb();

        //when
        var result = requestTool.sendGetRequest(PRICE_LIST);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenCorrectAnswer() throws Exception {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var path = PRICE_LIST + "/" + priceList.getId();

        //and
        var changedShortTermPrice = 10_000.0;
        var updateRequest = PriceListFactory.getSimplePriceListRequestBuilder()
                .shortTermPrice(changedShortTermPrice)
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(priceList.getId()))
                .andExpect(jsonPath("$.shortTermPrice").value(changedShortTermPrice));
    }

    @Test
    public void whenUpdate_thenForbidden() throws Exception {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var path = PRICE_LIST + "/" + priceList.getId();

        //and
        var changedPricePerDay = 10_000.0;
        var updateRequest = PriceListFactory.getSimplePriceListRequestBuilder()
                .shortTermPrice(changedPricePerDay)
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource("priceListRequestIncorrectParameters")
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenValidationFailed(PriceListRequest request) throws Exception {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var path = PRICE_LIST + "/" + priceList.getId();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenBadRequest() throws Exception {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var path = PRICE_LIST + "/" + priceList.getId();

        //and
        var request = " ";

        //when
        var result = requestTool.sendPatchRequest(path, request);

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenDelete_thenCorrectAnswer() throws Exception {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();
        var car = dbOperations.addSimpleCarToDb(office, priceList);

        //and
        var path = PRICE_LIST + "/" + priceList.getId();

        //and
        assertTrue(priceListRepository.existsById(priceList.getId()));
        assertPriceListFieldsNotNull(car);

        //when
        var result = requestTool.sendDeleteRequest(path);

        //then
        result.andExpect(status().isNoContent());

        //and
        assertFalse(priceListRepository.existsById(office.getId()));
        assertPriceListFieldsNull(car);
    }

    @Test
    public void whenDelete_thenForbidden() throws Exception {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();
        dbOperations.addSimpleCarToDb(office, priceList);

        //and
        var path = PRICE_LIST + "/" + priceList.getId();

        //when
        var result = requestTool.sendDeleteRequest(path);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenDelete_thenNotFound() throws Exception {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();
        dbOperations.addSimpleCarToDb(office, priceList);

        //and
        var path = PRICE_LIST + "/" + Long.MAX_VALUE;

        //when
        var result = requestTool.sendDeleteRequest(path);

        //then
        result.andExpect(status().isNotFound());
    }

    private static Stream<Arguments> priceListRequestIncorrectParameters() {
        return Stream.of(
                Arguments.of(PriceListFactory.getSimplePriceListRequestBuilder().shortTermPrice(null).build()),
                Arguments.of(PriceListFactory.getSimplePriceListRequestBuilder().shortTermPrice(-3.0).build()),
                Arguments.of(PriceListFactory.getSimplePriceListRequestBuilder().mediumTermPrice(null).build()),
                Arguments.of(PriceListFactory.getSimplePriceListRequestBuilder().mediumTermPrice(-3.0).build()),
                Arguments.of(PriceListFactory.getSimplePriceListRequestBuilder().longTermPrice(null).build()),
                Arguments.of(PriceListFactory.getSimplePriceListRequestBuilder().longTermPrice(-3.0).build())
        );
    }

    private void expectSimplePriceList(ResultActions result) throws Exception {
        result.andExpect(jsonPath("$.shortTermPrice").value(PriceListFactory.simplePriceListShortTermPrice))
                .andExpect(jsonPath("$.mediumTermPrice").value(PriceListFactory.simplePriceListMediumTermPrice))
                .andExpect(jsonPath("$.longTermPrice").value(PriceListFactory.simplePriceListLongTermPrice));
    }

    private void assertPriceListFieldsNull(Car car) {
        assertNull(carRepository.findById(car.getId()).orElseThrow().getPriceList());
    }

    private void assertPriceListFieldsNotNull(Car car) {
        assertNotNull(carRepository.findById(car.getId()).orElseThrow().getPriceList());
    }
}
