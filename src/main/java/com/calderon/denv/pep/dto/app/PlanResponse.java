package com.calderon.denv.pep.dto.app;

import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponse {
  Long id;
  String name;
  String description;
  BigDecimal price;
  BigInteger storageLimit;
}
