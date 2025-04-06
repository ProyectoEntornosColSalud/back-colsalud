package com.calderon.denv.pep.controller;

import static com.calderon.denv.pep.constant.Constant.AUTH_HEADER;

import com.calderon.denv.pep.dto.app.AvailableDoctorResponse;
import com.calderon.denv.pep.dto.app.DateFilter;
import com.calderon.denv.pep.security.JwtUtil;
import com.calderon.denv.pep.service.app.AppointmentService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Validated
public class AppointmentController {

  private final AppointmentService appointmentService;
  private final JwtUtil jwtUtil;

  @GetMapping("/doctors")
  public ResponseEntity<List<AvailableDoctorResponse>> getDoctors(
      @RequestParam("specialty") Long specialtyId) {
    return ResponseEntity.ok(appointmentService.getDoctorsBySpecialty(specialtyId));
  }

  @GetMapping("/dates")
  public ResponseEntity<Map<String, List<LocalDateTime>>> getDoctorAvailableDates(
      @RequestHeader(AUTH_HEADER) String token,
      @RequestParam("specialty") Long specialtyId,
      @RequestParam("doctor") Long doctorId,
      @RequestParam(value = "day", required = false) String day,
      @RequestParam(value = "start", required = false) Integer start,
      @RequestParam(value = "end", required = false) Integer end) {
    Long userId = jwtUtil.extractUserId(token);
    var response =
        appointmentService.getDoctorAvailableDates(
            userId, new DateFilter(specialtyId, doctorId, day, start, end));
    return ResponseEntity.ok(Map.of("available", response));
  }
}
