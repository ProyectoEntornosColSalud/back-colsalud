package com.calderon.denv.pep.model.app;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plan", schema = "app")
public class Plan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "price", precision = 10, scale = 2, nullable = false)
  private BigDecimal price;

  @Column(name = "storage_limit", nullable = false)
  private BigInteger storageLimit;
}
