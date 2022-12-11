package com.digitaleit.unibackend.entities.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitaleit.unibackend.entities.Partner;
import com.digitaleit.unibackend.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	List<User> findByStatus(String status);
	List<User> findByPartner(Partner partner);
	List<User> findByPartnerAndStatus(Partner partner, String status);
	Optional<User> findByPartnerAndId(Partner partner, Long id);
	
}
