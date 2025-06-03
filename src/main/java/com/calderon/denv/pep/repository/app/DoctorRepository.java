package com.calderon.denv.pep.repository.app;

import com.calderon.denv.pep.model.app.Doctor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

  @Query("select ds.doctor from DoctorSpecialty ds where ds.specialty.id = :specialtyId")
  List<Doctor> findBySpecialtyId(Long specialtyId);

  @Query("select d from Doctor d join User u on d.person.id = u.personId where u.id = :userId")
  Optional<Doctor> getByUserId(Long userId);

	@Query("select count(d) > 0 from Doctor d join Person p on d.person.id = p.id where p.id = :personId")
	boolean existsByPersonId(Long personId);
}
