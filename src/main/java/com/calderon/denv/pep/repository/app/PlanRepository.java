package com.calderon.denv.pep.repository.app;

import com.calderon.denv.pep.model.app.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {}
