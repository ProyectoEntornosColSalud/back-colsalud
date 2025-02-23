package com.calderon.denv.pep.service.app.impl;

import com.calderon.denv.pep.model.app.Plan;
import com.calderon.denv.pep.repository.app.PlanRepository;
import com.calderon.denv.pep.service.app.PlanService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
  private final PlanRepository planRepository;

  @Override
  public List<Plan> getPlans() {
    return planRepository.findAll();
  }
}
