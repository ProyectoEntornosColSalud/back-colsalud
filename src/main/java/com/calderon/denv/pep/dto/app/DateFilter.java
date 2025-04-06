package com.calderon.denv.pep.dto.app;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateFilter {
  private Long specialtyId;
  private Long doctorId;
  private String day;
  private Integer startTime;
  private Integer endTime;
}
