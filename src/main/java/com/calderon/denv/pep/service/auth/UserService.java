package com.calderon.denv.pep.service.auth;

import com.calderon.denv.pep.dto.app.RegisterUserRequest;
import com.calderon.denv.pep.dto.app.UpdateUserRequest;
import com.calderon.denv.pep.model.auth.User;
import jakarta.annotation.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

  @Nullable
  User getUserById(Long userId);

  @Nullable
  User getByUsername(String email);

  UserDetails loadUser(Long userId);

  User save(RegisterUserRequest dto);

  void update(Long userId, UpdateUserRequest request);
}
