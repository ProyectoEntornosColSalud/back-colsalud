package com.calderon.denv.pep.repository.app;

import com.calderon.denv.pep.constant.AppointmentStatus;
import com.calderon.denv.pep.model.app.Appointment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

  @Query(
      "select a.startTime from Appointment a where a.doctor.id = :doctorId and a.startTime >= :now and a.status = 'PENDIENTE'")
  List<LocalDateTime> getFutureAppointments(Long doctorId, LocalDateTime now);

  @Query(
      "select a.startTime from Appointment a where a.person.id = :personId and a.startTime >= :now and a.status = 'PENDIENTE'")
  List<LocalDateTime> getFuturePatientAppointmentsTimes(@NonNull Long personId, LocalDateTime now);

  @Query(
      "select a from Appointment a where a.person.id = :personId")
  List<Appointment> getPatientAppointments(@NonNull Long personId);

  boolean existsByDoctorIdAndStartTimeAndStatus(Long doctorId, LocalDateTime startTime, AppointmentStatus status);

  Optional<Appointment> findByIdAndPersonId(Long id, Long personId);

  @Query("select a from Appointment  a where a.status = 'PENDIENTE' and a.startTime < :now")
  List<Appointment> findPendingPastAppointments(LocalDateTime now);
}
