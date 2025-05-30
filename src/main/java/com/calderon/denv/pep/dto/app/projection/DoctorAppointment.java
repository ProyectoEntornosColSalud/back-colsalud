package com.calderon.denv.pep.dto.app.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public interface DoctorAppointment {
  @JsonProperty("appointment_id")
  Long getAppointmentId();

  @JsonProperty("patient_name")
  String getPatientName();

  @JsonProperty("specialty_name")
  String getSpecialtyName();

  @JsonProperty("start_time")
  LocalDateTime getAppointmentTime();
}
