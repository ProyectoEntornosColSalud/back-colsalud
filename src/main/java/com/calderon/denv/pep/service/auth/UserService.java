package com.calderon.denv.pep.service.auth;

import com.calderon.denv.pep.dto.app.RegisterUserRequest;
import com.calderon.denv.pep.model.auth.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface  UserService {

  User getByUsername(String email);

  UserDetails loadUser(String email);

  String registerUser(RegisterUserRequest request);
}
