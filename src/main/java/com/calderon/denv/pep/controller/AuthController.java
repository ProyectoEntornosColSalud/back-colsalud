package com.calderon.denv.pep.controller;

import com.calderon.denv.pep.dto.JwtResponse;
import com.calderon.denv.pep.dto.LoginRequest;
import com.calderon.denv.pep.dto.RegisterRequest;
import com.calderon.denv.pep.service.UserService;
import com.calderon.denv.pep.service.impl.AuthenticationService;
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
  public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    String token = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    return ResponseEntity.ok(new JwtResponse(token));
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest req) {
    userService.registerUser(req);
    return ResponseEntity.ok("User registered successfully");
  }

  @PostMapping("/register-admin")
  public ResponseEntity<String> registerAdmin(
      @RequestHeader Long idRequester, @RequestParam Long idUser) {
    userService.registerAdmin(idRequester, idUser);
    return ResponseEntity.ok("Admin registered successfully");
  }

  @GetMapping("/test")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("You are authenticated");
  }
}
