package com.calderon.denv.pep.dto.app.projection;

import com.calderon.denv.pep.constant.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public interface AppointmentResponse {

  @JsonProperty("appointmentId")
  Long getAppointmentId();

  @JsonProperty("doctorName")
  String getDoctorName();

  @JsonProperty("specialtyName")
  String getSpecialtyName();

  @JsonProperty("time")
  LocalDateTime getTime();

  @JsonProperty("status")
  AppointmentStatus getStatus();
}
