package com.calderon.denv.pep.service.auth;

import com.calderon.denv.pep.dto.auth.RegisterRequest;
import com.calderon.denv.pep.model.auth.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

  User getByEmail(String email);

  @Transactional
  void registerAdmin(Long idRequester, Long idUser);

  UserDetails loadUser(String email);

  void registerUser(RegisterRequest req);
}
