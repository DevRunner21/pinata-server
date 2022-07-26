package com.nexters.pinataserver.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.pinataserver.common.exception.ResponseException;
import com.nexters.pinataserver.common.exception.e4xx.NotFoundException;
import com.nexters.pinataserver.user.domain.User;
import com.nexters.pinataserver.user.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public void createUser(User user) {
		userRepository.save(user);
	}

	// TODO Exception
	@Transactional(readOnly = true)
	public User getUserByProviderId(Long providerId) throws ResponseException {
		return userRepository.findUserByProviderId(providerId).orElseThrow(NotFoundException.USER);
	}

}
