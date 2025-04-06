package com.calderon.denv.pep.repository;

import com.calderon.denv.pep.model.app.DoctorSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorSpecialtyRepository extends JpaRepository<DoctorSpecialty, Long> {

  boolean existsByDoctorIdAndSpecialtyId(Long doctorId, Long specialtyId);
}
