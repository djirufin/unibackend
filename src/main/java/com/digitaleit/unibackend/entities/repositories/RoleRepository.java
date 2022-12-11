package com.digitaleit.unibackend.entities.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitaleit.unibackend.entities.ERole;
import com.digitaleit.unibackend.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(ERole name);
}
