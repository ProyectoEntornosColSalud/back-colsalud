package com.calderon.denv.pep.repository.app;

import com.calderon.denv.pep.model.app.Appointment;
import java.time.LocalDateTime;
import java.util.List;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

  @Query(
      "select a.startTime from Appointment a where a.doctor.id = :doctorId and a.startTime >= current_timestamp")
  List<LocalDateTime> getFutureAppointments(Long doctorId);

  @Query(
      "select a.startTime from Appointment a where a.person.id = :personId and a.startTime >= current_timestamp")
  List<LocalDateTime> getPatientAppointments(@NonNull Long personId);

  boolean existsByDoctorIdAndStartTime(Long doctorId, LocalDateTime startTime);
}
