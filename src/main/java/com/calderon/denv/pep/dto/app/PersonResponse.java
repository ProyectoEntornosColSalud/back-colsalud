package com.calderon.denv.pep.dto.app;

import com.calderon.denv.pep.constant.DocumentType;
import com.calderon.denv.pep.model.app.Person;
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
public class PersonResponse {
  @NotBlank String name;

  @NotBlank
  @JsonProperty("last_name")
  String lastname;

  @NotBlank String gender;

  @JsonProperty("birth_date")
  @NotNull
  LocalDate birthday;

  @NotNull
  @JsonProperty("doc_type")
  DocumentType documentType;

  @NotBlank
  @JsonProperty("doc_number")
  String documentNumber;

  @Email @NotBlank String email;
  @NotBlank String phone;

  public static PersonResponse from(Person person) {
    return PersonResponse.builder()
        .name(person.getName())
        .lastname(person.getLastname())
        .gender(person.getGender())
        .birthday(person.getBirthday())
        .documentType(person.getDocumentType())
        .documentNumber(person.getDocumentNumber())
        .email(person.getEmail())
        .phone(person.getPhone())
        .build();
  }
}
