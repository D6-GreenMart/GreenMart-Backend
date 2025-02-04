package com.greenmart.app.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.greenmart.app.domain.entities.User;
import com.greenmart.app.repositories.UserRepository;
import com.greenmart.app.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUserById(UUID userId) throws EntityNotFoundException {
		return userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User updateUser(UUID userId, User userDetails) throws EntityNotFoundException {
		User existingUser = getUserById(userId);
		existingUser.setName(userDetails.getName());
		existingUser.setPassword(userDetails.getPassword());
		existingUser.setEmail(userDetails.getEmail());
		existingUser.setAddress(userDetails.getAddress());
		existingUser.setPhoneNumber(userDetails.getPhoneNumber());
		existingUser.setRole(userDetails.getRole());
		return userRepository.save(existingUser);
	}

	@Override
	public void deleteUser(UUID userId) throws EntityNotFoundException {
		User user = getUserById(userId);
		userRepository.delete(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
