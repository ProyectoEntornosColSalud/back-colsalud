package com.calderon.denv.pep.repository;

import com.calderon.denv.pep.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(String withPrefix);
}
