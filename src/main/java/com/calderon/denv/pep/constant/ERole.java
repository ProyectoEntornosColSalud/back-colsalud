package com.calderon.denv.pep.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ERole {
  USER("ROLE_USER"),
  ADMIN("ROLE_ADMIN");
  private final String withPrefix;

  public String withPrefix() {
    return this.withPrefix;
  }
}
