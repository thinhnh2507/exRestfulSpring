package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.config.CustomUserDetail;
import com.jwt.JwtTokenProvider;
import com.model.User;
import com.payload.JwtReponse;
import com.payload.JwtRequest;
import com.repository.UserRepository;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@GetMapping("/test")
	public ResponseEntity<List<User>> getAllUser(){
		List<User> users = userRepository.findAll();
		if(users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest){
		JwtReponse response = new JwtReponse();
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));

		} catch (AuthenticationException e) {
			response.setMessage("Email or password does not match");
			return new ResponseEntity(response, HttpStatus.NOT_FOUND);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
		response.setTokken(jwt);
		return new ResponseEntity(response, HttpStatus.OK);

	}
}
