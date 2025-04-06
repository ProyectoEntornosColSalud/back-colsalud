package com.calderon.denv.pep.service.app;

import com.calderon.denv.pep.dto.ListItem;
import com.calderon.denv.pep.dto.app.DateFilter;
import com.calderon.denv.pep.model.app.Appointment;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
  void schedule(Appointment appointment);

  List<ListItem> getDoctorsBySpecialty(Long specialtyId);

  List<ListItem> getSpecialties();

  /**
   * Returns a list of available dates for a doctor based on the provided filter. If the filter has
   * non-consistent values, it will generate default filter values according to the current time
   */
  List<LocalDateTime> getDoctorAvailableDates(Long userId, DateFilter filter);

  List<Object> filterDates(
      Long specialtyId, Long doctorId, LocalDate day, Integer start, Integer end);
}
