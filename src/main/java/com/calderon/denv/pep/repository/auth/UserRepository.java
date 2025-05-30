package com.calderon.denv.pep.repository.auth;

import com.calderon.denv.pep.model.app.Person;
import com.calderon.denv.pep.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

	@Query("select u.personId from User u where u.id = :userId")
	Long getPersonIdByUserId(Long userId);
}
