package com.calderon.denv.pep.model.app;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "specialty", schema = "app")
public class Specialty {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "specialty_id")
  private Long id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "active", nullable = false)
  private Boolean active;

  public Specialty(Long id) {
    this.id = id;
  }
}
