package com.calderon.denv.pep.model.app;

import com.calderon.denv.pep.constant.ESubscriptionStatus;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription", schema = "app")
public class Subscription {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @OneToOne
  @JoinColumn(name = "plan_id", nullable = false)
  private Plan planId;

  @Column(name = "start_date", nullable = false)
  private Instant startDate;

  @Column(name = "end_date", nullable = false)
  private Instant endDate;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private ESubscriptionStatus status;
}
