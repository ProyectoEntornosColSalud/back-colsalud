package com.calderon.denv.pep.service.auth.impl;

import com.calderon.denv.pep.constant.Role;
import com.calderon.denv.pep.dto.app.RegisterUserRequest;
import com.calderon.denv.pep.exception.ValidationException;
import com.calderon.denv.pep.model.app.Person;
import com.calderon.denv.pep.model.auth.User;
import com.calderon.denv.pep.repository.app.PersonRepository;
import com.calderon.denv.pep.repository.auth.UserRepository;
import com.calderon.denv.pep.security.JwtUtil;
import com.calderon.denv.pep.service.auth.UserService;
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
  private final PersonRepository personRepository;
  private final JwtUtil jwtUtil;

  @Override
  @Transactional
  public String registerUser(RegisterUserRequest request) {
    validateUserRegistrationRequest(request);
    Person person = personRepository.save(mapRequestToPerson(request));
    userRepository.save(
        User.builder()
            .person(person)
            .username(request.getDocumentNumber())
            .password(encoder.encode(request.getPassword()))
            .role(Role.ROLE_USER)
            .build());
    return jwtUtil.generateToken(person.getDocumentNumber());
  }

  private static Person mapRequestToPerson(RegisterUserRequest request) {
    return Person.builder()
        .name(request.getName())
        .lastname(request.getLastName())
        .birthday(request.getBirthDate())
        .gender(request.getGender())
        .documentType(request.getDocumentType())
        .documentNumber(request.getDocumentNumber())
        .phone(request.getPhone())
        .email(request.getEmail())
        .build();
  }

  private void validateUserRegistrationRequest(RegisterUserRequest request) {
    validateEmail(request.getEmail());
    validateDocument(request);
  }

  private void validateDocument(RegisterUserRequest request) {
    if (personRepository.existsByDocumentTypeAndDocumentNumber(
        request.getDocumentType(), request.getDocumentNumber()))
      throw new ValidationException("Error: Document is already in use");
  }

  private void validateEmail(String email) {
    if (personRepository.existsByEmail(email))
      throw new ValidationException("Error: Email is already in use");
  }

  @Override
  public UserDetails loadUser(String email) {
    User user = getByUsername(email);
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), new ArrayList<>());
  }

  @Override
  public User getByUsername(String email) {
    return userRepository
        .findByUsername(email)
            .orElse(null);
  }
}
