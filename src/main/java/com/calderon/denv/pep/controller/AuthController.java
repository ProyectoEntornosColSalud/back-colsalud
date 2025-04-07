package com.calderon.denv.pep.controller;

import com.calderon.denv.pep.dto.app.RegisterUserRequest;
import com.calderon.denv.pep.dto.auth.LoginRequest;
import com.calderon.denv.pep.service.auth.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.Header;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

import static com.calderon.denv.pep.constant.Constant.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Authentication", description = "User authentication and registration")
public class AuthController {
  private final AuthenticationService authService;

  @PostMapping("/login")
  public ResponseEntity<Void> login(
      @RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
    String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

//    Cookie cookie = new Cookie(TOKEN_COOKIE_NAME, token);
//    cookie.setHttpOnly(true);
//    // cookie.setSecure(true); // Solo en HTTPS (desactivar en desarrollo)
//    cookie.setPath("/");
//    cookie.setMaxAge(TOKEN_EXPIRATION_TIME);
//    response.addCookie(cookie);

    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
  }

  @GetMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletResponse response) {
    Cookie cookie = new Cookie(TOKEN_COOKIE_NAME, null);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(0);

    response.addCookie(cookie);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(
      @RequestBody @Valid RegisterUserRequest request, HttpServletResponse response) {
    String token = authService.register(request);

    Cookie cookie = new Cookie(TOKEN_COOKIE_NAME, token);
    cookie.setHttpOnly(true);
    // cookie.setSecure(true); // Solo en HTTPS (desactivar en desarrollo)
    cookie.setPath("/");
    cookie.setMaxAge(TOKEN_EXPIRATION_TIME);

    response.addCookie(cookie);
    return ResponseEntity.ok().build();
  }
}
