package com.calderon.denv.pep.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User-related operations")
public class UserController {
  @GetMapping("/test")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("You are authenticated");
  }
}
