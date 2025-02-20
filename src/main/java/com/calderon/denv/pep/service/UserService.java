package com.calderon.denv.pep.service;

import com.calderon.denv.pep.dto.RegisterRequest;
import com.calderon.denv.pep.model.auth.User;
import com.calderon.denv.pep.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
  User save(User user);

  User getByEmail(String email);

  UserDetails loadUser(String email);

  void registerUser(RegisterRequest req);
}
