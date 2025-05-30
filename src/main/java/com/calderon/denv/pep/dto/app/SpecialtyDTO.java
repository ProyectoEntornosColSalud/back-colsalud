package com.calderon.denv.pep.dto.app;

import com.calderon.denv.pep.model.app.Specialty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyDTO {
  private Long id;
  private String name;
  private String description;

  public static SpecialtyDTO map(Specialty specialty) {
    return new SpecialtyDTO(specialty.getId(), specialty.getName(), specialty.getDescription());
  }
}
