package com.booke.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booke.model.User;
import com.booke.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	private static final Sort PageRequest = null;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	private Sort pageequest;

	@Override
	public User save(User user) {

		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);

		return userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public long getTotalUsers() {
		return userRepository.count();
	}

	@Override
	public Page<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Page<User> getUsers(PageRequest of) {
		return (Page<User>) userRepository.findAll(PageRequest);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Page<User> getAllUsersPageable(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Page<User> getAllUsersPageable(org.springframework.data.domain.PageRequest of) {
		return (Page<User>) userRepository.findAll(pageequest);
	}

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	@Override
	public void removeUserById(Object userId) {
		userRepository.deleteById((Long) userId);

	}

	@Override
	public void updateUser(User user) {
		Long userId = user.getId();
		if (userId == null) {
			throw new IllegalArgumentException("User ID cannot be null");
		}

		User existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser != null) {
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setUsername(user.getUsername());
			existingUser.setEmail(user.getEmail());
			existingUser.setRole(user.getRole());
			existingUser.setPassword(user.getPassword());

			userRepository.save(existingUser);
		} else {
			throw new RuntimeException("User not found with id: " + userId);
		}
	}

	@Override
	public void update(User user) {
		Long userId = user.getId();
		if (userId == null) {
			throw new IllegalArgumentException("User ID cannot be null");
		}

		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setUsername(user.getUsername());
		existingUser.setEmail(user.getEmail());

		// Check if password is provided and update it
		String newPassword = user.getPassword();
		if (newPassword != null && !newPassword.isEmpty()) {
			existingUser.setPassword(newPassword);
		}

		// Check if role is provided and update it
		String newRole = user.getRole();
		if (newRole != null && !newRole.isEmpty()) {
			existingUser.setRole(newRole);
		}

		// Save the updated user
		userRepository.save(existingUser);
	}

}
