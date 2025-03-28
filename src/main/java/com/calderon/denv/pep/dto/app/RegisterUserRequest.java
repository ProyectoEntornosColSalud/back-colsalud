package com.calderon.denv.pep.dto.app;

import com.calderon.denv.pep.constant.DocumentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {

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

  @NotBlank
  @JsonProperty("doc_number")
  String documentNumber;

  @Email @NotBlank String email;

  @NotBlank String password;
  
  @NotBlank String phone;
}
