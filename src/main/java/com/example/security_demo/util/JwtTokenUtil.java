package com.example.security_demo.util;

import com.example.security_demo.exception.TokenWasExpiredException;
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

  private static final long TOKEN_EXPIRATION = 24 * 60 * 60;

  @Value("${jwt.secret}")
  private String secret;

  public String gwtUserEmail(String token) throws ExpiredJwtException {
    return Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public String verifyAndParseToken(String emailToken) {
    String userEmail = gwtUserEmail(emailToken);
    if (!isTokenExpired(emailToken)) {
      return userEmail;
    }
    throw new TokenWasExpiredException("Token was expired");
  }

  public String generateToken(String email) {

    return Jwts
        .builder()
        .setClaims(new HashMap<>())
        .setSubject(email)
        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public Boolean isTokenExpired(String token) throws ExpiredJwtException {
    return Jwts
        .parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody()
        .getExpiration()
        .before(new Date());
  }
}
