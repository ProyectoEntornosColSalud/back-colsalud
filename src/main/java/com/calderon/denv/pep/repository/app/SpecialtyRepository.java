package com.calderon.denv.pep.repository.app;

import com.calderon.denv.pep.model.app.Specialty;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

  @Query("SELECT s FROM Specialty s WHERE s.active = true")
  List<Specialty> findAllActive();
}
