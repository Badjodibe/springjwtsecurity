package com.security.security.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private static  final String SECRET_KEY = "NyAjjmAqHifAkJwZxX3A9rbFs1OtYNe8Rm0P31wLInx6+WecnIF3DAfYtq6T7fFz\n";
    public String extractEmail(String token){
        return extractClaim(token, Claims::getAudience);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return  claimsResolver.apply(claims);
    }
    public  String generatedToken(UserDetails userDetails){
        return createToken(new HashMap<>(), userDetails);
    }

    /*
    * This function is for token generation it accepts as input userDetails object and a
    * Map object of extract claims. I return a string of token.
    * */
    public String createToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ){

       return Jwts
               .builder()
               .setClaims(extractClaims)
               .setSubject(userDetails.getUsername())
               .setIssuedAt(new Date((System.currentTimeMillis())))
               .setExpiration(new Date(System.currentTimeMillis() +  1000*60*24))
               .signWith(getSingInKey(), SignatureAlgorithm.HS256)
               .compact();
    }

    /*
    * The goal of this function is tho check that a token is validated
    * Inputs :
    *       String of the token
    *       userDetails
    * if token is valid if its expiration time is not reached
    * and the corresponding userDetails is valid
    * */
    public  boolean isTokenValidated(
            String token,
            UserDetails userDetails
    ){
        final  String username = extractEmail(token);
        return (username.equals(userDetails.getUsername())) && isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return  extractClaim(token, Claims::getExpiration);
    }
    /*
    *  This function is used to extract all claims
    * */
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    /*
    * This is the used signature
    * */
    private Key getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
