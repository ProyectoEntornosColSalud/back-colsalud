package com.calderon.denv.pep.exception;

import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InputDataError extends ErrorDetails {
  private Map<String, String> errors;
}
