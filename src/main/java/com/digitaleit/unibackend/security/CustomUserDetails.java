package com.digitaleit.unibackend.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.digitaleit.unibackend.entities.User;

@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails {
	private Long id;
	private final String UserName;
	private final String email;
	
	private final String password;
	
	private final boolean active;
	
	private final boolean accountNonExpired;
	
	private final boolean accountNonlocked;
	
	private final boolean credentialNotExpired;
	
	
private final List<GrantedAuthority> autorities;
	
	public CustomUserDetails(User user) {
		this.id = user.getId();
		this.UserName = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.active  = true; //user.getActive().equals("1");
		this.autorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
//		Date today = new Date();
		this.accountNonExpired = true; //(today.compareTo(user.getAccountExpiryDate()) <= 0) ? true : false;
		this.accountNonlocked = true; //(user.getAccountLocked().equals("0")) ? true : false;
		this.credentialNotExpired = true; //(today.compareTo(user.getCredentialExpiryDate() ) <= 0) ? true: false;
	    
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.autorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {		
		return this.UserName;
	}	
	

	@Override
	public boolean isAccountNonExpired() {
		
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return this.accountNonlocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {		
		return this.credentialNotExpired;
	}

	@Override
	public boolean isEnabled() {		
		return this.active;
	}
	
	public Long getId() {
		return this.id;
	}

	public String getEmail() {
		return this.email;
	}

}
