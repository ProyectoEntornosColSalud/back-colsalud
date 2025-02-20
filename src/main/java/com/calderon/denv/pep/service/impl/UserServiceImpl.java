package com.calderon.denv.pep.service.impl;

import com.calderon.denv.pep.constant.ERole;
import com.calderon.denv.pep.dto.RegisterRequest;
import com.calderon.denv.pep.exception.DataNotFoundException;
import com.calderon.denv.pep.model.auth.User;
import com.calderon.denv.pep.repository.UserRepository;
import com.calderon.denv.pep.service.RoleService;
import com.calderon.denv.pep.service.UserService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final PasswordEncoder encoder;
  private final UserRepository userRepository;
  private final RoleService roleService;

  //  private final

  @Override
  public User save(User user) {
    //    Optional<Role> adminRole
    return null;
  }

  @Override
  @Transactional
  public void registerUser(RegisterRequest req) {
    validateRequest(req);
    User user = mapRequest(req);
    assignRole(user, ERole.USER);
    userRepository.save(user);
  }

  private void validateRequest(RegisterRequest req) {
    validateEmail(req.getEmail());
    validatePassword(req.getPassword());
  }

  private User mapRequest(RegisterRequest req) {
    return User.builder()
        .name(req.getName())
        .lastName(req.getLastName())
        .email(req.getEmail())
        .password(encoder.encode(req.getPassword()))
        .build();
  }

  private void validateEmail(String email) {
    if (userRepository.existsByEmail(email))
      throw new DataNotFoundException("Error: Email is already in use");
  }

  private void validatePassword(String password) {}

  private void assignRole(User user, ERole role) {
    user.addRole(roleService.getRole(role));
  }

  @Override
  public UserDetails loadUser(String email) {
    User user = getByEmail(email);
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(), user.getPassword(), new ArrayList<>());
  }

  @Override
  public User getByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(DataNotFoundException::new);
  }
}
