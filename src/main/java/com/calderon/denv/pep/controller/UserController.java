package com.calderon.denv.pep.controller;

import static com.calderon.denv.pep.constant.Constant.AUTH_HEADER;

import com.calderon.denv.pep.dto.app.PersonResponse;
import com.calderon.denv.pep.dto.app.UpdateUserRequest;
import com.calderon.denv.pep.model.app.Person;
import com.calderon.denv.pep.repository.app.PersonRepository;
import com.calderon.denv.pep.security.JwtUtil;
import com.calderon.denv.pep.service.auth.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User-related operations")
public class UserController {
  private final UserService userService;
  private final JwtUtil jwtUtil;
  private final PersonRepository personRepository;

  @PutMapping
  @Operation(summary = "Update person information")
  public ResponseEntity<String> update(
    @RequestBody @Validated UpdateUserRequest request, @RequestHeader(AUTH_HEADER) String token) {
    Long userId = jwtUtil.extractUserId(token);
    userService.update(userId, request);
    return ResponseEntity.ok("You are authenticated");
  }

  @GetMapping
  @Operation(summary = "Get person information")
  public ResponseEntity<PersonResponse> get(@RequestHeader(AUTH_HEADER) String token) {
    Long userId = jwtUtil.extractUserId(token);
    Person person = personRepository.getByUserId(userId).orElse(null);
    if (person == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(PersonResponse.from(person));
  }
}
