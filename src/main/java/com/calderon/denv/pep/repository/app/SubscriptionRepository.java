package com.calderon.denv.pep.repository.app;

import com.calderon.denv.pep.model.app.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {}
