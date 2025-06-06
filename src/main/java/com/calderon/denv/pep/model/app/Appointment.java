package com.calderon.denv.pep.model.app;

import com.calderon.denv.pep.constant.AppointmentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointment", schema = "app")
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "appointment_id")
	private Long id;

	@Column(name = "person_id")
	private Long personId;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "specialty_id")
	private Specialty specialty;

  @Column(name = "start_time", nullable = false)
  private LocalDateTime startTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private AppointmentStatus status;
}
