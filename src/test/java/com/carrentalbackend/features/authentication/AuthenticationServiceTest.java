package com.carrentalbackend.features.authentication;

import com.carrentalbackend.config.security.JwtService;
import com.carrentalbackend.features.authentication.rest.AuthenticationRequest;
import com.carrentalbackend.model.entity.User;
import com.carrentalbackend.repository.UserRepository;
import com.carrentalbackend.util.AuthFactory;
import com.carrentalbackend.util.UserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;

    @Test
    public void whenAuthenticate_thenShouldReturnAuthenticationResponse() {
        //given
        AuthenticationRequest request = getSimpleUserAuthenticationRequest();

        //and
        ArgumentCaptor<UsernamePasswordAuthenticationToken> tokenCaptor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);

        //and
        setMocks_correctResponse();

        //when
        var result = authenticationService.authenticate(request);

        //then
        verify(authenticationManager).authenticate(tokenCaptor.capture());

        //and
        assertEquals(AuthFactory.emptyToken, result.getToken());
    }

    @Test
    public void whenAuthenticate_thenShouldThrow() {
        //given
        AuthenticationRequest request = getSimpleUserAuthenticationRequest();

        //and
        setMocks_wrongResponse();


        //when and then
        assertThrows(BadCredentialsException.class,
                () -> authenticationService.authenticate(request));
    }



    private void setMocks_wrongResponse() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException(""));
    }

    private void setMocks_correctResponse() {
        when(userRepository.findByEmail(UserFactory.simpleUserEmail)).thenReturn(Optional.of(UserFactory.getSimpleUser()));
        when(jwtService.generateToken(any(User.class), anyMap())).thenReturn(AuthFactory.emptyToken);
    }

    private AuthenticationRequest getSimpleUserAuthenticationRequest() {
        return new AuthenticationRequest(UserFactory.simpleUserEmail, UserFactory.simpleUserPassword);
    }
}
