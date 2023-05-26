package com.carrentalbackend.features.employees;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.features.employees.rest.EmployeeResponse;
import com.carrentalbackend.util.factories.EmployeeFactory;
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

import static com.carrentalbackend.config.ApiConstraints.EMPLOYEE;
import static com.carrentalbackend.features.employees.rest.EmployeeRequest.EmployeeRequestBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerIT extends BaseIT {

    @BeforeEach
    void cleanTables() {
        dbOperations.cleanOfficeTable();
        dbOperations.cleanEmployeeTable();
        dbOperations.cleanUserTable();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenResponseCreated() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var request = EmployeeFactory.getSimpleEmployeeRequestBuilder(office.getId()).build();

        //when
        var result = requestTool.sendPostRequest(EMPLOYEE, toJsonString(request));

        //then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(Matchers.greaterThan(0)));

        expectSimpleEmployee(result, office.getId());
    }

    @ParameterizedTest
    @MethodSource("employeeRequestBuilderIncorrectParameters")
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenBasicValidationFailed(EmployeeRequestBuilder requestBuilder) throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var request = requestBuilder
                .officeId(office.getId()) //setting valid office id
                .build();

        //when
        var result = requestTool.sendPostRequest(EMPLOYEE, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenExistingOfficeIdValidationFailed() throws Exception {
        //given
        var request = EmployeeFactory.getSimpleEmployeeRequestBuilder(Long.MAX_VALUE).build();

        //when
        var result = requestTool.sendPostRequest(EMPLOYEE, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenSave_thenForbidden() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();

        //and
        var request = EmployeeFactory.getSimpleEmployeeRequestBuilder(office.getId()).build();

        //when
        var result = requestTool.sendPostRequest(EMPLOYEE, toJsonString(request));

        //then
        result.andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenSave_thenBadRequest() throws Exception {
        //given
        var request = new Object();

        //when
        var result = requestTool.sendPostRequest(EMPLOYEE, request.toString());

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindById_thenCorrectAnswer() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var id = dbOperations.addSimpleEmployeeToDB(office).getId();
        var path = EMPLOYEE + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        //and
        expectSimpleEmployee(result, office.getId());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindById_thenNotFound() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        dbOperations.addSimpleEmployeeToDB(office);

        //and
        var path = EMPLOYEE + "/" + Long.MAX_VALUE;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenFindById_thenForbidden() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var id = dbOperations.addSimpleEmployeeToDB(office).getId();
        var path = EMPLOYEE + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenFindAll_thenCorrectAnswer() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        dbOperations.addSimpleEmployeeToDB(office);

        //when
        var result = requestTool.sendGetRequest(EMPLOYEE);

        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<EmployeeResponse> employees = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(1, employees.size());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenFindAll_thenForbidden() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        dbOperations.addSimpleEmployeeToDB(office);

        //when
        var result = requestTool.sendGetRequest(EMPLOYEE);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenCorrectAnswer() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var path = EMPLOYEE + "/" + employee.getId();

        //and
        var changedLastName = "Brzeczyszczykiewicz";
        var updateRequest = EmployeeFactory.getSimpleEmployeeRequestBuilder(office.getId())
                .lastName(changedLastName)
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.lastName").value(changedLastName));
    }

    @Test
    public void whenUpdate_thenForbidden() throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var path = EMPLOYEE + "/" + employee.getId();

        //and
        var changedLastName = "Brzeczyszczykiewicz";
        var updateRequest = EmployeeFactory.getSimpleEmployeeRequestBuilder(office.getId())
                .lastName(changedLastName)
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource("employeeRequestBuilderIncorrectParameters")
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenValidationFailed(EmployeeRequestBuilder requestBuilder) throws Exception {
        //given
        var office = dbOperations.addSimpleOfficeToDB();
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var path = EMPLOYEE + "/" + employee.getId();

        //and
        var request = requestBuilder.officeId(office.getId()).build();

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
        var employee = dbOperations.addSimpleEmployeeToDB(office);
        var path = EMPLOYEE + "/" + employee.getId();

        //and
        var request = new Object();

        //when
        var result = requestTool.sendPatchRequest(path, request.toString());

        //then
        result.andExpect(status().isBadRequest());
    }

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
//
    private static Stream<Arguments> employeeRequestBuilderIncorrectParameters() {
        return Stream.of(
                Arguments.of(EmployeeFactory.getSimpleEmployeeRequestBuilder(null).firstName("")),
                Arguments.of(EmployeeFactory.getSimpleEmployeeRequestBuilder(null).lastName(null)),
                Arguments.of(EmployeeFactory.getSimpleEmployeeRequestBuilder(null).jobPosition(null)),
                Arguments.of(EmployeeFactory.getSimpleEmployeeRequestBuilder(null).email("incorrect email format")),
                Arguments.of(EmployeeFactory.getSimpleEmployeeRequestBuilder(null).password(""))
        );
    }

    private void expectSimpleEmployee(ResultActions result, Long officeId) throws Exception {
        result.andExpect(jsonPath("$.email").value(EmployeeFactory.simpleEmployeeEmail))
                .andExpect(jsonPath("$.firstName").value(EmployeeFactory.simpleEmployeeFirstName))
                .andExpect(jsonPath("$.lastName").value(EmployeeFactory.simpleEmployeeLastName))
                .andExpect(jsonPath("$.jobPosition").value(EmployeeFactory.simpleEmployeeJobPosition.name()))
                .andExpect(jsonPath("$.officeId").value(officeId));
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
