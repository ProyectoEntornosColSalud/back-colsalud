package com.calderon.denv.pep.service.auth.impl;

import com.calderon.denv.pep.exception.BadRequestException;
import com.calderon.denv.pep.model.auth.User;
import com.calderon.denv.pep.security.JwtUtil;
import com.calderon.denv.pep.service.auth.UserService;
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
      throw new BadRequestException("Error: Incorrect Password");

    return jwtUtil.generateToken(user.getUsername());
  }
}
