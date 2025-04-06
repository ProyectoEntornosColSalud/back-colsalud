package com.calderon.denv.pep.model.app;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class DoctorSpecialtyId implements Serializable {
  private Long doctorId;
  private Long specialtyId;
}
