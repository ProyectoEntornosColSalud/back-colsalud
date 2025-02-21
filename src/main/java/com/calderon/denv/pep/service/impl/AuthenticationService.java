package com.calderon.denv.pep.service.impl;

import com.calderon.denv.pep.exception.ValidationException;
import com.calderon.denv.pep.model.auth.User;
import com.calderon.denv.pep.security.JwtUtil;
import com.calderon.denv.pep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserService userService;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder encoder;

  public String authenticate(String email, String password) {
    User user = userService.getByEmail(email);

    if (!encoder.matches(password, user.getPassword()))
      throw new ValidationException("Error: Incorrect Password");

    return jwtUtil.generateToken(user.getEmail());
  }
}
