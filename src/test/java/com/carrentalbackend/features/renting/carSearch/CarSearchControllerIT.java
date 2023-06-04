package com.carrentalbackend.features.renting.carSearch;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.model.enumeration.Color;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Stream;

import static com.carrentalbackend.config.ApiConstraints.CAR_SEARCH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarSearchControllerIT extends BaseIT {

    @BeforeEach
    void setUp() {
        dbOperations.cleanCarsTable();
        dbOperations.cleanReservationTable();
        dbOperations.cleanOfficeTable();
        if (companyRepository.findFirstByIdIsNotNull().isEmpty())
            dbOperations.addSimpleCompanyToDB();
    }

    @Test
    @WithMockUser
    public void whenPost_withoutCriteria_thenCorrectAnswer() throws Exception {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        dbOperations.addSimpleCarToDb(office, priceList);
        dbOperations.addSimpleCarToDb(office, priceList);

        //and
        var dateFrom = LocalDate.now();
        var dateTo = LocalDate.now().plusDays(2);
        var pickUpOfficeId = office.getId();
        var returnOfficeId = office.getId();

        //when
        var result = mockMvc.perform(post(CAR_SEARCH)
                .param("dateFrom", dateFrom.format(DateTimeFormatter.ISO_DATE))
                .param("dateTo", dateTo.format(DateTimeFormatter.ISO_DATE))
                .param("pickUpOfficeId", String.valueOf(pickUpOfficeId))
                .param("returnOfficeId", String.valueOf(returnOfficeId))
        );
        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<CarSearchResponse> responses = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(2, responses.size());
    }

    @Test
    @WithMockUser
    public void whenPost_withCriteria_thenCorrectAnswer() throws Exception {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var car1 = dbOperations.addSimpleCarToDb(office, priceList);
        car1.setColor(Color.RED);
        var car2 = dbOperations.addSimpleCarToDb(office, priceList);
        car2.setColor(Color.BLACK);

        //and
        var dateFrom = LocalDate.now();
        var dateTo = LocalDate.now().plusDays(2);
        var pickUpOfficeId = office.getId();
        var returnOfficeId = office.getId();

        //and
        var criteria = CarSearchByCriteriaRequest.builder()
                .colorOf(Set.of(Color.RED))
                .build();

        //when
        var result = mockMvc.perform(post(CAR_SEARCH)
                .param("dateFrom", dateFrom.format(DateTimeFormatter.ISO_DATE))
                .param("dateTo", dateTo.format(DateTimeFormatter.ISO_DATE))
                .param("pickUpOfficeId", String.valueOf(pickUpOfficeId))
                .param("returnOfficeId", String.valueOf(returnOfficeId))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(toJsonString(criteria))
        );
        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<CarSearchResponse> responses = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(1, responses.size());
    }

    @WithMockUser
    @ParameterizedTest
    @MethodSource("searchRequestIncorrectDateParameters")
    public void whenPost_thenDateValidationFailed(String dateFrom, String dateTo) throws Exception {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        dbOperations.addSimpleCarToDb(office, priceList);

        //when
        var result = mockMvc.perform(post(CAR_SEARCH)
                .param("dateFrom", dateFrom)
                .param("dateTo", dateTo)
                .param("pickUpOfficeId", String.valueOf(office.getId()))
                .param("returnOfficeId", String.valueOf(office.getId()))
        );
        //then
        result.andExpect(status().isBadRequest());

    }

    @WithMockUser
    @Test
    public void whenPost_thenExistingOfficeIdValidationFailed() throws Exception {
        //given
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        dbOperations.addSimpleCarToDb(office, priceList);

        //and
        var dateFrom = LocalDate.now();
        var dateTo = LocalDate.now().plusDays(2);
        var pickUpOfficeId = Long.MAX_VALUE;
        var returnOfficeId = office.getId();

        //when
        var result = mockMvc.perform(post(CAR_SEARCH)
                .param("dateFrom", dateFrom.format(DateTimeFormatter.ISO_DATE))
                .param("dateTo", dateTo.format(DateTimeFormatter.ISO_DATE))
                .param("pickUpOfficeId", String.valueOf(pickUpOfficeId))
                .param("returnOfficeId", String.valueOf(returnOfficeId))
        );
        //then
        result.andExpect(status().isBadRequest());

    }

    private static Stream<Arguments> searchRequestIncorrectDateParameters() {
        return Stream.of(
                Arguments.of(LocalDate.now().minusDays(10).format(DateTimeFormatter.ISO_DATE), LocalDate.now().format(DateTimeFormatter.ISO_DATE)),
                Arguments.of(LocalDate.now().format(DateTimeFormatter.ISO_DATE), LocalDate.now().minusDays(10).format(DateTimeFormatter.ISO_DATE)),

                Arguments.of(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), LocalDate.now().format(DateTimeFormatter.ISO_DATE)),
                Arguments.of(LocalDate.now().format(DateTimeFormatter.ISO_DATE), LocalDate.now().format(DateTimeFormatter.ISO_WEEK_DATE)),
                Arguments.of(LocalDate.now().format(DateTimeFormatter.ISO_DATE), null),
                Arguments.of(null, LocalDate.now().format(DateTimeFormatter.ISO_DATE))
        );
    }

}
