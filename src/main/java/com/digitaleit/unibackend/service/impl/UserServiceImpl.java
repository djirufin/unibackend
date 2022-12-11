package com.digitaleit.unibackend.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.digitaleit.unibackend.entities.Partner;
import com.digitaleit.unibackend.entities.User;
import com.digitaleit.unibackend.entities.repositories.PartnerRepository;
import com.digitaleit.unibackend.entities.repositories.UserRepository;
import com.digitaleit.unibackend.payload.request.UserRegistrationRequest;
import com.digitaleit.unibackend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public List<User> getUsers(Integer partner_id) {
		Partner partner = partnerRepository.findById(partner_id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find this IP"));
		return userRepository.findByPartner(partner);
	}

	@Override
	public List<User> getPendingApproval() {
		return userRepository.findByStatus("PENDING");
	}

	@Override
	public List<User> getPendingApproval(Integer partner_id) {
		Partner partner = partnerRepository.findById(partner_id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find this IP"));

		return userRepository.findByPartnerAndStatus(partner, "PENDING");
	}

	@Override
	public User approveOrDenyUser(Long id, String status) {
		return this.changeStatus(id, status);
	}

	@Override
	public User getUserById(Integer partner_id, Long user_id) {
		Partner partner = partnerRepository.findById(partner_id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find this assocation"));
		return userRepository.findByPartnerAndId(partner, user_id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Cet utilisateur n'existe pas pour cette partner"));
	}

	@Override
	public User saveUser(Integer partner_id, UserRegistrationRequest userRequest) {
		Partner partner = partnerRepository.findById(partner_id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find this assocation"));

		User user = new User();
		user.setFirstname(userRequest.getFirstname());
		user.setLastname(userRequest.getLastname());
		user.setAdresse(userRequest.getAdresse());
		user.setMsisdn(userRequest.getMsisdn());
		user.setGender(userRequest.getGender());

		user.setPartner(partner);
		user.setEmail(String.format("%s@oryc.com", userRequest.getMsisdn()));
		user.setUsername(userRequest.getMsisdn());
		user.setStatus("PENDING");
		return userRepository.save(user);

	}

	@Override
	public User saveUser(Long id, UserRegistrationRequest user) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur n'existe pas"));
		existingUser.setFirstname(user.getFirstname());
		existingUser.setLastname(user.getLastname());

		/*
		 * Verifier si le numero n'a pas de pret en cours avec le numero avant de le
		 * changer
		 */
		existingUser.setMsisdn(user.getMsisdn());
		existingUser.setGender(user.getGender());
		return userRepository.save(existingUser);
	}

	@Override
	public User deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur n'existe pas"));
		userRepository.delete(user);
		return user;
	}

	@Override
	public User disableUser(Long id) {
		return this.changeStatus(id, "DISABLED");
	}

	private User changeStatus(Long id, String status) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur n'existe pas"));
		user.setStatus(status);
		return user;
	}



}
