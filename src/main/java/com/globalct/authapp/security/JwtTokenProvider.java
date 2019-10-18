package com.globalct.authapp.security;

import com.globalct.authapp.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtTokenProvider {

  @Value("${secret}")
  private String secret;
  @Value("${expirationTime}")
  private String expirationTime;

  public String generateToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    Date now = new Date(System.currentTimeMillis());
    Date expiryDate = new Date(now.getTime() + Long.valueOf(expirationTime));

    String userId = user.getId().toString();

    Map<String, Object> claims = new HashMap<>();
    claims.put("id", userId);
    claims.put("name", user.getName());

    return Jwts.builder()
            .setSubject(userId)
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();

  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (SignatureException ex) {
      System.out.println("Invalid JWT Signature");
    } catch (MalformedJwtException ex) {
      System.out.println("Invalid JWT Token");
    } catch (ExpiredJwtException ex) {
      System.out.println("Expired Jwt Exception");
    } catch (UnsupportedJwtException ex) {
      System.out.println("Unsupported JWT Exception");
    } catch (IllegalArgumentException ex) {
      System.out.println("JWT claims string is empty");
    }
    return false;
  }

  public UUID getUserIdFromJWT(String token) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    String idString = (String) claims.get("id");
    return UUID.fromString(idString);
  }

}
