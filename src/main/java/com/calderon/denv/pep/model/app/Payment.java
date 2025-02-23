package com.calderon.denv.pep.model.app;

import com.calderon.denv.pep.constant.EPaymentStatus;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments", schema = "app")
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "subscription_id", nullable = false)
  @OneToOne
  private Subscription subscription;

  @Column(name = "payment_date", nullable = false)
  private Instant paymentDate;

  @Column(name = "transaction_id", nullable = false)
  private String transactionId;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private EPaymentStatus status;
}
