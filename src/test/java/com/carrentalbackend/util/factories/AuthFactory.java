package com.carrentalbackend.util.factories;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthFactory {
    public static String emptyToken = "";
    public static String SECRET_KEY = "462D4A614E645266556A586E3272357538782F413F4428472B4B625065536856";

    public static String getSimpleUserToken(){
        Map<String, Object> extraClaims = getSimpleTokenExtraClaims();


        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(UserFactory.simpleUserEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static Map<String, Object> getSimpleTokenExtraClaims(){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("rol", UserFactory.simpleUserRole);
        return extraClaims;
    }

    public static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
