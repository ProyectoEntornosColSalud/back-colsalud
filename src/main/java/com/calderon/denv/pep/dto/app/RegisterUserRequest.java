package com.calderon.denv.pep.dto.app;

import com.calderon.denv.pep.constant.DocumentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long id;

  @NotBlank String name;

  @NotBlank
  @JsonProperty("last_name")
  String lastName;

  @NotBlank String gender;

  @NotNull
  @JsonProperty("birth_date")
  LocalDate birthDate;

  @NotNull
  @JsonProperty("doc_type")
  DocumentType documentType;

  @NotBlank
  @JsonProperty("doc_number")
  String documentNumber;

  @Email @NotBlank String email;
  @NotBlank String password;
  @NotBlank String phone;
}
