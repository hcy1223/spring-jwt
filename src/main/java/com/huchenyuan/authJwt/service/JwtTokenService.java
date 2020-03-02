package com.huchenyuan.authJwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtTokenService {

    private static final long JWT_TIME_OUT = 1000 * 5 * 60 * 60;
    private static final String SECRET = "hcy32j3kjdasdljaklj4";

    public boolean validateToken(UserDetails userDetails, String token) {
        final String username = getUsernameFromToken(token);
        return userDetails.getUsername().equals(username) && checkTokenExpired(token);
    }

    public String generateToken(UserDetails userDetails) {
        long now = System.currentTimeMillis();
        return Jwts.builder().setClaims(new HashMap<>())
                .setIssuedAt(new Date(now))
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(now + JWT_TIME_OUT))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private boolean checkTokenExpired(String token) {
        Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date());

    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}
