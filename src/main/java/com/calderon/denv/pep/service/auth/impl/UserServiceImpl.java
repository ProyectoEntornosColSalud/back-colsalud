package com.calderon.denv.pep.service.auth.impl;

import static java.util.Objects.requireNonNull;

import com.calderon.denv.pep.constant.Role;
import com.calderon.denv.pep.dto.app.RegisterUserRequest;
import com.calderon.denv.pep.exception.ValidationException;
import com.calderon.denv.pep.model.app.Person;
import com.calderon.denv.pep.model.auth.User;
import com.calderon.denv.pep.repository.app.PersonRepository;
import com.calderon.denv.pep.repository.auth.UserRepository;
import com.calderon.denv.pep.service.auth.UserService;
import jakarta.annotation.Nullable;
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

  @Override
  @Transactional
  public User save(RegisterUserRequest request) {
    validateUserRegistrationRequest(request);
    Person person = savePersonInfo(request);
    return userRepository.save(
        User.builder()
            .person(person)
            .username(request.getDocumentNumber())
            .password(encoder.encode(request.getPassword()))
            .role(Role.ROLE_USER)
            .build());
  }

  private Person savePersonInfo(RegisterUserRequest dto) {
    return personRepository.save(
        Person.builder()
            .name(dto.getName())
            .lastname(dto.getLastName())
            .birthday(dto.getBirthDate())
            .gender(dto.getGender())
            .documentType(dto.getDocumentType())
            .documentNumber(dto.getDocumentNumber())
            .phone(dto.getPhone())
            .email(dto.getEmail())
            .build());
  }

  private void validateUserRegistrationRequest(RegisterUserRequest request) {
    validateEmail(request.getEmail());
    validateDocument(request);
  }

  private void validateDocument(RegisterUserRequest request) {
    if (personRepository.existsByDocumentTypeAndDocumentNumber(
        request.getDocumentType(), request.getDocumentNumber()))
      throw new ValidationException("Document is already in use");
  }

  private void validateEmail(String email) {
    if (personRepository.existsByEmail(email))
      throw new ValidationException("Email is already in use");
  }

  @Override
  public UserDetails loadUser(Long userId) {
    User user = requireNonNull(getUserById(userId));
    return new org.springframework.security.core.userdetails.User(
        user.getId().toString(), user.getPassword(), new ArrayList<>());
  }

  @Nullable
  @Override
  public User getUserById(Long userId) {
    return userRepository.findById(userId).orElse(null);
  }

  @Override
  public @Nullable User getByUsername(String document) {
    return userRepository.findByUsername(document).orElse(null);
  }
}
