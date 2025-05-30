package com.calderon.denv.pep.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AppointmentStatus {
  PENDIENTE(1),
  PERDIDA(2),
  ASISTIDA(3),
  CANCELADA(4);

  private final int order;
}
