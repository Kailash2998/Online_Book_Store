package com.booke.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.engine.spi.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booke.dto.UserNotFoundException;
import com.booke.model.Book;
import com.booke.model.Cart;
import com.booke.model.OrderStatus;
import com.booke.model.User;
import com.booke.repository.BookRepository;
import com.booke.repository.CartRepository;
import com.booke.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserService userService;

	@Autowired
	BookService bookService;

	@Autowired
	CartService cartService;

	private Map<Long, Integer> cartItems = new HashMap<>();

	private int bookId;

	public Map<Long, Integer> getCartItems() {
		return Collections.unmodifiableMap(cartItems);
	}

	@Transactional
	public void addToCart(Long bookId, int quantity, User user) {
		Book book = bookService.findById(bookId).orElse(null);
		if (book == null) {
			return;
		}

		Optional<Cart> existingCartItem = cartRepository.findByUserAndBookAndStatus(user, book, OrderStatus.PENDING);

		if (existingCartItem.isPresent()) {
			Cart cartItem = existingCartItem.get();
			cartItem.setQuanity(cartItem.getQuanity() + quantity);
			cartRepository.save(cartItem);
			System.out.println("Existing cart item updated. Book: " + book.getName() + ", Quantity: " + quantity);
		} else {
			Cart newCartItem = new Cart();
			newCartItem.setUser(user);
			newCartItem.setBook(book);
			newCartItem.setQuanity(quantity);
			newCartItem.setStatus(OrderStatus.PENDING);
			cartRepository.save(newCartItem);
			System.out.println("New cart item added. Book: " + book.getName() + ", Quantity: " + quantity);
		}
	}

	@Transactional
	public List<Cart> getCartItems(String userEmail) {
		User user = userService.findByEmail(userEmail);
		if (user != null) {

			return cartRepository.findByUser(user);
		} else {
			throw new UserNotFoundException("User with email " + userEmail + " not found");
		}
	}

	@Transactional
	public BigDecimal calculateTotalAmount(List<Cart> carts) {
		BigDecimal totalAmount = BigDecimal.ZERO;

		for (Cart cart : carts) {
			Book book = cart.getBook();
			int quantity = cart.getQuanity();

			if (book != null && quantity > 0) {
				BigDecimal price = book.getPrice();
				BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));

				totalAmount = totalAmount.add(subtotal);
			}
		}

		return totalAmount;
	}

	@Transactional
	public List<Cart> getPendingCartItemsByUser(User user) {
		return cartRepository.findByUserAndStatus(user, OrderStatus.PENDING);
	}

	@Transactional
	public void removeCartItem(Long cartItemId) {
		cartRepository.deleteById(cartItemId);
	}

	@Transactional
	public Cart findCartItemById(Long itemId) {
		Optional<Cart> optionalCartItem = cartRepository.findById(itemId);
		return optionalCartItem.orElse(null);
	}

	@Transactional
	public void updateCartItemQuantity(Long itemId, int quanity) {
		Optional<Cart> optionalCartItem = cartRepository.findById(itemId);

		if (optionalCartItem.isPresent()) {
			Cart cartItem = optionalCartItem.get();

			cartItem.setQuanity(quanity);
			cartRepository.save(cartItem);
		} else {
			throw new IllegalArgumentException("Cart item with ID " + itemId + " not found");
		}
	}

	public Map<Long, Integer> getCartItemsByUser(User user) {
		Map<Long, Integer> cartItems = new HashMap<>();
		List<Cart> userCartItems = cartRepository.findByUser(user);
		for (Cart cartItem : userCartItems) {
			cartItems.put(cartItem.getBook().getBookId(), cartItem.getQuanity());
		}
		return cartItems;
	}

	public void clearCartItemsByUser(User user) {
		List<Cart> userCartItems = cartRepository.findByUser(user);
		cartRepository.deleteAll(userCartItems);
	}

	public List<Cart> getPendingCartItems(String name) {
		return cartRepository.findByStatusAndUser_Username(OrderStatus.PENDING, name);
	}

}
