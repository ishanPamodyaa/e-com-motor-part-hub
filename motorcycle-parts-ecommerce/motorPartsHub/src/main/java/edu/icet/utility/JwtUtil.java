package edu.icet.utility;


import edu.icet.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final String SECRET = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";

    public String generateToken(String userName){
        Map<String ,Object > claims = new HashMap<>();
        return createToken(claims,userName);
    }

    private String createToken(Map<String ,Object> claims , String userName){

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+10000*60*30))
                .signWith(getSignKey() , SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);

        return Keys.hmacShaKeyFor(keyBytes);
    }
    public  String extractUsername(String token){

        return extractClaim(token , Claims::getSubject);
    }

    public <T> T extractClaim(String token , Function<Claims , T> claimsResolver){
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String token ){
        return  Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpire (String token){
        return extractExpiration(token).before(new Date());

    }

    public Date extractExpiration(String token){

        return extractClaim(token, Claims::getExpiration);
    }

    public  Boolean validateToken(String token , UserDetails userDetail){
        final String userName = extractUsername(token);
        return (userName.equals(userDetail.getUsername()) && !isTokenExpire(token));
    }
}
