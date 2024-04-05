/*
 * package com.booke.controller;
 * 
 * import java.math.BigDecimal; import java.security.Principal; import
 * java.util.HashMap; import java.util.List; import java.util.Map; import
 * java.util.Optional;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.core.Authentication; import
 * org.springframework.security.core.context.SecurityContextHolder; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.ModelAttribute; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping;
 * 
 * import com.booke.model.Book; import com.booke.model.Cart; import
 * com.booke.model.Category; import com.booke.model.OrderStatus; import
 * com.booke.model.PlaceOrder; import com.booke.model.User; import
 * com.booke.repository.BookRepository; import
 * com.booke.repository.CartRepository; import
 * com.booke.repository.OrderRepository; import
 * com.booke.repository.UserRepository; import com.booke.service.BookService;
 * import com.booke.service.CartService; import com.booke.service.OrderService;
 * import com.booke.service.UserService;
 * 
 * @Controller
 * 
 * @RequestMapping("/orders") public class OrderController {
 * 
 * @Autowired private OrderRepository orderRepository;
 * 
 * @Autowired private CartRepository cartRepository;
 * 
 * @Autowired private UserRepository userRepository;
 * 
 * @Autowired private CartService cartService;
 * 
 * @Autowired private BookService bookService;
 * 
 * @Autowired private UserService userService;
 * 
 * @Autowired private OrderService orderService;
 * 
 * private Book book;
 * 
 * @Autowired public OrderController(BookService bookService, OrderService
 * orderService) { this.bookService = bookService; this.orderService =
 * orderService; }
 * 
 * @Autowired BookRepository bookRepository;
 * 
 * @GetMapping("/orderForm") public String showCheckoutForm(Model model,
 * Principal principal) { model.addAttribute("order", new PlaceOrder());
 * 
 * if (principal != null) { User user =
 * userService.findByEmail(principal.getName());
 * 
 * List<Cart> cartItems = cartService.getCartItems(principal.getName());
 * model.addAttribute("cartItems", cartItems);
 * 
 * // Calculate total amount BigDecimal totalAmount =
 * cartService.calculateTotalAmount(cartItems);
 * model.addAttribute("totalAmount", totalAmount);
 * 
 * Map<Long, Integer> cartItems1 = cartService.getCartItemsByUser(user);
 * Map<Book, Integer> cartWithNames = new HashMap<>();
 * cartItems1.forEach((bookId, quantity) -> { Optional<Book> optionalBook =
 * bookService.findById(bookId); optionalBook.ifPresent(book ->
 * cartWithNames.put(book, quantity)); }); model.addAttribute("cartItems",
 * cartWithNames); }
 * 
 * List<Category> categories = bookService.getAllCategories();
 * model.addAttribute("categories", categories); return
 * "order/place_order_form"; }
 * 
 * @PostMapping("/placeOrder") public String
 * processCheckoutForm(@ModelAttribute("order") PlaceOrder order, Model model) {
 * Authentication authentication =
 * SecurityContextHolder.getContext().getAuthentication(); String userEmail =
 * authentication.getName(); User user = userRepository.findByEmail(userEmail);
 * 
 * List<Cart> pendingCarts = cartRepository.findByUserAndStatus(user,
 * OrderStatus.PENDING);
 * 
 * for (Cart cart : pendingCarts) { order.setUser(user);
 * order.setOrderStatus(OrderStatus.PENDING); order.setCart(cart);
 * 
 * orderRepository.save(order);
 * 
 * cart.setStatus(OrderStatus.CONFIRMED); cartRepository.save(cart); } return
 * "redirect:/customer/profile"; }
 * 
 * @GetMapping("/orderhistoryu") public String viewOrderHistory(Model model,
 * Principal principal) { if (principal == null) {
 * model.addAttribute("errorMessage",
 * "You are not authenticated. Please log in."); return "userOrderHistory"; }
 * 
 * User user = userService.findByEmail(principal.getName()); if (user == null) {
 * model.addAttribute("errorMessage", "User not found."); return
 * "userOrderHistory"; }
 * 
 * List<PlaceOrder> orderHistory =
 * orderRepository.findByUserOrderByIdDesc(user); if (orderHistory.isEmpty()) {
 * model.addAttribute("errorMessage", "No order history found."); return
 * "userOrderHistory"; }
 * 
 * model.addAttribute("orderHistory", orderHistory); return
 * "order/userOrderHistory"; }
 * 
 * }
 */

