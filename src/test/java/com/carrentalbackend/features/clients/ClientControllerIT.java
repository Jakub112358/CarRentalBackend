package com.carrentalbackend.features.clients;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.util.AddressFactory;
import com.carrentalbackend.util.ClientFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;
import java.util.stream.Stream;

import static com.carrentalbackend.config.ApiConstraints.CLIENT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientControllerIT extends BaseIT {


    @Test
    public void whenSaveClient_thenResponseCreated() throws Exception {
        //given
        ClientCreateUpdateRequest clientRequest = ClientFactory.getSimpleClientRequestBuilder().build();
        String clientRequestJson = toJsonString(clientRequest);

        //when
        var result = sendPostRequest(clientRequestJson);

        //then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(Matchers.greaterThan(0)));

        expectSimpleClient(result);
    }

    private void expectSimpleClient(ResultActions result) throws Exception {
        result.andExpect(jsonPath("$.firstName").value(ClientFactory.simpleFirstName))
                .andExpect(jsonPath("$.lastName").value(ClientFactory.simpleLastName))
                .andExpect(jsonPath("$.email").value(ClientFactory.simpleEmail))
                .andExpect(jsonPath("$.address.id").value(Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.address.zipCode").value(AddressFactory.simpleZipCode))
                .andExpect(jsonPath("$.address.town").value(AddressFactory.simpleTown))
                .andExpect(jsonPath("$.address.street").value(AddressFactory.simpleStreet))
                .andExpect(jsonPath("$.address.houseNumber").value(AddressFactory.simpleHouseNumber));
    }

    @Test
    public void whenSaveClientWithAlreadyRegisteredEmail_thenValidationFailed() throws Exception {
        //given
        var request = ClientFactory.getSimpleClientRequestBuilder().build();
        var simpleClientRequestJson = toJsonString(request);

        //and
        dbOperations.addSimpleClientToDB();

        //when
        var result = sendPostRequest(simpleClientRequestJson);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    public void whenSaveClient_thenBadRequest() throws Exception {
        //given
        var request = new Object();

        //when
        var result = sendPostRequest(request.toString());

        //then
        result.andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("saveIncorrectParameters")
    public void whenSaveClient_thenValidationFailed(ClientCreateUpdateRequest request) throws Exception {
        //given
        String clientRequestJson = toJsonString(request);

        //when
        var result = sendPostRequest(clientRequestJson);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT", username = ClientFactory.simpleEmail)
    public void whenClientGetById_thenCorrectAnswer() throws Exception {
        //given
        var id = dbOperations.addSimpleClientToDB().getId();
        var path = CLIENT + "/" + id;

        //when
        var result = sendGetRequest(path);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @WithMockUser(roles = "CLIENT", username = "incorrect username")
    public void whenClientGetById_thenForbidden() throws Exception {
        //given
        var id = dbOperations.addSimpleClientToDB().getId();
        var path = CLIENT + "/" + id;

        //when
        var result = sendGetRequest(path);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenAdminGetById_thenCorrectAnswer() throws Exception {
        //given
        var id = dbOperations.addSimpleClientToDB().getId();
        var path = CLIENT + "/" + id;

        //when
        var result = sendGetRequest(path);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenAdminGetAll_thenCorrectAnswer() throws Exception {
        //given
        dbOperations.addSimpleClientToDB();

        //when
        var result = sendGetRequest(CLIENT);

        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<Client> clients = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        Assertions.assertTrue(clients.size() > 0);
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenClientGetAll_thenForbidden() throws Exception {
        //given
        dbOperations.addSimpleClientToDB();

        //when
        var result = sendGetRequest(CLIENT);

        //then
        result.andExpect(status().isForbidden());
    }


    private ResultActions sendPatchRequest(String path, String request) throws Exception {
        return mockMvc.perform(patch(path).contentType(MediaType.APPLICATION_JSON_VALUE).content(request));
    }


    private ResultActions sendPostRequest(String request) throws Exception {
        return mockMvc.perform(post(CLIENT).contentType(MediaType.APPLICATION_JSON_VALUE).content(request));
    }

    private ResultActions sendGetRequest(String path) throws Exception {
        return mockMvc.perform(get(path));
    }

    private static Stream<Arguments> saveIncorrectParameters() {
        return Stream.of(
                Arguments.of(ClientFactory.getSimpleClientRequestBuilder().firstName("    ").build()),
                Arguments.of(ClientFactory.getSimpleClientRequestBuilder().lastName(null).build()),
                Arguments.of(ClientFactory.getSimpleClientRequestBuilder().email("wrong@formatted@email").build()),
                Arguments.of(ClientFactory.getSimpleClientRequestBuilder().address(new Address()).build()),
                Arguments.of(ClientFactory.getSimpleClientRequestBuilder().password("").build())
        );
    }
}
