package com.calderon.denv.pep.model.app;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor_specialty", schema = "app")
public class DoctorSpecialty {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "doctor_id")
  private Doctor doctor;

  @ManyToOne
  @JoinColumn(name = "specialty_id")
  private Specialty specialty;
}
