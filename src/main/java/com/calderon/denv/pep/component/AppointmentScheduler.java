package com.calderon.denv.pep.component;

import static com.calderon.denv.pep.util.Tools.getColTime;

import com.calderon.denv.pep.service.app.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppointmentScheduler {

  private final AppointmentService appointmentService;

  @Scheduled(cron = "0 15 * * * *")
  public void markMissedAppointments() {
    log.info("It's {} - Marking appointments missed...", getColTime());
    appointmentService.updateMissedAppointments();
  }
}
