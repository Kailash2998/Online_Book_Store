/*
 * package com.booke.controller;
 * 
 * import java.security.Principal; import java.util.List; import
 * java.util.Optional;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.data.domain.Page; import
 * org.springframework.data.domain.PageRequest; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.validation.BindingResult; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.ModelAttribute; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam;
 * 
 * import com.booke.model.Book; import com.booke.model.Category; import
 * com.booke.model.User; import com.booke.repository.BookRepository; import
 * com.booke.repository.UserRepository; import com.booke.service.AuthorService;
 * import com.booke.service.BookService; import
 * com.booke.service.CategoryService; import com.booke.service.UserService;
 * 
 * import jakarta.validation.Valid;
 * 
 * @Controller
 * 
 * @RequestMapping("/customer") public class CustomerController {
 * 
 * @Autowired private UserRepository userRepository;
 * 
 * @Autowired private UserService userService;
 * 
 * @Autowired private BookService bookService;
 * 
 * @Autowired CategoryService categoryService;
 * 
 * @Autowired BookRepository bookRepository;
 * 
 * @Autowired AuthorService authorService;
 * 
 * @ModelAttribute public void commonUser(Principal p, Model m) { if (p != null)
 * { String email = p.getName(); User user = userRepository.findByEmail(email);
 * m.addAttribute("user", user); } }
 * 
 * @GetMapping("/profile") public String customer(Model
 * model, @RequestParam(defaultValue = "0") int page,
 * 
 * @RequestParam(defaultValue = "20") int size) { Page<Book> books =
 * bookService.getAllBooks(PageRequest.of(page, size));
 * model.addAttribute("categories", categoryService.getAllCategory());
 * model.addAttribute("authors", authorService.getAllAuthor());
 * model.addAttribute("books", books); return "user/user"; }
 * 
 * @GetMapping("/profile/category/{id}") public String shopByCategory(Model
 * model, @PathVariable int id, @RequestParam(defaultValue = "0") int page,
 * 
 * @RequestParam(defaultValue = "20") int size) { Page<Book> books =
 * bookService.getAllBookByCategory(PageRequest.of(page, size), id);
 * model.addAttribute("categories", categoryService.getAllCategory());
 * model.addAttribute("books", books); return "user/user"; }
 * 
 * @GetMapping("/profile/viewbook/{id}") public String viewBook(Model
 * model, @PathVariable("id") Long id) { Optional<Book> optionalBook =
 * bookService.getBookById(id);
 * 
 * if (optionalBook.isPresent()) { Book book = optionalBook.get();
 * model.addAttribute("book", book); } else { model.addAttribute("errorMessage",
 * "Book not found"); return "errorPage"; }
 * 
 * return "user/viewBook"; }
 * 
 * @GetMapping("/profile/search") public String
 * searchBooks(@RequestParam("keyword") String keyword, Model model) {
 * List<Book> books = bookService.searchBooks(keyword);
 * model.addAttribute("books", books); return "user/search-results"; // Return
 * the view name }
 * 
 * @GetMapping("/profile/page") public String customer(Model model, Principal
 * principal) { User customer = userService.findByEmail(principal.getName());
 * model.addAttribute("user", customer); return "user/profilePage"; }
 * 
 * @GetMapping("/profile/edit") public String editProfile(Model model, Principal
 * principal) { User user = userService.findByEmail(principal.getName());
 * model.addAttribute("user", user); return "user/editProfile"; }
 * 
 * @PostMapping("/profile/edit") public String editUser(Long
 * id, @ModelAttribute("user") User user) { userService.updateUser(user); return
 * "redirect:/customer/profile/page"; }
 * 
 * }
 */

package com.booke.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.booke.model.Book;
import com.booke.model.Category;
import com.booke.model.User;
import com.booke.repository.BookRepository;
import com.booke.repository.UserRepository;
import com.booke.service.AuthorService;
import com.booke.service.BookService;
import com.booke.service.CategoryService;
import com.booke.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	AuthorService authorService;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepository.findByEmail(email);
			m.addAttribute("user", user);
		}
	}

	@GetMapping("/profile")
	public String customer(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		logger.info("Displaying customer profile page");
		Page<Book> books = bookService.getAllBooks(PageRequest.of(page, size));
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("authors", authorService.getAllAuthor());
		model.addAttribute("books", books);
		return "user/user";
	}

	@GetMapping("/profile/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		logger.info("Displaying books by category with ID: {}", id);
		Page<Book> books = bookService.getAllBookByCategory(PageRequest.of(page, size), id);
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("books", books);
		return "user/user";
	}

	@GetMapping("/profile/viewbook/{id}")
	public String viewBook(Model model, @PathVariable("id") Long id) {
		logger.info("Viewing book with ID: {}", id);
		Optional<Book> optionalBook = bookService.getBookById(id);

		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();
			model.addAttribute("book", book);
		} else {
			model.addAttribute("errorMessage", "Book not found");
			logger.error("Book not found with ID: {}", id);
			return "errorPage";
		}

		return "user/viewBook";
	}

	@GetMapping("/profile/search")
	public String searchBooks(@RequestParam("keyword") String keyword, Model model) {
		logger.info("Searching for books with keyword: {}", keyword);
		List<Book> books = bookService.searchBooks(keyword);
		model.addAttribute("books", books);
		return "user/search-results"; // Return the view name
	}

	@GetMapping("/profile/page")
	public String customerProfile(Model model, Principal principal) {
		logger.info("Displaying customer profile page");
		User customer = userService.findByEmail(principal.getName());
		model.addAttribute("user", customer);
		return "user/profilePage";
	}

	@GetMapping("/profile/edit")
	public String editProfile(Model model, Principal principal) {
		logger.info("Displaying edit profile page for user: {}", principal.getName());
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("user", user);
		return "user/editProfile";
	}

	@PostMapping("/profile/edit")
	public String editUser(Long id, @ModelAttribute("user") User user) {
		logger.info("Editing profile for user with ID: {}", id);
		userService.updateUser(user);
		return "redirect:/customer/profile/page";
	}

}
