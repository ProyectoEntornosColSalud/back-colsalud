package com.calderon.denv.pep.model.app;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor", schema = "app")
public class Doctor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "doctor_id")
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id")
  private Person person;

  @Column(name = "license_number", nullable = false, length = 20)
  private String licenseNumber;

  public Doctor(Long id) {
    this.id = id;
  }
}