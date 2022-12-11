package com.digitaleit.unibackend.payload.request;

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
public class UserRegistrationRequest {
	@NotBlank
	@Size(max = 50)
	String firstname;

	@NotBlank
	@Size(max = 50)
	String lastname;

	@NotBlank
	@Size(max = 10)
	String gender;
	
	@NotBlank
	@Size(max = 20)
	String msisdn;
	
	@NotBlank
	@Size(max = 50)
	String adresse;
	
}