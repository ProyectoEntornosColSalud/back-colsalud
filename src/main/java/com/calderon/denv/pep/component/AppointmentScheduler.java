package com.calderon.denv.pep.component;

import static com.calderon.denv.pep.util.Tools.getColTime;

import com.calderon.denv.pep.constant.AppointmentStatus;
import com.calderon.denv.pep.model.app.Appointment;
import com.calderon.denv.pep.repository.app.AppointmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppointmentScheduler {

  private final AppointmentRepository appointmentRepository;

  @Scheduled(cron = "0 15 * * * *")
  public void markMissedAppointments() {
    log.info("It's {} - Marking appointments missed...", getColTime());
    List<Appointment> appointments =
        appointmentRepository.findPendingPastAppointments(getColTime());
    for (Appointment a : appointments) {
      a.setStatus(AppointmentStatus.PERDIDA);
    }
    appointmentRepository.saveAll(appointments);
  }
}
