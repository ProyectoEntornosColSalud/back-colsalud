package com.calderon.denv.pep.dto.app;

import lombok.*;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDoctorResponse {
  Long doctorId;
  String doctorName;
}
