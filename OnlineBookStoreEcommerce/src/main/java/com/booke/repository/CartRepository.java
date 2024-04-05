package com.booke.repository;

import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booke.model.Book;
import com.booke.model.Cart;
import com.booke.model.OrderStatus;
import com.booke.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Optional<Cart> findByUserAndBookAndStatus(User user, Book book, String string);

	Optional<Cart> findByUserAndBookAndStatus(User user, Book book, OrderStatus status);

	int countByUser(User user);

	List<Cart> findByUserId(Long userId);

	List<Cart> findByUser(User user);

	Optional<Cart> findByBook(Book book);

	List<Cart> findByUserAndStatus(User user, OrderStatus status);

	List<Cart> findByStatusAndUser_Username(OrderStatus status, String username);


}
