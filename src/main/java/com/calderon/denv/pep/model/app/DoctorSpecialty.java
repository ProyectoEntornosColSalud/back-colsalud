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
  @EmbeddedId private DoctorSpecialtyId id;

  @ManyToOne
  @MapsId("doctorId")
  @JoinColumn(name = "doctor_id")
  private Doctor doctor;

  @ManyToOne
  @MapsId("specialtyId")
  @JoinColumn(name = "specialty_id")
  private Specialty specialty;
}
