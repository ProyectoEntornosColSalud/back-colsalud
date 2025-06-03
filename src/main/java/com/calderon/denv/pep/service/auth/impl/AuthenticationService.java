package com.calderon.denv.pep.service.auth.impl;

import static java.util.Objects.isNull;

import com.calderon.denv.pep.dto.app.RegisterUserRequest;
import com.calderon.denv.pep.model.auth.User;
import com.calderon.denv.pep.repository.app.DoctorRepository;
import com.calderon.denv.pep.security.JwtUtil;
import com.calderon.denv.pep.service.auth.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserService userService;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder encoder;
  private final DoctorRepository doctorRepository;

  public String login(String username, String password) {
    User user = userService.getByUsername(username);
    if (isNull(user) || !encoder.matches(password, user.getPassword()))
      throw new BadCredentialsException("Incorrect credentials");
    boolean isDoctor =doctorRepository.existsByPersonId(user.getPersonId());
    return jwtUtil.generateToken(user.getId(), isDoctor);
  }

  public String register(@Valid RegisterUserRequest request) {
    User user = userService.save(request);
    return jwtUtil.generateToken(user.getId(), false);
  }
}
