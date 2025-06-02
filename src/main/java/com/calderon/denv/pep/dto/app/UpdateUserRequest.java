package com.calderon.denv.pep.dto.app;

import com.calderon.denv.pep.constant.DocumentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

  @NotBlank private String name;

  @NotBlank
  @JsonProperty("last_name")
  private String lastName;

  @NotBlank private String gender;

  @NotNull
  @PastOrPresent
  @JsonProperty("birth_date")
  private LocalDate birthDate;

  @NotNull
  @JsonProperty("doc_type")
  private DocumentType documentType;

  @Email @NotBlank private String email;

  @NotBlank private String phone;

  @Nullable private String password;
}
