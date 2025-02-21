package com.calderon.denv.pep.exception;

import java.time.LocalDateTime;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
  private LocalDateTime timestamp;
  private String message;
  private String details;
}
