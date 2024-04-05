/*
 * package com.booke.controller;
 * 
 * import java.io.IOException; import java.nio.file.Files; import
 * java.nio.file.Path; import java.nio.file.Paths; import
 * java.security.Principal; import java.util.List; import java.util.Optional;
 * import org.springframework.data.domain.Pageable; import org.slf4j.Logger;
 * import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.data.domain.Page; import
 * org.springframework.data.domain.PageRequest; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.ModelAttribute; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.multipart.MultipartFile;
 * 
 * import com.booke.dto.BookDTO; import com.booke.model.Author; import
 * com.booke.model.Book; import com.booke.model.Category; import
 * com.booke.model.OrderStatus; import com.booke.model.PlaceOrder; import
 * com.booke.model.User; import com.booke.repository.CategoryRepository; import
 * com.booke.repository.OrderRepository; import com.booke.service.AuthorService;
 * import com.booke.service.BookService; import
 * com.booke.service.CategoryService; import com.booke.service.OrderService;
 * import com.booke.service.UserService;
 * 
 * 
 * 
 * @Controller public class AdminControlller {
 * 
 * @Autowired CategoryService categoryService;
 * 
 * @Autowired BookService bookService;
 * 
 * @Autowired UserService userService;
 * 
 * @Autowired CategoryRepository categoryRepository;
 * 
 * @Autowired AuthorService authorService;
 * 
 * @Autowired private OrderRepository orderRepository;
 * 
 * @Autowired OrderService orderService;
 * 
 * private static final Logger logger =
 * LoggerFactory.getLogger(AdminControlller.class);
 * 
 * // // Category Section // @GetMapping("/admin-page/categories") // public
 * String showCategories(Model model, @RequestParam(defaultValue = "0") int
 * page) { // Page<Category> categoryPage =
 * categoryService.findAll(PageRequest.of(page, 4)); //
 * model.addAttribute("categories", categoryPage.getContent()); //
 * model.addAttribute("currentPage", page); // model.addAttribute("totalPages",
 * categoryPage.getTotalPages()); // return "category/categories"; // }
 * 
 * // Category Section
 * 
 * @GetMapping("/admin-page/categories") public String showCategories(Model
 * model, @RequestParam(defaultValue = "0") int page) { try { Page<Category>
 * categoryPage = categoryService.findAll(PageRequest.of(page, 4));
 * model.addAttribute("categories", categoryPage.getContent());
 * model.addAttribute("currentPage", page); model.addAttribute("totalPages",
 * categoryPage.getTotalPages()); return "category/categories"; } catch
 * (Exception e) {
 * logger.error("An error occurred while fetching categories: {}",
 * e.getMessage()); return "error"; } }
 * 
 * @GetMapping("/admin-page/categories/add") public String getCatAdd(Model
 * model) { model.addAttribute("category", new Category()); return
 * "category/categoriesAdd"; }
 * 
 * @PostMapping("/admin-page/categories/add") public String
 * postCatAdd(@ModelAttribute("category") Category category) {
 * categoryService.addCategory(category); return
 * "redirect:/admin-page/categories"; }
 * 
 * @GetMapping("/admin-page/categories/delete/{id}") public String
 * deleteCat(@PathVariable int id) { categoryService.removeCategoryById(id);
 * return "redirect:/admin/categories"; }
 * 
 * @GetMapping("/admin-page/categories/update/{id}") public String
 * updateCat(@PathVariable int id, Model model) { Optional<Category> category =
 * categoryService.getCategoryById(id); if (category.isPresent()) {
 * model.addAttribute("category", category.get()); return
 * "category/categoriesAdd"; } else { return "404"; } }
 * 
 * // author Section
 * 
 * @GetMapping("/admin-page/authors") public String getAuthorsPage(Model
 * model, @RequestParam(defaultValue = "0") int page) { int pageSize = 3;
 * Page<Author> authorPage = authorService.getAllAuthors(PageRequest.of(page,
 * pageSize)); model.addAttribute("authors", authorPage.getContent());
 * model.addAttribute("currentPage", page); model.addAttribute("totalPages",
 * authorPage.getTotalPages()); return "author/authors"; }
 * 
 * @GetMapping("/admin-page/authors/add") public String getAddAuthor(Model
 * model) { model.addAttribute("author", new Author()); return
 * "author/authorAdd"; }
 * 
 * @PostMapping("/admin-page/authors/add") public String
 * postAddAuthor(@ModelAttribute("author") Author author) {
 * authorService.addAuthor(author); return "redirect:/admin-page/authors"; }
 * 
 * @GetMapping("/admin-page/authors/delete/{id}") public String
 * deleteAuthor(@PathVariable int id) { authorService.removeAuthorById(id);
 * return "redirect:/admin/authors"; }
 * 
 * @GetMapping("/admin-page/authors/update/{id}") public String
 * updateAuthor(@PathVariable int id, Model model) { Optional<Author> author =
 * authorService.getAuthorById(id); if (author.isPresent()) {
 * model.addAttribute("author", author.get()); return "author/authorAdd"; } else
 * { return "404"; } }
 * 
 * @GetMapping("/admin-page/books") public String books(Model
 * model, @RequestParam(defaultValue = "0") int page) { int pageSize = 2;
 * Pageable pageable = PageRequest.of(page, pageSize); Page<Book> bookPage =
 * bookService.getAllBooks(pageable); model.addAttribute("books",
 * bookPage.getContent()); model.addAttribute("currentPage", page);
 * model.addAttribute("totalPages", bookPage.getTotalPages());
 * 
 * return "book/books"; }
 * 
 * @GetMapping("/admin-page/books/add") public String productAddGet(Model model)
 * { model.addAttribute("bookDTO", new BookDTO());
 * model.addAttribute("categories", categoryService.getAllCategory());
 * model.addAttribute("authors", authorService.getAllAuthor()); return
 * "book/booksAdd"; }
 * 
 * private static String uploadDir = System.getProperty("user.dir") +
 * "/src/main/resources/static/bookImages";
 * 
 * @PostMapping("/admin-page/books/add") public String
 * productAddPost(@ModelAttribute("bookDTO") BookDTO bookDTO,
 * 
 * @RequestParam("bookImage") MultipartFile file, @RequestParam("imgName")
 * String imgName) throws IOException {
 * 
 * Book book = new Book(); book.setBookId(bookDTO.getBookId());
 * book.setName(bookDTO.getName());
 * book.setAuthor(authorService.getAuthorById(bookDTO.getAuthorId()).get());
 * book.setCategory(categoryService.getCategoryById(bookDTO.getCategoryId()).get
 * ()); book.setPrice(bookDTO.getPrice()); book.setWeight(bookDTO.getWeight());
 * book.setQuantity(bookDTO.getQuantity()); book.setIsbn(bookDTO.getIsbn());
 * book.setDescription(bookDTO.getDescription()); String imageUUID; if
 * (!file.isEmpty()) { imageUUID = file.getOriginalFilename(); Path
 * fileNameAndPath = Paths.get(uploadDir, imageUUID);
 * Files.write(fileNameAndPath, file.getBytes());
 * 
 * } else { imageUUID = imgName;
 * 
 * } book.setImageName(imageUUID); bookService.addBook(book); return
 * "redirect:/admin-page/books"; }
 * 
 * @GetMapping("/admin-page/books/delete/{id}") public String
 * deleteBook(@PathVariable long id) { bookService.removeBookById(id); return
 * "redirect:/admin-page/books"; }
 * 
 * @GetMapping("/admin-page/books/update/{id}") public String
 * updateBookGet(@PathVariable long id, Model model) { Book book =
 * bookService.getBookById(id).get(); BookDTO bookDTO = new BookDTO();
 * bookDTO.setBookId(book.getBookId()); bookDTO.setName(book.getName());
 * bookDTO.setIsbn(book.getIsbn());
 * bookDTO.setAuthorId(book.getAuthor().getId());
 * bookDTO.setCategoryId(book.getCategory().getId());
 * bookDTO.setPrice(book.getPrice()); bookDTO.setQuantity(book.getQuantity());
 * bookDTO.setWeight(book.getWeight());
 * bookDTO.setDescription(book.getDescription());
 * bookDTO.setImageName(book.getImageName());
 * 
 * model.addAttribute("categories", categoryService.getAllCategory());
 * model.addAttribute("authors", authorService.getAllAuthor());
 * model.addAttribute("bookDTO", bookDTO);
 * 
 * return "book/booksAdd"; }
 * 
 * // order section
 * 
 * @GetMapping("/admin-page/updateOrderStatus") public String
 * showOrderList(Model model, @RequestParam(defaultValue = "0") int page) {
 * Page<PlaceOrder> pagedOrders = orderService.getOrders(PageRequest.of(page,
 * 3)); // Change 10 to the desired page // size
 * 
 * model.addAttribute("pagedOrders", pagedOrders);
 * model.addAttribute("currentPage", pagedOrders.getNumber());
 * model.addAttribute("totalPages", pagedOrders.getTotalPages());
 * model.addAttribute("orderCount", pagedOrders.getTotalElements());
 * 
 * return "admin/ordersList"; }
 * 
 * @PostMapping("/admin-page/updateOrderStatus") public String
 * updateOrderStatus(@RequestParam("orderId") Long
 * orderId, @RequestParam("status") String status) { Optional<PlaceOrder>
 * optionalOrder = orderRepository.findById(orderId); if
 * (optionalOrder.isPresent()) { PlaceOrder order = optionalOrder.get(); String
 * uppercaseStatus = status.toUpperCase(); switch (uppercaseStatus) { case
 * "CANCELLED": order.setOrderStatus(OrderStatus.CANCELLED); break; case
 * "CONFIRMED": order.setOrderStatus(OrderStatus.CONFIRMED); break; default:
 * return "redirect:/admin-page/updateOrderStatus"; }
 * orderRepository.save(order); } return
 * "redirect:/admin-page/updateOrderStatus"; }
 * 
 * @GetMapping("/admin-page/canceledOrders") public String showAllOrders(Model
 * model) { List<PlaceOrder> allOrders = orderRepository.findAll();
 * List<PlaceOrder> canceledOrders =
 * orderRepository.findByOrderStatus(OrderStatus.CANCELLED); List<PlaceOrder>
 * confirmedOrders = orderRepository.findByOrderStatus(OrderStatus.CONFIRMED);
 * 
 * model.addAttribute("allOrders", allOrders);
 * model.addAttribute("canceledOrders", canceledOrders);
 * model.addAttribute("confirmedOrders", confirmedOrders);
 * 
 * return "admin/canceledOrdersList"; }
 * 
 * @GetMapping("/admin-page/orderhistory") public String showOrderHistory(Model
 * model, @RequestParam(defaultValue = "0") int page) { try { Page<PlaceOrder>
 * pagedOrders = orderService.getOrders(PageRequest.of(page, 5));
 * 
 * model.addAttribute("pagedOrders", pagedOrders);
 * model.addAttribute("currentPage", page); model.addAttribute("totalPages",
 * pagedOrders.getTotalPages());
 * 
 * return "admin/orderHistory"; } catch (Exception e) { return "error"; } }
 * 
 * // User Section
 * 
 * @GetMapping("/admin-page/userlist") public String showAllUsers(Model
 * model, @RequestParam(defaultValue = "0") int page) { int pageSize = 10;
 * Pageable pageable = PageRequest.of(page, pageSize); Page<User> usersPage =
 * userService.getAllUsersPageable(pageable); model.addAttribute("usersPage",
 * usersPage); return "admin/userList"; }
 * 
 * @GetMapping("/admin-page/edit-user/{id}") public String editUser(Model
 * model, @PathVariable("id") Long userId) { User user =
 * userService.getUserById(userId); model.addAttribute("id", userId);
 * model.addAttribute("user", user); return "admin/editUser"; }
 * 
 * @PostMapping("/admin-page/update-user/{id}") public String
 * editUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
 * userService.updateUser(user);
 * 
 * return "redirect:/admin-page/userlist"; }
 * 
 * @GetMapping("/admin-page/users/delete/{id}") public String
 * deleteUser(@PathVariable("id") Long id) { userService.removeUserById(id);
 * return "redirect:/admin-page/userlist"; }
 * 
 * // profile section
 * 
 * @GetMapping("/admin-page/profile/page") public String customer(Model model,
 * Principal principal) { User customer =
 * userService.findByEmail(principal.getName()); model.addAttribute("user",
 * customer); return "admin/adminprofilePage"; }
 * 
 * @GetMapping("/admin-page/profile/edit") public String editProfile(Model
 * model, Principal principal) { User user =
 * userService.findByEmail(principal.getName()); model.addAttribute("user",
 * user); return "admin/adminEditProfile"; }
 * 
 * @PostMapping("/admin-page/profile/edit") public String updateUser(Long
 * id, @ModelAttribute("user") User user) { userService.update(user);
 * 
 * return "redirect:/admin-page/profile/page"; }
 * 
 * }
 */

