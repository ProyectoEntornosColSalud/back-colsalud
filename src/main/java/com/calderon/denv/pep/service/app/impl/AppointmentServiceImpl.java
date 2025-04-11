package com.calderon.denv.pep.service.app.impl;

import static com.calderon.denv.pep.constant.Constant.END_WORK_HOUR;
import static com.calderon.denv.pep.constant.Constant.START_WORK_HOUR;
import static com.calderon.denv.pep.util.Tools.getColTime;
import static com.calderon.denv.pep.util.Tools.parseDate;
import static java.util.Objects.requireNonNull;

import com.calderon.denv.pep.constant.AppointmentStatus;
import com.calderon.denv.pep.dto.ListItem;
import com.calderon.denv.pep.dto.app.AppointmentResponse;
import com.calderon.denv.pep.dto.app.DateFilter;
import com.calderon.denv.pep.exception.BadRequestException;
import com.calderon.denv.pep.exception.ValidationException;
import com.calderon.denv.pep.model.app.Appointment;
import com.calderon.denv.pep.model.app.Doctor;
import com.calderon.denv.pep.model.app.Person;
import com.calderon.denv.pep.model.app.Specialty;
import com.calderon.denv.pep.model.auth.User;
import com.calderon.denv.pep.repository.DoctorSpecialtyRepository;
import com.calderon.denv.pep.repository.app.AppointmentRepository;
import com.calderon.denv.pep.repository.app.DoctorRepository;
import com.calderon.denv.pep.repository.app.SpecialtyRepository;
import com.calderon.denv.pep.service.app.AppointmentService;
import com.calderon.denv.pep.service.auth.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
  private final DoctorSpecialtyRepository doctorSpecialtyRepository;

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
    FilterDayResult filterDayResult = getValidFilterDay(filter.getDay());
    int startHour = getValidStartHour(filter.getStartTime(), filterDayResult.date);
    int endHour = getValidEndHour(filter.getEndTime(), startHour);

    LocalDateTime startDate = filterDayResult.date.atTime(startHour, 0);
    LocalDateTime endDate =
        filterDayResult.usingGivenDate
            ? filterDayResult.date.atTime(endHour, 0)
            : filterDayResult.date.plusYears(2).atTime(END_WORK_HOUR, 0);

    return getAvailableAppointments(filter.getDoctorId(), userId, startDate, endDate, 12);
  }

  private static FilterDayResult getValidFilterDay(String day) {
    LocalDate today = getColTime().toLocalDate();
    LocalDate givenDate = parseDate(day).orElse(null);
    boolean usingGivenDate = givenDate != null && !givenDate.isBefore(today);
    return new FilterDayResult(usingGivenDate ? givenDate : today, usingGivenDate);
  }

  private static int getValidStartHour(Integer startHour, LocalDate day) {
    boolean isValidWorkHour = isWorkTime(startHour);

    if (day.isEqual(getColTime().toLocalDate())) {
      int nextHour = getColTime().getHour() + 1;
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
    return appointmentRepository.getFutureAppointments(doctorId, getColTime()).stream()
        .map(date -> date.truncatedTo(ChronoUnit.HOURS))
        .collect(Collectors.toSet());
  }

  private HashSet<LocalDateTime> getPatientAppointments(Long userID) {
    User user = requireNonNull(userService.getUserById(userID));
    return appointmentRepository
        .getFuturePatientAppointmentsTimes(user.getPerson().getId(), getColTime())
        .stream()
        .map(date -> date.truncatedTo(ChronoUnit.HOURS))
        .collect(Collectors.toCollection(HashSet::new));
  }

  @Override
  public void schedule(Long userId, Long doctorId, Long specialtyId, LocalDateTime date) {
    User user = requireNonNull(userService.getUserById(userId));
    date = date.truncatedTo(ChronoUnit.HOURS);

    if (date.isBefore(getColTime()) || !isWorkTime(date.getHour())) {
      throw new BadRequestException("Date is not valid");
    }

    if (!doctorSpecialtyRepository.existsByDoctorIdAndSpecialtyId(doctorId, specialtyId)) {
      throw new ValidationException("Doctor does not have the selected specialty");
    }

    if (appointmentRepository.existsByDoctorIdAndStartTimeAndStatus(doctorId, date, AppointmentStatus.PENDIENTE))
      throw new ValidationException("Date is already booked");

    appointmentRepository.save(
        Appointment.builder()
            .doctor(new Doctor(doctorId))
            .person(user.getPerson())
            .specialty(new Specialty(specialtyId))
            .startTime(date)
            .status(AppointmentStatus.PENDIENTE)
            .build());
  }

  @Override
  public List<AppointmentResponse> getUserAppointments(Long userId) {
    User user = requireNonNull(userService.getUserById(userId));
    return appointmentRepository.getPatientAppointments(user.getPerson().getId()).stream()
        .map(
            ap ->
                AppointmentResponse.builder()
                    .appointmentId(ap.getId())
                    .specialtyName(ap.getSpecialty().getName())
                    .doctorName(formatPersonName(ap.getDoctor().getPerson()))
                    .time(ap.getStartTime())
                    .status(ap.getStatus())
                    .build())
        .toList();
  }

  @Override
  public void cancelAppointment(Long userId, Long appointmentId) {
    Person person = requireNonNull(userService.getUserById(userId)).getPerson();
    Appointment ap =
        appointmentRepository
            .findByIdAndPersonId(appointmentId, person.getId())
            .orElseThrow(() -> new BadRequestException("Appointment not found"));
    ap.setStatus(AppointmentStatus.CANCELADA);
    appointmentRepository.save(ap);
  }

  record FilterDayResult(LocalDate date, boolean usingGivenDate) {}
}