package com.booke.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.booke.model.Book;
import com.booke.model.Cart;
import com.booke.model.Category;
import com.booke.model.OrderStatus;
import com.booke.model.PlaceOrder;
import com.booke.model.User;
import com.booke.repository.BookRepository;
import com.booke.repository.CartRepository;
import com.booke.repository.OrderRepository;
import com.booke.repository.UserRepository;
import com.booke.service.BookService;
import com.booke.service.CartService;
import com.booke.service.OrderService;
import com.booke.service.UserService;

@Controller
@RequestMapping("/orders")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private BookService bookService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	private Book book;

	@Autowired
	public OrderController(BookService bookService, OrderService orderService) {
		this.bookService = bookService;
		this.orderService = orderService;
	}

	@Autowired
	BookRepository bookRepository;

	@GetMapping("/orderForm")
	public String showCheckoutForm(Model model, Principal principal) {
		logger.info("Displaying checkout form");
		model.addAttribute("order", new PlaceOrder());

		if (principal != null) {
			User user = userService.findByEmail(principal.getName());

			List<Cart> cartItems = cartService.getCartItems(principal.getName());
			model.addAttribute("cartItems", cartItems);

			// Calculate total amount
			BigDecimal totalAmount = cartService.calculateTotalAmount(cartItems);
			model.addAttribute("totalAmount", totalAmount);

			Map<Long, Integer> cartItems1 = cartService.getCartItemsByUser(user);
			Map<Book, Integer> cartWithNames = new HashMap<>();
			cartItems1.forEach((bookId, quantity) -> {
				Optional<Book> optionalBook = bookService.findById(bookId);
				optionalBook.ifPresent(book -> cartWithNames.put(book, quantity));
			});
			model.addAttribute("cartItems", cartWithNames);
		}

		List<Category> categories = bookService.getAllCategories();
		model.addAttribute("categories", categories);
		return "order/place_order_form";
	}

	@PostMapping("/placeOrder")
	public String processCheckoutForm(@ModelAttribute("order") PlaceOrder order, Model model) {
		logger.info("Processing checkout form");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = authentication.getName();
		User user = userRepository.findByEmail(userEmail);

		List<Cart> pendingCarts = cartRepository.findByUserAndStatus(user, OrderStatus.PENDING);

		for (Cart cart : pendingCarts) {
			order.setUser(user);
			order.setOrderStatus(OrderStatus.PENDING);
			order.setCart(cart);

			orderRepository.save(order);

			cart.setStatus(OrderStatus.CONFIRMED);
			cartRepository.save(cart);
		}
		return "redirect:/customer/profile";
	}

	@GetMapping("/orderhistoryu")
	public String viewOrderHistory(Model model, Principal principal) {
		logger.info("Viewing order history");
		if (principal == null) {
			model.addAttribute("errorMessage", "You are not authenticated. Please log in.");
			return "userOrderHistory";
		}

		User user = userService.findByEmail(principal.getName());
		if (user == null) {
			model.addAttribute("errorMessage", "User not found.");
			return "userOrderHistory";
		}

		List<PlaceOrder> orderHistory = orderRepository.findByUserOrderByIdDesc(user);
		if (orderHistory.isEmpty()) {
			model.addAttribute("errorMessage", "No order history found.");
			return "userOrderHistory";
		}

		model.addAttribute("orderHistory", orderHistory);
		return "order/userOrderHistory";
	}

}
