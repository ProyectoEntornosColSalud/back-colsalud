package com.calderon.denv.pep.security;

import static com.calderon.denv.pep.constant.Constant.TOKEN_MINUTES_EXPIRATION_TIME;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  /** Generates a JWT token with the userId as the subject */
  public String generateToken(Long userId, boolean isDoctor) {
    ZoneId zoneId = ZoneId.of("America/Bogota");
    return Jwts.builder()
        .subject(String.valueOf(userId))
        .claims(Map.of("isDoctor", isDoctor))
        .issuedAt(Date.from(java.time.ZonedDateTime.now(zoneId).toInstant()))
        .expiration(
            Date.from(
                java.time.ZonedDateTime.now(zoneId).plusMinutes(TOKEN_MINUTES_EXPIRATION_TIME).toInstant()))
        .signWith(getSigningKey())
        .compact();
  }

  /** Subject is the userId */
  public String extractSubject(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Long extractUserId(String token) {
    final String authPrefix = "Bearer ";
    if (token.startsWith(authPrefix)) token = token.substring(authPrefix.length());
    return Long.valueOf(extractSubject(token));
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
  }

  public boolean isTokenValid(String token, String userId) {
    final String extractedUserId = extractSubject(token);
    return extractedUserId.equals(userId) && !isTokenExpired(token);
  }

  public boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }
}
