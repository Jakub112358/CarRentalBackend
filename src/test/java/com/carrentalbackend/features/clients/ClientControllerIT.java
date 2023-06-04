package com.carrentalbackend.features.clients;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.features.clients.rest.ClientCreateRequest;
import com.carrentalbackend.features.clients.rest.ClientResponse;
import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.util.factories.AddressFactory;
import com.carrentalbackend.util.factories.ClientFactory;
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

import static com.carrentalbackend.config.ApiConstraints.CLIENT;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientControllerIT extends BaseIT {

    @BeforeEach
    void setUp() {
        dbOperations.cleanUserTable();
    }

    @Test
    public void whenSaveClient_thenResponseCreated() throws Exception {
        //given
        ClientCreateRequest clientRequest = ClientFactory.getSimpleClientRequestBuilder().build();
        String clientRequestJson = toJsonString(clientRequest);

        //when
        var result = requestTool.sendPostRequest(CLIENT, clientRequestJson);

        //then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(Matchers.greaterThan(0)));

        expectSimpleClient(result);
    }


    @Test
    public void whenSaveClientWithAlreadyRegisteredEmail_thenValidationFailed() throws Exception {
        //given
        var request = ClientFactory.getSimpleClientRequestBuilder().build();
        var simpleClientRequestJson = toJsonString(request);

        //and
        dbOperations.addSimpleClientToDB();

        //when
        var result = requestTool.sendPostRequest(CLIENT, simpleClientRequestJson);

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void whenSaveClient_thenBadRequest() throws Exception {
        //given
        var request = new Object();

        //when
        var result = requestTool.sendPostRequest(CLIENT, request.toString());

        //then
        result.andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("clientRequestIncorrectParameters")
    public void whenSaveClient_thenValidationFailed(ClientCreateRequest request) throws Exception {
        //given
        String clientRequestJson = toJsonString(request);

        //when
        var result = requestTool.sendPostRequest(CLIENT, clientRequestJson);

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "CLIENT", username = ClientFactory.simpleEmail)
    public void whenClientGetById_thenCorrectAnswer() throws Exception {
        //given
        var id = dbOperations.addSimpleClientToDB().getId();
        var path = CLIENT + "/" + id;

        //when
        var result = requestTool.sendGetRequest(path);

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
        var result = requestTool.sendGetRequest(path);

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
        var result = requestTool.sendGetRequest(path);

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
        var result = requestTool.sendGetRequest(CLIENT);

        //then
        result.andExpect(status().isOk());

        //and
        var responseJson = result.andReturn().getResponse().getContentAsString();
        Set<ClientResponse> clients = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, clients.size());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenClientGetAll_thenForbidden() throws Exception {
        //given
        dbOperations.addSimpleClientToDB();

        //when
        var result = requestTool.sendGetRequest(CLIENT);

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT", username = ClientFactory.simpleEmail)
    public void whenClientUpdate_thenCorrectAnswer() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var path = CLIENT + "/" + client.getId();

        //and
        var changedEmail = "changed@mail.com";
        var updateRequest = ClientFactory.getSimpleClientRequestBuilder()
                .email(changedEmail)
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.firstName").value(client.getFirstName()))
                .andExpect(jsonPath("$.email").value(changedEmail));
    }

    @ParameterizedTest
    @MethodSource("clientRequestIncorrectParameters")
    @WithMockUser(roles = "CLIENT", username = ClientFactory.simpleEmail)
    public void whenClientUpdate_thenValidationFailed(ClientCreateRequest updateRequest) throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var path = CLIENT + "/" + client.getId();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "CLIENT", username = "incorrect@username.com")
    public void whenClientUpdate_thenForbidden() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var path = CLIENT + "/" + client.getId();

        //and
        var changedEmail = "changed@mail.com";
        var updateRequest = ClientFactory.getSimpleClientRequestBuilder()
                .email(changedEmail)
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenUpdate_thenEmailAlreadyExists() throws Exception {
        //given
        var user = dbOperations.addSimpleUserToDB();
        var client = dbOperations.addSimpleClientToDB();
        var path = CLIENT + "/" + client.getId();

        //and
        var changedEmail = user.getEmail();
        var updateRequest = ClientFactory.getSimpleClientRequestBuilder()
                .email(changedEmail)
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenClientUpdate_ByAdmin_thenCorrectAnswer() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var path = CLIENT + "/" + client.getId();

        //and
        var changedEmail = "changed@mail.com";
        var updateRequest = ClientFactory.getSimpleClientRequestBuilder()
                .email(changedEmail)
                .build();

        //when
        var result = requestTool.sendPatchRequest(path, toJsonString(updateRequest));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.firstName").value(client.getFirstName()))
                .andExpect(jsonPath("$.email").value(changedEmail));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenDeleteClient_ByAdmin_thenCorrectAnswer() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var path = CLIENT + "/" + client.getId();

        //and
        assertTrue(clientRepository.existsByEmail(client.getEmail()));

        //when
        var result = sendDeleteRequest(path);

        //then
        result.andExpect(status().isNoContent());

        //and
        assertFalse(clientRepository.existsByEmail(client.getEmail()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenDeleteClient_ByAdmin_thenNotFound() throws Exception {
        //given
        long id = Long.MAX_VALUE;
        var path = CLIENT + "/" + id;

        //when
        var result = sendDeleteRequest(path);

        //then
        result.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void whenDeleteClient_ByClient_thenForbidden() throws Exception {
        //given
        var client = dbOperations.addSimpleClientToDB();
        var path = CLIENT + "/" + client.getId();

        //and
        assertTrue(clientRepository.existsByEmail(client.getEmail()));

        //when
        var result = sendDeleteRequest(path);

        //then
        result.andExpect(status().isForbidden());
    }

    private ResultActions sendDeleteRequest(String path) throws Exception {
        return mockMvc.perform(delete(path));
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

    private static Stream<Arguments> clientRequestIncorrectParameters() {
        return Stream.of(
                Arguments.of(ClientFactory.getSimpleClientRequestBuilder().firstName("    ").build()),
                Arguments.of(ClientFactory.getSimpleClientRequestBuilder().lastName(null).build()),
                Arguments.of(ClientFactory.getSimpleClientRequestBuilder().email("wrong@formatted@email").build()),
                Arguments.of(ClientFactory.getSimpleClientRequestBuilder().address(new Address()).build()),
                Arguments.of(ClientFactory.getSimpleClientRequestBuilder().password("").build())
        );
    }
}
