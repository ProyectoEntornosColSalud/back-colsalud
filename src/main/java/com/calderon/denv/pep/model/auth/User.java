package com.calderon.denv.pep.model.auth;

import com.calderon.denv.pep.constant.Role;
import com.calderon.denv.pep.model.app.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", schema = "app")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "id_person")
  private Long personId;

  @NotBlank
  @Column(nullable = false, unique = true, length = 100)
  private String username;

  @NotBlank
  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false, length = 10)
  private Role role;
}
