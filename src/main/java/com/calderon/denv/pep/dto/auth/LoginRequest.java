package com.calderon.denv.pep.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
  @NotBlank private String username;

  @NotBlank private String password;
}
