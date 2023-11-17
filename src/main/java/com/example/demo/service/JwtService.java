package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    // Todo: import secret key from .env file
    private static final String SECRET_KEY = "PdRgUkXp2s5v8y3g324sweBEHMbQeThVmYq3t6w9zCFJNcRfUjXn2r4u7xAG";
    private static final int TOKEN_EXPIRATION_HOURS = 1000;

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public Date getHoursInDateTime(int hours) {
        return new Date(System.currentTimeMillis() + 1000L * 60 * 60 * hours);
    }

    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(getHoursInDateTime(0))
                .setExpiration(getHoursInDateTime(TOKEN_EXPIRATION_HOURS))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Claims = map - info about user in token
    public Claims extractAllClaims(String token) {
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    // generic fn - extract specific claim from claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userEmail = extractUserEmail(token);
        return userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String getJwtFromCookie(HttpServletRequest request) {
        // String jwt = null;
        // if (request.getCookies() != null) {
        //     for (var cookie : request.getCookies()) {
        //         if (cookie.getName().equals("jwt")) {
        //             jwt = cookie.getValue();
        //             break;
        //         }
        //     }
        // }
        String jwt = null;
        String header=request.getHeader("Authorization");
        if(header!=null){
            String[] token=header.split(" ");
            if(token.length>1  && !token[1].equals("null")){
                jwt=token[1];
            } 
            System.out.println(jwt);
        }
        return jwt;
    }
}
