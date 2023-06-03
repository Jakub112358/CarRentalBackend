package com.carrentalbackend.features.companies;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.features.companies.rest.CompanyRequest;
import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.util.factories.AddressFactory;
import com.carrentalbackend.util.factories.CompanyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.stream.Stream;

import static com.carrentalbackend.config.ApiConstraints.COMPANY;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CompanyControllerIT extends BaseIT {


    @BeforeEach
    void setUp() {
        dbOperations.cleanCompanyTable();
    }

    @Test
    public void whenFindCompany_thenCorrectAnswer() throws Exception {
        //given
        dbOperations.addSimpleCompanyToDB();

        //when
        var result = requestTool.sendGetRequest(COMPANY);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(CompanyFactory.simpleCompanyName));
    }

    @Test
    public void whenFindCompany_thenNotFound() throws Exception {
        //when
        var result = requestTool.sendGetRequest(COMPANY);

        //then
        result.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdateCompany_thenCorrectAnswer() throws Exception {
        //given
        dbOperations.addSimpleCompanyToDB();

        //and
        var changedName = "changed name";
        var updateRequest = CompanyFactory.getSimpleCompanyRequestBuilder()
                .name(changedName)
                .build();

        //when
        var result = requestTool.sendPatchRequest(COMPANY, toJsonString(updateRequest));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(changedName))
                .andExpect(jsonPath("$.address.street").value(AddressFactory.simpleStreet));
    }

    @ParameterizedTest
    @MethodSource("CompanyRequestIncorrectParameters")
    @WithMockUser(roles = "ADMIN")
    public void whenUpdateCompany_thenValidationFailed(CompanyRequest updateRequest) throws Exception {
        //given
        dbOperations.addSimpleCompanyToDB();

        //when
        var result = requestTool.sendPatchRequest(COMPANY, toJsonString(updateRequest));

        //then
        result.andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void whenUpdateCompany_thenForbidden() throws Exception {
        //given
        dbOperations.addSimpleCompanyToDB();

        //and
        var changedName = "changed name";
        var updateRequest = CompanyFactory.getSimpleCompanyRequestBuilder()
                .name(changedName)
                .build();

        //when
        var result = requestTool.sendPatchRequest(COMPANY, toJsonString(updateRequest));

        //then
        result.andExpect(status().isForbidden());
    }


    private static Stream<Arguments> CompanyRequestIncorrectParameters() {
        Address invalidAddress = AddressFactory.getSimpleAddress();
        invalidAddress.setZipCode("invalid zip code");
        return Stream.of(
                Arguments.of(CompanyFactory.getSimpleCompanyRequestBuilder().name(null).build()),
                Arguments.of(CompanyFactory.getSimpleCompanyRequestBuilder().name("  ").build()),
                Arguments.of(CompanyFactory.getSimpleCompanyRequestBuilder().domain("").build()),
                Arguments.of(CompanyFactory.getSimpleCompanyRequestBuilder().address(null).build()),
                Arguments.of(CompanyFactory.getSimpleCompanyRequestBuilder().address(invalidAddress).build()),
                Arguments.of(CompanyFactory.getSimpleCompanyRequestBuilder().differentOfficesExtraCharge(-0.1).build()),
                Arguments.of(CompanyFactory.getSimpleCompanyRequestBuilder().mediumTermRentMinDays(0).build()),
                Arguments.of(CompanyFactory.getSimpleCompanyRequestBuilder().longTermRentMinDays(-1).build())
        );

    }

}
