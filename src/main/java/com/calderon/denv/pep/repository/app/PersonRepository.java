package com.calderon.denv.pep.repository.app;

import com.calderon.denv.pep.constant.DocumentType;
import com.calderon.denv.pep.model.app.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
	boolean existsByDocumentTypeAndDocumentNumber(DocumentType documentType, String documentNumber);

	boolean existsByEmail(String email);
}
