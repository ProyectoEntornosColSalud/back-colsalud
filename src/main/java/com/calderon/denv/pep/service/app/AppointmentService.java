package com.calderon.denv.pep.service.app;

import com.calderon.denv.pep.dto.ListItem;
import com.calderon.denv.pep.dto.app.AppointmentResponse;
import com.calderon.denv.pep.dto.app.DateFilter;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

  List<ListItem> getDoctorsBySpecialty(Long specialtyId);

  List<ListItem> getSpecialties();

  /**
   * Returns a list of available dates for a doctor based on the provided filter. If the filter has
   * non-consistent values, it will generate default filter values according to the current time
   */
  List<LocalDateTime> getDoctorAvailableDates(Long userId, DateFilter filter);


  void schedule(Long userId, Long doctorId, Long specialtyId, LocalDateTime date);

	List<AppointmentResponse> getUserAppointments(Long userId);

  void cancelAppointment(Long userId, @Min(1) Long appointmentId);
}
