package com.calderon.denv.pep.service;

import com.calderon.denv.pep.dto.RegisterRequest;
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
