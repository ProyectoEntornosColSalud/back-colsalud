package com.calderon.denv.pep.repository.app;

import com.calderon.denv.pep.constant.AppointmentStatus;
import com.calderon.denv.pep.dto.app.projection.DoctorAppointment;
import com.calderon.denv.pep.model.app.Appointment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

  @Query(
      "select a.startTime from Appointment a where a.doctor.id = :doctorId and a.startTime >= :now and a.status = 'PENDIENTE'")
  List<LocalDateTime> getFutureAppointments(Long doctorId, LocalDateTime now);

  @Query(
      "select a.startTime from Appointment a where a.personId = :personId and a.startTime >= :now and a.status = 'PENDIENTE'")
  List<LocalDateTime> getFuturePatientAppointmentsTimes(@NonNull Long personId, LocalDateTime now);

  @Query("select a from Appointment a where a.personId = :personId")
  List<Appointment> getPatientAppointments(@NonNull Long personId);

  boolean existsByDoctorIdAndStartTimeAndStatus(
      Long doctorId, LocalDateTime startTime, AppointmentStatus status);

  Optional<Appointment> findByIdAndPersonId(Long id, Long personId);

  @Modifying
  @Query(
      "update Appointment a set a.status = 'PERDIDA' where a.status = 'PENDIENTE' and a.startTime < :now")
  void updateMissedAppointments(LocalDateTime now);

  boolean existsByPersonIdAndStartTime(Long personId, LocalDateTime startTime);

  @Query(
      """
        select
        a.id as appointmentId,
        concat(p.name, ' ', p.lastname) as patientName,
        a.specialty.name as specialtyName,
        a.startTime as appointmentTime
        from Appointment a
        join Person p on a.personId = p.id
        where a.doctor.id = :id
        and a.status = 'PENDIENTE'
        """)
  Page<DoctorAppointment> getPendingApptsForDoctor(Long id, Pageable pageable);

  @Query(
      """
        select
        a.id as appointmentId,
        concat(p.name, ' ', p.lastname) as patientName,
        a.specialty.name as specialtyName,
        a.startTime as appointmentTime
        from Appointment a
        join Person p on a.personId = p.id
        where a.doctor.id = :id
        and a.status <> 'PENDIENTE'
        """)
  Page<DoctorAppointment> getPastApptsForDoctor(Long id, Pageable pageable);
}
