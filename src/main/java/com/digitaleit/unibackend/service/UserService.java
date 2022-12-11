package com.digitaleit.unibackend.service;

import java.util.List;

import com.digitaleit.unibackend.entities.User;
import com.digitaleit.unibackend.payload.request.UserRegistrationRequest;

public interface UserService {
	List<User> getUsers();
	List<User> getUsers(Integer association_id);
	User getUserById(Integer association_id, Long user_id);
	List<User> getPendingApproval();
	List<User> getPendingApproval(Integer association_id);
	User approveOrDenyUser(Long id, String status);
	User saveUser(Integer assocation_id, UserRegistrationRequest userRequest);
	User saveUser(Long id, UserRegistrationRequest userRequest);
	User deleteUser(Long id);
	User disableUser(Long id);
}
