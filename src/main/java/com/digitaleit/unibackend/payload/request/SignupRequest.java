package com.digitaleit.unibackend.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupRequest {
	@NotBlank
	@Size(min = 3, max = 20)
	String username;

	@NotBlank
	@Size(max = 50)
	@Email
	String email;

	Set<String> role;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;


	@NotBlank
	@Size(max = 50)
	String firstname;

	@NotBlank
	@Size(max = 50)
	String lastname;
	
}