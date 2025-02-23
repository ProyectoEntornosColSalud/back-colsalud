package com.calderon.denv.pep.model.app;

import jakarta.persistence.*;
import java.math.BigInteger;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files", schema = "app")
public class File {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "file_name", nullable = false)
  private String name;

  @Column(name = "file_path", nullable = false)
  private String path;

  @Column(name = "file_size", nullable = false)
  private BigInteger size;

  @Column(name = "file_type", nullable = false)
  private String type;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;
}
