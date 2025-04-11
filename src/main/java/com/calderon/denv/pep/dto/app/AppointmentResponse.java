package com.calderon.denv.pep.dto.app;

import java.time.LocalDateTime;

import com.calderon.denv.pep.constant.AppointmentStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
  private Long appointmentId;
  private String doctorName;
  private String specialtyName;
  private LocalDateTime time;
  private AppointmentStatus status;
}
