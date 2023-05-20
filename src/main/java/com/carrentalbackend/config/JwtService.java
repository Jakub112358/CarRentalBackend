package com.carrentalbackend.config;

import com.carrentalbackend.exception.MissingTokenClaimException;
import com.carrentalbackend.model.enumeration.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private static final String SECRET_KEY = "462D4A614E645266556A586E3272357538782F413F4428472B4B625065536856";

    public String extractUsername(String jwt) {
        String subjectClaim = getAllClaims(jwt).getSubject();
        if(subjectClaim == null){
            throw new MissingTokenClaimException("subject");
        } else {
            return subjectClaim;
        }
    }

    public Role extractRole(String jwt){
        String roleClaim = getAllClaims(jwt).get("rol", String.class);
        if(roleClaim == null){
            throw new MissingTokenClaimException("role");
        } else{
            return Role.valueOf(roleClaim);
        }
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
    }

    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims getAllClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenExpired(String jwt) {
        Date expiration = getAllClaims(jwt).getExpiration();
        if(expiration == null){
            return true;
        }
        return expiration.before(new Date());
    }
}
