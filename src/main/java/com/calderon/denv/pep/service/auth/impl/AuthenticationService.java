package com.calderon.denv.pep.service.auth.impl;

import com.calderon.denv.pep.model.auth.User;
import com.calderon.denv.pep.security.JwtUtil;
import com.calderon.denv.pep.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserService userService;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder encoder;

  public String authenticate(String email, String password) {


    User user = userService.getByUsername(email);
    if (Objects.isNull(user) || !encoder.matches(password, user.getPassword()))
      throw new BadCredentialsException("Error: Incorrect credentials");


    return jwtUtil.generateToken(user.getUsername());
  }
}
