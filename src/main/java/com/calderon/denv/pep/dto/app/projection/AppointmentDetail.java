package com.calderon.denv.pep.dto.app.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class AppointmentDetail {

  private final Long appointmentId;
  private final Appointment appointment;
  private final Patient patient;

  public AppointmentDetail(
      Long appointmentId,
      String specialtyName,
      LocalDateTime date,
      String patientName,
      LocalDate patientDob,
      String patientGender) {
    this.appointmentId = appointmentId;
    this.appointment = new Appointment(appointmentId, specialtyName, date);
    this.patient = new Patient(patientName, patientDob, patientGender);
  }

  @AllArgsConstructor
  @Getter
  static class Appointment {
		private Long id;
    private String specialtyName;
    private LocalDateTime date;
  }

  @AllArgsConstructor
  @Getter
  static class Patient {
    private String name;
    private LocalDate dob;
    private String gender;
  }
}
