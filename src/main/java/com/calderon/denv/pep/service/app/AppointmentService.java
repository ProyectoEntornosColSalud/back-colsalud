package com.calderon.denv.pep.service.app;

import com.calderon.denv.pep.constant.DoctorAppointmentSearchType;
import com.calderon.denv.pep.dto.ListItem;
import com.calderon.denv.pep.dto.app.DateFilter;
import com.calderon.denv.pep.dto.app.projection.AppointmentDetail;
import com.calderon.denv.pep.dto.app.projection.AppointmentResponse;
import com.calderon.denv.pep.dto.app.projection.DoctorAppointment;
import com.calderon.denv.pep.model.app.Appointment;
import com.calderon.denv.pep.model.app.Doctor;
import com.calderon.denv.pep.model.app.Specialty;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

  List<ListItem> getDoctorsBySpecialty(Long specialtyId);

  List<Specialty> getSpecialties();

  /**
   * Returns a list of available dates for a doctor based on the provided filter. If the filter has
   * non-consistent values, it will generate default filter values according to the current time
   */
  List<LocalDateTime> getDoctorAvailableDates(Long userId, DateFilter filter);


  void schedule(Long userId, Long doctorId, Long specialtyId, LocalDateTime date);

	List<AppointmentResponse> getUserAppointments(Long userId);

  void cancelAppointment(Long userId, @Min(1) Long appointmentId);
  void updateMissedAppointments();

  Appointment get(Long appointmentId);

  void reschedule(Appointment appointment, LocalDateTime newDate);

  Page<DoctorAppointment> getAppointmentsByDoctor(Doctor doctor, DoctorAppointmentSearchType searchType, Pageable pageable);

  void markAsAttended(Appointment appointment);

  AppointmentDetail getAppointmentDetail(Long appointmentId);
}
