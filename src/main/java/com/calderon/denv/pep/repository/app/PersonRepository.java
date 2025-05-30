package com.calderon.denv.pep.repository.app;

import com.calderon.denv.pep.constant.DocumentType;
import com.calderon.denv.pep.model.app.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
	boolean existsByDocumentTypeAndDocumentNumber(DocumentType documentType, String documentNumber);

	boolean existsByEmail(String email);

	@Query("select p from Person p join User u on u.personId = p.id where u.id = :userId")
	Optional<Person> getByUserId(Long userId);
}
