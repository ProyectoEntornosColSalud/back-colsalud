package com.calderon.denv.pep.service;

import com.calderon.denv.pep.constant.ERole;
import com.calderon.denv.pep.model.auth.Role;

public interface RoleService {
  Role getRole(ERole role);

  Role save(Role role);
}
