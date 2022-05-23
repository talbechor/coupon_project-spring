package com.coupon.coupon_projectspring.utils;



import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.UserDetails;
import com.coupon.coupon_projectspring.exceptions.LoginException;
import com.coupon.coupon_projectspring.exceptions.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTUtils {
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();

    private String encodedSecretKey = "this+is+my+key+and+i+cry+if+i+want+to+you+little+POS";

    private Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), this.signatureAlgorithm);

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userType", userDetails.getClientType());
        return createToken(claims, userDetails.getEmail());
    }

    //we create the JWT token from the information that we got.
    private String createToken(Map<String, Object> claims, String email) {
        Instant now = Instant.now();
        return "Bearer "+ Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecretKey)
                .compact();
    }

    //create method that parse all the information from token
    private Claims extractAllClaims(String token) throws ExpiredJwtException, MalformedJwtException  {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(decodedSecretKey).build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String extractSignature(String token) throws ExpiredJwtException, MalformedJwtException {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(decodedSecretKey).build();
        return jwtParser.parseClaimsJws(token).getSignature();
    }

    //create helping method to get claims information
    // get userType
    public String extractUserType(String token) {
        Claims claims = extractAllClaims(token.replace("Bearer ", ""));
        return (String) claims.get("userType");
    }

    // get email=subject
    public String extractEmail(String token) {
        return extractAllClaims(token.replace("Bearer ", "")).getSubject();
    }

    // get expired
    public Date extractExpirationDate(String token) {
        return extractAllClaims(token.replace("Bearer ", "")).getExpiration();
    }

    // create method to check token validation
    public boolean isTokenValid(String token) throws MalformedJwtException {
        final Claims claims = extractAllClaims(token);
        return true;
    }

    // create method to check user valid 1
    public void checkUser(String token, ClientType clientType) throws LoginException, TokenException {
        String newToken = token.replace("Bearer ", "");
        if (isTokenValid(newToken)) {
            if (!extractUserType(token).equalsIgnoreCase(clientType.toString())) {
                throw new LoginException(clientType);
            }

        } else {
            throw new TokenException();
        }

    }
    public String generateToken(String token) {
        Map<String, Object> claims = new HashMap<>();
        Claims myClaims = extractAllClaims(token);
        claims.put("clientType", myClaims.get("clientType"));
        return createToken(claims, myClaims.getSubject());
    }

    // create method to check user valid 2
    public String checkUser(String token) throws MalformedJwtException,ExpiredJwtException, SignatureException {
        Claims claims = extractAllClaims(token.replace("Bearer ", ""));
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(claims.getSubject());
        userDetails.setClientType(Enum.valueOf(ClientType.class, extractUserType(token)));
        return generateToken(userDetails);

    }




}
