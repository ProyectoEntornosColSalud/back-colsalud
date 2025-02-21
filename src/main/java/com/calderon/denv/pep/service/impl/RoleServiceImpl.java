package com.calderon.denv.pep.service.impl;

import com.calderon.denv.pep.constant.ERole;
import com.calderon.denv.pep.exception.DataNotFoundException;
import com.calderon.denv.pep.model.auth.Role;
import com.calderon.denv.pep.repository.RoleRepository;
import com.calderon.denv.pep.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  @Override
  public Role getRole(ERole role) {
    return roleRepository
        .findByName(role.prefix())
        .orElseThrow(() -> new DataNotFoundException("Error: Role is not found"));
  }

  @Override
  public Role save(Role role) {
    return null;
  }
}
