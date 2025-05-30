package com.calderon.denv.pep.controller;

import static com.calderon.denv.pep.constant.Constant.AUTH_HEADER;

import com.calderon.denv.pep.constant.DoctorAppointmentSearchType;
import com.calderon.denv.pep.dto.app.projection.DoctorAppointment;
import com.calderon.denv.pep.model.app.Appointment;
import com.calderon.denv.pep.model.app.Doctor;
import com.calderon.denv.pep.repository.app.DoctorRepository;
import com.calderon.denv.pep.security.JwtUtil;
import com.calderon.denv.pep.service.app.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctor")
@RequiredArgsConstructor
class DoctorController {

  private final AppointmentService appointmentService;
  private final JwtUtil jwtUtil;
  private final DoctorRepository doctorRepository;

  @GetMapping("/appointments")
  @Operation(summary = "View Doctor Appointments")
  @Parameter(name = "status", description = "Status of the appointment, PENDING, PAST")
  public ResponseEntity<Page<DoctorAppointment>> viewDoctorAppointments(
      @Parameter(hidden = true) @RequestHeader(AUTH_HEADER) String token,
      @RequestParam("status") DoctorAppointmentSearchType searchType,
      @PageableDefault(sort = "startTime", direction = Sort.Direction.ASC) Pageable pageable) {
    Long userId = jwtUtil.extractUserId(token);
    Doctor doctor = doctorRepository.getByUserId(userId).orElseThrow();

    // Logic to retrieve and display doctor's appointments
    Page<DoctorAppointment> response =
        appointmentService.getAppointmentsByDoctor(doctor, searchType, pageable);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Mark an appointment as attended")
  @PatchMapping("/appointments/{appointmentId}/check")
  public ResponseEntity<Void> checkDoctorAppointments(
      @Parameter(hidden = true) @RequestHeader(AUTH_HEADER) String token,
      @PathVariable Long appointmentId) {
    Long userId = jwtUtil.extractUserId(token);
    Doctor doctor = doctorRepository.getByUserId(userId).orElseThrow();
    Appointment appointment = appointmentService.get(appointmentId);
    if (!doctor.getId().equals(appointment.getDoctor().getId()))
      return ResponseEntity.badRequest().build();
    appointmentService.markAsAttended(appointment);
    return ResponseEntity.ok().build();
  }
}
