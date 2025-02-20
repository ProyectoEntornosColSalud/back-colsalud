package com.calderon.denv.pep.model.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS", schema = "AUTH")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false, length = 100)
  private String name;

  @NotBlank
  @Column(name = "last_name", nullable = false, length = 100)
  private String lastName;

  @NotBlank
  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @NotBlank
  @Column(nullable = false)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      schema = "auth",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"),
      uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
  private Set<Role> roles;

  public void addRole(Role role) {
    if (this.roles == null) this.roles = new HashSet<>();
    this.roles.add(role);
  }
}
