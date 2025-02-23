package com.calderon.denv.pep.controller;

import com.calderon.denv.pep.dto.app.PlanResponse;
import com.calderon.denv.pep.mapper.PlanMapper;
import com.calderon.denv.pep.model.app.Plan;
import com.calderon.denv.pep.service.app.PlanService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pricing")
@RequiredArgsConstructor
public class PlanController {

  private final PlanService planService;
  static final PlanMapper PLAN_MAPPER = PlanMapper.INSTANCE;

  @RequestMapping("/plans")
  public ResponseEntity<List<PlanResponse>> getPlans() {
    List<Plan> plans = planService.getPlans();
    return new ResponseEntity<>(PLAN_MAPPER.toResponse(plans), HttpStatus.OK);
  }
}
