package com.booke.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.booke.dto.UserDto;
import com.booke.model.User;

import jakarta.validation.Valid;

public interface UserService {

	User save(User user);

	User findByEmail(String email);

	User getUserByUsername(String username);

	User findByUsername(String username);

	long getTotalUsers();

	Page<User> getUsers(PageRequest of);

	Page<User> getUsers(Pageable pageable);

	List<User> getAllUsers();

	Page<User> getAllUsersPageable(PageRequest of);

	Page<User> getAllUsersPageable(Pageable pageable);

	User getUserById(Long userId);

	void removeUserById(Object userId);

	void updateUser(User user);

	void update(@Valid User user);

}
