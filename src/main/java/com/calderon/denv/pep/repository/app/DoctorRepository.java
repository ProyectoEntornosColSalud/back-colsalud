package com.calderon.denv.pep.repository.app;

import com.calderon.denv.pep.model.app.Doctor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

  @Query("select ds.doctor from DoctorSpecialty ds where ds.specialty.id = :specialtyId")
  List<Doctor> findBySpecialtyId(Long specialtyId);
}