package com.booke.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.hibernate.query.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.booke.dto.BookDTO;
import com.booke.model.Author;
import com.booke.model.Book;
import com.booke.model.Category;
import com.booke.model.OrderStatus;
import com.booke.model.PlaceOrder;
import com.booke.model.User;
import com.booke.repository.BookRepository;
import com.booke.repository.CategoryRepository;
import com.booke.repository.OrderRepository;
import com.booke.service.AuthorService;
import com.booke.service.BookService;
import com.booke.service.CategoryService;
import com.booke.service.OrderService;
import com.booke.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AdminControlller {

	private static final Logger logger = LoggerFactory.getLogger(AdminControlller.class);

	@Autowired
	CategoryService categoryService;

	@Autowired
	BookService bookService;

	@Autowired
	UserService userService;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	AuthorService authorService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	OrderService orderService;

	private int PAGE_SIZE;

	// Category Section
	@GetMapping("/admin-page/categories")
	public String showCategories(Model model, @RequestParam(defaultValue = "0") int page) {
		try {
			logger.info("Fetching categories for page: {}", page);
			Page<Category> categoryPage = categoryService.findAll(PageRequest.of(page, 4));
			model.addAttribute("categories", categoryPage.getContent());
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", categoryPage.getTotalPages());
			return "category/categories";
		} catch (Exception e) {
			logger.error("An error occurred while fetching categories: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/categories/add")
	public String getCatAdd(Model model) {
		logger.info("Rendering category add form");
		model.addAttribute("category", new Category());
		return "category/categoriesAdd";
	}

	@PostMapping("/admin-page/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category) {
		try {
			logger.info("Adding category: {}", category.getName());
			categoryService.addCategory(category);
			return "redirect:/admin-page/categories";
		} catch (Exception e) {
			logger.error("An error occurred while adding category: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/categories/delete/{id}")
	public String deleteCat(@PathVariable int id) {
		try {
			logger.info("Deleting category with ID: {}", id);
			categoryService.removeCategoryById(id);
			return "redirect:/admin/categories";
		} catch (Exception e) {
			logger.error("An error occurred while deleting category: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model model) {
		try {
			logger.info("Rendering category update form for ID: {}", id);
			Optional<Category> category = categoryService.getCategoryById(id);
			if (category.isPresent()) {
				model.addAttribute("category", category.get());
				return "category/categoriesAdd";
			} else {
				logger.error("Category with ID {} not found", id);
				return "404";
			}
		} catch (Exception e) {
			logger.error("An error occurred while updating category: {}", e.getMessage());
			return "error";
		}
	}

	// Author Section
	@GetMapping("/admin-page/authors")
	public String getAuthorsPage(Model model, @RequestParam(defaultValue = "0") int page) {
		try {
			logger.info("Fetching authors for page: {}", page);
			int pageSize = 3;
			Page<Author> authorPage = authorService.getAllAuthors(PageRequest.of(page, pageSize));
			model.addAttribute("authors", authorPage.getContent());
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", authorPage.getTotalPages());
			return "author/authors";
		} catch (Exception e) {
			logger.error("An error occurred while fetching authors: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/authors/add")
	public String getAddAuthor(Model model) {
		logger.info("Rendering author add form");
		model.addAttribute("author", new Author());
		return "author/authorAdd";
	}

	@PostMapping("/admin-page/authors/add")
	public String postAddAuthor(@ModelAttribute("author") Author author) {
		try {
			logger.info("Adding author: {}", author.getName());
			authorService.addAuthor(author);
			return "redirect:/admin-page/authors";
		} catch (Exception e) {
			logger.error("An error occurred while adding author: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/authors/delete/{id}")
	public String deleteAuthor(@PathVariable int id) {
		try {
			logger.info("Deleting author with ID: {}", id);
			authorService.removeAuthorById(id);
			return "redirect:/admin/authors";
		} catch (Exception e) {
			logger.error("An error occurred while deleting author: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/authors/update/{id}")
	public String updateAuthor(@PathVariable int id, Model model) {
		try {
			logger.info("Rendering author update form for ID: {}", id);
			Optional<Author> author = authorService.getAuthorById(id);
			if (author.isPresent()) {
				model.addAttribute("author", author.get());
				return "author/authorAdd";
			} else {
				logger.error("Author with ID {} not found", id);
				return "404";
			}
		} catch (Exception e) {
			logger.error("An error occurred while updating author: {}", e.getMessage());
			return "error";
		}
	}

	// Book Section
	@GetMapping("/admin-page/books")
	public String books(Model model, @RequestParam(defaultValue = "0") int page) {
		try {
			logger.info("Fetching books for page: {}", page);
			int pageSize = 2;
			Pageable pageable = PageRequest.of(page, pageSize);
			Page<Book> bookPage = bookService.getAllBooks(pageable);
			model.addAttribute("books", bookPage.getContent());
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", bookPage.getTotalPages());
			return "book/books";
		} catch (Exception e) {
			logger.error("An error occurred while fetching books: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/books/add")
	public String productAddGet(Model model) {
		logger.info("Rendering book add form");
		model.addAttribute("bookDTO", new BookDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("authors", authorService.getAllAuthor());
		return "book/booksAdd";
	}

	private static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/bookImages";

	@PostMapping("/admin-page/books/add")
	public String productAddPost(@ModelAttribute("bookDTO") BookDTO bookDTO,
			@RequestParam("bookImage") MultipartFile file, @RequestParam("imgName") String imgName) throws IOException {
		try {
			logger.info("Adding book: {}", bookDTO.getName());
			Book book = new Book();
			book.setBookId(bookDTO.getBookId());
			book.setName(bookDTO.getName());
			book.setAuthor(authorService.getAuthorById(bookDTO.getAuthorId()).get());
			book.setCategory(categoryService.getCategoryById(bookDTO.getCategoryId()).get());
			book.setPrice(bookDTO.getPrice());
			book.setWeight(bookDTO.getWeight());
			book.setQuantity(bookDTO.getQuantity());
			book.setIsbn(bookDTO.getIsbn());
			book.setDescription(bookDTO.getDescription());
			String imageUUID;
			if (!file.isEmpty()) {
				imageUUID = file.getOriginalFilename();
				Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
				Files.write(fileNameAndPath, file.getBytes());
			} else {
				imageUUID = imgName;
			}
			book.setImageName(imageUUID);
			bookService.addBook(book);
			return "redirect:/admin-page/books";
		} catch (Exception e) {
			logger.error("An error occurred while adding book: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/books/delete/{id}")
	public String deleteBook(@PathVariable long id) {
		try {
			logger.info("Deleting book with ID: {}", id);
			bookService.removeBookById(id);
			return "redirect:/admin-page/books";
		} catch (Exception e) {
			logger.error("An error occurred while deleting book: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/books/update/{id}")
	public String updateBookGet(@PathVariable long id, Model model) {
		try {
			logger.info("Rendering book update form for ID: {}", id);
			Book book = bookService.getBookById(id).get();
			BookDTO bookDTO = new BookDTO();
			bookDTO.setBookId(book.getBookId());
			bookDTO.setName(book.getName());
			bookDTO.setIsbn(book.getIsbn());
			bookDTO.setAuthorId(book.getAuthor().getId());
			bookDTO.setCategoryId(book.getCategory().getId());
			bookDTO.setPrice(book.getPrice());
			bookDTO.setQuantity(book.getQuantity());
			bookDTO.setWeight(book.getWeight());
			bookDTO.setDescription(book.getDescription());
			bookDTO.setImageName(book.getImageName());

			model.addAttribute("categories", categoryService.getAllCategory());
			model.addAttribute("authors", authorService.getAllAuthor());
			model.addAttribute("bookDTO", bookDTO);

			return "book/booksAdd";
		} catch (Exception e) {
			logger.error("An error occurred while rendering book update form: {}", e.getMessage());
			return "error";
		}
	}

	// order section

	@GetMapping("/admin-page/updateOrderStatus")
	public String showOrderList(Model model, @RequestParam(defaultValue = "0") int page) {
		try {
			logger.info("Fetching orders for page: {}", page);
			Page<PlaceOrder> pagedOrders = orderService.getOrders(PageRequest.of(page, 3)); // Change 10 to the desired
																							// page size

			model.addAttribute("pagedOrders", pagedOrders);
			model.addAttribute("currentPage", pagedOrders.getNumber());
			model.addAttribute("totalPages", pagedOrders.getTotalPages());
			model.addAttribute("orderCount", pagedOrders.getTotalElements());

			return "admin/ordersList";
		} catch (Exception e) {
			logger.error("An error occurred while fetching orders: {}", e.getMessage());
			return "error";
		}
	}

	@PostMapping("/admin-page/updateOrderStatus")
	public String updateOrderStatus(@RequestParam("orderId") Long orderId, @RequestParam("status") String status) {
		try {
			Optional<PlaceOrder> optionalOrder = orderRepository.findById(orderId);
			if (optionalOrder.isPresent()) {
				PlaceOrder order = optionalOrder.get();
				String uppercaseStatus = status.toUpperCase();
				switch (uppercaseStatus) {
				case "CANCELLED":
					order.setOrderStatus(OrderStatus.CANCELLED);
					break;
				case "CONFIRMED":
					order.setOrderStatus(OrderStatus.CONFIRMED);
					break;
				default:
					return "redirect:/admin-page/updateOrderStatus";
				}
				orderRepository.save(order);
			}
			return "redirect:/admin-page/updateOrderStatus";
		} catch (Exception e) {
			logger.error("An error occurred while updating order status: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/canceledOrders")
	public String showAllOrders(Model model) {
		try {
			logger.info("Fetching all orders and canceled orders");
			List<PlaceOrder> allOrders = orderRepository.findAll();
			List<PlaceOrder> canceledOrders = orderRepository.findByOrderStatus(OrderStatus.CANCELLED);
			List<PlaceOrder> confirmedOrders = orderRepository.findByOrderStatus(OrderStatus.CONFIRMED);

			model.addAttribute("allOrders", allOrders);
			model.addAttribute("canceledOrders", canceledOrders);
			model.addAttribute("confirmedOrders", confirmedOrders);

			return "admin/canceledOrdersList";
		} catch (Exception e) {
			logger.error("An error occurred while fetching orders: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/orderhistory")
	public String showOrderHistory(Model model, @RequestParam(defaultValue = "0") int page) {
		try {
			logger.info("Fetching order history for page: {}", page);
			Page<PlaceOrder> pagedOrders = orderService.getOrders(PageRequest.of(page, 5));

			model.addAttribute("pagedOrders", pagedOrders);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", pagedOrders.getTotalPages());

			return "admin/orderHistory";
		} catch (Exception e) {
			logger.error("An error occurred while fetching order history: {}", e.getMessage());
			return "error";
		}
	}

	// User Section
	@GetMapping("/admin-page/userlist")
	public String showAllUsers(Model model, @RequestParam(defaultValue = "0") int page) {
		try {
			logger.info("Fetching users for page: {}", page);
			int pageSize = 10;
			Pageable pageable = PageRequest.of(page, pageSize);
			Page<User> usersPage = userService.getAllUsersPageable(pageable);
			model.addAttribute("usersPage", usersPage);
			return "admin/userList";
		} catch (Exception e) {
			logger.error("An error occurred while fetching users: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/edit-user/{id}")
	public String editUser(Model model, @PathVariable("id") Long userId) {
		try {
			logger.info("Rendering edit user form for user ID: {}", userId);
			User user = userService.getUserById(userId);
			model.addAttribute("id", userId);
			model.addAttribute("user", user);
			return "admin/editUser";
		} catch (Exception e) {
			logger.error("An error occurred while rendering edit user form: {}", e.getMessage());
			return "error";
		}
	}

	@PostMapping("/admin-page/update-user/{id}")
	public String editUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
		try {
			logger.info("Updating user with ID: {}", id);
			userService.updateUser(user);
			return "redirect:/admin-page/userlist";
		} catch (Exception e) {
			logger.error("An error occurred while updating user: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/users/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		try {
			logger.info("Deleting user with ID: {}", id);
			userService.removeUserById(id);
			return "redirect:/admin-page/userlist";
		} catch (Exception e) {
			logger.error("An error occurred while deleting user: {}", e.getMessage());
			return "error";
		}
	}

	// profile section
	@GetMapping("/admin-page/profile/page")
	public String customer(Model model, Principal principal) {
		try {
			logger.info("Rendering admin profile page");
			User customer = userService.findByEmail(principal.getName());
			model.addAttribute("user", customer);
			return "admin/adminprofilePage";
		} catch (Exception e) {
			logger.error("An error occurred while rendering admin profile page: {}", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/admin-page/profile/edit")
	public String editProfile(Model model, Principal principal) {
		try {
			logger.info("Rendering admin profile edit form");
			User user = userService.findByEmail(principal.getName());
			model.addAttribute("user", user);
			return "admin/adminEditProfile";
		} catch (Exception e) {
			logger.error("An error occurred while rendering admin profile edit form: {}", e.getMessage());
			return "error";
		}
	}

	@PostMapping("/admin-page/profile/edit")
	public String updateUser(Long id, @ModelAttribute("user") User user) {
		try {
			logger.info("Updating admin profile");
			userService.update(user);
			return "redirect:/admin-page/profile/page";
		} catch (Exception e) {
			logger.error("An error occurred while updating admin profile: {}", e.getMessage());
			return "error";
		}
	}
}
