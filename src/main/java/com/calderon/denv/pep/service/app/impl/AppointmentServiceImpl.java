package com.calderon.denv.pep.service.app.impl;

import static com.calderon.denv.pep.constant.Constant.END_WORK_HOUR;
import static com.calderon.denv.pep.constant.Constant.START_WORK_HOUR;
import static com.calderon.denv.pep.util.Tools.parseDate;
import static java.util.Objects.requireNonNull;

import com.calderon.denv.pep.dto.ListItem;
import com.calderon.denv.pep.dto.app.DateFilter;
import com.calderon.denv.pep.model.app.Appointment;
import com.calderon.denv.pep.model.app.Doctor;
import com.calderon.denv.pep.model.app.Person;
import com.calderon.denv.pep.model.app.Specialty;
import com.calderon.denv.pep.model.auth.User;
import com.calderon.denv.pep.repository.app.AppointmentRepository;
import com.calderon.denv.pep.repository.app.DoctorRepository;
import com.calderon.denv.pep.repository.app.SpecialtyRepository;
import com.calderon.denv.pep.service.app.AppointmentService;
import com.calderon.denv.pep.service.auth.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
  private final DoctorRepository doctorRepository;
  private final AppointmentRepository appointmentRepository;
  private final UserService userService;
  private final SpecialtyRepository specialtyRepository;

  @Override
  public void schedule(Appointment appointment) {}

  @Override
  public List<ListItem> getSpecialties() {
    return specialtyRepository.findAllActive().stream().map(this::mapSpecialtyResponse).toList();
  }

  @Override
  public List<ListItem> getDoctorsBySpecialty(Long specialtyId) {
    return doctorRepository.findBySpecialtyId(specialtyId).stream()
        .map(this::mapDoctorResponse)
        .toList();
  }

  private ListItem mapDoctorResponse(Doctor doctor) {
    return new ListItem(doctor.getId(), formatPersonName(doctor.getPerson()));
  }

  private ListItem mapSpecialtyResponse(Specialty specialty) {
    return new ListItem(specialty.getId(), specialty.getName());
  }

  private static String formatPersonName(Person person) {
    return person.getName() + " " + person.getLastname().replaceAll("\\s+", " ");
  }

  @Override
  public List<LocalDateTime> getDoctorAvailableDates(Long userId, DateFilter filter) {
    LocalDate filterDate = getValidFilterDay(filter.getDay());
    int startHour = getValidStartHour(filter.getStartTime(), filterDate);
    int endHour = getValidEndHour(filter.getEndTime(), startHour);

    LocalDateTime startDate = filterDate.atTime(startHour, 0);
    LocalDateTime endDate = filterDate.atTime(endHour, 0);

    return getAvailableAppointments(filter.getDoctorId(), userId, startDate, endDate, 12);
  }

  private static LocalDate getValidFilterDay(String day) {
    LocalDate today = LocalDate.now();
    return parseDate(day).filter(d -> !d.isBefore(today)).orElse(today);
  }

  private static int getValidStartHour(Integer startHour, LocalDate day) {
    boolean isValidWorkHour = isWorkTime(startHour);

    if (day.isEqual(LocalDate.now())) {
      int nextHour = LocalDateTime.now().getHour() + 1;
      return isValidWorkHour && startHour > nextHour ? startHour : nextHour;
    }

    // For future days, use the time provided if it is valid, or the work start time otherwise
    return isValidWorkHour ? startHour : START_WORK_HOUR;
  }

  private int getValidEndHour(Integer endHour, Integer startHour) {
    if (isWorkTime(endHour) && endHour > startHour) return endHour;
    else return END_WORK_HOUR;
  }

  public List<LocalDateTime> getAvailableAppointments(
      @NonNull Long doctorId,
      @NonNull Long userId,
      @NonNull LocalDateTime startDate,
      @NonNull LocalDateTime endDate,
      int amount) {
    LocalDateTime current = startDate.truncatedTo(ChronoUnit.HOURS);
    endDate = endDate.truncatedTo(ChronoUnit.HOURS);

    Set<LocalDateTime> occupiedAppointments = new HashSet<>();
    occupiedAppointments.addAll(getDoctorAppointments(doctorId));
    occupiedAppointments.addAll(getPatientAppointments(userId));

    List<LocalDateTime> availableAppointments = new ArrayList<>();

    while (availableAppointments.size() < amount) {
      if (current.isAfter(endDate)) break;

      int hour = current.getHour();
      if (isWorkTime(hour) && !occupiedAppointments.contains(current)) {
        availableAppointments.add(current);
      }
      current = current.plusHours(1);
    }

    return availableAppointments;
  }

  private static boolean isWorkTime(Integer hour) {
    return hour != null && hour >= START_WORK_HOUR && hour < END_WORK_HOUR;
  }

  private Set<LocalDateTime> getDoctorAppointments(Long doctorId) {
    return appointmentRepository.getFutureAppointments(doctorId).stream()
        .map(date -> date.truncatedTo(ChronoUnit.HOURS))
        .collect(Collectors.toSet());
  }

  private HashSet<LocalDateTime> getPatientAppointments(Long userID) {
    User user = requireNonNull(userService.getUserById(userID));
    return appointmentRepository.getPatientAppointments(user.getPerson().getId()).stream()
        .map(date -> date.truncatedTo(ChronoUnit.HOURS))
        .collect(Collectors.toCollection(HashSet::new));
  }

  @Override
  public List<Object> filterDates(
      Long specialtyId, Long doctorId, LocalDate day, Integer start, Integer end) {
    return List.of();
  }
}
