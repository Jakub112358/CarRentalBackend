package com.carrentalbackend.features.authentication;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.features.authentication.rest.AuthenticationRequest;
import com.carrentalbackend.util.factories.UserFactory;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

import static com.carrentalbackend.config.ApiConstraints.AUTHENTICATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthenticationControllerIT extends BaseIT {

    @Test
    public void whenAuthenticate_thenCorrectResponse() throws Exception {
        //given
        dbOperations.addSimpleUserToDB();

        //and
        var requestContentJson = getRequestContentJson(UserFactory.simpleUserEmail, UserFactory.simpleUserPassword);

        //when
        var result = requestTool.sendPostRequest(AUTHENTICATION, requestContentJson);

        //then
        result.andExpect(status().isOk());

        //and
        String token = extractToken(result);
        assertEquals(UserFactory.simpleUserEmail, jwtService.extractUsername(token));
        assertEquals(UserFactory.simpleUserRole, jwtService.extractRole(token));
    }

    @ParameterizedTest
    @MethodSource("authenticateParams")
    public void whenAuthenticate_thenForbiddenResponse(String email, String password) throws Exception {
        //given
        dbOperations.addSimpleUserToDB();

        //and
        var requestContentJson = getRequestContentJson(email, password);

        //when
        var result = requestTool.sendPostRequest(AUTHENTICATION, requestContentJson);

        //then
        result.andExpect(status().isForbidden());
    }

    private String extractToken(ResultActions result) throws UnsupportedEncodingException {
        return JsonPath.read(result.andReturn().getResponse().getContentAsString(), "$.token");
    }

    private String getRequestContentJson(String email, String password) {
        var authenticationRequest = new AuthenticationRequest(email, password);
        return toJsonString(authenticationRequest);
    }

    private static Stream<Arguments> authenticateParams() {
        return Stream.of(
                Arguments.of(UserFactory.simpleUserEmail, "wrong password"),
                Arguments.of("wrong email", UserFactory.simpleUserPassword),
                Arguments.of(null, UserFactory.simpleUserPassword),
                Arguments.of(UserFactory.simpleUserEmail, null)
        );
    }
}
