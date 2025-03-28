package com.calderon.denv.pep.controller;

import com.calderon.denv.pep.dto.app.RegisterUserRequest;
import com.calderon.denv.pep.dto.auth.LoginRequest;
import com.calderon.denv.pep.service.auth.UserService;
import com.calderon.denv.pep.service.auth.impl.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationService authService;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest loginRequest) {
    String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserRequest request) {
    String token = userService.registerUser(request);
    return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
  }
}
