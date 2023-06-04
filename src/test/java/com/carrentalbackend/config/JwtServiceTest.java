package com.carrentalbackend.config;

import com.carrentalbackend.config.security.JwtService;
import com.carrentalbackend.exception.MissingTokenClaimException;
import com.carrentalbackend.model.entity.User;
import com.carrentalbackend.model.enumeration.Role;
import com.carrentalbackend.util.factories.AuthFactory;
import com.carrentalbackend.util.factories.UserFactory;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
    @InjectMocks
    private JwtService jwtService;

    @Test
    public void whenIsTokenValid_thenTrue() {
        //given
        String token = AuthFactory.getSimpleUserToken();
        User user = UserFactory.getSimpleUser();

        //when
        var result = jwtService.isTokenValid(token, user);

        //then
        assertTrue(result);
    }

    @Test
    public void whenIsTokenValid_thenFalse() {
        //given
        String token = generateToken("invalid subject", new Date(System.currentTimeMillis() + 1000 * 60 * 60), UserFactory.simpleUserRole);

        //and
        User user = UserFactory.getSimpleUser();

        //when
        var result = jwtService.isTokenValid(token, user);

        //then
        assertFalse(result);
    }

    @Test
    public void whenIsTokenValid_thenExpiredJwtException() {
        //given
        String token = generateToken(UserFactory.simpleUserEmail, new Date(System.currentTimeMillis() - 1000), UserFactory.simpleUserRole);

        //and
        User user = UserFactory.getSimpleUser();

        //when and then
        assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenValid(token, user));
    }

    @Test
    public void whenExtractRole_thenExtractCorrect() {
        //given
        String token = AuthFactory.getSimpleUserToken();

        //when
        var result = jwtService.extractRole(token);

        //then
        assertEquals(UserFactory.simpleUserRole, result);
    }

    @Test
    public void whenExtractRole_thenMissingTokenClaimException() {
        //given
        Key signingKey = AuthFactory.getSigningKey();

        //and
        var token = Jwts
                .builder()
                .setSubject(UserFactory.simpleUserEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
        //when and then
        assertThrows(MissingTokenClaimException.class,
                () -> jwtService.extractRole(token));
    }

    @Test
    public void whenExtractUsername_thenExtractCorrectly() {
        //given
        String token = AuthFactory.getSimpleUserToken();
        String username = UserFactory.simpleUserEmail;

        //when
        var result = jwtService.extractUsername(token);

        //then
        assertEquals(username, result);
    }

    @Test
    public void whenExtractUsername_thenMissingTokenClaimException() {
        //given
        Key signingKey = AuthFactory.getSigningKey();
        Map<String, Object> extraClaims = AuthFactory.getSimpleTokenExtraClaims();

        //and
        String token = Jwts
                .builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

        //when and then
        assertThrows(MissingTokenClaimException.class,
                () -> jwtService.extractUsername(token));
    }

    private String generateToken(String subject, Date expiration, Role role) {
        Map<String, Object> extraClaims = generateExtraClaims(role);
        Key signingKey = AuthFactory.getSigningKey();

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiration)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Map<String, Object> generateExtraClaims(Role role) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("rol", role);
        return extraClaims;
    }

}
