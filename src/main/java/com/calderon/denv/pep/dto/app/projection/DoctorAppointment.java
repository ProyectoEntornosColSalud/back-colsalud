package com.calderon.denv.pep.dto.app.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public interface DoctorAppointment {
  @JsonProperty("appointmentId")
  Long getAppointmentId();

  @JsonProperty("patientName")
  String getPatientName();

  @JsonProperty("specialtyName")
  String getSpecialtyName();

  @JsonProperty("startTime")
  LocalDateTime getAppointmentTime();

  @JsonProperty("status")
  String getStatus();
}
