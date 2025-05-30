package com.calderon.denv.pep.controller;

import static com.calderon.denv.pep.constant.Constant.AUTH_HEADER;

import com.calderon.denv.pep.dto.ListItem;
import com.calderon.denv.pep.dto.app.AppointmentResponse;
import com.calderon.denv.pep.dto.app.DateFilter;
import com.calderon.denv.pep.dto.app.SpecialtyDTO;
import com.calderon.denv.pep.model.app.Appointment;
import com.calderon.denv.pep.repository.auth.UserRepository;
import com.calderon.denv.pep.security.JwtUtil;
import com.calderon.denv.pep.service.app.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Validated
@Tag(name = "Appointments", description = "Book and manage appointments")
public class AppointmentController {

  private final AppointmentService appointmentService;
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  @PostMapping("/schedule")
  @Operation(summary = "Schedule an appointment")
  @Parameter(name = "doctor", description = "Doctor ID")
  @Parameter(name = "specialty", description = "Specialty ID")
  @Parameter(
      name = "date",
      description = "Date and time of the appointment, format: yyyy-MM-ddTHH:mm:ss")
  public ResponseEntity<Void> schedule(
      @RequestHeader(AUTH_HEADER) String token,
      @RequestParam("doctor") @Min(1) Long doctorId,
      @RequestParam("specialty") @Min(1) Long specialtyId,
      @RequestParam("date") LocalDateTime date) {
    Long userId = jwtUtil.extractUserId(token);
    appointmentService.schedule(userId, doctorId, specialtyId, date);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{appointmentId}/reschedule")
  public ResponseEntity<Void> reschedule(
      @RequestHeader(AUTH_HEADER) String token,
      @PathVariable Long appointmentId,
      LocalDateTime newDate) {
    Long patientId = userRepository.getPersonIdByUserId(jwtUtil.extractUserId(token));
    Appointment appointment = appointmentService.get(appointmentId);
    if (!Objects.equals(patientId, appointment.getPersonId()))
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    appointmentService.reschedule(appointment, newDate);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/specialties")
  @Operation(summary = "Get list of specialties")
  public ResponseEntity<List<SpecialtyDTO>> getSpecialties() {
    return ResponseEntity.ok(
        appointmentService.getSpecialties().stream().map(SpecialtyDTO::map).toList());
  }

  @GetMapping("/doctors")
  @Operation(summary = "Get doctors by specialty")
  public ResponseEntity<List<ListItem>> getDoctors(
      @RequestParam("specialty") @Min(1) Long specialtyId) {
    return ResponseEntity.ok(appointmentService.getDoctorsBySpecialty(specialtyId));
  }

  @GetMapping("/dates")
  @Operation(summary = "Get list of available dates for appointment booking")
  public ResponseEntity<Map<String, List<LocalDateTime>>> getDoctorAvailableDates(
      @RequestHeader(AUTH_HEADER) String token,
      @RequestParam("doctor") @Min(1) Long doctorId,
      @RequestParam(value = "day", required = false) String day,
      @RequestParam(value = "start", required = false) Integer start,
      @RequestParam(value = "end", required = false) Integer end) {
    Long userId = jwtUtil.extractUserId(token);
    var response =
        appointmentService.getDoctorAvailableDates(
            userId, new DateFilter(doctorId, day, start, end));
    return ResponseEntity.ok(Map.of("available", response));
  }

  @GetMapping
  @Operation(summary = "Get user appointments")
  public ResponseEntity<List<AppointmentResponse>> getUserAppointments(
      @RequestHeader(AUTH_HEADER) String token) {
    Long userId = jwtUtil.extractUserId(token);
    List<AppointmentResponse> appointments = appointmentService.getUserAppointments(userId);
    List<AppointmentResponse> sorted =
        appointments.stream()
            .sorted(
                Comparator.comparing((AppointmentResponse a) -> a.getStatus().getOrder())
                    .thenComparing(AppointmentResponse::getTime))
            .toList();
    return ResponseEntity.ok(sorted);
  }

  @PutMapping("/cancel")
  public ResponseEntity<Void> cancelAppointment(
      @RequestHeader(AUTH_HEADER) String token,
      @RequestParam("appointment") @Min(1) Long appointmentId) {

    Long userId = jwtUtil.extractUserId(token);
    appointmentService.cancelAppointment(userId, appointmentId);
    return ResponseEntity.ok().build();
  }

}
