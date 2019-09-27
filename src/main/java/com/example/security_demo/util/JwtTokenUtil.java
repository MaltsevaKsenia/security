package com.example.security_demo.util;

import com.example.security_demo.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

  public static final long TOKEN_EXPIRATION = 24 * 60 * 60;
  private final UserService userService;

  @Value("${jwt.secret}")
  private String secret;

  public String gwtCustomerEmail(String token) throws ExpiredJwtException {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody()
        .getSubject();
  }

  public String generateToken(String email) {

    return Jwts
        .builder()
        .setClaims(new HashMap<>())
        .setSubject(email)
        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION * 1000))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public Boolean isTokenNotExpired(String token) throws ExpiredJwtException {
    return !Jwts
        .parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody()
        .getExpiration()
        .before(new Date());
  }
}
