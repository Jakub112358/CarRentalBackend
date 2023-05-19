package com.carrentalbackend.features.authentication;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.util.UserFactory;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

import static com.carrentalbackend.config.ApiConstraints.AUTHENTICATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthenticationControllerIT extends BaseIT {

    @Test
    public void whenAuthenticate_thenCorrectResponse() throws Exception {
        //given
        addSimpleUserToDB();

        //and
        var requestContentJson = getRequestContentJson(UserFactory.simpleUserEmail, UserFactory.simpleUserPassword);

        //when
        var result = sendRequest(requestContentJson);

        //then
        result.andExpect(status().isOk());

        //and
        String token = extractToken(result);
        assertEquals(jwtService.extractUsername(token), UserFactory.simpleUserEmail);
        assertEquals(jwtService.extractRole(token), UserFactory.simpleUserRole);
    }

    @ParameterizedTest
    @MethodSource("authenticateParams")
    public void whenAuthenticate_thenForbiddenResponse(String email, String password) throws Exception {
        //given
        addSimpleUserToDB();

        //and
        var requestContentJson = getRequestContentJson(email, password);

        //when
        var result = sendRequest(requestContentJson);

        //then
        result.andExpect(status().isForbidden());
    }

    private String extractToken(ResultActions result) throws UnsupportedEncodingException {
        return JsonPath.read(result.andReturn().getResponse().getContentAsString(), "$.token");
    }

    private ResultActions sendRequest(String requestContentJson) throws Exception {
        return mockMvc.perform(post(AUTHENTICATION).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestContentJson));
    }

    private String getRequestContentJson(String email, String password) {
        var authenticationRequest = new AuthenticationRequest(email, password);
        return toJsonString(authenticationRequest);
    }

    private void addSimpleUserToDB() {
        var user = UserFactory.simpleUser();
        userRepository.save(user);
        assertTrue(userRepository.existsByEmail(user.getEmail()));
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
