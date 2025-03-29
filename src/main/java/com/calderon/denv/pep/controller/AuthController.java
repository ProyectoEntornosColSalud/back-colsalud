package com.calderon.denv.pep.controller;

import com.calderon.denv.pep.dto.app.RegisterUserRequest;
import com.calderon.denv.pep.dto.auth.LoginRequest;
import com.calderon.denv.pep.service.auth.UserService;
import com.calderon.denv.pep.service.auth.impl.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
  public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
    String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

    Cookie cookie = new Cookie("colsalud_token", token);
    cookie.setHttpOnly(true); // 🔒 No accesible desde JavaScript
    //cookie.setSecure(true); // 🔐 Solo en HTTPS (desactivar en desarrollo)
    cookie.setPath("/"); // 📌 Disponible para toda la aplicación
    cookie.setMaxAge(3600*8); // ⏳ Expira en 1 hora

    response.addCookie(cookie);
    return ResponseEntity.ok().build();
  }

//  @GetMapping("/logout")
//  public ResponseEntity<?> logout(HttpServletResponse response) {
//    // Invalidar la cookie estableciendo una fecha de expiración negativa
//    Cookie cookie = new Cookie("token", null);
//    cookie.setHttpOnly(true);
//    cookie.setSecure(true);
//    cookie.setPath("/");
//    cookie.setMaxAge(0); // 🔥 Expira inmediatamente
//
//    response.addCookie(cookie);
//    return ResponseEntity.ok("Logout exitoso, cookie eliminada.");
//  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserRequest request) {
    String token = userService.registerUser(request);
    return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
  }
}
