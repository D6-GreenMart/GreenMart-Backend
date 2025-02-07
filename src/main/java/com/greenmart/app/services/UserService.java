package com.greenmart.app.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.greenmart.app.domain.entities.User;

import jakarta.persistence.EntityNotFoundException;

public interface UserService {
	User getUserById(UUID id);

	User createUser(User user);

	List<User> getAllUsers();

	User updateUser(UUID userId, User userDetails) throws EntityNotFoundException;

	void deleteUser(UUID userId) throws EntityNotFoundException;

	Optional<User> findByEmail(String email);

	User getUserByEmail(String username);
}
