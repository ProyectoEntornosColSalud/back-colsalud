package com.calderon.denv.pep.controller;

import com.calderon.denv.pep.dto.app.RegisterUserRequest;
import com.calderon.denv.pep.dto.auth.LoginRequest;
import com.calderon.denv.pep.service.auth.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Authentication", description = "User authentication and registration")
public class AuthController {
  private final AuthenticationService authService;

  @PostMapping("/login")
  public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest loginRequest) {
    String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(
      @RequestBody @Valid RegisterUserRequest request, HttpServletResponse response) {
    String token = authService.register(request);
    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
  }
}
