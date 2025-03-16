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
@Table(name = "USERS", schema = "auth")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_person")
  private Person person;

  @NotBlank
  @Column(nullable = false, unique = true, length = 100)
  private String username;

  @NotBlank
  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;
}
