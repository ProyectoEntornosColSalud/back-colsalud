package com.calderon.denv.pep.dto.app;

import com.calderon.denv.pep.constant.DocumentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

  @NotBlank String name;

  @NotBlank
  @JsonProperty("last_name")
  String lastName;

  @NotBlank String gender;

  @NotNull
  @PastOrPresent
  @JsonProperty("birth_date")
  LocalDate birthDate;

  @NotNull
  @JsonProperty("doc_type")
  DocumentType documentType;

  @Email @NotBlank String email;

  @NotBlank String phone;
}
