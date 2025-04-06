package com.calderon.denv.pep.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Items for lists and dropdowns")
public class ListItem {
  private Object id;
  private String name;
}
