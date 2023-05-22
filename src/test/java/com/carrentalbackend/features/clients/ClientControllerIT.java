package com.carrentalbackend.features.clients;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.features.clients.register.ClientCreateRequest;
import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.util.AddressFactory;
import com.carrentalbackend.util.ClientFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Stream;

import static com.carrentalbackend.config.ApiConstraints.CLIENT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientControllerIT extends BaseIT {


    @Test
    public void whenSaveClient_thenResponseCreated() throws Exception {
        //given
        ClientCreateRequest clientCreateRequest = ClientFactory.getSimpleClientCreateRequestBuilder().build();
        String clientCreateRequestJson = toJsonString(clientCreateRequest);

        //when
        var result = sendCreateRequest(clientCreateRequestJson);

        //then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.firstName").value(ClientFactory.simpleFirstName))
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
        var request = ClientFactory.getSimpleClientCreateRequestBuilder().build();
        var simpleClientCreateRequestJson = toJsonString(request);

        //and
        dbOperations.addSimpleClientToDB();

        //when
        var result = sendCreateRequest(simpleClientCreateRequestJson);

        //then
        result.andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource("saveIncorrectParameters")
    public void whenSaveClient_thenValidationFailed(ClientCreateRequest request) throws Exception {
        //given
        String clientCreateRequestJson = toJsonString(request);

        //when
        var result = sendCreateRequest(clientCreateRequestJson);

        //then
        result.andExpect(status().isForbidden());
    }


    private ResultActions sendCreateRequest(String request) throws Exception {
        return mockMvc.perform(post(CLIENT).contentType(MediaType.APPLICATION_JSON_VALUE).content(request));
    }

    private static Stream<Arguments> saveIncorrectParameters() {
        return Stream.of(
                Arguments.of(ClientFactory.getSimpleClientCreateRequestBuilder().firstName("    ").build()),
                Arguments.of(ClientFactory.getSimpleClientCreateRequestBuilder().lastName(null).build()),
                Arguments.of(ClientFactory.getSimpleClientCreateRequestBuilder().email("wrong@formatted@email").build()),
                Arguments.of(ClientFactory.getSimpleClientCreateRequestBuilder().address(new Address()).build()),
                Arguments.of(ClientFactory.getSimpleClientCreateRequestBuilder().password("").build())
        );
    }
}
